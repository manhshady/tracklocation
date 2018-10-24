package vn.softlink.core.util.handler;


import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

import vn.softlink.core.util.android.AppUtil;

/**
 * *******************************************************************
 *
 * @Project: Core
 * @Created: Huy QV 2018/10/09
 * @Description: ...
 * All Right Reserved.
 * *******************************************************************
 */
public class DataHandler<T> extends Handler {

    private WeakReference<DataReceiver<T>> dataReceiverRef;

    public void attach(DataReceiver<T> dataReceiver) {
        dataReceiverRef = new WeakReference<>(dataReceiver);
    }

    @Override
    public void handleMessage(Message msg) {
        T data = (T) msg.obj;
        if (null == dataReceiverRef) {
            AppUtil.showToast("DataReceiver not initialized");
            return;
        }
        dataReceiverRef.get().onDataReceived(data);
    }
}
