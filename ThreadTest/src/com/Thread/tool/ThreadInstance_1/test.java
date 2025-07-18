package com.Thread.tool.ThreadInstance_1;

public class test {
    public static void main(String[] args) {
        Thread test1 = new Demo();
        Thread test2 = new Demo();
        test1.setName("test1");
        test2.setName("test2");
        test1.start();
        test2.start();
    }
}
