package com.Thread.tool.RedPack;

public class Run {
    public static void main(String[] args) {
        Thread person_1 = new Person("@1");
        Thread person_2 = new Person("@2");
        Thread person_3 = new Person("@3");
        Thread person_4 = new Person("@4");
        Thread person_5 = new Person("@5");

        person_1.start();
        person_2.start();
        person_3.start();
        person_4.start();
        person_5.start();
    }
}
