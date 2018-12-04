package com.zmm.diary.mvp.presenter.contract;

import com.zmm.diary.bean.BaseBean;
import com.zmm.diary.bean.RecordBean;
import com.zmm.diary.mvp.view.BaseView;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/12/4
 * Email:65489469@qq.com
 */
public interface RecordContract {

    interface IRecordModel{

        Observable<BaseBean<RecordBean>> addRecordAndPics(String userId,String content, MultipartBody.Part [] part);


        Observable<BaseBean<RecordBean>> addRecord(String userId,String content);


        Observable<BaseBean<String>> deleteRecord(String id);


        Observable<BaseBean<List<RecordBean>>> findAllRecords(String userId,Integer page,Integer size);


    }

    interface RecordView extends BaseView{
        void addSuccess();

        void deleteSuccess();

        void findAllRecordsSuccess(List<RecordBean> recordBeanList);
    }
}
