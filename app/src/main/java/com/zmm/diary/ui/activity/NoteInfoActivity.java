package com.zmm.diary.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.yanzhenjie.sofia.Sofia;
import com.zmm.diary.R;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.ui.adapter.ImagePickerAdapter;
import com.zmm.diary.ui.widget.TitleBar;
import com.zmm.diary.utils.PictureCompressUtil;
import com.zmm.diary.utils.ToastUtils;
import com.zmm.diary.utils.UIUtils;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/12/3
 * Email:65489469@qq.com
 */
public class NoteInfoActivity extends BaseActivity implements ImagePickerAdapter.OnRecyclerViewItemClickListener{


    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;


    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 6;               //允许选择图片最大数
    private ArrayList<ImageItem> images = null;
    private ArrayList<String> mNewListPath = new ArrayList<>();


    @Override
    protected int setLayout() {
        return R.layout.activity_note_info;
    }

    @Override
    protected void setupActivityComponent(HttpComponent httpComponent) {

    }

    @Override
    protected void init() {

        hideKeyboard(mEtContent);

        initToolBar();

        initRecyclerView();


        //多选
        ImagePicker.getInstance().setMultiMode(true);
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
                ToastUtils.SimpleToast("发表了");
            }
        });
    }

    private void initRecyclerView() {

        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:

                //打开选择,本次允许选择的数量
                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                Intent intent1 = new Intent(NoteInfoActivity.this, ImageGridActivity.class);
                startActivityForResult(intent1, REQUEST_CODE_SELECT);

                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);

                    for (int i = 0; i < images.size(); i++) {

                        System.out.println("选取的图片名称1："+images.get(i).name);
                        Bitmap bitmap = null;
                        try {
                            bitmap = PictureCompressUtil.revitionImageSize(images.get(i).path);
                            String newPath = PictureCompressUtil.saveBitmapFile(bitmap, "tmsystem/"+images.get(i).name);
                            mNewListPath.add("/storage/emulated/0/"+newPath);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images != null) {
                    selImageList.clear();
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);

                    mNewListPath.clear();

                    for (int i = 0; i < images.size(); i++) {

                        System.out.println("选取的图片名称2："+images.get(i).name);
                        Bitmap bitmap = null;
                        try {
                            bitmap = PictureCompressUtil.revitionImageSize(images.get(i).path);
                            String newPath = PictureCompressUtil.saveBitmapFile(bitmap, "tmsystem/"+images.get(i).name);
                            mNewListPath.add("/storage/emulated/0/"+newPath);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
