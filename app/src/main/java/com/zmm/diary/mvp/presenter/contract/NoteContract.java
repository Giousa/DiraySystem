package com.zmm.diary.mvp.presenter.contract;

import com.zmm.diary.bean.BaseBean;
import com.zmm.diary.bean.NoteBean;
import com.zmm.diary.mvp.view.BaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/12
 * Email:65489469@qq.com
 */
public interface NoteContract {

    interface INoteModel{

        Observable<BaseBean<NoteBean>> add(NoteBean noteBean);

        Observable<BaseBean<NoteBean>> update(NoteBean noteBean);

        Observable<BaseBean<NoteBean>> findNoteById(String id);

        Observable<BaseBean<String>> delete(String id);

        Observable<BaseBean<List<NoteBean>>> findToday(String userId);


    }

    interface NoteView extends BaseView{

        void addSuccess();

        void updateSuccess();

        void deleteSuccess();

        void findNoteSuccess(NoteBean noteBean);

        void findTodayNotesSuccess(List<NoteBean> noteBeanList);
    }
}
