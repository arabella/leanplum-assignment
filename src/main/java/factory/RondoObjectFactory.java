package factory;

import api.app.rondo.adHoc.AdHoc;
import api.app.rondo.alert.Alert;
import api.app.rondo.appPicker.AppPicker;
import api.app.rondo.home.Home;
import api.app.rondo.nativeActions.NativeActions;
import api.app.rondo.navigation.Navigation;
import core.managers.DriverManager;
import core.LogResults;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import settings.Settings;

public class RondoObjectFactory {

    public AdHoc adHoc;
    public Alert alert;
    public AndroidDriver<MobileElement> mobileDriver;
    public LogResults log;
    public String udid;
    public Navigation navigation;
    public Home home;
    public AppPicker appPicker;
    public NativeActions nativeActions;
    Settings settings;

    public RondoObjectFactory getAndroidObject(
            String udid, String port, String deviceName,
            DesiredCapabilities caps) throws Exception {
        if (caps == null) {
            mobileDriver = new DriverManager(settings).getDriver(udid, port, deviceName);
        } else {
            mobileDriver = new DriverManager(settings).getMobileDriver();
        }
        this.udid = udid;
        adHoc = new AdHoc(mobileDriver, log);
        alert = new Alert(mobileDriver, log);
        navigation = new Navigation(mobileDriver, log);
        log = new LogResults(mobileDriver);
        home = new Home(mobileDriver, log);
        appPicker = new AppPicker(mobileDriver, log);
        nativeActions = new NativeActions(mobileDriver, log);
        return this;
    }

    public RondoObjectFactory getAndroidObject(String udid, String port, String deviceName) throws Exception {
        return getAndroidObject(udid, port, deviceName, null);
    }
}
