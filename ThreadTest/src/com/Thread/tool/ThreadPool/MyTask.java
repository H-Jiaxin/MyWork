package com.Thread.tool.ThreadPool;

import com.Thread.tool.countNum.ThreadMain;

import java.util.Random;

import static java.lang.Thread.sleep;

public class MyTask implements Runnable {
    static int amount = 500;
    static int MAX = 0;
    static String LUCK = "";
    Random random = new Random();
    String name = "";

    MyTask(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        synchronized (ThreadMain.class) {
            if (amount <= 0) {
                System.out.println(name + " have not get RedPack");
                return;
            }
            int getAmount = random.nextInt(10) + 30;
            getAmount = Math.min(amount, getAmount);
            amount = amount - getAmount;
            System.out.println(name + " get redPack amount = " + getAmount);
            if (MAX < getAmount) {
                MAX = getAmount;
                LUCK = name;
            }
        }
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
