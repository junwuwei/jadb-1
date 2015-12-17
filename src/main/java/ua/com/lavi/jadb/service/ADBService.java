package ua.com.lavi.jadb.service;

import ua.com.lavi.jadb.engine.*;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface ADBService {

    void executeShellCommand(String udid, String command) throws Exception;

    List<String> getConnectedDevicesUdid() throws Exception;

    Map getPropertiesForDevice(String udid) throws Exception;

    byte[] getScreenshot(String udid, ADBScreenshotType screenshotType) throws Exception;

    List<ADBRemoteFile> getFileList(String udid, String path) throws Exception;

    void pushFile(String udid, File localeFile, ADBRemoteFile remoteFile) throws Exception;

    void pullFile(String udid, File localeFile, ADBRemoteFile remoteFile) throws Exception;

    byte[] pullFile(String udid, ADBRemoteFile remoteFile) throws Exception;

    void rotate (String udid, ADBDisplayOrientation orientation) throws Exception;

    void sendKeys(String udid, String text) throws Exception;

    List<ADBProcess> getProcessList(String udid) throws Exception;

    ADBBatteryStatus getBatteryStatus(String udid) throws Exception;
}
