package com.Thread.tool.SellTicket;

public class main {
    public static void main(String[] args) {
        Thread windows_1 = new ThreadInstance("@1");
        Thread windows_2 = new ThreadInstance("@2");

        windows_1.start();
        windows_2.start();
    }
}
