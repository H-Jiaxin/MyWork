package com.Thread.tool.ThreadPool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class MyThreadPool {
    private final int MAX_CORE_POOL_SIZE;
    private final int MAX_POOL_SIZE;
    private final int KEEP_ALIVE_TIME;
    private final TimeUnit TIME_UNIT;
    private final BlockingQueue<Runnable> workQueue;

    private final ArrayList<Thread> Threads = new ArrayList<>();
    private final Set<Thread> activeThread = Collections.synchronizedSet(new HashSet<Thread>());
    public boolean isShutdown = false;
    private void Before(Thread t) {
        this.activeThread.add(t);
    }

    private void After(Thread t) {
        this.activeThread.remove(t);
    }

    // 创建核心线程
    MyThreadPool(int MaxCorePoolSize, int MaxPoolSize, int KeepAliveTime, TimeUnit TimeUnit, BlockingQueue<Runnable> workQueue) {
        this.MAX_CORE_POOL_SIZE = MaxCorePoolSize;
        this.MAX_POOL_SIZE = MaxPoolSize;
        this.KEEP_ALIVE_TIME = KeepAliveTime;
        this.TIME_UNIT = TimeUnit;
        this.workQueue = workQueue;
    }

    private synchronized boolean addTask(Runnable r, boolean core) {
        int bound = core ? this.MAX_CORE_POOL_SIZE : this.MAX_POOL_SIZE;
        if (Threads.size() >= bound) return false;
        Thread t = new Thread(() -> {
            try {
                // 首先执行当前任务
                Before(Thread.currentThread());
                r.run();
                After(Thread.currentThread());
                // 然后循环消费队列任务
                while (!Thread.currentThread().isInterrupted() && (!this.isShutdown || !this.workQueue.isEmpty())) {
                    // 等待工作队列完成后再退出
                    Runnable task;
                    if (core) {
                        task = workQueue.take(); // 核心线程一直等待
                    } else {
                        task = workQueue.poll(KEEP_ALIVE_TIME, TIME_UNIT);
                        if (task == null) break; // 超时无需继续
                    }
                    Before(Thread.currentThread());
                    task.run();
                    After(Thread.currentThread());
                }
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            } finally {
                synchronized (this) {
                    Threads.remove(Thread.currentThread());
                }
            }
        });
        t.start();
        Threads.add(t);
        return true;
    }

    public void execute(Runnable task) {
        if (this.isShutdown) {
            throw new RejectedExecutionException("ThreadPool is shutdown.");
        } else if (this.Threads.size() >= this.MAX_CORE_POOL_SIZE || !addTask(task, true)) {
            if (!this.workQueue.offer(task)) {
                if (this.Threads.size() < this.MAX_POOL_SIZE) {
                    this.addTask(task, false);
                } else {
                    throw new RuntimeException("阻塞队列添加任务失败");
                }
            }
        }
    }

    public void shutdown() {
        this.isShutdown = true;

        for(Thread t : this.Threads) {
            if (!this.activeThread.contains(t)) {
                t.interrupt();
            }
        }
    }

    public synchronized java.util.List<Runnable> shutdownNow() {
        isShutdown = true;

        // 1. 中断所有线程
        for (Thread t : Threads) {
            t.interrupt(); // 会中断正在执行或阻塞中的线程
        }

        // 2. 清空队列中的未执行任务
        java.util.List<Runnable> notExecuted = new ArrayList<>();
        workQueue.drainTo(notExecuted); // drainTo 会清空队列并转移到列表中
        return notExecuted;
    }


    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        long endTime = System.nanoTime() + unit.toNanos(timeout);
        while (true) {
            synchronized (this) {
                if (Threads.isEmpty()) return true;
            }
            if (System.nanoTime() >= endTime) return false;
            Thread.sleep(10L); // 稍等再检查
        }
    }
}