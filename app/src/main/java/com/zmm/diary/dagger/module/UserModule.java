package com.zmm.diary.dagger.module;

import com.zmm.diary.http.ApiService;
import com.zmm.diary.mvp.model.UserModel;
import com.zmm.diary.mvp.presenter.contract.UserContract;

import dagger.Module;
import dagger.Provides;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/12
 * Email:65489469@qq.com
 */
@Module
public class UserModule {

    private UserContract.UserView mUserView;

    public UserModule(UserContract.UserView userView) {
        mUserView = userView;
    }

    @Provides
    public UserContract.UserView provideUserView(){
        return mUserView;
    }

    @Provides
    public UserContract.IUserModel provideUserModel(ApiService apiService){
        return new UserModel(apiService);
    }

}
