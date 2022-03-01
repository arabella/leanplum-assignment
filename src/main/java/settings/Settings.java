package settings;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.Platform;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.util.Properties;

public class Settings {

    private final Properties properties;
    String propFileName = "config";

    public Settings() {
        properties = loadProperties(propFileName);
    }

    public String getDeviceName() {
        return properties.getProperty("deviceName");
    }

    public Platform getPlatform() {
        String platformString = properties.getProperty("platform");
        if (platformString.toLowerCase().contains("android")) {
            return Platform.ANDROID;
        } else if (platformString.toLowerCase().contains("ios")) {
            return Platform.IOS;
        } else {
            throw new RuntimeException(String.format("Unknown platform: %s", platformString));
        }
    }

    public double getPlatformVersion() {
        String platformVersionString = properties.getProperty("platformVersion");
        return Double.parseDouble(platformVersionString);
    }

    public String getUdid() {
        return properties.getProperty("udid");
    }

    public String getAvdName() {
        return properties.getProperty("avdName");
    }

    public String getAppPath() {
        String appPath = properties.getProperty("appPath");
        if (appPath != null && !appPath.toLowerCase().contains("http")) {
            appPath = System.getProperty("user.dir") + appPath;
            File f = new File(appPath);
            if (!f.exists() || f.isDirectory()) {
                throw new RuntimeException(String.format("Invalid appPath: %s", appPath));
            }
        }
        return appPath;
    }

    public String getAppPackage() {
        return properties.getProperty("appPackage");
    }

    public String getAppActivity() {
        return properties.getProperty("appActivity");
    }

    public String getAppName() {
        return properties.getProperty("appName");
    }


    public URL getAppiumHost() throws MalformedURLException {
        return new URL(properties.getProperty("appiumHost"));
    }

    public boolean isDebug() {
        return ManagementFactory.getRuntimeMXBean().getInputArguments().toString().contains("jdwp");
    }

    private Properties loadProperties(String propFileName) {
        ClassLoader classLoader = Settings.class.getClassLoader();
        InputStream resource = classLoader.getResourceAsStream(propFileName + ".properties");
        if (resource != null) {
            Properties properties = new Properties();
            try {
                properties.load(resource);
            } catch (IOException e) {
                String message = String.format(
                        "Fail to load properties from %s file.", propFileName  + ".properties");
                throw new RuntimeException(message);
            }
            return properties;
        } else {
            String message = String.format(
                    "Specified config file does not exist: %s", propFileName  + ".properties");
            throw new RuntimeException(message);
        }
    }

}