package ua.com.lavi.jadb.engine;

public class ADBRemoteFile {

    public static final ADBRemoteFile DONE = new ADBRemoteFile(null, 0, 0, 0);

    private final String name;
    private final int mode;
    private final int size;
    private final long lastModified;

    public ADBRemoteFile(String name, int mode, int size, long lastModified) {
        this.name = name;
        this.mode = mode;
        this.size = size;
        this.lastModified = lastModified;
    }
    public ADBRemoteFile(String name) {
        this(name, 0, 0, 0);
    }

    public int getSize() {
        return size;
    }

    public long getLastModified() {
        return lastModified;
    }

    public boolean isDirectory() {
        return (mode & (1 << 14)) == (1 << 14);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "file:" + name;
    }
}
