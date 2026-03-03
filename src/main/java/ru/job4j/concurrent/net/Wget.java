package ru.job4j.concurrent.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
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
        try {
            URL sourceUrl = new URL(url);
            String fileName = extractFileName(sourceUrl);

            try (InputStream input = sourceUrl.openStream();
                 FileOutputStream output = new FileOutputStream(fileName)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                long startTime = System.currentTimeMillis();
                int bytesDownloadedInPeriod = 0;

                while ((bytesRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                    bytesDownloadedInPeriod += bytesRead;

                    if (bytesDownloadedInPeriod >= speed) {
                        long elapsed = System.currentTimeMillis() - startTime;
                        if (elapsed < 1000) {
                            Thread.sleep(1000 - elapsed);
                        }
                        bytesDownloadedInPeriod = 0;
                        startTime = System.currentTimeMillis();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String extractFileName(URL url) {
        String path = url.getPath();
        String fileName = path.substring(path.lastIndexOf('/') + 1);
        if (fileName.isEmpty()) {
            return "downloaded.file";
        }
        return fileName;
    }

    private static void validateArgs(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException(
                    "Usage: java Wget <url> <speed>"
            );
        }

        try {
            new URL(args[0]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid URL", e);
        }

        int speed;
        try {
            speed = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Speed must be a number", e);
        }

        if (speed <= 0) {
            throw new IllegalArgumentException("Speed must be greater than 0");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validateArgs(args);

        String url = args[0];
        int speed = Integer.parseInt(args[1]);

        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
