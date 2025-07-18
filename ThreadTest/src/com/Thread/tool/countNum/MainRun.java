package com.Thread.tool.countNum;

public class MainRun {
    public static void main(String[] args) {
        Thread person_1 = new ThreadMain("@1");
        Thread person_2 = new ThreadMain("@2");
        person_2.start();
        person_1.start();
    }
}
