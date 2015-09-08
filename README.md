#JADB#
ADB client implemented in Java. Idea of using TCP Sockets by Samuel Carlsson.

Now it's an interface (ADBService) which allow to get active devices, push/pull files, take screenshot in png/raw format, rotate screen, get device configuration and get files in remote directory.

## Examples ##

    ADBService adbService = new ADBServiceImpl();
	
	List<String> udids = adbService.getConnectedDevicesUdid();
	byte[] rawScreenshot = adbService.getScreenshot("deviceUdid", ADBScreenshotType.RAW);
	Map configuration = adbService.getPropertiesForDevice("deviceUdid");
	byte[] file = adbService.pullFile(deviceUdid, new ADBRemoteFile("/sdcard/testFile.txt"));
	adbService.pushFile(deviceUdid, new File("pom.xml"), new ADBRemoteFile("/sdcard/pom.xml"));
	adbService.sendKeys(deviceUdid, "testSendKeys via ADB");
	List<ADBProcess> list = adbService.getProcessList(deviceUdid);
	
	

Make sure the adb server is running. You can start it by running `adb start-server` once from the command line.
