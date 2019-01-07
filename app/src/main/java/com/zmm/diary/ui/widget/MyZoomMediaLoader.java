package com.zmm.diary.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.previewlibrary.loader.IZoomMediaLoader;
import com.previewlibrary.loader.MySimpleTarget;
import com.zmm.diary.utils.GlideUtils;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2019/1/7
 * Email:65489469@qq.com
 */
public class MyZoomMediaLoader implements IZoomMediaLoader {
    @Override
    public void displayImage(@NonNull Fragment context, @NonNull String path, ImageView imageView, @NonNull final MySimpleTarget simpleTarget) {
        GlideUtils.loadPreviewImage(context.getContext(), path, imageView, new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                simpleTarget.onLoadFailed(null);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                simpleTarget.onResourceReady();
                return false;
            }
        });

    }

    @Override
    public void displayGifImage(@NonNull Fragment context, @NonNull String path, ImageView imageView, @NonNull final MySimpleTarget simpleTarget) {
        GlideUtils.loadPreviewImage(context.getContext(), path, imageView, new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                simpleTarget.onLoadFailed(null);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                simpleTarget.onResourceReady();
                return false;
            }
        });
    }

    @Override
    public void onStop(@NonNull Fragment context) {
        Glide.with(context).onStop();

    }

    @Override
    public void clearMemory(@NonNull Context c) {
        Glide.get(c).clearMemory();

    }
}
