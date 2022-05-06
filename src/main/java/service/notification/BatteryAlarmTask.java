package service.notification;

import service.battery.BatteryInfoService;

import javax.swing.*;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

public class BatteryAlarmTask implements Runnable, Closeable {

    private static final JFrame INVISIBLE_FRAME = new JFrame() {
        {
            setAlwaysOnTop(true);
        }
    };

    private final BatteryInfoService batteryInfoService;
    private final int minPercent;

    private final int maxPercent;

    private final PrintStream logger;

    private final String mediaFile;

    public BatteryAlarmTask(BatteryInfoService batteryInfoService, Properties properties) {
        this.batteryInfoService = batteryInfoService;
        this.minPercent = Integer.parseInt(properties.getProperty("percent.min"));
        this.maxPercent = Integer.parseInt(properties.getProperty("percent.max"));
        try {
            this.mediaFile = properties.getProperty("media.file");
            this.logger = new PrintStream(String.format("%s.txt", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void run() {
        boolean isCharging = batteryInfoService.isInCharging();
        var percent = batteryInfoService.getBatteryPercent();
        var message = "";
        this.logger.printf("Battery percent: %d%n", percent);
        if (!isCharging && percent < minPercent) {
            message = "Connect the charger ";
        }
        if (isCharging && percent > maxPercent) {
            message = "Disconnect the charger ";
        }
        if (!message.isEmpty()) {
            var sound = Sound.playSound(mediaFile);
            sound.setVolume(100);
            CompletableFuture.runAsync(() -> {
                var attempt = 10;
                while (attempt > 0) {
                    sound.join();
                    sound.play();
                    attempt--;
                }
            });
            JOptionPane.showMessageDialog(
                    INVISIBLE_FRAME, String.format("Battery level: %d. %s", percent, message), "Warning",
                    JOptionPane.WARNING_MESSAGE
            );
            sound.close();
        }
    }

    @Override
    public void close() {
        this.logger.close();
    }

}
