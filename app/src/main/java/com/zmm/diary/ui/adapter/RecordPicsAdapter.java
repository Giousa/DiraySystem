package com.zmm.diary.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zmm.diary.R;
import com.zmm.diary.utils.config.CommonConfig;

import java.util.ArrayList;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/12/18
 * Email:65489469@qq.com
 */
public class RecordPicsAdapter extends BaseQuickAdapter<String,BaseViewHolder>{


    public RecordPicsAdapter(int layoutId, ArrayList<String> piclist) {
        super(layoutId,piclist);

    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        ImageView icon = helper.getView(R.id.iv_img);

        Glide.with(mContext)
                .load(CommonConfig.BASE_PIC_URL+item)
                .placeholder(R.drawable.default_bg)
                .error(R.drawable.default_bg)
                .into(icon);

    }
}
