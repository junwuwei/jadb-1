package ua.com.lavi.jadb.engine;

import java.io.*;
import java.nio.charset.Charset;

public class ADBSyncTransport {
    private final String UTF_CODEPAGE = "utf-8";
    private final DataOutput output;
    private final DataInput input;

    public ADBSyncTransport(InputStream inputStream, OutputStream outputStream) {
        input = new DataInputStream(inputStream);
        output = new DataOutputStream(outputStream);
    }

    public void send(String syncCommand, String name) throws IOException {
        if (syncCommand.length() != 4) {
            throw new IllegalArgumentException("sync commands must have length 4");
        }
        output.writeBytes(syncCommand);
        output.writeInt(Integer.reverseBytes(name.length()));
        output.writeBytes(name);
    }

    public void sendStatus(String statusCode, int length) throws IOException {
        output.writeBytes(statusCode);
        output.writeInt(Integer.reverseBytes(length));
    }

    public void verifyStatus() throws Exception{
        String response = readString(4);
        int length = readInt();
        if (response.equalsIgnoreCase("FAIL")) {
            String error = readString(length);
            throw new Exception(error);
        }
        if (!response.equals("OKAY")) {
            throw new Exception("Unknown error: " + response);
        }
    }

    public int readInt() throws IOException {
        return Integer.reverseBytes(input.readInt());
    }

    public String readString(int length) throws IOException {
        byte[] buffer = new byte[length];
        input.readFully(buffer);
        return new String(buffer, Charset.forName(UTF_CODEPAGE));
    }

    public ADBRemoteFile readDirectoryEntry() throws IOException {
        String id = readString(4);
        int mode = readInt();
        int size = readInt();
        int time = readInt();
        int nameLength = readInt();
        String name = readString(nameLength);

        if (!"DENT".equals(id)) {
            return ADBRemoteFile.DONE;
        }
        return new ADBRemoteFile(name, mode, size, time);
    }

    private void sendChunk(byte[] buffer, int offset, int length) throws IOException {
        output.writeBytes("DATA");
        output.writeInt(Integer.reverseBytes(length));
        output.write(buffer, offset, length);
    }

    private int readChunk(byte[] buffer) throws Exception {
        String id = readString(4);
        int n = readInt();
        if (id.equals("FAIL")){
            throw new Exception(readString(n));
        }
        if (!id.equals("DATA")) {
            return -1;
        }
        input.readFully(buffer, 0, n);
        return n;
    }

    public void sendStream(InputStream in) throws IOException {
        byte[] buffer = new byte[1024 * 64];
        int n = in.read(buffer);
        while (n != -1) {
            sendChunk(buffer, 0, n);
            n = in.read(buffer);
        }
    }

    public void readChunksTo(OutputStream stream) throws Exception {
        byte[] buffer = new byte[1024 * 64];
        int n = readChunk(buffer);
        while (n != -1) {
            stream.write(buffer, 0, n);
            n = readChunk(buffer);
        }
    }
}
