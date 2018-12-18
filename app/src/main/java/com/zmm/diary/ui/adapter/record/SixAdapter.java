package com.zmm.diary.ui.adapter.record;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.imagepicker.ImagePicker;
import com.zmm.diary.R;
import com.zmm.diary.utils.config.CommonConfig;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/12/18
 * Email:65489469@qq.com
 */
public class SixAdapter extends BaseQuickAdapter<String,BaseViewHolder>{


    public SixAdapter() {
        super(R.layout.item_record_six);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        ImageView icon = helper.getView(R.id.iv_img);

        Glide.with(mContext).load(CommonConfig.BASE_PIC_URL+item).into(icon);

    }
}
