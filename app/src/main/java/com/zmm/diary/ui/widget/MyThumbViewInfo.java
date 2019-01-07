package com.zmm.diary.ui.widget;

import android.graphics.Rect;
import android.os.Parcel;
import android.support.annotation.Nullable;

import com.previewlibrary.enitity.IThumbViewInfo;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2019/1/7
 * Email:65489469@qq.com
 */
public class MyThumbViewInfo implements IThumbViewInfo {

    private String url;  //图片地址

    public MyThumbViewInfo(String url) {
        this.url = url;
    }

    public MyThumbViewInfo(Parcel source) {
        this.url = source.readString();

    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public Rect getBounds() {
        return null;
    }

    @Nullable
    @Override
    public String getVideoUrl() {
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
    }

    public static final Creator<MyThumbViewInfo> CREATOR = new Creator<MyThumbViewInfo>() {
        @Override
        public MyThumbViewInfo createFromParcel(Parcel source) {
            return new MyThumbViewInfo(source);
        }

        @Override
        public MyThumbViewInfo[] newArray(int size) {
            return new MyThumbViewInfo[size];
        }
    };
}
