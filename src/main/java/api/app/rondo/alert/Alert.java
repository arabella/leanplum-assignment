package api.app.rondo.alert;

import api.app.rondo.home.Home;
import core.MobilePage;
import core.LogResults;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class Alert extends MobilePage {

    @AndroidFindBy(id = "android:id/alertTitle")
    @iOSXCUITFindBy(accessibility = "")
    private MobileElement alertTitle;

    @AndroidFindBy(id = "android:id/message")
    @AndroidFindBy(id = "message")
    @iOSXCUITFindBy(accessibility = "")
    private MobileElement alertMessage;

    @AndroidFindBy(id = "android:id/button1")
    @iOSXCUITFindBy(accessibility = "")
    private MobileElement alertNativeBtnYes;

    @AndroidFindBy(id = "android:id/button2")
    @iOSXCUITFindBy(accessibility = "")
    private MobileElement alertNativeBtnNo;


    public Alert(AppiumDriver<MobileElement> driver, LogResults log) {
        super(driver, log);
    }

    public Home closeAlertOnStart() {
        alertNativeBtnYes.click();
        return new Home(driver, log);
    }

    public void isAlertDisplayed() {
        if (alertMessage.isDisplayed()) {
            log.assertStep("Alert is displayed");
        }
    }

    public void verifyAlertOnStart(
            String expectedAlertTitle,
            String expectedAlertMessage,
            String btnText) {
        String actualAlertTitle = alertTitle.getText();
        String actualAlertMessage = alertMessage.getText();
        String actualAlertBtnTxt = alertNativeBtnYes.getText();

        if (expectedAlertTitle.equals(actualAlertTitle)) {
            log.assertStep("Title " + actualAlertTitle + " is correct");
        } else {
            log.assertStep(expectedAlertTitle + " not matching " + actualAlertTitle);
        }

        if (expectedAlertMessage.equals(actualAlertMessage)) {
            log.assertStep("Title " + actualAlertMessage + " is correct");
        } else {
            log.assertStep(expectedAlertMessage + " not matching " + actualAlertMessage);
        }

        if (btnText.equals(actualAlertBtnTxt)) {
            log.assertStep("Title " + actualAlertBtnTxt + " is correct");
        } else {
            log.assertStep(btnText + " not matching " + actualAlertBtnTxt);
        }

    }
}