package com.zmm.diary.http;

import com.zmm.diary.bean.BaseBean;
import com.zmm.diary.bean.CommentBean;
import com.zmm.diary.bean.CommentReplyBean;
import com.zmm.diary.bean.CorrelateBean;
import com.zmm.diary.bean.HotspotBean;
import com.zmm.diary.bean.NoteBean;
import com.zmm.diary.bean.RecordBean;
import com.zmm.diary.bean.UserBean;

import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
    @POST("user/resetPassword/")
    Observable<BaseBean<String>> resetPassword(@Field("phone") String phone, @Field("newPassword") String newPassword, @Field("verifyCode") String verifyCode);


    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    @GET("user/findUserById/{id}")
    Observable<BaseBean<UserBean>> findUserById(@Path("id") String id);


    /**
     * -----------------------------日记界面接口-----------------------------
     */


    @POST("note/addNote")
    Observable<BaseBean<NoteBean>> addNote(@Body NoteBean noteBean);

    @POST("note/updateNote")
    Observable<BaseBean<NoteBean>> updateNote(@Body NoteBean noteBean);

    @GET("note/findNoteById/{id}")
    Observable<BaseBean<NoteBean>> findNoteById(@Path("id")String id);

    @GET("note/deleteNote/{id}")
    Observable<BaseBean<String>> deleteNote(@Path("id")String id);

    @GET("note/findTodayNotesByUserId/{userId}")
    Observable<BaseBean<List<NoteBean>>> findToday(@Path("userId")String userId);

    @GET("note/findNotesByCreateTime/{userId}/{createTime}")
    Observable<BaseBean<List<NoteBean>>> findNotesByCreateTime(@Path("userId")String userId,
                                                               @Path("createTime")String createTime);

    /**
     * -----------------------------用户信息接口-----------------------------
     */


    @Multipart
    @POST("user/uploadIcon/{id}")
    Observable<BaseBean<UserBean>> uploadIcon( @Path("id") String id,  @Part() MultipartBody.Part file);

    @POST("user/updateUser")
    Observable<BaseBean<UserBean>> updateUserBean(@Body UserBean userBean);


    /**
     * -----------------------------记录接口-----------------------------
     */

    @Multipart
    @POST("record/addRecordAndPics")
    Observable<BaseBean<RecordBean>> addRecordAndPics(@Query("userId") String userId, @Query("content") String content, @Part() MultipartBody.Part[] file);

    @POST("record/addRecord")
    Observable<BaseBean<RecordBean>> addRecord(@Query("userId") String userId, @Query("content") String content);


    @GET("record/deleteRecord/{id}")
    Observable<BaseBean<String>> deleteRecord(@Path("id")String id);

    @GET("record/findAllRecords")
    Observable<BaseBean<List<RecordBean>>> findAllRecords(@Query("userId") String userId,@Query("page") Integer page,@Query("size") Integer size);


    /**
     * -----------------------------热点接口-----------------------------
     */

    @Multipart
    @POST("hotspot/addHotspot")
    Observable<BaseBean<HotspotBean>> addHotspot(@Query("userId")String userId, @Query("content")String content, @Query("type")String type, @Part() MultipartBody.Part file);

    @GET("hotspot/deleteHotspot/{id}")
    Observable<BaseBean<String>> deleteHotspot(@Path("id")String id);

    @GET("hotspot/findHotspotById")
    Observable<BaseBean<HotspotBean>> findHotspotById(@Query("userId")String userId,@Query("hotspotId")String hotspotId);

    @GET("hotspot/findHotspotsByUId")
    Observable<BaseBean<List<HotspotBean>>> findHotspotsByUId(@Query("userId")String userId, @Query("page")Integer page, @Query("size")Integer size);

    @GET("hotspot/findAllHotspots")
    Observable<BaseBean<List<HotspotBean>>> findAllHotspots(@Query("page")Integer page, @Query("size")Integer size);

    @GET("hotspot/appreciateHotspot")
    Observable<BaseBean<String>> appreciateHotspot(@Query("userId")String userId,@Query("hotspotId")String hotspotId);

    @GET("hotspot/collectionHotspot")
    Observable<BaseBean<String>> collectionHotspot(@Query("userId")String userId,@Query("hotspotId")String hotspotId);

    @GET("hotspot/findCollectionHotspotsByUId")
    Observable<BaseBean<List<HotspotBean>>> findCollectionHotspotsByUId(@Query("userId")String userId, @Query("page")Integer page, @Query("size")Integer size);

    /**
     * -----------------------------关注和粉丝接口-----------------------------
     */

    @GET("correlate/correlateAuthor")
    Observable<BaseBean<String>> correlateAuthor(@Query("userId")String userId,@Query("authorId")String authorId);

    @GET("correlate/findAllFollowers")
    Observable<BaseBean<List<CorrelateBean>>> findAllFollowers(@Query("userId")String userId, @Query("page")Integer page, @Query("size")Integer size);

    @GET("correlate/findFollowersByUserId")
    Observable<BaseBean<List<CorrelateBean>>> findFollowersByUserId(@Query("userId")String userId, @Query("page")Integer page, @Query("size")Integer size);

    @GET("correlate/findFunsByUserId")
    Observable<BaseBean<List<CorrelateBean>>> findFunsByUserId(@Query("userId")String userId, @Query("page")Integer page, @Query("size")Integer size);


    /**
     * -----------------------------评论和回复接口-----------------------------
     */
    @GET("comment/newComment")
    Observable<BaseBean<String>> newComment(@Query("hotspotId")String hotspotId, @Query("fromUid")String fromUid, @Query("content")String content);

    @GET("comment/replyComment")
    Observable<BaseBean<String>> replyComment(@Query("commentId")String commentId, @Query("fromUid")String fromUid, @Query("toUid")String toUid, @Query("content")String content);
}

