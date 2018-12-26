package com.zmm.diary.ui.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zmm.diary.R;
import com.zmm.diary.ui.widget.wheelview.WheelView;
import com.zmm.diary.ui.widget.wheelview.adapter.ListStringWheelAdapter;

import java.util.List;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/16
 * Time:下午8:37
 */

public class SingleSelectView extends View {

    private Context mContext;
    private int mScreenWidth;
    private String mTitle;
    private List<String> mList;
    private int mIndex;

    private ListStringWheelAdapter mListStringWheelAdapter = null;
    private LinearLayout mRootView;
    private TextView mTvTitle;
    private WheelView mWheelView;

    private OnSelectClickListener mOnSelectClickListener;
    private PopupWindow mPopupWindow;

    public void setOnSelectClickListener(OnSelectClickListener onSelectClickListener) {
        mOnSelectClickListener = onSelectClickListener;
    }

    public interface OnSelectClickListener {

        void onCancel();

        void onConfirm(String content);
    }


    public SingleSelectView(Context context, LinearLayout rootView, int screenWidth, String title, List<String> list,int index) {
        super(context);
        mContext = context;
        mRootView = rootView;
        mScreenWidth = screenWidth;
        mTitle = title;
        mList = list;
        mIndex = index;
        init();
    }

    private void init() {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View view = inflater.inflate(R.layout.item_single_select, null);
        Button cancel = view.findViewById(R.id.btn_select_cancel);
        Button confirm = view.findViewById(R.id.btn_select_confirm);
        mTvTitle = view.findViewById(R.id.pop_title);
        mWheelView = view.findViewById(R.id.pop_wl);

        mPopupWindow = new PopupWindow(view, mScreenWidth, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setFocusable(false);
        mPopupWindow.setOutsideTouchable(false);

        mTvTitle.setText(mTitle);
        mListStringWheelAdapter = new ListStringWheelAdapter(mContext, mList);
        mWheelView.setViewAdapter(mListStringWheelAdapter);
        mListStringWheelAdapter.setTextColor(R.color.black);
        mListStringWheelAdapter.setTextSize(20);
        mWheelView.setCyclic(false);
        mWheelView.setCurrentItem(0);
        mListStringWheelAdapter.setItemTextResource(mWheelView.getCurrentItem());

        mWheelView.setCurrentItem(mIndex);

        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                mPopupWindow.dismiss();
                if (mOnSelectClickListener != null) {
                    mOnSelectClickListener.onCancel();
                }
            }
        });


        confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                if (mOnSelectClickListener != null) {
                    mOnSelectClickListener.onConfirm(mList.get(mWheelView.getCurrentItem()));
                }
            }
        });

        mPopupWindow.showAtLocation(mRootView, Gravity.BOTTOM, 0, 0);
    }


}
