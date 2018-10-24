package vn.softlink.core.livedata;

/**
 * --------------------------------------------------------------------------------
 *
 * @Project: Pimp
 * @Created: Huy QV 2018/09/16
 * @Description: ...
 * All Right Reserved.
 * --------------------------------------------------------------------------------
 */
public class ProgressEvent extends EventLiveData<Boolean> {

    private static ProgressEvent sInstance;
    private static volatile boolean processing;

    public static synchronized ProgressEvent getInstance() {
        if (sInstance == null) {
            sInstance = new ProgressEvent();
        }
        return sInstance;
    }

    public static void startProcess() {
        if (false == getInstance().processing) {
            getInstance().processing = true;
            getInstance().set(true);
        }
    }

    public static void endProcess() {
        if (true == getInstance().processing) {
            getInstance().processing = false;
            getInstance().set(false);
        }
    }
}
