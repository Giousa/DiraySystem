package com.zmm.diary.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.zmm.diary.R;
import com.zmm.diary.ui.activity.RecordInfoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/12/3
 * Email:65489469@qq.com
 */
public class ImagePickerAdapter extends BaseQuickAdapter<ImageItem,BaseViewHolder> {


    private int maxImgCount;
    private Context mContext;
    private List<ImageItem> mData;
    private boolean isAdded;   //是否额外添加了最后一个图片
    private OnRecyclerViewItemClickListener listener;



    public ImagePickerAdapter(Context mContext, List<ImageItem> data, int maxImgCount) {
        super(R.layout.item_note_info_pic);

        this.mContext = mContext;
        this.maxImgCount = maxImgCount;

        setImages(data);
    }

    public void setImages(List<ImageItem> data) {
        mData = new ArrayList<>(data);
        if (mData.size() < maxImgCount) {
            mData.add(new ImageItem());
            isAdded = true;
        } else {
            isAdded = false;
        }
        setNewData(mData);
    }

    public List<ImageItem> getImages() {
        //由于图片未选满时，最后一张显示添加图片，因此这个方法返回真正的已选图片
        if (isAdded){
            return new ArrayList<>(mData.subList(0, mData.size() - 1));
        } else {
            return mData;
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, ImageItem item) {

        final int clickPosition;
        int currentPosition = helper.getLayoutPosition();
        ImageView icon = helper.getView(R.id.iv_img);

        System.out.println("getItemCount() = "+mData.size());
        System.out.println("currentPosition = "+currentPosition);

        if (isAdded && currentPosition == mData.size() - 1) {
            helper.setImageResource(R.id.iv_img,R.drawable.selector_image_add);
            clickPosition = RecordInfoActivity.IMAGE_ITEM_ADD;
        } else {
            ImagePicker.getInstance().getImageLoader().displayImage((Activity) mContext, item.path, icon, 0, 0);
            clickPosition = currentPosition;
        }

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onItemClick(clickPosition);
                }
            }
        });
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }
}
