package com.googleplay.fragment;

import android.support.v4.app.Fragment;
import android.util.SparseArray;

/**
 * @author TanJJ
 * @time 2018/6/24 8:54
 * @des Fragment管理
 */

public class FragmentList {
    // 防止多次加载
    private static SparseArray<Fragment> mFragmentSparseArray = new SparseArray<>();

    public static Fragment getFragment(int position) {
        Fragment fragment = mFragmentSparseArray.get(position);
        if (fragment == null) {
            switch (position) {
                case 0:
                    fragment = new HomeFragment();
                    break;
                case 1:
                    fragment = new HomeFragment();
                    break;
                case 2:
                    fragment = new HomeFragment();
                    break;
                case 3:
                    fragment = new HomeFragment();
                    break;
                case 4:
                    fragment = new HomeFragment();
                    break;
                case 5:
                    fragment = new HomeFragment();
                    break;
                default:
                    break;
            }
            mFragmentSparseArray.append(position, fragment);
        }
        return fragment;
    }
}
