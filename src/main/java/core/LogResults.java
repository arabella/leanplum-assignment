package core;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;

import io.appium.java_client.AppiumDriver;

public class LogResults {

    private @Nonnull String stepDescription;
    public boolean testStatus;
    private final @Nonnull AppiumDriver<?> driver;
    private final @Nullable String deviceSettings;
    private @Nonnull String stackTraceString = "";
    private @Nonnull String stepName = "";
    private static final File TEST_OUTPUT_DIR = new File("test-output/");
    private static final String SCREENSHOT = "screenshot/";
    private final boolean debug;

    public LogResults(@Nonnull AppiumDriver<?> driver, @Nullable String deviceSettings) {
        this(driver, deviceSettings, false);
    }

    public LogResults(
            @Nonnull AppiumDriver<?> driver,
            @Nullable String deviceSettings,
            boolean debug) {

        this.deviceSettings = deviceSettings;
        this.driver = driver;
        this.debug = debug;
    }

    public LogResults(@Nonnull AppiumDriver<?> driver) {
        this(driver, null);
    }

    // Login reports
    public void startTest(@Nonnull String description) {
        Reporter.log(String.format(
                "<h2><font color='DarkBlue'>Device:[%s] </font>Description:[%s]</h2>"
                + " - test started at: [%s]",
                deviceSettings,
                description,
                new Timestamp(System.currentTimeMillis())));
        Reporter.log("<ol type='1'>");
        testStatus = true;
    }

    public void assertStep(@Nonnull String step) {
        stepName = step;
        stepDescription = "<b><big><font color='DarkBlue'>" + step + "</font></b></big>";
    }

    public void endTest() {

        Reporter.log("</ol>");
    }

    public void startStep(@Nonnull String step) {
        stepName = step;
        stepDescription = step;
    }

    public void endStep(boolean condition) {
        String logLine = debug ? "<b>" : "";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String picName = (timestamp.toString()) + ".png";
        final String testStep =
                getWhoIsCallingMe(getClass().getName() + "." + "endStep").toString()
                + stepName;

        String relativePath = SCREENSHOT + testStep;

        if (condition) {
            logLine += String.format(
                    "<li> [%s] %s...<b><font color='green'>OK</font></b>",
                    timestamp,
                    stepDescription);
            if (debug) {
                logLine += addScreenshot(picName, relativePath);
            }
        } else {
            logLine = String.format(
                    "<li><b><font color='red'> [%s] </font><big>%s</big>..."
                    + "<font color='red'>FAILED!</font></b>",
                    timestamp,
                    stepDescription);
            logLine += addScreenshot(relativePath, picName);

            testStatus = false;
            stackTraceString += "\n" + "failed step: [" + stepName  + "] with reason: " + testStep;
        }

        logLine += "</li>" + (debug ? "</b>" : "");
        Reporter.log(logLine);
    }

    private @Nonnull String addScreenshot(@Nonnull String path, @Nonnull String picName) {
        takeScreen(new File(TEST_OUTPUT_DIR, path), picName);
        return String.format(
                "<div class=\"content\"><img height=\"400\" src=\"%s\"/></div>",
                path + "/" + picName);
    }

    public void endStep() {

        endStep(true);
    }

    public void verifyTestStatus() throws Exception {
        assertStep("Test Status ");
        if (!testStatus) {
            throw new Exception(stackTraceString);
        } else {
            endStep();
        }
        stackTraceString = "";
    }

    public void takeScreen(@Nonnull File destDir, @Nonnull String name) {
        try {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            if (!destDir.exists()) {
                if (!destDir.mkdirs()) {
                    throw new IllegalStateException(
                            "Cannot create directory [" + destDir + "]");
                }
            }
            FileUtils.copyFile(scrFile, new File(destDir, name));
        } catch (IOException e) {
            e.printStackTrace(System.out);
            throw new RuntimeException(e);
        }
    }


    public void msg(@Nonnull String msg) {
        msg(msg, true);
    }

    public void msg(@Nonnull String msg, boolean result) {
        startStep(msg);
        endStep(result);
    }

    public static StackTraceElement getWhoIsCallingMe(String currentMethod) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        int section = 0;
        for (int i = 1; i < stackTrace.length; i++) {
            if (currentMethod.equals(stackTrace[i].getClassName() + "." + stackTrace[i].getMethodName())) {
                section = i + 1;
                break;
            }
        }
        if (section == 0) {
            throw new IllegalArgumentException(
                    "There is no method \"" + currentMethod + "\" in the current " + "StackTrace.");
        }
        return stackTrace[section];
    }
}

