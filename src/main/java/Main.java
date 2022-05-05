import service.battery.PowerShellBatteryInfoService;
import service.notification.BatteryAlarmTask;

import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        var batteryInfoService = new PowerShellBatteryInfoService();
        var properties = new Properties();
        properties.setProperty("percent.min", args[0]);
        properties.setProperty("percent.max", args[1]);
        properties.setProperty("media.file", args[2]);

        var scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(
                new BatteryAlarmTask(batteryInfoService, properties), 0, 1, TimeUnit.MINUTES
        );

    }

}
