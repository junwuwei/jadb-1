package ua.com.lavi.jadb.service.impl;

import ua.com.lavi.jadb.engine.*;
import ua.com.lavi.jadb.service.ADBService;

import java.io.File;
import java.io.FileOutputStream;
import java.net.ConnectException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ADBServiceImpl implements ADBService {

    @Override
    public Map getPropertiesForDevice(String udid) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        Map map = adbDevice.getProperties();
        map.putAll(adbDevice.getDisplayInformation());
        adbConnection.close();
        return map;
    }

    @Override
    public List<String> getConnectedDevicesUdid() throws Exception {
        ADBConnection adbConnection = createConnection();
        List<ADBDevice> adbDevices = adbConnection.getDevices();
        List<String> udids = adbDevices.stream().map(ADBDevice::getUdid).collect(Collectors.toList());
        adbConnection.close();
        return udids;
    }

    @Override
    public byte[] getScreenshot(String udid, ADBScreenshotType screenshotType) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        byte[] screenshot = new byte[0];
        if (screenshotType == ADBScreenshotType.RAW) {
            screenshot = adbDevice.getRAWScreenshot();
        }
        if (screenshotType == ADBScreenshotType.PNG) {
            screenshot = adbDevice.getPNGScreenshot();
        }
        adbConnection.close();
        return screenshot;
    }

    @Override
    public List<ADBRemoteFile> getFileList(String udid, String path) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        List<ADBRemoteFile> files = adbDevice.getFileList(path);
        adbConnection.close();
        return files;
    }

    @Override
    public void pushFile(String udid, File localeFile, ADBRemoteFile remoteFile) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        adbDevice.push(localeFile, remoteFile);
        adbConnection.close();
    }

    @Override
    public void pullFile(String udid, File localeFile, ADBRemoteFile remoteFile) throws Exception {
        byte[] file = pullFile(udid, remoteFile);
        FileOutputStream fos = new FileOutputStream(localeFile);
        fos.write(file);
        fos.close();
    }

    @Override
    public byte[] pullFile(String udid, ADBRemoteFile remoteFile) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        byte[] file = adbDevice.pull(remoteFile);
        adbConnection.close();
        return file;
    }

    @Override
    public void rotate(String udid, ADBDisplayOrientation orientation) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        adbDevice.executeShell(ADBShellCommands.DISABLE_ACCELEROMETER_ROTATION);

        if (orientation == ADBDisplayOrientation.LANDSCAPE_ORIENTATION_LEFT) {
            adbDevice.executeShell(ADBShellCommands.LANDSCAPE_ORIENTATION_LEFT);
        }
        if (orientation == ADBDisplayOrientation.LANDSCAPE_ORIENTATION_RIGHT) {
            adbDevice.executeShell(ADBShellCommands.LANDSCAPE_ORIENTATION_RIGHT);
        }
        if (orientation == ADBDisplayOrientation.PORTRAIT_ORIENTATION_TOP) {
            adbDevice.executeShell(ADBShellCommands.PORTRAIT_ORIENTATION_TOP);
        }
        if (orientation == ADBDisplayOrientation.PORTRAIT_ORIENTATION_BOTTOM) {
            adbDevice.executeShell(ADBShellCommands.PORTRAIT_ORIENTATION_BOTTOM);
        }
        adbConnection.close();
    }

    @Override
    public void sendKeys(String udid, String text) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        text = text.replaceAll(" ", "%s");
        String shellCommand = ADBShellCommands.INPUT_TEXT + " \'" + text + "\'";
        adbDevice.executeShell(shellCommand);
        adbConnection.close();
    }

    @Override
    public List<ADBProcess> getProcessList(String udid) throws Exception {
        ADBConnection adbConnection = createConnection();
        ADBDevice adbDevice = adbConnection.getDevice(udid);
        List<ADBProcess> adbProcesses = adbDevice.getProcessList();
        adbConnection.close();
        return adbProcesses;
    }

    private ADBConnection createConnection() throws Exception {
        ADBConnection adbConnection;
        try {
            adbConnection = new ADBConnection();
        }
        catch (ConnectException e) {
            throw new ConnectException("Unable to connect to ADB server. Please check that adb is running");
        }
        return adbConnection;
    }
}
