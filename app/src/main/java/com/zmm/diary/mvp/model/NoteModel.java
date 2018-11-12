package com.zmm.diary.mvp.model;

import com.zmm.diary.bean.BaseBean;
import com.zmm.diary.bean.NoteBean;
import com.zmm.diary.http.ApiService;
import com.zmm.diary.mvp.presenter.contract.NoteContract;

import java.util.List;

import io.reactivex.Observable;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/12
 * Email:65489469@qq.com
 */
public class NoteModel implements NoteContract.INoteModel{

    private ApiService mApiService;

    public NoteModel(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Observable<BaseBean<NoteBean>> add(NoteBean noteBean) {
        return mApiService.addNote(noteBean);
    }

    @Override
    public Observable<BaseBean<NoteBean>> update(NoteBean noteBean) {
        return mApiService.updateNote(noteBean);
    }

    @Override
    public Observable<BaseBean<NoteBean>> findNoteById(String id) {
        return mApiService.findNoteById(id);
    }

    @Override
    public Observable<BaseBean<String>> delete(String id) {
        return mApiService.deleteNote(id);
    }

    @Override
    public Observable<BaseBean<List<NoteBean>>> findToday(String userId) {
        return mApiService.findToday(userId);
    }
}
