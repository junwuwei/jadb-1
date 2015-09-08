package ua.com.lavi.jadb.engine;

/**
 * Created by Oleksandr Loushkin on 9/8/2015.
 */
public class ADBProcess {

    private String user;
    private Integer pid;
    private Integer ppid;
    private Integer vsize;
    private Integer rss;
    private String wchan;
    private String pc;
    private String type;
    private String name;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getPpid() {
        return ppid;
    }

    public void setPpid(Integer ppid) {
        this.ppid = ppid;
    }

    public Integer getVsize() {
        return vsize;
    }

    public void setVsize(Integer vsize) {
        this.vsize = vsize;
    }

    public Integer getRss() {
        return rss;
    }

    public void setRss(Integer rss) {
        this.rss = rss;
    }

    public String getWchan() {
        return wchan;
    }

    public void setWchan(String wchan) {
        this.wchan = wchan;
    }

    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("user='%s', pid=%d, ppid=%d, vsize=%d, rss=%d, wchan='%s', pc='%s', type='%s', name='%s'", user, pid, ppid, vsize, rss, wchan, pc, type, name);
    }
}
