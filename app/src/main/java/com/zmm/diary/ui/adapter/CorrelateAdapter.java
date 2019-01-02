package com.zmm.diary.ui.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zmm.diary.R;
import com.zmm.diary.bean.CorrelateBean;
import com.zmm.diary.ui.widget.GlideCircleTransform;
import com.zmm.diary.utils.config.CommonConfig;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/9
 * Email:65489469@qq.com
 */
public class CorrelateAdapter extends BaseQuickAdapter<CorrelateBean,BaseViewHolder>{

    public CorrelateAdapter(){
        super(R.layout.item_correlate);
    }

    @Override
    protected void convert(BaseViewHolder helper, final CorrelateBean item) {

        //头像
        ImageView icon = helper.getView(R.id.iv_correlate_icon);
        Glide.with(mContext)
                .load(CommonConfig.BASE_PIC_URL+item.getIcon())
                .placeholder(R.drawable.default_my_icon)
                .error(R.drawable.default_my_icon)
                .transform(new GlideCircleTransform(mContext))
                .into(icon);

        //昵称
        String nickname = item.getNickname();
        if(TextUtils.isEmpty(nickname)){
            helper.setText(R.id.tv_correlate_name,item.getUsername());
        }else {
            helper.setText(R.id.tv_correlate_name,nickname);
        }

        //标签
        helper.setText(R.id.tv_correlate_sign,item.getSign());

        //发布
        helper.setText(R.id.tv_correlate_release_count,item.getReleases()+"");

        //粉丝
        helper.setText(R.id.tv_correlate_fun_count,item.getFuns()+"");

    }

}
