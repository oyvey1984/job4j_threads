package ru.job4j.concurrent;

public class ConcurrentOutput {
    public static void main(String[] args) {
        System.out.println("Before start: " + Thread.currentThread().getName());
        Thread another = new Thread(
                () -> System.out.println("Inside thread another: " + Thread.currentThread().getName())
        );
        another.start();

        Thread second = new Thread(
                () -> System.out.println("Inside thread second: " + Thread.currentThread().getName())
        );
        second.start();
        System.out.println("After start: " + Thread.currentThread().getName());
    }
}
