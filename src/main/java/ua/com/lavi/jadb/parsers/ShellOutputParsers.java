package ua.com.lavi.jadb.parsers;

import ua.com.lavi.jadb.engine.ADBBatteryStatus;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Oleksandr Loushkin on 12/17/2015.
 */
public class ShellOutputParsers {

    public static ADBBatteryStatus batteryStatusParser(String input) {
        ADBBatteryStatus adbBatteryStatus = new ADBBatteryStatus();
        List<String> lines = Arrays.asList(input.split("\r\n"));
        for (int i = 1; i < lines.size(); i++) { // skip first line
            String line = lines.get(i);
                if (line.contains("AC powered: ")) {
                    adbBatteryStatus.setAcPowered(Boolean.parseBoolean(line.split(": ")[1]));
                }
                if (line.contains("USB powered: ")) {
                    adbBatteryStatus.setUsbPowered(Boolean.parseBoolean(line.split(": ")[1]));
                }
                if (line.contains("Wireless powered: ")) {
                    adbBatteryStatus.setWirelessPowered(Boolean.parseBoolean(line.split(": ")[1]));
                }
                if (line.contains("status: ")) {
                    adbBatteryStatus.setStatus(Integer.parseInt(line.split(": ")[1]));
                }
                if (line.contains("health: ")) {
                    adbBatteryStatus.setHealth(Integer.parseInt(line.split(": ")[1]));
                }
                if (line.contains("present: ")) {
                    adbBatteryStatus.setPresent(Boolean.parseBoolean(line.split(": ")[1]));
                }
                if (line.contains("level: ")) {
                    adbBatteryStatus.setLevel(Integer.parseInt(line.split(": ")[1]));
                }
                if (line.contains("scale: ")) {
                    adbBatteryStatus.setScale(Integer.parseInt(line.split(": ")[1]));
                }
                if (line.contains("voltage: ")) {
                    adbBatteryStatus.setVoltage(Integer.parseInt(line.split(": ")[1]));
                }
                if (line.contains("current now: ")) {
                    adbBatteryStatus.setCurrentNow(Integer.parseInt(line.split(": ")[1]));
                }
                if (line.contains("temperature: ")) {
                    adbBatteryStatus.setTemperature(Integer.parseInt(line.split(": ")[1]));
                }
                if (line.contains("technology: ")) {
                    adbBatteryStatus.setTechnology((line.split(": ")[1]));
                }
        }

        return adbBatteryStatus;
    }
}
