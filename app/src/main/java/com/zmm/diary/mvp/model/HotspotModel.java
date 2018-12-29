package com.zmm.diary.mvp.model;

import com.zmm.diary.bean.BaseBean;
import com.zmm.diary.bean.HotspotBean;
import com.zmm.diary.bean.UserBean;
import com.zmm.diary.http.ApiService;
import com.zmm.diary.mvp.presenter.contract.HotspotContract;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/12/25
 * Email:65489469@qq.com
 */
public class HotspotModel implements HotspotContract.IHotspotModel {


    private ApiService mApiService;

    public HotspotModel(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Observable<BaseBean<HotspotBean>> addHotspot(String userId, String content,String type, MultipartBody.Part file) {
        return mApiService.addHotspot(userId,content,type,file);
    }

    @Override
    public Observable<BaseBean<HotspotBean>> findHotspotById(String userId,String hotspotId) {
        return mApiService.findHotspotById(userId,hotspotId);
    }

    @Override
    public Observable<BaseBean<String>> deleteHotspot(String id) {
        return mApiService.deleteHotspot(id);
    }

    @Override
    public Observable<BaseBean<List<HotspotBean>>> findHotspotsByUId(String userId, Integer page, Integer size) {
        return mApiService.findHotspotsByUId(userId,page,size);
    }

    @Override
    public Observable<BaseBean<List<HotspotBean>>> findCollectionHotspotsByUId(String userId, Integer page, Integer size) {
        return mApiService.findCollectionHotspotsByUId(userId,page,size);
    }

    @Override
    public Observable<BaseBean<List<HotspotBean>>> findAllHotspots(Integer page, Integer size) {
        return mApiService.findAllHotspots(page,size);
    }

    @Override
    public Observable<BaseBean<String>> appreciateHotspot(String userId, String hotspotId) {
        return mApiService.appreciateHotspot(userId,hotspotId);
    }

    @Override
    public Observable<BaseBean<String>> collectionHotspot(String userId, String hotspotId) {
        return mApiService.collectionHotspot(userId,hotspotId);
    }
}
