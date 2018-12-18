package com.zmm.diary.dagger.component;

import com.zmm.diary.dagger.ActivityScope;
import com.zmm.diary.dagger.module.RecordModule;
import com.zmm.diary.ui.activity.RecordInfoActivity;
import com.zmm.diary.ui.fragment.RecordFragment;

import dagger.Component;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/12
 * Email:65489469@qq.com
 */
@ActivityScope
@Component(modules = RecordModule.class,dependencies = HttpComponent.class)
public interface RecordComponent {

    void inject(RecordFragment fragment);

    void inject(RecordInfoActivity activity);
}