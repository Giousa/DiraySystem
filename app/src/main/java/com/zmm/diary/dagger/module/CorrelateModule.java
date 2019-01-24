package com.zmm.diary.dagger.module;

import com.zmm.diary.http.ApiService;
import com.zmm.diary.mvp.model.CorrelateModel;
import com.zmm.diary.mvp.presenter.contract.CorrelateContract;
import com.zmm.diary.ui.adapter.CorrelateAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/12/4
 * Email:65489469@qq.com
 */

@Module
public class CorrelateModule {


    private CorrelateContract.CorrelateView mCorrelateView;
    private int mType;

    public CorrelateModule(CorrelateContract.CorrelateView correlateView,int type) {
        mCorrelateView = correlateView;
        mType = type;
    }

    @Provides
    public CorrelateContract.CorrelateView provideCorrelateView(){
        return mCorrelateView;
    }

    @Provides
    public CorrelateContract.ICorrelateModel provideCorrelateModel(ApiService apiService){
        return new CorrelateModel(apiService);
    }

    @Provides
    public CorrelateAdapter provideCorrelateAdapter(){
        return new CorrelateAdapter(mType);
    }

}
