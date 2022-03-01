package core;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.AutomationName;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static java.util.concurrent.TimeUnit.SECONDS;

@SuppressWarnings("unused")
public abstract class MobilePage {
    protected AppiumDriver<MobileElement> driver;
    protected WebDriverWait wait;
    protected LogResults log;

    public MobilePage(AppiumDriver<MobileElement> driver, LogResults log) {
        this.driver = driver;
        this.log = log;
        wait = new WebDriverWait(driver, 30);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public MobileElement findElement(By locator, Duration timeout) {
        try {
            driver.manage().timeouts().implicitlyWait(0, SECONDS);
            return (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            return null;
        } finally {
            driver.manage().timeouts().implicitlyWait(30, SECONDS);
        }
    }

    public MobileElement findElement(By locator) {
        return findElement(locator, Duration.ofSeconds(30));
    }

    public MobileElement findByText(String text, boolean exactMatch) {
        By locator;
        String automation = driver.getCapabilities().getCapability("automationName").toString();
        if (automation.equalsIgnoreCase(AutomationName.ANDROID_UIAUTOMATOR2)) {
            if (exactMatch) {
                locator = MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + text + "\")");
            } else {
                locator = MobileBy.AndroidUIAutomator("new UiSelector().textContains(\"" + text + "\")");
            }
        } else if (automation.equalsIgnoreCase(AutomationName.IOS_XCUI_TEST)) {
            if (exactMatch) {
                String exactPredicate = "name == \"" + text + "\" OR label == \"" + text + "\"";
                locator = MobileBy.iOSNsPredicateString(exactPredicate);
            } else {
                String containsPredicate = "name contains '" + text + "' OR label contains '" + text + "'";
                locator = MobileBy.iOSNsPredicateString(containsPredicate);
            }
        } else {
            throw new RuntimeException("Unsupported automation technology: " + automation);
        }
        return driver.findElement(locator);
    }

    public MobileElement findByText(String text) {
        return findByText(text, true);
    }

}
