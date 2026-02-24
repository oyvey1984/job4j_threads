package ru.job4j.concurrent;

public class Daemon {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {}
        );

        thread.setDaemon(true);
        thread.start();
        System.out.println(thread.isDaemon());
    }
}
