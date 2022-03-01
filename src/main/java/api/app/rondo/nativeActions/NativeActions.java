package api.app.rondo.nativeActions;

import core.LogResults;
import core.MobilePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class NativeActions extends MobilePage {

    public NativeActions(AppiumDriver<MobileElement> driver, LogResults log) {
        super(driver, log);
    }

    public void reStartApp() {
        log.startStep("Restart app");
        driver.launchApp();
        log.endStep();
    }


}
