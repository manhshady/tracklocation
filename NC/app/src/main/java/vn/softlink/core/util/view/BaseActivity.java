package vn.softlink.core.util.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CompoundButton;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import vn.softlink.core.Logger;
import vn.softlink.core.R;
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
public abstract class BaseActivity extends AppCompatActivity {

    public interface Command {
        void block();
    }

    private Unbinder mUnBinder;
    private Logger mLogger;

    @LayoutRes
    protected abstract int getLayoutResource();

    @IdRes
    protected abstract int getFragmentContainer();


    /**
     * [AppCompatActivity] overriding
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        mUnBinder = ButterKnife.bind(this);
        mLogger = Logger.get(getClass());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != progressDialog) {
            progressDialog = null;
        }
        if (null != mUnBinder) try {
            mUnBinder.unbind();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * PERMISSION WRAPPER
     * WITH OVERRIDABLE METHOD onPermissionGranted(requestCode)
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
     * Widget events
     */
    private ProgressDialog progressDialog;

    public void showProgress() {
        if (null == progressDialog)
            progressDialog = new ProgressDialog(this);
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    public void hideProgress() {
        if (null != progressDialog && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public void toast(String charSequence) {
        AppUtil.showToast(charSequence);
    }

    public void toast(@StringRes int stringRes) {
        AppUtil.showToast(stringRes);
    }

    public AlertDialog.Builder buildAlert(String message) {
        return new AlertDialog.Builder(this)
                .setTitle(getString(R.string.app_name))
                .setMessage(message);
    }

    public void alertMessage(String message) {
        AlertDialog.Builder dialog = buildAlert(message);
        dialog.setPositiveButton("close", null);
        dialog.create().show();
    }

    public void alertMessage(@StringRes int message) {
        alertMessage(getString(message));
    }

    public <T extends Activity> void start(Class<T> cls) {
        startActivity(new Intent(this, cls));
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


    /**
     * Executor Utils
     */
    public void delay(Command command) {
        delay(command, 700);
    }

    public void delay(Command command, long time) {
        showProgress();
        new Handler().postDelayed(() -> {
            hideProgress();
            command.block();

        }, time);
    }


    /**
     * Fragment Utils
     */
    public void add(Fragment fragment) {
        FragmentUtil.addFragment(this, fragment, getFragmentContainer(), true);
    }

    public void add(Fragment fragment, boolean stack) {
        FragmentUtil.addFragment(this, fragment, getFragmentContainer(), stack);
    }

    public void replace(Fragment fragment) {
        FragmentUtil.replaceFragment(this, fragment, getFragmentContainer(), true);
    }

    public void replace(Fragment fragment, boolean stack) {
        FragmentUtil.replaceFragment(this, fragment, getFragmentContainer(), stack);
    }


    /**
     * Widget Utils
     */
    public void setText(@IdRes int viewRes, String string) {
        ((TextView) findViewById(viewRes)).setText(string);
    }

    public void setText(@IdRes int viewRes, @StringRes int stringRes) {
        ((TextView) findViewById(viewRes)).setText(stringRes);
    }

    public void setChecked(@IdRes int viewRes, boolean checked) {
        ((CompoundButton) findViewById(viewRes)).setChecked(checked);
    }

    public void configRecycler(@IdRes int viewRes, RecyclerView.Adapter adapter) {
        configRecycler(viewRes, adapter, LinearLayoutManager.VERTICAL, false);
    }

    public void configRecycler(@IdRes int viewRes, RecyclerView.Adapter adapter, int orientation, boolean reservedLayout) {
        RecyclerView recyclerView = findViewById(viewRes);
        LinearLayoutManager llm = new LinearLayoutManager(this, orientation, reservedLayout);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
    }

    public void configRecycler(@IdRes int viewRes, RecyclerView.Adapter adapter, int column) {
        RecyclerView recyclerView = findViewById(viewRes);
        recyclerView.setLayoutManager(new GridLayoutManager(this, column));
        recyclerView.setAdapter(adapter);
    }

    public int color(@ColorRes int colorRes) {
        return ContextCompat.getColor(this, colorRes);
    }

    public Drawable drawable(@DrawableRes int drawableRes) {
        return ContextCompat.getDrawable(this, drawableRes);
    }
}
