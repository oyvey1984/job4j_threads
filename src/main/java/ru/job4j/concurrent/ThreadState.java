package ru.job4j.concurrent;


public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println("Name first thread: " + Thread.currentThread().getName())
        );
        System.out.println(first.getState());
        first.start();

        while (first.getState() != Thread.State.TERMINATED) {
            System.out.println(first.getState());
        }
        System.out.println(first.getState());

        Thread second = new Thread(
                () -> System.out.println("Name second thread: " + Thread.currentThread().getName())
        );
        second.start();
    }
}
