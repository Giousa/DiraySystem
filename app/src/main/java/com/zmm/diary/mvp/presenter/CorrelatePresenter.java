package com.zmm.diary.mvp.presenter;

import com.zmm.diary.bean.CorrelateBean;
import com.zmm.diary.mvp.presenter.contract.CorrelateContract;
import com.zmm.diary.rx.RxHttpResponseCompat;
import com.zmm.diary.rx.subscriber.ErrorHandlerSubscriber;

import java.util.List;

import javax.inject.Inject;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2019/1/2
 * Email:65489469@qq.com
 */
public class CorrelatePresenter extends BasePresenter<CorrelateContract.ICorrelateModel,CorrelateContract.CorrelateView>{


    @Inject
    public CorrelatePresenter(CorrelateContract.ICorrelateModel model, CorrelateContract.CorrelateView view) {
        super(model, view);
    }

    /**
     * 查询所有关注用户
     * @param userId
     * @param page
     * @param size
     * @param flag 0:加载更多 1:刷新
     */
    public void findFollowersByUserId(String userId, int page, int size, final int flag) {
        mModel.findFollowersByUserId(userId,page,size)
                .compose(RxHttpResponseCompat.<List<CorrelateBean>>compatResult())
                .subscribe(new ErrorHandlerSubscriber<List<CorrelateBean>>() {
                    @Override
                    public void onNext(List<CorrelateBean> correlateBeanList) {
                        if(flag == 0){
                            mView.loadMoreCorrelateSuccess(correlateBeanList);
                        }else {
                            mView.refreshCorrelateSuccess(correlateBeanList);
                        }
                    }
                });
    }

    /**
     * 查询所有推荐关注成员
     * @param userId
     * @param page
     * @param size
     * @param flag
     */
    public void findAllFollowers(String userId, int page, int size, final int flag) {
        mModel.findAllFollowers(userId,page,size)
                .compose(RxHttpResponseCompat.<List<CorrelateBean>>compatResult())
                .subscribe(new ErrorHandlerSubscriber<List<CorrelateBean>>() {
                    @Override
                    public void onNext(List<CorrelateBean> correlateBeanList) {
                        if(flag == 0){
                            mView.loadMoreCorrelateSuccess(correlateBeanList);
                        }else {
                            mView.refreshCorrelateSuccess(correlateBeanList);
                        }
                    }
                });
    }

    /**
     * 取消关注
     * @param userId
     * @param id
     */
    public void correlateAuthor(String userId,String id) {
        mModel.correlateAuthor(userId,id)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        mView.correlateChangeSuccess(s);
                    }
                });
    }


}
