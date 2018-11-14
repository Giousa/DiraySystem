package com.zmm.diary.mvp.presenter;

import com.zmm.diary.bean.NoteBean;
import com.zmm.diary.mvp.presenter.contract.NoteContract;
import com.zmm.diary.rx.RxHttpResponseCompat;
import com.zmm.diary.rx.subscriber.ErrorHandlerSubscriber;

import java.util.List;

import javax.inject.Inject;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/12
 * Email:65489469@qq.com
 */
public class NotePresenter extends BasePresenter<NoteContract.INoteModel,NoteContract.NoteView>{


    @Inject
    public NotePresenter(NoteContract.INoteModel model, NoteContract.NoteView view) {
        super(model, view);
    }

    /**
     * 添加日记
     * @param noteBean
     */
    public void addNote(NoteBean noteBean) {
        mModel.add(noteBean)
                .compose(RxHttpResponseCompat.<NoteBean>compatResult())
                .subscribe(new ErrorHandlerSubscriber<NoteBean>() {
                    @Override
                    public void onNext(NoteBean noteBean) {
                        mView.addSuccess();
                    }
                });
    }

    /**
     * 当天日记列表
     * @param id
     */
    public void requestTodayNotes(String id) {

        mModel.findToday(id)
                .compose(RxHttpResponseCompat.<List<NoteBean>>compatResult())
                .subscribe(new ErrorHandlerSubscriber<List<NoteBean>>() {
                    @Override
                    public void onNext(List<NoteBean> noteBeanList) {
                        mView.findTodayNotesSuccess(noteBeanList);
                    }
                });
    }
}
