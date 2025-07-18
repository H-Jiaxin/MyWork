package com.Thread.tool.RedPack;

import com.Thread.tool.countNum.ThreadMain;

import java.util.Random;

public class Person extends Thread {

    Person(String name) {
        super(name);
    }

    static int amount = 100;
    Random random = new Random();
    @Override
    public void run() {
        synchronized (ThreadMain.class) {
            if (amount <= 0) {
                System.out.println(getName() + " have not get RedPack");
                return;
            }
            int getAmount = random.nextInt(10) + 30;
            if (getAmount > amount) {getAmount = amount;}
            amount = amount - getAmount;
            System.out.println(getName() + " get redPack amount = " + getAmount);
        }
    }
}
