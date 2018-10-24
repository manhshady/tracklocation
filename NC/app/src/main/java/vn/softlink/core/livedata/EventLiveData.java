package vn.softlink.core.livedata;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.Nullable;

/**
 * --------------------------------------------------------------------------------
 * @Project: Pimp
 * @Created: Huy QV 2018/09/16
 * @Description: Shared data between fragment and fragment, fragment and activity
 * All Right Reserved.
 * --------------------------------------------------------------------------------
 */
public class EventLiveData<T> extends MutableLiveData<Event<T>> {

    public void set(@Nullable T model) {
        super.setValue(new Event(model));
    }

    @Nullable
    public T get(){
        return getValue().getData();
    }

    public void active() {
        super.setValue(getValue());
    }

}
