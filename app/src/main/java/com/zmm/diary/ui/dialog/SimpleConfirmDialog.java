package com.zmm.diary.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zmm.diary.R;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/15
 * Time:下午11:41
 */

public class SimpleConfirmDialog extends Dialog {

    private TextView mTitle;
    private Button mCancel;
    private Button mConfirm;
    private String title;


    private OnClickListener mOnClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public SimpleConfirmDialog(Context context, String title) {
        super(context, R.style.SimpleDialog);
        this.title = title;
    }

    public interface OnClickListener{

        void onCancel();

        void onConfirm();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_simple_confirm_dialog);
        //按空白处不能取消dialog
        setCanceledOnTouchOutside(false);

        initView();

        initEvent();

    }


    private void initView() {
        mTitle = findViewById(R.id.tv_dialog_title);
        mCancel = findViewById(R.id.btn_dialog_cancel);
        mConfirm = findViewById(R.id.btn_dialog_confirm);

        if(!TextUtils.isEmpty(title)){
            mTitle.setText(title);
        }
    }


    private void initEvent() {
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnClickListener != null){
                    mOnClickListener.onCancel();
                }
            }
        });

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnClickListener != null){
                    mOnClickListener.onConfirm();
                }
            }
        });

    }

}
