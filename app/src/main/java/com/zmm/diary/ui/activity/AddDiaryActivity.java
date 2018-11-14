package com.zmm.diary.ui.activity;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zmm.diary.MyApplication;
import com.zmm.diary.R;
import com.zmm.diary.bean.NoteBean;
import com.zmm.diary.bean.UserBean;
import com.zmm.diary.dagger.component.DaggerNoteComponent;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.dagger.module.NoteModule;
import com.zmm.diary.mvp.presenter.NotePresenter;
import com.zmm.diary.mvp.presenter.contract.NoteContract;
import com.zmm.diary.ui.popup.DiaryTitlePopup;
import com.zmm.diary.ui.widget.TitleBar;
import com.zmm.diary.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/11
 * Email:65489469@qq.com
 */
public class AddDiaryActivity extends BaseActivity<NotePresenter> implements NoteContract.NoteView {


    @BindView(R.id.ll_root)
    LinearLayout mLlRoot;
    @BindView(R.id.radio_group_shiwu)
    RadioGroup mRadioGroupShiwu;
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

    private String mType = "私";
    private String mSpendType = "支出";

    private List<String> mPersonalList =  Arrays.asList("上班","回家","游戏","聚餐","购物","逛街","锻炼","程序","宠物","旅游","拍摄","保密");
    private List<String> mWorkList =  Arrays.asList("工作","上班打卡","下班打卡","请假","工资","加班","程序","奖金","活动","补卡","展会","拓展","面试","考核");
    private List<String> mPopupShowList = new ArrayList<>();

    @Override
    protected int setLayout() {
        return R.layout.activity_add_diary;
    }

    @Override
    protected void setupActivityComponent(HttpComponent httpComponent) {
        DaggerNoteComponent.builder()
                .httpComponent(httpComponent)
                .noteModule(new NoteModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void init() {


        mRadioGroupShiwu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                switch (checkedId) {
                    case R.id.rb_personal:
                        mType = "私";
                        break;

                    case R.id.rb_work:
                        mType = "公";
                        break;

                    case R.id.rb_spend:
                        mType = "消费";
                        break;
                }

                spendChecked();

            }
        });

        mRadioGroupSpend.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_zhichu:
                        mSpendType = "支出";
                        break;

                    case R.id.rb_shouru:
                        mSpendType = "收入";

                        break;
                }
            }
        });


    }

    private void spendChecked() {
        if (mRbSpend.isChecked()) {
            mLlSpend.setVisibility(View.VISIBLE);
            mRadioGroupSpend.setVisibility(View.VISIBLE);
            mLlTitle.setVisibility(View.GONE);

        } else {
            mLlSpend.setVisibility(View.GONE);
            mRadioGroupSpend.setVisibility(View.GONE);
            mLlTitle.setVisibility(View.VISIBLE);
        }
    }


    @OnClick({R.id.iv_popup, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_popup:

                if(mType.equals("私")){
                    mPopupShowList = mPersonalList;
                }else {
                    mPopupShowList = mWorkList;
                }

                DiaryTitlePopup diaryTitlePopup = DiaryTitlePopup.create(mPopupShowList)
                        .setContext(this)
                        .apply();

                diaryTitlePopup.showAtLocation(mLlRoot, Gravity.BOTTOM, 0, 0);
                diaryTitlePopup.setOnPopupClickListener(new DiaryTitlePopup.OnPopupClickListener() {
                    @Override
                    public void OnPopupClick(String title) {
                        mEtTitle.setText(title);
                        mEtTitle.setSelection(title.length());
                    }
                });
                break;
            case R.id.btn_submit:

                submit();
                break;
        }
    }

    private void submit() {

        UserBean userBean = MyApplication.userBean;

        if(userBean == null || TextUtils.isEmpty(userBean.getId())){
            startActivity(LoginActivity.class,true);
            return;
        }

        String title = mEtTitle.getText().toString();
        String content = mEtContent.getText().toString();
        String spend = mEtSpend.getText().toString();

        NoteBean noteBean = new NoteBean();

        noteBean.setUid(userBean.getId());
        noteBean.setContent(content);

        if(mType.equals("私")){

            if(TextUtils.isEmpty(title) || TextUtils.isEmpty(content)){
                ToastUtils.SimpleToast("标题和内容不能为空");
                return;
            }

            noteBean.setType(mType);
            noteBean.setTitle(title);

        }else if(mType.equals("公")){
            if(TextUtils.isEmpty(title) || TextUtils.isEmpty(content)){
                ToastUtils.SimpleToast("标题和内容不能为空");
                return;
            }

            noteBean.setType(mType);
            noteBean.setTitle(title);
        }else {//消费

            if(TextUtils.isEmpty(spend) || TextUtils.isEmpty(content)){
                ToastUtils.SimpleToast("消费和内容不能为空");
                return;
            }

            noteBean.setType(mSpendType);
            noteBean.setTitle(spend);
        }


        mPresenter.addNote(noteBean);

    }

    @Override
    public void addSuccess() {
        ToastUtils.SimpleToast("添加成功");
        finish();
    }

    @Override
    public void updateSuccess() {

    }

    @Override
    public void deleteSuccess() {

    }

    @Override
    public void findNoteSuccess(NoteBean noteBean) {

    }

    @Override
    public void findTodayNotesSuccess(List<NoteBean> noteBeanList) {

    }

}

