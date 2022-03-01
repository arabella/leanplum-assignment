package api.app.rondo.home;

import api.app.rondo.appPicker.AppPicker;
import core.MobilePage;
import core.LogResults;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class Home extends MobilePage {

    @AndroidFindBy(id = "com.leanplum.rondo:id/appName")
    @iOSXCUITFindBy(accessibility = "")
    private MobileElement appName;

    @AndroidFindBy(id = "com.leanplum.rondo:id/appId")
    @iOSXCUITFindBy(accessibility =  "")
    private MobileElement appID;

    @AndroidFindBy(id = "com.leanplum.rondo:id/prodKey")
    @iOSXCUITFindBy(accessibility =  "")
    private MobileElement appKey;

    @AndroidFindBy(id = "com.leanplum.rondo:id/devKey")
    @iOSXCUITFindBy(accessibility =  "")
    private MobileElement devKey;

    @AndroidFindBy(id = "com.leanplum.rondo:id/userId")
    @iOSXCUITFindBy(accessibility =  "")
    private MobileElement userID;

    @AndroidFindBy(id = "com.leanplum.rondo:id/deviceId")
    @iOSXCUITFindBy(accessibility =  "")
    private MobileElement deviceID;

    @AndroidFindBy(id = "com.leanplum.rondo:id/app_picker")
    @iOSXCUITFindBy(accessibility =  "")
    private MobileElement appPicker;

    //TODO add envPicker,
    //    callLeanplum,
    //    CreatePushChannel,
    //    envModeSwitch

    public Home(AppiumDriver<MobileElement> driver, LogResults log) {
        super(driver, log);
    }

    public AppPicker openAppPicker() {
        appPicker.click();
        return new AppPicker(driver, log);
    }

    public boolean isUserIdEmpty() {
        System.out.println("UserId: " + userID.getText());
        System.out.println(userID.getText() == null || userID.getText().isEmpty());
        return userID.getText() == null || userID.getText().isEmpty();
    }

}