package com.bwei.like.yunifang.factory;

import android.support.v4.app.Fragment;

import com.bwei.like.yunifang.fragment.CartFragment;
import com.bwei.like.yunifang.fragment.CategoryFragment;
import com.bwei.like.yunifang.fragment.HomeFragment;
import com.bwei.like.yunifang.fragment.MineFragment;

import java.util.HashMap;

/**
 * Created by LiKe on 2016/11/28.
 */
public class FragmentFactory {
    //创建集合
    private static HashMap<Integer, Fragment> fragmentHashMap = new HashMap<>();

    //创建静态方法
    public static Fragment getFragment(int position) {
        Fragment fragment = fragmentHashMap.get(position);
        if (fragment != null) {
            return fragment;
        }

        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;

            case 1:
                fragment = new CategoryFragment();
                break;

            case 2:
                fragment = new CartFragment();
                break;

            case 3:
                fragment = new MineFragment();
                break;
        }

        fragmentHashMap.put(position, fragment);
        return fragment;
    }
}
