package com.Thread.tool.ThreadInstance_1;

import java.util.Date;
import java.util.TimeZone;

public class Demo extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            Date date = new Date();
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(getName() + "<UNK>" + date.getTime());
        }
    }
}
