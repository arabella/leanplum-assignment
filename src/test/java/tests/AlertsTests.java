package tests;
import factory.RondoObjectFactory;
import core.managers.TestManager;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class AlertsTests extends TestManager {

    private static final String title = "Alert on start";
    private static final String message = "Alert displayed on app start";
    private static final String btnTxt = "ТУК Е!";

    @Test(groups = "alerts")
    @Parameters({"udid", "port", "deviceName"})
    public void alertOnStartTest(String udid, String port, String deviceName) throws Exception {
        RondoObjectFactory rondo =
                new RondoObjectFactory().getAndroidObject(udid, port, deviceName);
        rondo.log.startTest("Check alert box");
        try {
            rondo.log.startStep("Check alert box");
            rondo.alert.verifyAlertOnStart(title, message, btnTxt);
            rondo.alert.closeAlertOnStart();
            rondo.log.endStep();
            rondo.log.verifyTestStatus();
        } catch (Exception e) {
            rondo.log.endStep(false);
            throw e;
        } finally {
            rondo.log.endStep();
        }
    }
}
