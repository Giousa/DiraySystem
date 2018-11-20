package com.zmm.diary.mvp.presenter;

import com.zmm.diary.bean.NoteBean;
import com.zmm.diary.mvp.presenter.contract.NoteContract;
import com.zmm.diary.rx.RxHttpResponseCompat;
import com.zmm.diary.rx.subscriber.ErrorHandlerSubscriber;

import java.util.Date;
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

    /**
     * 删除日记
     * @param id
     */
    public void deleteNote(String id) {
        mModel.delete(id)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        mView.deleteSuccess();
                    }
                });
    }

    /**
     * 获取日记详情
     * @param id
     */
    public void findNoteById(String id) {
        mModel.findNoteById(id)
                .compose(RxHttpResponseCompat.<NoteBean>compatResult())
                .subscribe(new ErrorHandlerSubscriber<NoteBean>() {
                    @Override
                    public void onNext(NoteBean noteBean) {
                        mView.findNoteSuccess(noteBean);
                    }
                });
    }

    /**
     * 更新日记
     * @param noteBean
     */
    public void updateNote(NoteBean noteBean) {
        mModel.update(noteBean)
                .compose(RxHttpResponseCompat.<NoteBean>compatResult())
                .subscribe(new ErrorHandlerSubscriber<NoteBean>() {
                    @Override
                    public void onNext(NoteBean noteBean) {
                        mView.updateSuccess();
                    }
                });
    }

    /**
     * 根据创建时间，获取日记
     */
    public void findNotesByCreateTime(String userId,String createTime) {
        mModel.findNotesByCreateTime(userId,createTime)
                .compose(RxHttpResponseCompat.<List<NoteBean>>compatResult())
                .subscribe(new ErrorHandlerSubscriber<List<NoteBean>>() {
                    @Override
                    public void onNext(List<NoteBean> noteBeanList) {
                        System.out.println("获取日记消息: "+noteBeanList);
                    }
                });
    }
}
