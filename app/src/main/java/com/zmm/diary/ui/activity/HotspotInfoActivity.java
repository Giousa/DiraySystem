package com.zmm.diary.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.zmm.diary.R;
import com.zmm.diary.bean.HotspotBean;
import com.zmm.diary.bean.UserBean;
import com.zmm.diary.dagger.component.DaggerHotspotComponent;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.dagger.module.HotspotModule;
import com.zmm.diary.mvp.presenter.HotspotPresenter;
import com.zmm.diary.mvp.presenter.contract.HotspotContract;
import com.zmm.diary.ui.widget.TitleBar;
import com.zmm.diary.utils.GlideUtils;
import com.zmm.diary.utils.PictureCompressUtil;
import com.zmm.diary.utils.ToastUtils;
import com.zmm.diary.utils.UIUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/12/3
 * Email:65489469@qq.com
 */
public class HotspotInfoActivity extends BaseActivity<HotspotPresenter> implements HotspotContract.HotspotView {


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

        DaggerHotspotComponent.builder()
                .httpComponent(httpComponent)
                .hotspotModule(new HotspotModule(this))
                .build()
                .inject(this);
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


        //TODO  判断时候登录
        UserBean userBean = UIUtils.getUserBean();

        if(userBean == null){
            startActivity(LoginActivity.class, true);
            return;
        }

        String content = mEtContent.getText().toString();


        if (!TextUtils.isEmpty(content)) {
            mPresenter.addHotspot(userBean.getId(),content,mNewListPath.get(0));
        } else {
            ToastUtils.SimpleToast("内容不能为空");
        }
    }

    @OnClick(R.id.iv_hotspot_select)
    public void onViewClicked() {

        //单选 不允许裁剪
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setMultiMode(false);
        imagePicker.setCrop(false);
//        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
//        imagePicker.setFocusWidth(1080);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
//        imagePicker.setFocusHeight(400);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
//        imagePicker.setOutPutX(1080);//保存文件的宽度。单位像素
//        imagePicker.setOutPutY(400);//保存文件的高度。单位像素

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

                    //展示图片
//                    Glide.with(mContext)
//                            .load(mImages.get(0).path)
//                            .placeholder(R.drawable.default_bg)
//                            .error(R.drawable.default_bg)
//                            .into(mIvHotspotSelect);

                    GlideUtils.loadImage(mContext,mImages.get(0).path,mIvHotspotSelect);




                    for (int i = 0; i < mImages.size(); i++) {


                        String mimeType = mImages.get(i).mimeType;
                        if(!TextUtils.isEmpty(mimeType) && mimeType.contains("gif")){
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


    @Override
    public void addSuccess() {
        ToastUtils.SimpleToast("发表成功");
        setResult(2);
        finish();

    }

    @Override
    public void deleteSuccess() {

    }

    @Override
    public void appreciateOrCollectionOrAuthorStatus(String msg) {

    }

    @Override
    public void findHotspotSuccess(HotspotBean hotspotBean) {

    }

    @Override
    public void loadMoreHotspotSuccess(List<HotspotBean> hotspotBeanList) {

    }

    @Override
    public void refreshHotspotSuccess(List<HotspotBean> hotspotBeanList) {

    }

    @Override
    public void commentSuccess() {

    }

    @Override
    public void commentReplySuccess() {

    }
}
