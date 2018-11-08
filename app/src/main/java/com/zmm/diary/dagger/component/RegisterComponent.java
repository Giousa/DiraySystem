package com.zmm.diary.dagger.component;


import com.zmm.diary.dagger.ActivityScope;
import com.zmm.diary.dagger.module.RegisterModule;
import com.zmm.diary.ui.activity.RegisterActivity;

import dagger.Component;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/10/31
 * Email:65489469@qq.com
 */
@ActivityScope
@Component(modules = RegisterModule.class,dependencies = HttpComponent.class)
public interface RegisterComponent {

    void inject(RegisterActivity activity);

}
