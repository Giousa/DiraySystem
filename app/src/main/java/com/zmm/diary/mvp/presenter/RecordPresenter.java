package com.zmm.diary.mvp.presenter;

import com.zmm.diary.bean.RecordBean;
import com.zmm.diary.mvp.presenter.contract.RecordContract;
import com.zmm.diary.rx.RxHttpResponseCompat;
import com.zmm.diary.rx.subscriber.ErrorHandlerSubscriber;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/12/4
 * Email:65489469@qq.com
 */
public class RecordPresenter extends BasePresenter<RecordContract.IRecordModel,RecordContract.RecordView>{

    @Inject
    public RecordPresenter(RecordContract.IRecordModel model, RecordContract.RecordView view) {
        super(model, view);
    }

    /**
     * 添加记录
     * @param userId
     * @param content
     * @param images
     */
    public void addRecord(String userId, String content, ArrayList<String> images) {

        if(images != null && images.size() > 0){
            MultipartBody.Part [] part = new MultipartBody.Part[images.size()];

            for (int i = 0; i < images.size(); i++) {
                File file= new File(images.get(i));
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                part[i] = MultipartBody.Part.createFormData("uploadFiles", file.getName(), requestFile);
            }


            mModel.addRecordAndPics(userId,content,part)
                    .compose(RxHttpResponseCompat.<RecordBean>compatResult())
                    .subscribe(new ErrorHandlerSubscriber<RecordBean>() {
                        @Override
                        public void onNext(RecordBean recordBean) {
                            mView.addSuccess();
                        }
                    });

        }else {
            mModel.addRecord(userId,content)
                    .compose(RxHttpResponseCompat.<RecordBean>compatResult())
                    .subscribe(new ErrorHandlerSubscriber<RecordBean>() {
                        @Override
                        public void onNext(RecordBean recordBean) {
                            mView.addSuccess();
                        }
                    });
        }



    }

    /**
     * 分页查询记录
     * @param userId
     * @param page
     * @param size
     */
    public void findAllRecords(String userId, int page, int size) {

        mModel.findAllRecords(userId,page,size)
                .compose(RxHttpResponseCompat.<List<RecordBean>>compatResult())
                .subscribe(new ErrorHandlerSubscriber<List<RecordBean>>() {
                    @Override
                    public void onNext(List<RecordBean> recordBeans) {
                        mView.findAllRecordsSuccess(recordBeans);
                    }
                });
    }
}
