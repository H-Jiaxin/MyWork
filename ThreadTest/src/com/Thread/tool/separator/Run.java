package com.Thread.tool.separator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.Thread.tool.separator.separatorThread.*;

public class Run {
    public static void main(String[] args) throws InterruptedException {
        Collections.addAll(banner, 10, 5, 20, 50, 100, 200, 500, 800, 2, 80, 300, 700);
        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < 5; i++) {
            String name = "box_" + i;
            Thread thread = new separatorThread(name);
            thread.start();
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.join();
        }


        System.out.println("在" + THREAD_NAME + "中产生了最大奖" + THREAD_MAX);
    }
}
