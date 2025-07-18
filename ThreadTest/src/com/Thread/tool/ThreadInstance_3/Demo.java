package com.Thread.tool.ThreadInstance_3;

import java.util.Date;
import java.util.concurrent.Callable;

public class Demo implements Callable<String> {
    @Override
    public String call() throws Exception {
        StringBuilder res = new StringBuilder(Thread.currentThread().getName() + "<UNK>" + '\n');
        for (int i = 0; i < 10; i++) {
            res.append(new Date().getTime());
            res.append('\n');
            Thread.sleep(1000);
        }
        return res.toString();
    }
}
