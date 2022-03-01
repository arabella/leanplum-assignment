package api.android;

import api.app.rondo.Rondo;
import core.ADB;
import io.appium.java_client.android.AndroidDriver;


public class Android {

    public static AndroidDriver<io.appium.java_client.MobileElement> driver;
    public static ADB adb;
    public static Rondo app = new Rondo(driver);
}