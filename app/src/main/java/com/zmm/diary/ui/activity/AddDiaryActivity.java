package com.zmm.diary.ui.activity;

import android.view.Gravity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zmm.diary.R;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.ui.popup.DiaryTitlePopup;
import com.zmm.diary.ui.widget.TitleBar;
import com.zmm.diary.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/11
 * Email:65489469@qq.com
 */
public class AddDiaryActivity extends BaseActivity {


    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.rb_personal)
    RadioButton mRbPersonal;
    @BindView(R.id.rb_work)
    RadioButton mRbWork;
    @BindView(R.id.rb_spend)
    RadioButton mRbSpend;
    @BindView(R.id.rb_zhichu)
    RadioButton mRbZhichu;
    @BindView(R.id.rb_shouru)
    RadioButton mRbShouru;
    @BindView(R.id.radio_group_spend)
    RadioGroup mRadioGroupSpend;
    @BindView(R.id.et_spend)
    EditText mEtSpend;
    @BindView(R.id.ll_spend)
    LinearLayout mLlSpend;
    @BindView(R.id.iv_popup)
    ImageView mIvPopup;
    @BindView(R.id.et_title)
    EditText mEtTitle;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.ll_root)
    LinearLayout mLlRoot;

    @Override
    protected int setLayout() {
        return R.layout.activity_add_diary;
    }

    @Override
    protected void setupActivityComponent(HttpComponent httpComponent) {

    }

    @Override
    protected void init() {

    }

    @OnClick(R.id.iv_popup)
    public void onViewClicked() {

        DiaryTitlePopup diaryTitlePopup = DiaryTitlePopup.create()
                .setContext(this)
                .apply();

        diaryTitlePopup.showAtLocation(mLlRoot, Gravity.BOTTOM, 0, 0);
        diaryTitlePopup.setOnPopupClickListener(new DiaryTitlePopup.OnPopupClickListener() {
            @Override
            public void OnPopupClick(String title) {
                ToastUtils.SimpleToast("被选中的："+title);
            }
        });
    }
}

