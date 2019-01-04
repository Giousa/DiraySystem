package com.zmm.diary.mvp.presenter.contract;

import com.zmm.diary.bean.BaseBean;
import com.zmm.diary.bean.CorrelateBean;
import com.zmm.diary.mvp.view.BaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2019/1/2
 * Email:65489469@qq.com
 */
public interface CorrelateContract {

    interface ICorrelateModel{


        Observable<BaseBean<List<CorrelateBean>>> findAllFollowers(String userId, Integer page, Integer size);

        Observable<BaseBean<List<CorrelateBean>>> findFollowersByUserId(String userId, Integer page, Integer size);

        Observable<BaseBean<List<CorrelateBean>>> findFunsByUserId(String userId, Integer page, Integer size);

        Observable<BaseBean<String>> correlateAuthor(String userId,String authorId);

    }

    interface CorrelateView extends BaseView{

        void findFollowersSuccess(List<CorrelateBean> correlateBeanList);

        void findFunsSuccess(List<CorrelateBean> correlateBeanList);

        void correlateChangeSuccess(String msg);

        void loadMoreCorrelateSuccess(List<CorrelateBean> correlateBeanList);

        void refreshCorrelateSuccess(List<CorrelateBean> correlateBeanList);
    }
}
