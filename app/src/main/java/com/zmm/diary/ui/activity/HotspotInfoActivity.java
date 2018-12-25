package com.zmm.diary.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.zmm.diary.R;
import com.zmm.diary.bean.UserBean;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.ui.widget.TitleBar;
import com.zmm.diary.utils.PictureCompressUtil;
import com.zmm.diary.utils.SharedPreferencesUtil;
import com.zmm.diary.utils.ToastUtils;
import com.zmm.diary.utils.UIUtils;
import com.zmm.diary.utils.config.CommonConfig;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/12/3
 * Email:65489469@qq.com
 */
public class HotspotInfoActivity extends BaseActivity {


    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.iv_hotspot_select)
    ImageView mIvHotspotSelect;


    private ArrayList<ImageItem> mImages;
    private ArrayList<String> mNewListPath = new ArrayList<>();


    @Override
    protected int setLayout() {
        return R.layout.activity_hotspot_info;
    }

    @Override
    protected void setupActivityComponent(HttpComponent httpComponent) {
    }

    @Override
    protected void init() {

        hideKeyboard(mEtContent);

        initToolBar();

    }

    private void initToolBar() {

        mTitleBar.setTitle("");
        mTitleBar.setLeftText("取消");
        mTitleBar.setLeftTextColor(UIUtils.getResources().getColor(R.color.white));
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleBar.setActionTextColor(UIUtils.getResources().getColor(R.color.md_green_A700));
        mTitleBar.addAction(new TitleBar.TextAction("发表") {
            @Override
            public void performAction(View view) {
                submit();

            }
        });
    }

    /**
     * 提交数据
     */
    private void submit() {

        String userJson = SharedPreferencesUtil.getString(CommonConfig.LOGIN_USER, null);


        //TODO  提示是否登录
        if (TextUtils.isEmpty(userJson)) {
            startActivity(LoginActivity.class, true);
            return;
        }

        UserBean userBean = SharedPreferencesUtil.fromJson(userJson, UserBean.class);

        if (userBean == null) {

            startActivity(LoginActivity.class, true);
            return;
        }

        String content = mEtContent.getText().toString();


        if (!TextUtils.isEmpty(content)) {
//            mPresenter.addRecord(userBean.getId(),content,mNewListPath);
        } else {
            ToastUtils.SimpleToast("内容不能为空");
        }
    }

    @OnClick(R.id.iv_hotspot_select)
    public void onViewClicked() {

        //单选 不允许裁剪
        ImagePicker.getInstance().setMultiMode(false);
        ImagePicker.getInstance().setCrop(false);

        Intent intent = new Intent(mContext, ImageGridActivity.class);
        startActivityForResult(intent, 100);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {

                mImages = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);

                if (mImages != null) {

                    Glide.with(mContext).load(mImages.get(0).path).placeholder(R.drawable.default_bg).error(R.drawable.default_bg).into(mIvHotspotSelect);

                    for (int i = 0; i < mImages.size(); i++) {

                        System.out.println("选取的图片名称1："+mImages.get(i).name);
                        System.out.println("选取的图片类型1："+mImages.get(i).mimeType);

                        String mimeType = mImages.get(i).mimeType;
                        if(mimeType.contains("gif")){
                            mNewListPath.add(0,mImages.get(i).path);

                        }else {

                            try {
                                Bitmap bitmap = PictureCompressUtil.revitionImageSize(mImages.get(i).path);
                                String newPath = PictureCompressUtil.saveBitmapFile(bitmap, "tmsystem/"+mImages.get(i).name);
                                mNewListPath.add(0,"/storage/emulated/0/"+newPath);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                }
            } else {
                System.out.println("没有数据");
            }
        }
    }



}
