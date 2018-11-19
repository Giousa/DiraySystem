package com.zmm.diary.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.zmm.diary.MyApplication;
import com.zmm.diary.R;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.mvp.presenter.BasePresenter;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/3
 * Email:65489469@qq.com
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    private Unbinder mUnbinder;
    protected MyApplication mMyApplication;

    private BaseActivity mBaseActivity;

    public int mScreenWidth;

    public Context mContext;


    @Inject
    T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

        super.onCreate(savedInstanceState);

        setContentView(setLayout());

        System.out.println("-------------胜楠添加的");
        System.out.println("-------------胜楠又添加的");
<<<<<<< HEAD
        System.out.println("-========胜楠");
=======
        System.out.println("----------hhhhh");
        
>>>>>>> 0eb3fc0d797de8cb7393cdc56c5a8ed3dc1eddda
        mContext = this;

        mUnbinder = ButterKnife.bind(this);

        mScreenWidth = getScreenWidth();

        mBaseActivity = this;

        mMyApplication = (MyApplication) getApplication();

        addActivity();

        setupActivityComponent(mMyApplication.getHttpComponent());

        init();

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity();
        if (mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
    }

    protected abstract int setLayout();

    protected abstract void setupActivityComponent(HttpComponent httpComponent);

    protected abstract void init();


    protected void startActivity(Class activity) {

        startActivity(activity, false);
    }

    protected void startActivity(Class activity, boolean flag) {

        Intent intent = new Intent(this, activity);
        startActivity(intent);

        if (flag) {
            finish();
        }

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

    }


    public void addActivity() {
        mMyApplication.addActivity_(mBaseActivity);
    }

    public void removeActivity() {
        mMyApplication.removeActivity_(mBaseActivity);
    }

    public void removeAllActivity() {
        mMyApplication.removeAllActivity_();
    }


    public int getScreenWidth() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }


    /**
     * 让屏幕变暗
     */
    protected void makeWindowDark() {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.5f;
        window.setAttributes(lp);
    }

    /**
     * 让屏幕变亮
     */
    protected void makeWindowLight() {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 1f;
        window.setAttributes(lp);
    }


    protected void hideKeyboard(EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isActive(editText)){
            editText.requestFocus();
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    protected void showKeyboard(EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            editText.requestFocus();
            inputMethodManager.showSoftInput(editText,InputMethodManager.SHOW_FORCED);

        }
    }

}
