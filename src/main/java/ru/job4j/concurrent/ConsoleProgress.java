package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        int index = 0;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                var process = new char[] {'-', '\\', '|', '/'};
                if (index == process.length) {
                    index = 0;
                }
                System.out.print("\r Loading ... " + process[index]);
                index++;
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();
    }
}
