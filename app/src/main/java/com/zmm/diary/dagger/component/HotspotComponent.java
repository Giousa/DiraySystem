package com.zmm.diary.dagger.component;

import com.zmm.diary.dagger.ActivityScope;
import com.zmm.diary.dagger.module.HotspotModule;
import com.zmm.diary.ui.activity.HotspotActivity;
import com.zmm.diary.ui.activity.HotspotDetailActivity;
import com.zmm.diary.ui.activity.HotspotInfoActivity;
import com.zmm.diary.ui.fragment.HotspotFragment;

import dagger.Component;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/12
 * Email:65489469@qq.com
 */
@ActivityScope
@Component(modules = HotspotModule.class,dependencies = HttpComponent.class)
public interface HotspotComponent {

    void inject(HotspotActivity activity);

    void inject(HotspotDetailActivity activity);

    void inject(HotspotInfoActivity activity);

    void inject(HotspotFragment fragment);

}
