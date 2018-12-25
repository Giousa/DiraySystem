package com.zmm.diary.dagger.module;

import com.zmm.diary.http.ApiService;
import com.zmm.diary.mvp.model.HotspotModel;
import com.zmm.diary.mvp.presenter.contract.HotspotContract;
import com.zmm.diary.ui.adapter.HotspotAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/12/4
 * Email:65489469@qq.com
 */

@Module
public class HotspotModule {


    private HotspotContract.HotspotView mHotspotView;

    public HotspotModule(HotspotContract.HotspotView hotspotView) {
        mHotspotView = hotspotView;
    }


    @Provides
    public HotspotContract.HotspotView provideHotspotView(){
        return mHotspotView;
    }

    @Provides
    public HotspotContract.IHotspotModel provideHotspotModel(ApiService apiService){
        return new HotspotModel(apiService);
    }

    @Provides
    public HotspotAdapter provideHotspotAdapter(){
        return new HotspotAdapter();
    }

}
