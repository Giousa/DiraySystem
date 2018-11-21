package com.zmm.diary.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zmm.diary.R;
import com.zmm.diary.utils.UIUtils;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/20
 * Email:65489469@qq.com
 */
public class CustomTitleItemView extends LinearLayout{


    private TextView mTvItemTitle;
    private RelativeLayout mRl_item;


    private OnItemClickListener mOnItemClickListener;
    private String mTitle;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


    public CustomTitleItemView(Context context) {
        this(context,null);
    }

    public CustomTitleItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomTitleItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //获取设置的自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomItemView);
        mTitle = typedArray.getString(R.styleable.CustomItemView_title);

        //开始初始化控件
        initView();

        typedArray.recycle();

        //将获取的属性值赋予控件从而展示
        mTvItemTitle.setText(mTitle);

    }

    private void initView() {


        View view;
        view = View.inflate(getContext(), R.layout.info_item_title_view, this);
        mTvItemTitle = view.findViewById(R.id.tv_item_title);
        mRl_item = view.findViewById(R.id.rl_item);

        mRl_item.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener != null){
                    mOnItemClickListener.OnItemClick(mTitle);
                }
            }
        });
    }

    public interface OnItemClickListener{
        void OnItemClick(String title);
    }
}
