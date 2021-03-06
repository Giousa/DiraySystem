package com.zmm.diary.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zmm.diary.R;
import com.zmm.diary.bean.AuthorBean;
import com.zmm.diary.bean.HotspotBean;
import com.zmm.diary.ui.widget.GlideCircleTransform;
import com.zmm.diary.utils.GlideUtils;
import com.zmm.diary.utils.VerificationUtils;
import com.zmm.diary.utils.config.CommonConfig;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/12/18
 * Email:65489469@qq.com
 */
public class HotspotAdapter extends BaseQuickAdapter<HotspotBean,BaseViewHolder>{


    public HotspotAdapter() {
        super(R.layout.item_hotspot);

    }

    @Override
    protected void convert(BaseViewHolder helper, HotspotBean item) {

        helper.setText(R.id.tv_item_content,item.getContent());

        ImageView picBg = helper.getView(R.id.iv_item_pic_bg);

        GlideUtils.loadImage(mContext,CommonConfig.BASE_PIC_URL + item.getPic(),picBg);

        ImageView authorIcon = helper.getView(R.id.iv_item_author_icon);

        //作者信息
        AuthorBean author = item.getAuthor();

        if(author != null){

            GlideUtils.loadCircleImage(mContext,CommonConfig.BASE_PIC_URL + author.getIcon(),authorIcon);

            if(TextUtils.isEmpty(author.getNickname())){
                helper.setText(R.id.tv_item_author_name, VerificationUtils.hidePhoneNumber(author.getUsername()));
            }else {
                helper.setText(R.id.tv_item_author_name,author.getNickname());
            }

        }


        //点赞数
        helper.setText(R.id.tv_item_appreciate_count,item.getAppreciate()+"");

    }
}
