package api.app.rondo.navigation;

import api.app.rondo.adHoc.AdHoc;
import core.MobilePage;
import core.LogResults;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class Navigation extends MobilePage {

    @AndroidFindBy(id = "com.leanplum.rondo:id/app_setup")
    @iOSXCUITFindBy(accessibility = "")
    private MobileElement appSetUp;

    @AndroidFindBy(id = "com.leanplum.rondo:id/adhoc")
    @iOSXCUITFindBy(accessibility =  "")
    private MobileElement adHoc;

    @AndroidFindBy(id = "com.leanplum.rondo:id/app_inbox")
    @iOSXCUITFindBy(accessibility =  "")
    private MobileElement appInbox;

    @AndroidFindBy(id = "com.leanplum.rondo:id/variables")
    @iOSXCUITFindBy(accessibility =  "")
    private MobileElement variables;

    @AndroidFindBy(id = "com.leanplum.rondo:id/sdq_qa")
    @iOSXCUITFindBy(accessibility =  "")
    private MobileElement sdkQA;


    public Navigation(AppiumDriver<MobileElement> driver, LogResults log) {
        super(driver, log);
    }

    public AdHoc goToAdHoc() {
        adHoc.click();
        return new AdHoc(driver, log);
    }
}