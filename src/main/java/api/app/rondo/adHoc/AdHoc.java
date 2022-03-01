package api.app.rondo.adHoc;

import core.LogResults;
import core.MobilePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class AdHoc extends MobilePage {

    @AndroidFindBy(id = "com.leanplum.rondo:id/trackName")
    @iOSXCUITFindBy(accessibility = "")
    private MobileElement eventName;

    @AndroidFindBy(id = "com.leanplum.rondo:id/paramKey")
    @iOSXCUITFindBy(accessibility = "")
    private MobileElement eventParameterKey;

    @AndroidFindBy(id = "com.leanplum.rondo:id/paramValue")
    @iOSXCUITFindBy(accessibility = "")
    private MobileElement eventParameterValue;

    @AndroidFindBy(id = "com.leanplum.rondo:id/buttonTrack")
    @iOSXCUITFindBy(accessibility = "")
    private MobileElement sendTrackEventBtn;

    //TODO add    stateName,
    //            stateParameterKey,
    //            stateParameterValue,
    //            sendSessionStateBtn,
    //            userAttrKey,
    //            userAttrValue,
    //            sendUserAttrBtn,
    //            latitude,
    //            longtitude,
    //            setDeviceLocationBtn,
    //            userID,
    //            sendUserIDBtn,
    //            forceContentUpdateBtn;


    public AdHoc(AppiumDriver<MobileElement> driver, LogResults log) {
        super(driver, log);
    }

    public AdHoc typeEventName(String text) {
        eventName.clear();
        eventName.sendKeys(text);
        return new AdHoc(driver, log);
    }

    public AdHoc clickSendEventButton() {
        sendTrackEventBtn.click();
        return new AdHoc(driver, log);
    }
}