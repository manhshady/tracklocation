package vn.softlink.core.util.handler;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

/**
 * *******************************************************************
 *
 * @Project: Core
 * @Created: Huy QV 2018/10/09
 * @Description: ...
 * All Right Reserved.
 * *******************************************************************
 */
public abstract class DataGenerator<T> extends HandlerThread {

    protected abstract T onDataGenerate();

    private volatile boolean generating = false;

    private Runnable generator;

    private Handler handler;

    private DataHandler<T> uiHandler = new DataHandler<>();

    public DataGenerator(DataReceiver<T> receiver, long interval, String name) {
        super(name);
        uiHandler.attach(receiver);
        generator = new Runnable() {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                msg.obj = onDataGenerate();
                msg.sendToTarget();
                handler.postDelayed(this, interval);
            }
        };
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        handler = getHandler(this.getLooper());
    }

    public Handler getHandler(Looper looper) {
        return new Handler(looper) {
            @Override
            public void handleMessage(Message msg) {
                Message message = uiHandler.obtainMessage();
                message.obj = onDataGenerate();
                message.sendToTarget();
            }
        };
    }

    public void startGenerating() {
        if (null != handler && !generating) {
            generating = true;
            handler.post(generator);
        }

    }

    public void pauseGenerating() {
        if (null != handler) {
            generating = false;
            handler.removeCallbacks(generator);
        }
    }

    public void stopGenerating() {
        pauseGenerating();
        quit();
    }

    public boolean isGenerating() {
        return generating;
    }

}