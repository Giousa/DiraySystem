package com.zmm.diary.dagger.module;

import com.zmm.diary.http.ApiService;
import com.zmm.diary.mvp.model.NoteModel;
import com.zmm.diary.mvp.presenter.contract.NoteContract;

import dagger.Module;
import dagger.Provides;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/12
 * Email:65489469@qq.com
 */
@Module
public class NoteModule {

    private NoteContract.NoteView mNoteView;

    public NoteModule(NoteContract.NoteView noteView) {
        mNoteView = noteView;
    }

    @Provides
    public NoteContract.NoteView provideNoteView(){
        return mNoteView;
    }

    @Provides
    public NoteContract.INoteModel provideNoteModel(ApiService apiService){
        return new NoteModel(apiService);
    }
}
