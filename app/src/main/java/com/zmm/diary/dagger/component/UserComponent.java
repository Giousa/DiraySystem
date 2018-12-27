package com.zmm.diary.dagger.component;

import com.zmm.diary.dagger.ActivityScope;
import com.zmm.diary.dagger.module.UserModule;
import com.zmm.diary.ui.activity.UserInfoActivity;
import com.zmm.diary.ui.fragment.MyFragment;

import dagger.Component;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/12
 * Email:65489469@qq.com
 */
@ActivityScope
@Component(modules = UserModule.class,dependencies = HttpComponent.class)
public interface UserComponent {

    void inject(MyFragment fragment);

    void inject(UserInfoActivity activity);
}
