package com.Thread.tool.SellTicket;

public class ThreadInstance extends Thread {
    static int ticketNum = 1000;
    ThreadInstance(String threadName) {
        super(threadName);
    }
    final static Object lock = new Object();
    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                if (ticketNum < 1) {break;}
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                ticketNum = ticketNum - 1;
                System.out.println(getName() + ": sell ticket, now remain:" + ticketNum);
            }
        }
    }
}
