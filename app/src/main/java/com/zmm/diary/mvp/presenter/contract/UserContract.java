package com.zmm.diary.mvp.presenter.contract;


import com.zmm.diary.bean.BaseBean;
import com.zmm.diary.bean.UserBean;
import com.zmm.diary.mvp.view.BaseView;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/10/31
 * Email:65489469@qq.com
 */
public interface UserContract {


    interface IUserModel{

        Observable<BaseBean<UserBean>> uploadIcon(String id, MultipartBody.Part file);

        Observable<BaseBean<UserBean>> findUserById(String id);

        Observable<BaseBean<String>> deleteUserById(String id);

        Observable<BaseBean<UserBean>> updateUserBean(UserBean userBean);



    }


    interface UserView extends BaseView {

        void updateSuccess(UserBean userBean);

        void deleteSuccess();
        
    }

}
