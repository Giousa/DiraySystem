package com.zmm.diary.mvp.presenter;

import com.zmm.diary.bean.HotspotBean;
import com.zmm.diary.mvp.presenter.contract.HotspotContract;
import com.zmm.diary.rx.RxHttpResponseCompat;
import com.zmm.diary.rx.subscriber.ErrorHandlerSubscriber;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/12/25
 * Email:65489469@qq.com
 */
public class HotspotPresenter extends BasePresenter<HotspotContract.IHotspotModel,HotspotContract.HotspotView> {

    @Inject
    public HotspotPresenter(HotspotContract.IHotspotModel model, HotspotContract.HotspotView view) {
        super(model, view);
    }

    /**
     * 添加热点
     * @param id
     * @param content
     * @param path
     */
    public void addHotspot(String id, String content, String path) {

        String type = ".jpg";
        if(path.contains(".gif")){
            type = ".gif";
        }

        File file= new File(path);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("uploadFile", file.getName(), requestFile);

        mModel.addHotspot(id,content,type,part)
                .compose(RxHttpResponseCompat.<HotspotBean>compatResult())
                .subscribe(new ErrorHandlerSubscriber<HotspotBean>() {
                    @Override
                    public void onNext(HotspotBean hotspotBean) {
                        mView.addSuccess();
                    }
                });

    }

    /**
     * 查询所有
     * @param page
     * @param size
     * @param flag 0:加载更多  1：刷新
     */
    public void findAllHotspots(int page, int size, final int flag) {
        mModel.findAllHotspots(page,size)
                .compose(RxHttpResponseCompat.<List<HotspotBean>>compatResult())
                .subscribe(new ErrorHandlerSubscriber<List<HotspotBean>>() {
                    @Override
                    public void onNext(List<HotspotBean> hotspotBeanList) {
                        if(flag == 0){
                            mView.loadMoreHotspotSuccess(hotspotBeanList);
                        }else {
                            mView.refreshHotspotSuccess(hotspotBeanList);
                        }
                    }
                });
    }

    /**
     * 根据用户id，查询所有热点数据
     * @param userId
     * @param page
     * @param size
     * @param flag 0:加载更多  1：刷新
     */
    public void findHotspotsByUId(String userId, int page, int size, final int flag) {

        mModel.findHotspotsByUId(userId,page,size)
                .compose(RxHttpResponseCompat.<List<HotspotBean>>compatResult())
                .subscribe(new ErrorHandlerSubscriber<List<HotspotBean>>() {
                    @Override
                    public void onNext(List<HotspotBean> hotspotBeanList) {
                        if(flag == 0){
                            mView.loadMoreHotspotSuccess(hotspotBeanList);
                        }else {
                            mView.refreshHotspotSuccess(hotspotBeanList);
                        }
                    }
                });

    }

    /**
     * 根据id查询当前热点详情
     * @param hotspotId
     */
    public void findHotspotById(String hotspotId) {
        mModel.findHotspotById(hotspotId)
                .compose(RxHttpResponseCompat.<HotspotBean>compatResult())
                .subscribe(new ErrorHandlerSubscriber<HotspotBean>() {
                    @Override
                    public void onNext(HotspotBean hotspotBean) {
                        mView.findHotspotSuccess(hotspotBean);
                    }
                });
    }
}
