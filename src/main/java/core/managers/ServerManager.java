package core.managers;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import settings.Settings;

public class ServerManager {

    private final Settings settings;
    private AppiumDriverLocalService service;
    private static String ANDROID_HOME;

    public ServerManager(Settings settings) {
        this.settings = settings;
    }

    public static String getAndroidHome(){
        if (ANDROID_HOME == null)
            ANDROID_HOME = System.getenv("ANDROID_HOME");
        if(ANDROID_HOME == null) throw new RuntimeException(
                "Failed to find ANDROID_HOME. Make sure the environment variable is set");
        return ANDROID_HOME;
    }

    public void start() {
        String logLevel = settings.isDebug() ? "debug" : "warn";
        AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder()
                .usingAnyFreePort()
                .withArgument(GeneralServerFlag.LOG_LEVEL, logLevel)
                .withArgument(GeneralServerFlag.RELAXED_SECURITY);

        // Start Appium Server
        service = AppiumDriverLocalService.buildService(serviceBuilder);
        service.start();
    }

    public URL getUrl() {
        return service.getUrl();
    }

    public void stop() {
        if (service != null) {
            service.stop();
        }
    }

    public static String run (String cmd) {
        String output = null;
        try {
            Scanner scanner = new Scanner(Runtime.getRuntime().exec(cmd).getInputStream())
                    .useDelimiter("\\A");
            if (scanner.hasNext())
                output = scanner.next();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return output;
    }

}
