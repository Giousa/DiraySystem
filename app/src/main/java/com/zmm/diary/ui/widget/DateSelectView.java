package com.zmm.diary.ui.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.zmm.diary.R;
import com.zmm.diary.ui.widget.wheelview.OnWheelChangedListener;
import com.zmm.diary.ui.widget.wheelview.WheelView;
import com.zmm.diary.ui.widget.wheelview.adapter.NumericWheelAdapter;

import java.util.Calendar;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/19
 * Time:上午11:05
 */

public class DateSelectView extends View {

    private Context mContext;
    private View mRootView;
    private int mScreenWidth;


    private WheelView wl_start_year;
    private WheelView wl_start_month;
    private WheelView wl_start_day;

    private final int START_YEAR = 1940;
    private final int MIDDLE_YEAR = 1940;
    private final int END_YEAR = 2030;
    private final int START_MONTH = 1;
    private final int END_MONTH = 12;

    private int curYear;
    private int curMonth;

    private String preBirthday = "1960-6-15";

    private int selcetY = 0;
    private int selcetM = 0;
    private int selcetD = 0;

    private OnDateClickListener mOnDateClickListener;

    public void setOnDateClickListener(OnDateClickListener onDateClickListener) {
        mOnDateClickListener = onDateClickListener;
    }

    public interface OnDateClickListener{
        void onDateClick(String date);
    }

    public DateSelectView(Context context, View rootView, int screenWidth, String date) {
        super(context);
        mContext = context;
        mRootView = rootView;
        mScreenWidth = screenWidth;
        if(!TextUtils.isEmpty(date)){
            preBirthday = date;
        }

        String[] split = preBirthday.split("-");


        selcetY = Integer.parseInt(split[0]);
        selcetM = Integer.parseInt(split[1]);
        selcetD = Integer.parseInt(split[2]);


        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupWindowView = inflater.inflate(R.layout.item_date_select, null, false);
        Button btn_cancel = (Button) popupWindowView.findViewById(R.id.btn_cancel);
        Button btn_ok = (Button) popupWindowView.findViewById(R.id.btn_ok);
//        final PopupWindow popupWindow = new PopupWindow(popupWindowView, 4 * mScreenWidth / 5, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        final PopupWindow popupWindow = new PopupWindow(popupWindowView, mScreenWidth, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(false);

        initAgeWheelView(popupWindowView);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
//                makeWindowLight();
            }
        });

        btn_ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                popupWindow.dismiss();

                String date = getDate();
                if(mOnDateClickListener != null){
                    mOnDateClickListener.onDateClick(date);
                }

            }
        });

        btn_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.showAtLocation(mRootView, Gravity.BOTTOM, 0, 0);

    }


    private void initAgeWheelView(View view) {
        Calendar c = Calendar.getInstance();
        curYear = c.get(Calendar.YEAR);
        curMonth = c.get(Calendar.MONTH) + 1;
        wl_start_year = (WheelView) view.findViewById(R.id.wl_start_year);
        wl_start_month = (WheelView) view.findViewById(R.id.wl_start_month);
        wl_start_day = (WheelView) view.findViewById(R.id.wl_start_day);

        NumericWheelAdapter numericWheelAdapterStart1 = new NumericWheelAdapter(mContext, START_YEAR, END_YEAR);
        numericWheelAdapterStart1.setLabel(" ");
        wl_start_year.setViewAdapter(numericWheelAdapterStart1);
        numericWheelAdapterStart1.setTextColor(R.color.black);
        numericWheelAdapterStart1.setTextSize(20);
        wl_start_year.setCyclic(true);
        wl_start_year.setVisibleItems(5);
        wl_start_year.setCurrentItem(selcetY - START_YEAR);

//        wl_start_year.addScrollingListener(startAgeScrollListener);
        wl_start_year.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                curYear = newValue + MIDDLE_YEAR;
                initStartDayAdapter();
            }
        });

        NumericWheelAdapter numericWheelAdapterStart2 = new NumericWheelAdapter(mContext, START_MONTH, END_MONTH, "%02d");
        numericWheelAdapterStart2.setLabel(" ");
        wl_start_month.setViewAdapter(numericWheelAdapterStart2);
        numericWheelAdapterStart2.setTextColor(R.color.black);
        numericWheelAdapterStart2.setTextSize(20);
        wl_start_month.setCyclic(true);
        wl_start_month.setVisibleItems(5);
        wl_start_month.setCurrentItem(selcetM - START_MONTH);

//        wl_start_month.addScrollingListener(startAgeScrollListener);
        wl_start_month.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                curMonth = newValue + 1;
                initStartDayAdapter();
            }
        });
        initStartDayAdapter();
    }

    private void initStartDayAdapter() {
        NumericWheelAdapter numericWheelAdapterStart3 = new NumericWheelAdapter(mContext, 1, getDay(curYear, curMonth), "%02d");
        numericWheelAdapterStart3.setLabel(" ");
        wl_start_day.setViewAdapter(numericWheelAdapterStart3);
        numericWheelAdapterStart3.setTextColor(R.color.black);
        numericWheelAdapterStart3.setTextSize(20);
        wl_start_day.setCyclic(true);
        wl_start_day.setVisibleItems(5);
        wl_start_day.setCurrentItem(selcetD-1);

//        wl_start_day.addScrollingListener(startAgeScrollListener);
    }

//    OnWheelScrollListener startAgeScrollListener = new OnWheelScrollListener() {
//        @Override
//        public void onScrollingStarted(WheelView wheel) {
//
//        }
//
//        @Override
//        public void onScrollingFinished(WheelView wheel) {
////            mAddEtAge.setText(getDate());//若是需要值随之改变,可调用此方法
////            getDate();
//        }
//    };

    private String getDate() {
        int n_year = wl_start_year.getCurrentItem() + MIDDLE_YEAR;
        int n_month = wl_start_month.getCurrentItem() + 1;
        int n_day = wl_start_day.getCurrentItem() + 1;
        String switchDate = n_year + "-" + n_month + "-" + n_day;
//        long time = System.currentTimeMillis();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy");
//        Date date = new Date(time);
//        String nowYear = format.format(date);
//        age = Integer.parseInt(nowYear) - n_year;

        return switchDate;
    }

    /**
     * 根据年月获得 这个月总共有几天
     * @param year
     * @param month
     * @return
     */
    public int getDay(int year, int month) {
        int day = 30;
        boolean flag = false;
        switch (year % 4) {
            case 0:
                flag = true;
                break;
            default:
                flag = false;
                break;
        }
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            case 2:
                day = flag ? 29 : 28;
                break;
            default:
                day = 30;
                break;
        }
        return day;
    }

}
