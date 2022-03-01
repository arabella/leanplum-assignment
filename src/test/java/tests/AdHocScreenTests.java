package tests;

import api.app.rondo.Rondo;
import core.managers.TestManager;
import factory.RondoObjectFactory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class AdHocScreenTests extends TestManager {

    private static final String startCampaignEventName = "startCampaignEvent";
//    private static final String startCampaignEventKey = "";
//    private static final String startCampaignEventtValue = "";
    private static final String displayActionEventName = "displayActionEvent";
//    private static final String displayActionEventKey = "";
//    private static final String displayActionEventValue = "";
    private static final String title = "Leanplum test by Lina";
    private static final String message = "Is this a test";
    private static final String btnTxtAllow = "YES";
    private static final String btnTxtCancel = "NO";


    @Test(groups = "adhoc")
    @Parameters({"udid", "port", "deviceName"})
    public void testStartCampaign(String udid, String port, String deviceName) throws Exception {
        RondoObjectFactory rondo =
                new RondoObjectFactory().getAndroidObject(udid, port, deviceName);
        rondo.log.startTest("Verify adhoc events");
        try {
            rondo.alert.closeAlertOnStart();
            rondo.log.startStep("Make sure we have UserId for the app");
            loadUserId(rondo);
            rondo.log.endStep();
            rondo.log.startStep("Send startCampaignEvent");
            rondo.navigation.goToAdHoc()
                            .typeEventName(startCampaignEventName)
                            .clickSendEventButton();
            rondo.log.endStep();
            rondo.log.startStep("Restart the app");
            rondo.nativeActions.reStartApp();
            rondo.alert.closeAlertOnStart();
            loadUserId(rondo);
            rondo.log.startStep("Send displayActionEvent");
            rondo.navigation.goToAdHoc()
                            .typeEventName(displayActionEventName)
                            .clickSendEventButton();
            rondo.log.endStep();
            rondo.alert.isAlertDisplayed();
            rondo.alert.verifyAlertOnStart(
                        title,
                        message,
                        btnTxtAllow);
            rondo.log.verifyTestStatus();
        } catch (Exception e) {
            rondo.log.endStep(false);
            throw e;
        } finally {
            rondo.log.endStep();
        }
    }

    public void loadUserId(RondoObjectFactory rondo) {
        if (rondo.home.isUserIdEmpty()) {
            rondo.home.openAppPicker()
                      .openTestApp();
        }
    }
}
