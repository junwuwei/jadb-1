package ua.com.lavi.jadb.engine;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ADBDevice {

	private final String udid;
	private final ADBTransport mainTransport;

    public ADBDevice(String udid, ADBTransport mainTransport) {
		this.udid = udid;
		this.mainTransport = mainTransport;
	}

	public String executeShell(String command) throws Exception {
        ADBTransport localTransport = mainTransport.cloneTransport();
        localTransport.send("host:transport:" + udid);
        localTransport.verifyResponse();
        localTransport.send("shell:" + command);
        localTransport.verifyResponse();
        String response = localTransport.readFromInputStreamTillEnd();
        localTransport.close();
        return response;
    }

    public List<ADBRemoteFile> getFileList(String remotePath) throws Exception {
        ADBTransport localTransport = mainTransport.cloneTransport();
        localTransport.send("host:transport:" + udid);
        localTransport.verifyResponse();
        ADBSyncTransport sync = localTransport.startSync();
        sync.send("LIST", remotePath);
        List<ADBRemoteFile> result = new ArrayList<>();
        for (ADBRemoteFile dent = sync.readDirectoryEntry(); dent != ADBRemoteFile.DONE; dent = sync.readDirectoryEntry()) {
            result.add(dent);
        }
        return result;
    }

    public void push(File localFile, ADBRemoteFile remoteFile) throws Exception {
        ADBTransport localTransport = mainTransport.cloneTransport();
        localTransport.send("host:transport:" + udid);
        localTransport.verifyResponse();
        FileInputStream fileStream = new FileInputStream(localFile);
        ADBSyncTransport sync  = localTransport.startSync();
        sync.send("SEND", remoteFile.getName() + "," + Integer.toString(0664));
        sync.sendStream(fileStream);
        sync.sendStatus("DONE", (int) localFile.lastModified());
        sync.verifyStatus();
        fileStream.close();
    }

    public byte[] pull(ADBRemoteFile remote) throws Exception {
        ADBTransport localTransport = mainTransport.cloneTransport();
        localTransport.send("host:transport:" + udid);
        localTransport.verifyResponse();
        ADBSyncTransport syncTransport = localTransport.startSync();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        syncTransport.send("RECV", remote.getName());
        syncTransport.readChunksTo(baos);
        byte[] file = baos.toByteArray();
        baos.close();
        return file;
    }

    public Map getProperties() throws Exception {
        Map propertyMap = new TreeMap<String,String>();
        String properties = executeShell(ADBShellCommands.GET_PROPERTIES);
        String lines[] = properties.split("\\n");
        for (String line : lines) {
            line = line.replaceAll("\r", "").replaceAll("[\\[\\](){}]","");
            line = line.replaceAll(": ",":");
            String parts[] = line.split(":");
            if (parts.length > 1) {
                String key = parts[0];
                String value = parts[1];
                propertyMap.put(key, value);
            }
        }
        return propertyMap;
    }

    public Map getDisplayInformation() throws Exception {
        Map<String,String> displayInformationMap = new TreeMap<String,String>();
        String properties = executeShell(ADBShellCommands.GET_DISPLAY_INFORMATION);
        String lines[] = properties.split("\\n");
        for (String line : lines) {
            if (line.contains("mDisplayHeight") || line.contains("mDisplayWidth")) {
                line = line.replaceAll("\r", "").replaceAll(" ","");
                String parts[] = line.split("=");
                if (parts.length > 1) {
                    String key = parts[0];
                    String value = parts[1];
                    displayInformationMap.put(key, value);
                }
            }
        }
        return displayInformationMap;
    }

    public byte[] getPNGScreenshot() throws Exception {
        executeShell(ADBShellCommands.GET_PNG_SCREENSHOT);
        byte[] screenshotBytes = pull(new ADBRemoteFile(ADBShellCommands.PROPERTY_PNG_SCREENSHOT_PATH));
        executeShell(ADBShellCommands.DELETE_PNG_SCREENSHOT);
        return screenshotBytes;
    }

    public byte[] getRAWScreenshot() throws Exception {
        executeShell(ADBShellCommands.GET_RAW_SCREENSHOT);
        byte[] screenshotBytes = pull(new ADBRemoteFile(ADBShellCommands.PROPERTY_RAW_SCREENSHOT_PATH));
        executeShell(ADBShellCommands.DELETE_RAW_SCREENSHOT);
        return screenshotBytes;
    }

    public String getUdid() {
        return udid;
    }

    public List<ADBProcess> getProcessList() throws Exception {
        List<ADBProcess> adbProcesses = new ArrayList<>();
        String unparsedProcesses = executeShell(ADBShellCommands.PROCESSES);
        String lines[] = unparsedProcesses.split("\r\n");
        for (int x = 1; x < lines.length; x++) { // skip first line
            ADBProcess adbProcess = new ADBProcess();
            String line = lines[x];

            int index = line.indexOf(" ");
            adbProcess.setUser(line.substring(0, index));
            line = line.substring(index).trim();
            index = line.indexOf(" ");
            adbProcess.setPid(Integer.parseInt(line.substring(0, index)));
            line = line.substring(index).trim();
            index = line.indexOf(" ");
            adbProcess.setPpid(Integer.parseInt(line.substring(0, index)));
            line = line.substring(index).trim();
            index = line.indexOf(" ");
            adbProcess.setVsize(Integer.parseInt(line.substring(0, index)));
            line = line.substring(index).trim();
            index = line.indexOf(" ");
            adbProcess.setRss(Integer.parseInt(line.substring(0, index)));
            line = line.substring(index).trim();
            index = line.indexOf(" ");
            adbProcess.setWchan(line.substring(0, index));
            line = line.substring(index).trim();
            index = line.indexOf(" ");
            adbProcess.setPc(line.substring(0, index));
            line = line.substring(index).trim();
            index = line.indexOf(" ");
            adbProcess.setType(line.substring(0, index));
            line = line.substring(index).trim();
            adbProcess.setName(line);

            adbProcesses.add(adbProcess);
        }
        return adbProcesses;
    }


}
