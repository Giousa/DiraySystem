package com.zmm.diary.mvp.model;

import com.zmm.diary.bean.BaseBean;
import com.zmm.diary.bean.CorrelateBean;
import com.zmm.diary.http.ApiService;
import com.zmm.diary.mvp.presenter.contract.CorrelateContract;

import java.util.List;

import io.reactivex.Observable;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2019/1/2
 * Email:65489469@qq.com
 */
public class CorrelateModel implements CorrelateContract.ICorrelateModel{


    private ApiService mApiService;

    public CorrelateModel(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Observable<BaseBean<List<CorrelateBean>>> findAllFollowers(String userId, Integer page, Integer size) {
        return mApiService.findAllFollowers(userId,page,size);
    }

    @Override
    public Observable<BaseBean<List<CorrelateBean>>> findFollowersByUserId(String userId, Integer page, Integer size) {
        return mApiService.findFollowersByUserId(userId,page,size);
    }

    @Override
    public Observable<BaseBean<List<CorrelateBean>>> findFunsByUserId(String userId, Integer page, Integer size) {
        return mApiService.findFunsByUserId(userId,page,size);
    }

    @Override
    public Observable<BaseBean<String>> deleteFollower(String authorId) {
        return mApiService.deleteFollower(authorId);
    }
}
