package vn.softlink.core.mvvm;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import vn.softlink.core.Logger;
import vn.softlink.core.livedata.EventObserver;
import vn.softlink.core.livedata.ProgressEvent;
import vn.softlink.core.livedata.ToastEvent;
import vn.softlink.core.util.view.BaseActivity;


/**
 * *******************************************************************
 *
 * @Project: LocationNativeCore
 * @Created: Huy QV 2018/10/11
 * @Description: ...
 * All Right Reserved.
 * *******************************************************************
 */
public abstract class MVVMActivity<VM extends MVVMViewModel> extends BaseActivity
        implements LifecycleOwner {

    public VM mViewModel;

    /**
     * M.V.VM configuration
     */
    @NonNull
    protected abstract Class<VM> mainViewModel();

    protected abstract void onViewReady();

    protected abstract void onRegisterData();


    /**
     * [LifecycleOwner] implementation
     */
    private LifecycleRegistry mLifecycleRegistry;

    public LifecycleRegistry getLifecycleRegistry() {
        return mLifecycleRegistry;
    }

    public <T> void observe(LiveData<T> liveData, Observer<T> observer) {
        if (liveData == null)
            liveData = new MutableLiveData<>();
        liveData.observe(this, observer);
    }

    public <T extends MVVMViewModel> T getViewModel(Class<T> cls) {
        return ViewModelProviders.of(this).get(cls);
    }

    /**
     * [BaseActivity] overriding
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLifecycleRegistry = new LifecycleRegistry(this);
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);
        mViewModel = ViewModelProviders.of(this).get(mainViewModel());
        mViewModel.mLogger = Logger.get(mainViewModel());
        onViewReady();
        observeProgress();
        observeToast();
        onRegisterData();
        mViewModel.onStart();
    }

    public void observeProgress() {
        observe(ProgressEvent.getInstance(), new EventObserver<Boolean>() {
            @Override
            protected void onEvent(@NonNull Boolean data) {
                if (data) {
                    showProgress();
                } else {
                    hideProgress();
                }
            }
        });
    }

    public void observeToast() {
        observe(ToastEvent.getInstance(), new EventObserver<String>() {
            @Override
            protected void onEvent(@NonNull String data) {
                toast(data);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLifecycleRegistry.markState(Lifecycle.State.STARTED);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mLifecycleRegistry.markState(Lifecycle.State.STARTED);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLifecycleRegistry.markState(Lifecycle.State.RESUMED);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ProgressEvent.getInstance().removeObservers(this);
        mLifecycleRegistry.markState(Lifecycle.State.DESTROYED);
    }


}
