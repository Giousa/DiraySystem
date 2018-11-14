package com.zmm.diary.dagger.component;

import com.zmm.diary.dagger.ActivityScope;
import com.zmm.diary.dagger.module.NoteModule;
import com.zmm.diary.ui.activity.DiaryInfoActivity;
import com.zmm.diary.ui.fragment.HomeFragment;

import dagger.Component;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/12
 * Email:65489469@qq.com
 */
@ActivityScope
@Component(modules = NoteModule.class,dependencies = HttpComponent.class)
public interface NoteComponent {

    void inject(DiaryInfoActivity activity);

    void inject(HomeFragment fragment);
}
