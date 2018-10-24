package vn.softlink.core.util.view;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import vn.softlink.core.Logger;

/**
 * *******************************************************************
 *
 * @Project: Core
 * @Created: Huy QV 2018/10/09
 * @Description: ...
 * All Right Reserved.
 * *******************************************************************
 */
public abstract class BaseFragment extends Fragment {

    private Unbinder mUnBinder;
    private Logger mLogger;

    @LayoutRes
    protected abstract int getLayoutResource();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLogger = Logger.get(getClass());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResource(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != mUnBinder) try {
            mUnBinder.unbind();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Permission handle wrapper
     * with overridable method onPermissionGranted(requestCode)
     */
    protected void onPermissionGranted(int requestCode) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length == 0){
            return;
        }
        for (int grant : grantResults) {
            if (grant != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        onPermissionGranted(requestCode);
    }


    /**
     * UI Events
     */
    public void showProgress() {
        getBaseActivity().showProgress();
    }

    public void hideProgress() {
        getBaseActivity().hideProgress();
    }

    public void toast(String charSequence) {
        getBaseActivity().toast(charSequence);
    }

    public void toast(@StringRes int stringRes) {
        getBaseActivity().toast(stringRes);
    }

    public void alertMessage(String message) {
        getBaseActivity().alertMessage(message);
    }

    public void alertMessage(@StringRes int message) {
        getBaseActivity().alertMessage(message);
    }


    /**
     * Log Utils
     */
    public void debug(String string) {
        mLogger.debug(string);
    }

    public void info(String string) {
        mLogger.info(string);
    }

    public void error(String string) {
        mLogger.error(string);
    }

    public void watch(String string) {
        mLogger.watch(string);
    }

    public void wtf(String string) {
        mLogger.wtf(string);
    }

    public <T extends Activity> void start(Class<T> cls) {
        startActivity(new Intent(getContext(), cls));
    }

    /**
     * Fragment Utils
     */
    public void add(Fragment fragment) {
        getBaseActivity().add(fragment);
    }

    public void add(Fragment fragment, boolean stack) {
        getBaseActivity().add(fragment, stack);
    }

    public void replace(Fragment fragment) {
        getBaseActivity().replace(fragment);
    }

    public void replace(Fragment fragment, boolean stack) {
        getBaseActivity().replace(fragment, stack);
    }

    /**
     * Widget Utils
     */
    public void setText(@IdRes int viewRes, String string) {
        ((TextView) getView().findViewById(viewRes)).setText(string);
    }

    public void setText(@IdRes int viewRes, @StringRes int stringRes) {
        ((TextView) getView().findViewById(viewRes)).setText(stringRes);
    }

    public void setChecked(@IdRes int viewRes, boolean checked) {
        ((CompoundButton) getView().findViewById(viewRes)).setChecked(checked);
    }

    public void configRecycler(@IdRes int viewRes, RecyclerView.Adapter adapter) {
        configRecycler(viewRes, adapter, LinearLayoutManager.VERTICAL, false);
    }

    public void configRecycler(@IdRes int viewRes, RecyclerView.Adapter adapter, int orientation, boolean reservedLayout) {
        RecyclerView recyclerView = getView().findViewById(viewRes);
        LinearLayoutManager llm = new LinearLayoutManager(getContext(), orientation, reservedLayout);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        recyclerView.setAdapter(adapter);
    }

    public void configRecycler(@IdRes int viewRes, RecyclerView.Adapter adapter, int column) {
        RecyclerView recyclerView = getView().findViewById(viewRes);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), column));
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    public int color(@ColorRes int colorRes) {
        return ContextCompat.getColor(getContext(), colorRes);
    }

    public Drawable drawable(@DrawableRes int drawableRes) {
        return ContextCompat.getDrawable(getContext(), drawableRes);
    }

    public BaseActivity getBaseActivity() {
        if (getActivity() instanceof BaseActivity) {
            return (BaseActivity) getActivity();
        }
        throw new ClassCastException("BaseFragment must be add on BaseActivity");
    }

}
