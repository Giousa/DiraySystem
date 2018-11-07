package com.zmm.diary.ui.activity;

import android.os.Bundle;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.zmm.diary.R;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.utils.SharedPreferencesUtil;
import com.zmm.diary.utils.config.CommonConfig;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/12
 * Time:下午4:15
 */

public class SplashActivity extends BaseActivity {


    @BindView(R.id.sub_image)
    SubsamplingScaleImageView mSubImage;

    @Override
    protected int setLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void setupActivityComponent(HttpComponent httpComponent) {

    }

    @Override
    protected void init() {

        mSubImage.setImage(ImageSource.resource(R.drawable.splash_bg));


        Observable.timer(2, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {

                boolean isLogin = SharedPreferencesUtil.getBoolean(CommonConfig.LOGIN_STATUS, false);

                if (isLogin) {
                    startActivity(MainActivity.class,true);
                } else {
                    startActivity(LoginActivity.class,true);
                }

            }
        });


    }
}
