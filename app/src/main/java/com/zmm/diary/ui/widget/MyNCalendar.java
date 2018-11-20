package com.zmm.diary.ui.widget;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.necer.ncalendar.calendar.NCalendar;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/15
 * Email:65489469@qq.com
 */
public class MyNCalendar extends NCalendar {


    public MyNCalendar(Context context) {
        this(context,null);
    }

    public MyNCalendar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyNCalendar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {

        try {
            super.onRestoreInstanceState(state);
        }catch (Exception e){
            state = null;
        }
    }
}
