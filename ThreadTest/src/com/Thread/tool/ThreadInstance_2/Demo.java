package com.Thread.tool.ThreadInstance_2;

import java.util.Date;

public class Demo implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {

            System.out.println(Thread.currentThread().getName() + "<UNK>" + new Date().getTime());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
