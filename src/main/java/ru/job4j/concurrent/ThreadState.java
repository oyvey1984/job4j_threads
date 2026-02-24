package ru.job4j.concurrent;


public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println("NAME first thread: " + Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println("NAME SECOND thread: " + Thread.currentThread().getName())
        );

        System.out.println("State first thread BEFORE cycle: " + first.getState());
        System.out.println("State SECOND thread BEFORE cycle: " + second.getState());

        first.start();
        second.start();

        while (first.getState() != Thread.State.TERMINATED || second.getState() != Thread.State.TERMINATED) {
            System.out.println("State first thread IN cycle: " + first.getState());
            System.out.println("State SECOND thread IN cycle: " + second.getState());
        }

        System.out.println("State first thread AFTER cycle: " + first.getState());
        System.out.println("State SECOND thread AFTER cycle: " + second.getState());
        System.out.println("Main: work is done");
    }
}
