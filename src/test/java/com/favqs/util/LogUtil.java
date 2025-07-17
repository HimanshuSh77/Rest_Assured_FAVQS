package com.favqs.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
 
public class LogUtil {
 
    private static final ThreadLocal<StringBuilder> buffer = ThreadLocal.withInitial(StringBuilder::new);
    private static final String LOG_FILE_PATH = "logs/test-log.txt";
    
    static {
        try {
            File file = new File(LOG_FILE_PATH);
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println("Old log file deleted.");
                } else {
                    System.out.println("Failed to delete old log file.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    public static void log(String message) {
        buffer.get()
              .append(LocalDateTime.now())
              .append(" | ")
              .append(Thread.currentThread().getName())
              .append(" | ")
              .append(message)
              .append("\n");
    }
 
    public static void flush(String testName) {
        synchronized (LogUtil.class) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
                writer.write("===== " + testName + " =====\n");
                writer.write(buffer.get().toString());
                writer.write("===== END " + testName + " =====\n\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        buffer.remove();
    }
}