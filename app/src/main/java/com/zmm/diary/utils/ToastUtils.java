package com.zmm.diary.utils;

import android.widget.Toast;

import com.zmm.diary.MyApplication;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2017/3/17
 * Time:下午1:28
 */

public class ToastUtils {

    private static Toast toast;

    public static void SimpleToast(String string){
        if (toast==null){
            toast= Toast.makeText(MyApplication.getContext(), string, Toast.LENGTH_SHORT);
        }else{
            toast.setText(string);
        }
        toast.show();
    }
}
