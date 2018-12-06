package com.zmm.diary.dagger.module;

import com.zmm.diary.http.ApiService;
import com.zmm.diary.mvp.model.RecordModel;
import com.zmm.diary.mvp.presenter.contract.RecordContract;
import com.zmm.diary.ui.adapter.RecordAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/12/4
 * Email:65489469@qq.com
 */

@Module
public class RecordModule {


    private RecordContract.RecordView mRecordView;

    public RecordModule(RecordContract.RecordView recordView) {
        mRecordView = recordView;
    }


    @Provides
    public RecordContract.RecordView provideRecordView(){
        return mRecordView;
    }

    @Provides
    public RecordContract.IRecordModel provideRecordModel(ApiService apiService){
        return new RecordModel(apiService);
    }

    @Provides
    public RecordAdapter provideRecordAdapter(){
        return new RecordAdapter();
    }
}
