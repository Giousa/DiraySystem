package com.zmm.diary.http.cookie;

import android.content.Context;
import android.text.TextUtils;

import com.zmm.diary.utils.SharedPreferencesUtil;
import com.zmm.diary.utils.config.CommonConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;


/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/14
 * Time:下午5:28
 */

public class AddCookiesInterceptor implements Interceptor {

    private Context context;

    public AddCookiesInterceptor(Context context) {
        super();
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {


        String cookieStr = SharedPreferencesUtil.getString(CommonConfig.COOKIE, "");

        System.out.println("cookieStr22 = "+cookieStr);
        if (!TextUtils.isEmpty(cookieStr)) {
            return chain.proceed(chain.request().newBuilder().header("Cookie", cookieStr).build());
        }
        return chain.proceed(chain.request());
    }
}
