package com.Thread.tool.countNum;

public class ThreadMain extends Thread {
    static int num = 1;

    ThreadMain(String name) {
        super(name);
    }
    @Override
    public void run() {
        while (true) {
            synchronized (ThreadMain.class) {
                if (num > 100) {break;}
                if (num % 2 != 0) {System.out.println(num);}
                num++;
            }
        }
    }
}
