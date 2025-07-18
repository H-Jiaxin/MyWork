package com.Thread.tool.ThreadInstance_2;

public class test {
    public static void main(String[] args) {
        Thread thread = new Thread(new Demo());
        thread.setName("Instance_2");
        thread.start();
    }
}
