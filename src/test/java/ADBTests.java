import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.com.lavi.jadb.engine.*;
import ua.com.lavi.jadb.service.ADBService;
import ua.com.lavi.jadb.service.impl.ADBServiceImpl;

import java.io.File;
import java.util.List;
import java.util.Map;

public class ADBTests {

    private static ADBService adbService = new ADBServiceImpl();
    private static List<String> activeUdids;

    @BeforeClass
    public static void setUp() throws Exception {
        activeUdids = adbService.getConnectedDevicesUdid();
    }

    @Test
    public void testDeviceRotation() throws Exception {
        for (String deviceUdid : activeUdids) {
            adbService.rotate(deviceUdid, ADBDisplayOrientation.LANDSCAPE_ORIENTATION_LEFT);
            adbService.rotate(deviceUdid, ADBDisplayOrientation.LANDSCAPE_ORIENTATION_RIGHT);
            adbService.rotate(deviceUdid, ADBDisplayOrientation.PORTRAIT_ORIENTATION_BOTTOM);
            adbService.rotate(deviceUdid, ADBDisplayOrientation.PORTRAIT_ORIENTATION_TOP);
        }
    }

    @Test
    public void testGetConfiguration() throws Exception {
        for (String deviceUdid : activeUdids) {
            Map configuration = adbService.getPropertiesForDevice(deviceUdid);
            Assert.assertTrue(configuration.size() > 0);
        }
    }

    @Test
    public void testGetScreenshot() throws Exception {
        for (String deviceUdid : activeUdids) {
            byte[] rawScreenshot = adbService.getScreenshot(deviceUdid, ADBScreenshotType.RAW);
            System.out.println("raw size: " + rawScreenshot.length);
            Assert.assertTrue(rawScreenshot.length > 0);

            byte[] pngScreenshot = adbService.getScreenshot(deviceUdid, ADBScreenshotType.PNG);
            System.out.println("png size: " + pngScreenshot.length);
            Assert.assertTrue(pngScreenshot.length > 0);
        }
    }

    @Test
    public void testListFiles() throws Exception {
        for (String deviceUdid : activeUdids) {
            List<ADBRemoteFile> files = adbService.getFileList(deviceUdid, "/sdcard");
            Assert.assertTrue(files.size() > 0);
        }
    }

    @Test
    public void testPushFile() throws Exception {
        for (String deviceUdid : activeUdids) {
            adbService.pushFile(deviceUdid, new File("pom.xml"), new ADBRemoteFile("/sdcard/pom.xml"));
        }
    }

    @Test
    public void testPullFile() throws Exception {
        for (String deviceUdid : activeUdids) {
            byte[] file = adbService.pullFile(deviceUdid, new ADBRemoteFile("/sdcard/pom.xml"));
            Assert.assertTrue(file.length > 0);
        }
    }

    @Test
    public void testSendKeys() throws Exception {
        for (String deviceUdid : activeUdids) {
            String text = "testSendKeys via ADB";
            adbService.sendKeys(deviceUdid, text);
        }
    }

    @Test
    public void testProcessList() throws Exception {
        for (String deviceUdid : activeUdids) {
            List<ADBProcess> list = adbService.getProcessList(deviceUdid);
            Assert.assertFalse(list.isEmpty());
        }
    }

    @Test
    public void testBatteryStatus() throws Exception {
        for (String deviceUdid : activeUdids) {
            adbService.getBatteryStatus(deviceUdid);
        }
    }
}
