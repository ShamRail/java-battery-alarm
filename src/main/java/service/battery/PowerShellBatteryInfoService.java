package service.battery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PowerShellBatteryInfoService implements BatteryInfoService {

    private static final String PERCENT_REQUEST = "powershell -command \"(Get-WmiObject Win32_Battery).EstimatedChargeRemaining\"";

    private static final String POWER_ONLINE_QUERY = "powershell -command \"Get-WmiObject -Class batterystatus -Namespace root\\wmi | select PowerOnline\"";


    @Override
    public int getBatteryPercent() {
        try {
            var percent = -1;
            var process = Runtime.getRuntime().exec(PERCENT_REQUEST);
            try (var reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                percent = Integer.parseInt(reader.readLine());
            }
            return percent;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isInCharging() {
        try {
            var result = false;
            var process = Runtime.getRuntime().exec(POWER_ONLINE_QUERY);
            try (var reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                result = reader.lines()
                        .filter(l -> l.contains("True") | l.contains("False"))
                        .findFirst()
                        .map(String::trim)
                        .map(Boolean::valueOf)
                        .orElse(false);
                return result;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
