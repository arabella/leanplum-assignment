package core.managers;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import settings.Settings;

public class TestManager {

    protected static Settings settings;
    protected static AppiumDriver<MobileElement> driver;
    private static DriverManager client;
    private static ServerManager server;
    private String logPrefix;
    private String fullTestName;
    private String device;
    private String methodName;

    protected String logPrefix() {
        return logPrefix;
    }

    protected String methodName() {
        return methodName;
    }

    protected String deviceName() {
        return device;
    }

    protected String fullTestName() {
        return fullTestName;
    }


    @Parameters({"udid", "port", "deviceName"})
    @BeforeSuite(alwaysRun = true)
    public static void beforeAll(
            @Optional String udid,
            @Optional String port,
            @Optional String deviceName) {
        settings = new Settings();
        server = new ServerManager(settings);
        server.start();
        client = new DriverManager(settings);
        client.createDriver(server.getUrl(), udid, port, deviceName);
    }

    @Parameters({"deviceName"})
    @BeforeMethod(alwaysRun = true)
    public void initTestContext(String deviceName, Method method) {
        device = deviceName;
        methodName = method.getName();
        fullTestName = method.getDeclaringClass() + "." + methodName;
        logPrefix = String.format("[%s:%s]", device, methodName);
        System.out.println(String.format("[%s:%s:START]", device, methodName));
    }

    @AfterMethod(alwaysRun = true)
    protected void printException(ITestResult result) {
        if (result.getThrowable() != null) {
            try (StringWriter stringWriter = new StringWriter();
                 PrintWriter writer = new PrintWriter(stringWriter)) {
                result.getThrowable().printStackTrace(writer);
                System.out.println(
                        String.format("[%s:%s:EXCEPTION] %s",
                                      device,
                                      methodName,
                                      stringWriter.toString()));
            } catch (IOException e) {
                e.printStackTrace(System.out);
            }
        }
        System.out.println(String.format("[%s:%s:FINISHED]", device, methodName));
    }

    @AfterClass
    public static void afterAll() {
        client.killDriver();
        server.stop();
    }

}