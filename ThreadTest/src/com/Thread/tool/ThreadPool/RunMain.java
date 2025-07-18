package com.Thread.tool.ThreadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class RunMain {
    /**
     * 一个抢红包的线程池使用示例
     */
    public static void main(String[] args) throws InterruptedException {
        // 创建线程池
        MyThreadPool MP =  new MyThreadPool(6,8, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
        for (int i = 0; i < 18; i++) {
            MyTask task = new MyTask("user_" + i);
            MP.execute(task);
//            String name = i+"";
//            MP.execute(() -> {
//                System.out.println(Thread.currentThread().getName() + " user_" + name + " run you service");
//            });
            sleep(500);
        }

        MP.shutdown(); // 等待任务执行完毕再退出
//        System.out.println("剩余任务 " + MP.shutdownNow().size()); //强制退出
//        boolean f = MP.awaitTermination(60, TimeUnit.SECONDS);  // 等待任务结束
//        if (f) {
//            System.out.println("luck man " + MyTask.LUCK + " amount " + MyTask.MAX);
//        }
    }
}
