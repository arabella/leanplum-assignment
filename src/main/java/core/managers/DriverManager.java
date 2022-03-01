package core.managers;

import api.android.Android;
import core.ADB;
import core.LogResults;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import settings.Settings;
import utils.Network;

public class DriverManager {

    private final Settings settings;
    private static AndroidDriver<MobileElement> driver;

    public DriverManager(Settings settings) {
        this.settings = settings;
    }

    public void createDriver(URL url, String udid, String systemPort, String deviceName) {
        try {
            DesiredCapabilities cap = new DesiredCapabilities();
            cap.setCapability("platformName", "Android");
            cap.setCapability("udid", udid);
            cap.setCapability("deviceName", deviceName);
            cap.setCapability("automationName", "UiAutomator2");
            cap.setCapability("appActivity", settings.getAppActivity());
            cap.setCapability("appPackage", settings.getAppPackage());
            cap.setCapability("systemPort", systemPort);
            cap.setCapability("app", settings.getAppPath());
            driver = new AndroidDriver<>(url, cap);
            driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
            Android.adb = new ADB(settings.getUdid());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private DesiredCapabilities getCapabilities() {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,
                                   String.valueOf(settings.getPlatformVersion()));


        if (settings.getAppPath() != null) {
            capabilities.setCapability(MobileCapabilityType.APP, settings.getAppPath());
        }
        if (settings.getAppPackage() != null) {
            capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, settings.getAppPackage());
        }
        if (settings.getAppActivity() != null) {
            capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, settings.getAppActivity());
        }

        if (settings.getAppPath() != null) {
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, settings.getDeviceName());
        }

        if (settings.getPlatform() == Platform.ANDROID) {
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
        }

        settings.getPlatformVersion();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,
                                   settings.getPlatformVersion());

        capabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, Network.nextFreePort(5200, 5299));

        return capabilities;
    }

    public void killDriver() {
        if (!(driver == null)) {
            driver.quit();
            Android.adb.unInstallApp(settings.getAppPackage());
        } else {
            System.out.println("No driver initialised. Nothing to kill.");
        }
    }

    public AndroidDriver<MobileElement> getDriver(String udid, String port, String deviceName) {
        return driver;
    }

    private String urlHost;
    public AndroidDriver<MobileElement> getMobileDriver() throws Exception {
        try {
            return new AndroidDriver<>(new URL(urlHost), getCapabilities());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
