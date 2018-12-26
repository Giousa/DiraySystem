package com.zmm.diary.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zmm.diary.R;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/15
 * Time:下午11:41
 */

public class SimpleInputDialog extends Dialog {

    private TextView mTitle;
    private EditText mEditText;
    private Button mCancel;
    private Button mConfirm;
    private String title;
    private String hint;
    private String name;


    private OnClickListener mOnClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public SimpleInputDialog(Context context, String title, String hint, String name) {
        super(context, R.style.SimpleDialog);
        this.title = title;
        this.hint = hint;
        this.name = name;
    }

    public interface OnClickListener{

        void onCancel();

        void onConfirm(String content);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_simple_input_dialog);
        //按空白处不能取消dialog
        setCanceledOnTouchOutside(false);

        initView();

        initEvent();

    }


    private void initView() {
        mTitle = findViewById(R.id.tv_dialog_title);
        mEditText = findViewById(R.id.et_dialog_input);
        mCancel = findViewById(R.id.btn_dialog_cancel);
        mConfirm = findViewById(R.id.btn_dialog_confirm);

        mTitle.setText(title);
        mEditText.setHint(hint);

        if(!TextUtils.isEmpty(name)){
            mEditText.setText(name);
            mEditText.setSelection(name.length());
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
                    mOnClickListener.onConfirm(mEditText.getText().toString().trim());
                }
            }
        });

        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(mOnClickListener != null){
                    mOnClickListener.onConfirm(mEditText.getText().toString().trim());
                }
                return true;
            }
        });
    }


    public String getInput(){
        return mEditText.getText().toString().trim();
    }


}
