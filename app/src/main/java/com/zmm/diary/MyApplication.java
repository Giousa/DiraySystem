package com.zmm.diary;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.view.View;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.zmm.diary.bean.UserBean;
import com.zmm.diary.dagger.component.DaggerHttpComponent;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.dagger.module.HttpModule;
import com.zmm.diary.ui.activity.BaseActivity;
import com.zmm.diary.ui.widget.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/10/31
 * Email:65489469@qq.com
 */
public class MyApplication extends Application {


    //全局上下文环境
    private static Context mContext;
    //全局的handler
    private static Handler mHandler;
    //主线程
    private static Thread mMainThread;
    //主线程id
    private static int mMainThreadId;

    public static UserBean userBean;

    private List<BaseActivity> mBaseActivityList;

    private HttpComponent mHttpComponent;

    public HttpComponent getHttpComponent() {
        return mHttpComponent;
    }

    public static MyApplication get(Context context){
        return (MyApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mHttpComponent = DaggerHttpComponent.builder().httpModule(new HttpModule()).build();

        mContext = getApplicationContext();

        mHandler = new Handler();

        mMainThread = Thread.currentThread();

        mMainThreadId = android.os.Process.myTid();

        mBaseActivityList = new ArrayList<>();

        initPhoto();
    }



    /**
     * 初始化拍照
     */
    private void initPhoto() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setMultiMode(false);//单选或多选模式
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.CIRCLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }



    /**
     * 添加Activity
     */
    public void addActivity_(BaseActivity activity) {
        if (!mBaseActivityList.contains(activity)) {
            mBaseActivityList.add(activity);//把当前Activity添加到集合中
        }
    }

    /**
     * 销毁单个Activity
     */
    public void removeActivity_(BaseActivity activity) {
        if (mBaseActivityList.contains(activity)) {
            mBaseActivityList.remove(activity);//从集合中移除
            activity.finish();//销毁当前Activity
        }
    }


    /**
     * 销毁全部Activity
     */
    public void removeAllActivity_(){
        for (BaseActivity activity:mBaseActivityList) {
            activity.finish();
        }
    }


    public static Context getContext() {
        return mContext;
    }

    public static Handler getHandler() {
        return mHandler;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

    public static int getMainThreadId() {
        return mMainThreadId;
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
