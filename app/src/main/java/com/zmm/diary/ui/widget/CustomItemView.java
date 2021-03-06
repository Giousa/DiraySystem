package com.zmm.diary.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
public class CustomItemView extends LinearLayout{



    private ImageView mIvIcon;
    private TextView mTvItemTitle;
    private TextView mTvItemContent;
    private RelativeLayout mRl_item;


    private OnItemClickListener mOnItemClickListener;
    private String mTitle;

    private boolean isShowPic = true;


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


    public CustomItemView(Context context) {
        this(context,null);
    }

    public CustomItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //获取设置的自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomItemView);
        mTitle = typedArray.getString(R.styleable.CustomItemView_title);
        int icon = typedArray.getResourceId(R.styleable.CustomItemView_icon, R.drawable.icon_add);
        String isShow = typedArray.getString(R.styleable.CustomItemView_isShowPic);


        if(!TextUtils.isEmpty(isShow) && isShow.equals("none")){
            isShowPic = false;
        }else {
            isShowPic = true;
        }

        //开始初始化控件
        initView();

        typedArray.recycle();

        //将获取的属性值赋予控件从而展示
        mTvItemTitle.setText(mTitle);
        mIvIcon.setImageDrawable(UIUtils.getResources().getDrawable(icon));

    }

    private void initView() {


        View view;
        view = View.inflate(getContext(), R.layout.info_item_pic_view, this);
        mIvIcon = view.findViewById(R.id.iv_item_pic);
        mTvItemTitle = view.findViewById(R.id.tv_item_title);
        mRl_item = view.findViewById(R.id.rl_item);
        mTvItemContent = view.findViewById(R.id.tv_item_content);

        mRl_item.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener != null){
                    mOnItemClickListener.OnItemClick(mTitle);
                }
            }
        });

        if(isShowPic){
            mIvIcon.setVisibility(VISIBLE);
        }else {
            mIvIcon.setVisibility(GONE);
        }
    }

    public void setContent(String content){
        mTvItemContent.setText(content);
    }

    public String getContent(){
        return mTvItemContent.getText().toString();
    }



    public interface OnItemClickListener{
        void OnItemClick(String title);
    }
}
