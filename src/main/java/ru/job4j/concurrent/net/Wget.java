package ru.job4j.concurrent.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        var startAt = System.currentTimeMillis();
        var file = new File("tmp.xml");
        try (var input = new URL(url).openStream();
             var output = new FileOutputStream(file)) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            var dataBuffer = new byte[1024];
            int bytesRead;
            while (true) {
                long start = System.nanoTime();
                bytesRead = input.read(dataBuffer);
                long timeNano = System.nanoTime() - start;
                if (bytesRead == -1) {
                    break;
                }
                output.write(dataBuffer, 0, bytesRead);
                long actualTimeMs = timeNano / 1_000_000;
                long expectedTimeMs = Math.max(1, bytesRead / speed);
                System.out.println(bytesRead + " bytes : "
                        + timeNano + " read time in nanoseconds, ");
                if (actualTimeMs < expectedTimeMs) {
                    Thread.sleep(expectedTimeMs - actualTimeMs);
                }
            }
            System.out.println(Files.size(file.toPath()) + " bytes");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 2) {
            System.out.println("Usage: java Wget <url> <speed>");
            return;
        }
        String url = args[0];
        int speed;

        try {
            speed = Integer.parseInt(args[1]);
            if (speed <= 0) {
                System.out.println("Speed must be greater than 0");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Speed must be a number");
            return;
        }

        try {
            new URL(url);
        } catch (Exception e) {
            System.out.println("Invalid URL");
            return;
        }

        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
