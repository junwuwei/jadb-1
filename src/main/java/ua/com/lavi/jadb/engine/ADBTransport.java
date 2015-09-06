package ua.com.lavi.jadb.engine;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

public class ADBTransport {

    private final String UTF_CODEPAGE = "utf-8";
    private Socket socket;

	public ADBTransport(Socket socket) throws IOException {
        this.socket = socket;
	}

	public String readString() throws IOException {
		String response = readFromInputStream(4);
        response = readFromInputStream(Integer.parseInt(response, 16));
		return response;
	}

    private String readFromInputStream(int length) throws IOException {
        DataInput reader = new DataInputStream(socket.getInputStream());
        byte[] responseBuffer = new byte[length];
        reader.readFully(responseBuffer);
        String result = new String(responseBuffer, Charset.forName(UTF_CODEPAGE));
        return result;
    }

	public String readFromInputStreamTillEnd() throws IOException {
        InputStream is = new BufferedInputStream(socket.getInputStream());
        return IOUtils.toString(is, UTF_CODEPAGE);
	}

	public void verifyResponse() throws Exception  {
        String response = readFromInputStream(4);
        if (response.equalsIgnoreCase("FAIL")) {
            String error = readString();
            throw new Exception(error);
        }
    }

	public void send(String command) throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
		writer.write(String.format("%04x", command.length()));
		writer.write(command);
        writer.flush();
    }

    public void close() throws IOException {
        socket.close();
	}

    public boolean isClosed() throws IOException {
        return socket.isClosed();
    }

    public ADBSyncTransport startSync() throws Exception{
        send("sync:");
        verifyResponse();
        return new ADBSyncTransport(socket.getInputStream(), socket.getOutputStream());
    }

    public ADBTransport cloneTransport() throws IOException {
        Socket clonedSocket = new Socket(socket.getInetAddress(), socket.getPort());
        return new ADBTransport(clonedSocket);
    }
}
