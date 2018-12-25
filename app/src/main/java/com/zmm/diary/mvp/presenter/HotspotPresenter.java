package com.zmm.diary.mvp.presenter;

import com.zmm.diary.mvp.presenter.contract.HotspotContract;

import javax.inject.Inject;

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
}
