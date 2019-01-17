package com.zmm.diary.dagger.component;

import com.zmm.diary.dagger.ActivityScope;
import com.zmm.diary.dagger.module.CorrelateModule;
import com.zmm.diary.ui.activity.CorrelateActivity;
import com.zmm.diary.ui.activity.CorrelateAllActivity;

import dagger.Component;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/12
 * Email:65489469@qq.com
 */
@ActivityScope
@Component(modules = CorrelateModule.class,dependencies = HttpComponent.class)
public interface CorrelateComponent {

    void inject(CorrelateActivity activity);

    void inject(CorrelateAllActivity allActivity);
}
