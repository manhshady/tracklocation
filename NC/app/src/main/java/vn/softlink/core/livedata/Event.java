package vn.softlink.core.livedata;

import android.support.annotation.Nullable;

/**
 * --------------------------------------------------------------------------------
 *
 * @Project: pimp
 * @Created: Huy QV 2018/09/27
 * @Description: ...
 * All Right Reserved.
 * --------------------------------------------------------------------------------
 */
public class Event<T> {

    private T mData;

    public Event(T data) {
        this.mData = data;
    }

    // Read only
    private volatile boolean hasBeenHandled = false;

    @Nullable
    public Event<T> getContentIfNotHandled() {
        // Returns the key and prevents its use again.
        return hasBeenHandled ? null : this;
    }

    public T getData() {
        return mData;
    }

    public void setData(T content) {
        this.mData = content;
    }

}
