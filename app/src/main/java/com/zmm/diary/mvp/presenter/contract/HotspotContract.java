package com.zmm.diary.mvp.presenter.contract;

import com.zmm.diary.bean.BaseBean;
import com.zmm.diary.bean.HotspotBean;
import com.zmm.diary.bean.UserBean;
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

        Observable<BaseBean<UserBean>> addHotspot(String id,String content, MultipartBody.Part file);

        Observable<BaseBean<String>> deleteHotspot(String id);

        Observable<BaseBean<List<HotspotBean>>> findHotspotsById(String userId,Integer page,Integer size);

        Observable<BaseBean<List<HotspotBean>>> findAllHotspots(Integer page,Integer size);

    }

    interface HotspotView extends BaseView{

        void addSuccess();

        void deleteSuccess();

        void loadMoreHotspotSuccess(List<HotspotBean> recordBeanList);

        void refreshHotspotSuccess(List<HotspotBean> recordBeanList);
    }
}
