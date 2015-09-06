package ua.com.lavi.jadb.engine;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ADBConnection implements AutoCloseable {
	
	private final String host;
	private final int port;

	private static final String DEFAULT_HOST = "localhost";
	private static final int DEFAULT_PORT = 5037;
	
	private final ADBTransport mainTransport;
	
	public ADBConnection() throws IOException {
		this(DEFAULT_HOST, DEFAULT_PORT);
	}
	
	public ADBConnection(String host, int port) throws IOException {
		this.host = host;
		this.port = port;
				
		mainTransport = createTransport();
	}

	public List<ADBDevice> getDevices() throws Exception {
		ADBTransport localTransport = mainTransport.cloneTransport();
        localTransport.send("host:devices");
        localTransport.verifyResponse();
		String response = localTransport.readString();
        localTransport.close();
        List<ADBDevice> devices = buildDevices(response);
		return devices;
	}

    private List<ADBDevice> buildDevices(String response) {
        String[] lines = response.split("\n");
        ArrayList<ADBDevice> devices = new ArrayList<>(lines.length);
        for (String line : lines) {
            String[] parts = line.split("\t");
            if (parts.length > 1) {
                ADBDevice adbDevice = new ADBDevice(parts[0], mainTransport);
                devices.add(adbDevice);
            }
        }
        return devices;
    }

    private ADBTransport createTransport() throws IOException {
		return new ADBTransport(new Socket(host, port));
	}

	public ADBDevice getDevice(String udid) {
		return new ADBDevice(udid, mainTransport);
	}

	public void close() throws IOException {
		mainTransport.close();
	}
}
