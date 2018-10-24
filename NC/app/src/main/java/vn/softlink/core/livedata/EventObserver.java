package vn.softlink.core.livedata;

import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * --------------------------------------------------------------------------------
 *
 * @Project: pimp
 * @Created: Huy QV 2018/09/25
 * @Description: ...
 * All Right Reserved.
 * --------------------------------------------------------------------------------
 */
public abstract class EventObserver<T> implements Observer<Event<T>> {

    protected abstract void onEvent(@NonNull T data);

    @Override
    public void onChanged(@Nullable Event<T> tEvent) {
        if (null != tEvent && null != tEvent.getContentIfNotHandled() && null != tEvent.getData()) {
            onEvent(tEvent.getData());
        }
    }
}
