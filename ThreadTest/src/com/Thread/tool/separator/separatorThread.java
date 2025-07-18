package com.Thread.tool.separator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class separatorThread extends Thread {
    static ArrayList<Integer> banner = new  ArrayList<Integer>(12);
    static public Integer THREAD_MAX = 0;
    static public String  THREAD_NAME = "";

    public separatorThread(String name) {
        super(name);
    }

    private int num = 0;
    private final List<Integer> getBonus = new ArrayList<>();

    @Override
    public void run() {
        Integer sum = 0;
        Integer MAX = 0;
        while (true) {
            synchronized (separatorThread.class) {
                if (banner.isEmpty()) {break;}
                Integer get = banner.remove(new Random().nextInt(banner.size()));
                sum += get;
                getBonus.add(get);
                num++;
                System.out.println(getName() + " 产生一个" + get + " 大奖");
                if (get > MAX) MAX  = get;
            }
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        System.out.println(getName() + "本次共产生 " + num + " 个奖项" + ": " + getBonus + " 最高为 " + MAX + " 总和 " + sum);
        if (THREAD_MAX < MAX) {
            THREAD_MAX = MAX;
            THREAD_NAME = getName();
        }
    }

}
