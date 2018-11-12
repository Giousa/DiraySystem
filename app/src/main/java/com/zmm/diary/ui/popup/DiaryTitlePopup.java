package com.zmm.diary.ui.popup;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.yang.flowlayoutlibrary.FlowLayout;
import com.zmm.diary.R;
import com.zmm.diary.ui.adapter.PopupTitleAdapter;
import com.zmm.diary.utils.ToastUtils;
import com.zyyoona7.popup.BasePopup;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/11
 * Email:65489469@qq.com
 */
public class DiaryTitlePopup extends BasePopup<DiaryTitlePopup>{


    private OnPopupClickListener mOnPopupClickListener;

    public void setOnPopupClickListener(OnPopupClickListener onPopupClickListener) {
        mOnPopupClickListener = onPopupClickListener;
    }

    public static DiaryTitlePopup create(){
        return new DiaryTitlePopup();
    }


    @Override
    protected void initAttributes() {
        setContentView(R.layout.item_diary_popup_view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusAndOutsideEnable(true);
    }

    @Override
    protected void initViews(View view, DiaryTitlePopup diaryPopup) {

        FlowLayout flKeyword = findViewById(R.id.fl_keyword);


        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("标题_"+i);
        }


        //这些配置，已在xml中设置完毕
//        flKeyword.setTextSize(15);
        // 设置文字颜色
//        flKeyword.setTextColor(Color.BLACK);
        // 设置文字背景
//        flKeyword.setBackgroundResource(R.drawable.bg_frame);

//        // 设置文字水平margin
//        flKeyword.setHorizontalSpacing(10);
//        // 设置文字垂直margin
//        flKeyword.setVerticalSpacing(10);
//        // 设置文字水平padding
//        flKeyword.setTextPaddingH(5);
//        // 设置文字垂直padding
//        flKeyword.setTextPaddingV(3);

        // 设置UI与点击事件监听
        // 最后调用setViews方法
        flKeyword.setViews(list, new FlowLayout.OnItemClickListener() {
            @Override
            public void onItemClick(String content) {

                if(mOnPopupClickListener != null){
                    mOnPopupClickListener.OnPopupClick(content);
                }
            }
        });

    }


    public interface OnPopupClickListener{
        void OnPopupClick(String title);
    }
}
