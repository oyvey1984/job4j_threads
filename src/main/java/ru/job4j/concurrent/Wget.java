package ru.job4j.concurrent;

public class Wget {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    try {
                        System.out.println("Start loading ... ");
                        int index = 0;
                        while (index <= 100) {
                            Thread.sleep(1000);
                            System.out.print("\rLoading : " + index  + "%");
                            index++;
                        }
                        System.out.println("\nLoaded.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        thread.start();
    }
}
