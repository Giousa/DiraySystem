package com.zmm.diary.dagger.component;


import com.zmm.diary.dagger.ActivityScope;
import com.zmm.diary.dagger.module.LoginModule;

import dagger.Component;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/10/31
 * Email:65489469@qq.com
 */
@ActivityScope
@Component(modules = LoginModule.class,dependencies = HttpComponent.class)
public interface LoginComponent {

}
