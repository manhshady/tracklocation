package vn.softlink.core.util.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * *******************************************************************
 *
 * @Project: Core
 * @Created: Huy QV 2018/10/11
 * @Description: ...
 * All Right Reserved.
 * *******************************************************************
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();

    public FragmentAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public FragmentAdapter add(Fragment fragment) {
        fragments.add(fragment);
        return this;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
