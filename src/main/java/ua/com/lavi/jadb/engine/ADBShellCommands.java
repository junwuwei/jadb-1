package ua.com.lavi.jadb.engine;

public class ADBShellCommands {

    public static final String PROPERTY_RAW_SCREENSHOT_PATH = "/sdcard/screenshot.raw";
    public static final String PROPERTY_PNG_SCREENSHOT_PATH = "/sdcard/screenshot.png";

    public static final String GET_PROPERTIES = "getprop";
    public static final String GET_DISPLAY_INFORMATION = "dumpsys display";
    public static final String GET_RAW_SCREENSHOT = "screencap " + PROPERTY_RAW_SCREENSHOT_PATH;
    public static final String DELETE_RAW_SCREENSHOT = "rm -r " + PROPERTY_RAW_SCREENSHOT_PATH;
    public static final String GET_PNG_SCREENSHOT = "screencap -p " + PROPERTY_PNG_SCREENSHOT_PATH;
    public static final String DELETE_PNG_SCREENSHOT = "rm -r " + PROPERTY_PNG_SCREENSHOT_PATH;
    public static final String DISABLE_ACCELEROMETER_ROTATION = "content insert --uri content://settings/system --bind name:s:accelerometer_rotation --bind value:i:0";
    public static final String PORTRAIT_ORIENTATION_TOP = "content insert --uri content://settings/system --bind name:s:user_rotation --bind value:i:0";
    public static final String LANDSCAPE_ORIENTATION_RIGHT = "content insert --uri content://settings/system --bind name:s:user_rotation --bind value:i:1";
    public static final String PORTRAIT_ORIENTATION_BOTTOM = "content insert --uri content://settings/system --bind name:s:user_rotation --bind value:i:2";
    public static final String LANDSCAPE_ORIENTATION_LEFT = "content insert --uri content://settings/system --bind name:s:user_rotation --bind value:i:3";
    public static final String INPUT_TEXT = "input text";
    public static final String PROCESSES = "ps";
}
