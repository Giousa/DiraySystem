package com.zmm.diary.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.liyi.viewer.ImageLoader;
import com.liyi.viewer.ViewData;
import com.liyi.viewer.dragger.ImageDraggerType;
import com.liyi.viewer.listener.OnItemClickListener;
import com.liyi.viewer.widget.ImageViewer;
import com.zmm.diary.R;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.utils.GlideUtils;
import com.zmm.diary.utils.config.CommonConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2019/1/3
 * Email:65489469@qq.com
 */
public class PreviewActivity extends BaseActivity {


    @BindView(R.id.image_preivew)
    ImageViewer mImagePreivew;

    @Override
    protected int setLayout() {
        return R.layout.activity_preview;
    }

    @Override
    protected void setupActivityComponent(HttpComponent httpComponent) {

    }

    @Override
    protected void init() {

        Intent intent = getIntent();
        int position = intent.getIntExtra("position",0);
        String[] pics = intent.getStringArrayExtra("pics");

        List<String> mImageList = new ArrayList<>();
        List<ViewData> mViewList = new ArrayList<>();

        for (String pic : pics) {
            mImageList.add(CommonConfig.BASE_PIC_URL+pic);
            ViewData viewData = new ViewData();
            mViewList.add(viewData);
        }

        mImagePreivew.doDrag(true);
        mImagePreivew.setDragType(ImageDraggerType.DRAG_TYPE_WX);
        mImagePreivew.setStartPosition(position);
        mImagePreivew.setImageData(mImageList);
        mImagePreivew.setViewData(mViewList);
        mImagePreivew.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(int position, Object source, final ImageView imageView) {
                GlideUtils.loadImage(mContext, (String) source,imageView);
            }
        });

        mImagePreivew.watch();

        mImagePreivew.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public boolean onItemClick(int position, View view) {
                finish();
                return false;
            }
        });

    }

}
