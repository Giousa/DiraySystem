package com.zmm.diary.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/8/30
 * Email:65489469@qq.com
 */
public class VersionUtils {

    /**
     * get App versionCode
     * @param context
     * @return
     */
    public static int getVersionCode(Context context){
        PackageManager packageManager=context.getPackageManager();
        PackageInfo packageInfo;
        int versionCode = 1;
        try {
            packageInfo=packageManager.getPackageInfo(context.getPackageName(),0);
            versionCode=packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * get App versionName
     * @param context
     * @return
     */
    public static String getVersionName(Context context){
        PackageManager packageManager=context.getPackageManager();
        PackageInfo packageInfo;
        String versionName="";
        try {
            packageInfo=packageManager.getPackageInfo(context.getPackageName(),0);
            versionName=packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

}
