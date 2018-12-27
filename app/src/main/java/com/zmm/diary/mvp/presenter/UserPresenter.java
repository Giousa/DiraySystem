package com.zmm.diary.mvp.presenter;

import com.zmm.diary.bean.UserBean;
import com.zmm.diary.mvp.presenter.contract.UserContract;
import com.zmm.diary.rx.RxHttpResponseCompat;
import com.zmm.diary.rx.subscriber.ErrorHandlerSubscriber;

import java.io.File;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/20
 * Email:65489469@qq.com
 */
public class UserPresenter extends BasePresenter<UserContract.IUserModel,UserContract.UserView>{


    @Inject
    public UserPresenter(UserContract.IUserModel model, UserContract.UserView view) {
        super(model, view);
    }

    /**
     * 上传头像
     * @param id
     * @param path
     */
    public void uploadPic(String id, String path) {

        File file= new File(path);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("uploadFile", file.getName(), requestFile);

        mModel.uploadIcon(id,part)
                .compose(RxHttpResponseCompat.<UserBean>compatResult())
                .subscribe(new ErrorHandlerSubscriber<UserBean>() {
                    @Override
                    public void onNext(UserBean userBean) {
                        mView.updateSuccess(userBean);
                    }
                });
    }


    /**
     * 根据id查询用户信息
     * @param id
     */
    public void findUserById(String id) {

        mModel.findUserById(id)
                .compose(RxHttpResponseCompat.<UserBean>compatResult())
                .subscribe(new ErrorHandlerSubscriber<UserBean>() {
                    @Override
                    public void onNext(UserBean userBean) {
                        mView.updateSuccess(userBean);
                    }
                });

    }

    /**
     * 更新用户信息
     * @param userBean
     */
    public void updateUser(UserBean userBean) {
        mModel.updateUserBean(userBean)
                .compose(RxHttpResponseCompat.<UserBean>compatResult())
                .subscribe(new ErrorHandlerSubscriber<UserBean>() {
                    @Override
                    public void onNext(UserBean userBean) {
                        mView.updateSuccess(userBean);
                    }
                });

    }
}
