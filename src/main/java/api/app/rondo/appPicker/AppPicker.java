package api.app.rondo.appPicker;

import api.app.rondo.home.Home;
import core.MobilePage;
import core.LogResults;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class AppPicker extends MobilePage {

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Lina qa']")
    @iOSXCUITFindBy(accessibility = "")
    private MobileElement appName;

    public AppPicker(
            AppiumDriver<MobileElement> driver,
            LogResults log
            ) {
        super(driver, log);
    }

    public Home openTestApp() {
        appName.click();
        return new Home(driver, log);
    }

}
