package com.zmm.diary.http.cookie;

import android.content.Context;

import com.zmm.diary.utils.SharedPreferencesUtil;
import com.zmm.diary.utils.config.CommonConfig;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;


/**Ø
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/14
 * Time:下午5:28
 */

public class ReceivedCookiesInterceptor implements Interceptor {

    private Context context;

    public ReceivedCookiesInterceptor(Context context) {
        super();
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        // 获取 Cookie
//        Response resp = chain.proceed(chain.request());
//
//        List<String> cookies = resp.headers("Set-Cookie");
//        String cookieStr = "";
//        if (cookies != null && cookies.size() > 0) {
//            for (int i = 0; i < cookies.size(); i++) {
//                cookieStr += cookies.get(i);
//            }
//
//            System.out.println("cookieStr11 = "+cookieStr);
//
//            if(cookieStr.contains(CommonConfig.COOKIE)){
//                SharedPreferencesUtil.saveString(CommonConfig.COOKIE,cookieStr);
//            }
//
//
//        }

        Response resp = chain.proceed(chain.request());

        List<String> cookies = resp.headers("Set-Cookie");
        String cookieStr = "";
        if (cookies != null && cookies.size() > 0) {
            for (int i = 0; i < cookies.size(); i++) {
                cookieStr += cookies.get(i);
            }

            System.out.println("cookieStr11 = "+cookieStr);

            if(cookieStr.contains(CommonConfig.COOKIE)){
                SharedPreferencesUtil.saveString(CommonConfig.COOKIE,cookieStr);
            }


        }

        return resp;
    }
}
