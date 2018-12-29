package com.zmm.diary.mvp.presenter.contract;

import com.zmm.diary.bean.BaseBean;
import com.zmm.diary.bean.HotspotBean;
import com.zmm.diary.mvp.view.BaseView;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/12/25
 * Email:65489469@qq.com
 */
public interface HotspotContract {

    interface IHotspotModel{

        Observable<BaseBean<HotspotBean>> addHotspot(String userId,String content,String type, MultipartBody.Part file);

        Observable<BaseBean<HotspotBean>> findHotspotById(String userId,String hotspotId);

        Observable<BaseBean<String>> deleteHotspot(String hotspotId);

        Observable<BaseBean<List<HotspotBean>>> findHotspotsByUId(String userId,Integer page,Integer size);

        Observable<BaseBean<List<HotspotBean>>> findAllHotspots(Integer page,Integer size);

        Observable<BaseBean<String>> appreciateHotspot(String userId,String hotspotId);

        Observable<BaseBean<String>> collectionHotspot(String userId,String hotspotId);

    }

    interface HotspotView extends BaseView{

        void addSuccess();

        void deleteSuccess();

        void appreciateOrCollectionStatus(String msg);

        void findHotspotSuccess(HotspotBean hotspotBean);

        void loadMoreHotspotSuccess(List<HotspotBean> hotspotBeanList);

        void refreshHotspotSuccess(List<HotspotBean> hotspotBeanList);
    }
}
