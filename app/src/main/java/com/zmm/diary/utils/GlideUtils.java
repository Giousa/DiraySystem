package com.zmm.diary.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zmm.diary.R;
import com.zmm.diary.ui.widget.GlideCircleTransform;
import com.zmm.diary.utils.config.CommonConfig;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2019/1/3
 * Email:65489469@qq.com
 */
public class GlideUtils {

    /**
     * 加载普通图片
     * @param context
     * @param imgUrl
     * @param imageView
     */
    public static void loadImage(Context context, String imgUrl, ImageView imageView) {

        Glide.with(context)
                .load(imgUrl)
                .placeholder(R.drawable.default_bg)
                .error(R.drawable.default_bg)
                .into(imageView);
    }

    /**
     * 加载圆形图片
     * @param context
     * @param imgUrl
     * @param imageView
     */
    public static void loadCircleImage(Context context, String imgUrl, ImageView imageView) {
        Glide.with(context)
                .load(imgUrl)
                .placeholder(R.drawable.default_my_icon)
                .error(R.drawable.default_my_icon)
                .transform(new GlideCircleTransform(context))
                .into(imageView);
    }
}
