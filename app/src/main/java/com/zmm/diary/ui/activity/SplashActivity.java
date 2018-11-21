package com.zmm.diary.ui.activity;

import android.graphics.PointF;
import android.text.TextUtils;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.zmm.diary.R;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.utils.SharedPreferencesUtil;
import com.zmm.diary.utils.config.CommonConfig;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
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

        //加载图片，和初始加载比例
        mSubImage.setImage(ImageSource.resource(R.drawable.splash_bg),new ImageViewState(1.2f, new PointF(0, 0), 0));

        //禁止缩放
        mSubImage.setZoomEnabled(false);

        //禁止滑动
        mSubImage.setPanEnabled(false);



        Observable.timer(2, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong){

                String userJson = SharedPreferencesUtil.getString(CommonConfig.LOGIN_USER, null);

                if (TextUtils.isEmpty(userJson)) {
                    startActivity(LoginActivity.class,true);
                } else {
                    startActivity(MainActivity.class,true);
                }

            }
        });


    }
}
