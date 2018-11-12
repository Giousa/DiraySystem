package com.zmm.diary.http;

import com.zmm.diary.bean.BaseBean;
import com.zmm.diary.bean.NoteBean;
import com.zmm.diary.bean.UserBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/10/31
 * Email:65489469@qq.com
 */
public interface ApiService {


//    /**
//     * 登录
//     * @param phone
//     * @param password
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("api/user/login.json")
//    Observable<BaseBean<UserBean>> login(@Field("loginId") String phone, @Field("password") String password);
//


    /**
     * -----------------------------登录注册界面接口-----------------------------
     */


    /**
     * 获取验证码
     * @param phone
     * @return
     */
    @GET("user/getVerifyCode/{phone}")
    Observable<BaseBean<String>> getVerifyCode(@Path("phone") String phone);


    /**
     * 登录
     * @param phone
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("user/login/")
    Observable<BaseBean<UserBean>> login(@Field("phone") String phone, @Field("password") String password);

    /**
     * 注册
     * @param phone
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("user/register/")
    Observable<BaseBean<UserBean>> register(@Field("phone") String phone, @Field("password") String password, @Field("verifyCode") String verifyCode);

    /**
     * 忘记密码
     * @param phone
     * @param newPassword
     * @param verifyCode
     * @return
     */
    @FormUrlEncoded
    @POST("user/modifyPassword/")
    Observable<BaseBean<String>> modifyPassword(@Field("phone") String phone, @Field("newPassword") String newPassword, @Field("verifyCode") String verifyCode);



    /**
     * -----------------------------日记界面接口-----------------------------
     */


    @POST("note/addNote")
    Observable<BaseBean<NoteBean>> addNote(@Body NoteBean noteBean);

    @POST("note/updateNote")
    Observable<BaseBean<NoteBean>> updateNote(@Body NoteBean noteBean);

    @GET("note/findNoteById/{id}")
    Observable<BaseBean<NoteBean>> findNoteById(String id);

    @GET("note/deleteNote/{id}")
    Observable<BaseBean<String>> deleteNote(String id);

    @GET("note/findTodayNotesByUserId/{userId}")
    Observable<BaseBean<List<NoteBean>>> findToday(String userId);
}
