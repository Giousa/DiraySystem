package com.zmm.diary.ui.fragment;

import java.util.HashMap;

/**
 * Description:
 * Author:Giousa
 * Date:2016/12/8
 * Email:65489469@qq.com
 */
public class FragmentFactory {

    public static HashMap<Integer, BaseFragment> hashMap = new HashMap<>();

    public static BaseFragment createFragment(int position) {

        BaseFragment fragment = hashMap.get(position);
        if(fragment != null){
            return fragment;
        }else{
            switch (position) {
                case 0:
                    fragment = new HomeFragment();
                    break;
                case 1:
                    fragment = new CalendarFragment();
                    break;
                case 2:
                    fragment = new RecordFragment();
                    break;
                case 3:
                    fragment = new MyFragment();
                    break;
            }
            //集合将创建过的fragment,管理起来
            hashMap.put(position, fragment);
            return fragment;
        }
    }

}
