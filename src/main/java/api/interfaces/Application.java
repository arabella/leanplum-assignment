package api.interfaces;

public interface Application {

    void forceStopApp();

    void forceStop();
    void clearData();
    Object open();
    String packageID();
    String activityID();
}
