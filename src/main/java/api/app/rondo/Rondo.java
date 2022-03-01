package api.app.rondo;
import api.android.Android;
import api.interfaces.Application;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class Rondo implements Application {

    protected AndroidDriver<MobileElement> driver;

    public Rondo(AndroidDriver<MobileElement> driver) {
        this.driver = driver;
    }

    @Override public void forceStopApp() {
        Android.adb.forceStopApp(packageID());
    }

    @Override public void forceStop() {

    }

    @Override public void clearData() {
        Android.adb.clearAppsData(packageID());
    }

    @Override public Object open() {
        Android.adb.openAppActivity(packageID(), activityID());
        return null;
    }

    @Override public String packageID() {
        return "com.leanplum.rondo";
    }

    @Override public String activityID() {
        return "com.leanplum.rondo.MainActivity";
    }

}
