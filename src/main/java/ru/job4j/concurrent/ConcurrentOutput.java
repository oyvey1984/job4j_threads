package ru.job4j.concurrent;

public class ConcurrentOutput {
    public static void main(String[] args) {
        System.out.println("Before start: " + Thread.currentThread().getName());
        Thread another = new Thread(
                () -> System.out.println("Inside thread: " + Thread.currentThread().getName())
        );
        another.start();
        System.out.println("After start: " + Thread.currentThread().getName());
    }
}
