package com.zmm.diary.mvp.model;

import com.zmm.diary.bean.BaseBean;
import com.zmm.diary.bean.RecordBean;
import com.zmm.diary.http.ApiService;
import com.zmm.diary.mvp.presenter.contract.RecordContract;
import com.zmm.diary.mvp.presenter.contract.RecordContract.IRecordModel;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/12/4
 * Email:65489469@qq.com
 */
public class RecordModel implements IRecordModel{

    private ApiService mApiService;

    public RecordModel(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Observable<BaseBean<RecordBean>> addRecordAndPics(String userId,String content, MultipartBody.Part [] part) {
        return mApiService.addRecordAndPics(userId,content,part);
    }

    @Override
    public Observable<BaseBean<RecordBean>> addRecord(String userId, String content) {
        return mApiService.addRecord(userId,content);
    }

    @Override
    public Observable<BaseBean<String>> deleteRecord(String id) {
        return mApiService.deleteRecord(id);
    }

    @Override
    public Observable<BaseBean<List<RecordBean>>> findAllRecords(String userId, Integer page, Integer size) {
        return mApiService.findAllRecords(userId,page,size);
    }
}
