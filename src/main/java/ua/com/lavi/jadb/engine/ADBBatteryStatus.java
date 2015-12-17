package ua.com.lavi.jadb.engine;

/**
 * Created by Oleksandr Loushkin on 12/17/2015.
 */
public class ADBBatteryStatus {

    private Boolean acPowered;
    private Boolean usbPowered;
    private Boolean wirelessPowered;
    private Integer status;
    private Integer health;
    private Boolean present;
    private Integer level;
    private Integer scale;
    private Integer voltage;
    private Integer currentNow;
    private Integer temperature;
    private String technology;

    public Boolean getAcPowered() {
        return acPowered;
    }

    public void setAcPowered(Boolean acPowered) {
        this.acPowered = acPowered;
    }

    public Boolean getUsbPowered() {
        return usbPowered;
    }

    public void setUsbPowered(Boolean usbPowered) {
        this.usbPowered = usbPowered;
    }

    public Boolean getWirelessPowered() {
        return wirelessPowered;
    }

    public void setWirelessPowered(Boolean wirelessPowered) {
        this.wirelessPowered = wirelessPowered;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public Boolean getPresent() {
        return present;
    }

    public void setPresent(Boolean present) {
        this.present = present;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public Integer getVoltage() {
        return voltage;
    }

    public void setVoltage(Integer voltage) {
        this.voltage = voltage;
    }

    public Integer getCurrentNow() {
        return currentNow;
    }

    public void setCurrentNow(Integer currentNow) {
        this.currentNow = currentNow;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }
}
