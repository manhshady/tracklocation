package vn.softlink.core.util.view;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * *******************************************************************
 *
 * @Project: Core
 * @Created: Huy QV 2018/10/09
 * @Description: ...
 * All Right Reserved.
 * *******************************************************************
 */
public class FragmentUtil {

    private interface Command {
        void exec(FragmentTransaction frmTransaction, String tag);
    }

    public static void addFragment(AppCompatActivity activity, Fragment fragment, @IdRes int container, boolean stack) {

        startTransaction(activity, fragment, (frmTransaction, tag) -> {

            frmTransaction.add(container, fragment, tag);
            if (stack) {
                frmTransaction.addToBackStack(tag);
            }
        });
    }

    public static void replaceFragment(AppCompatActivity activity, Fragment fragment, @IdRes int container, boolean stack) {

        startTransaction(activity, fragment, (frmTransaction, tag) -> {

            frmTransaction.replace(container, fragment, tag);
            if (stack) {
                frmTransaction.addToBackStack(tag);
            }
        });
    }

    private static void startTransaction(AppCompatActivity activity, Fragment fragment, Command command) {

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
       /* fragmentTransaction.setCustomAnimations(
                R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right
        );*/
        String tag = fragment.getClass().getSimpleName();
        command.exec(fragmentTransaction, tag);
        fragmentTransaction.commitAllowingStateLoss();
    }

}

