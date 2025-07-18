package com.Thread.tool.ThreadInstance_3;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Demo demo = new Demo();
        FutureTask<String> futureTask = new FutureTask<String>(demo);
        Thread thread = new Thread(futureTask);
        thread.start();
        String res = futureTask.get();
        System.out.println(res);
    }
}
