package com.zmm.diary.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zmm.diary.R;
import com.zmm.diary.bean.CorrelateBean;
import com.zmm.diary.ui.widget.GlideCircleTransform;
import com.zmm.diary.utils.GlideUtils;
import com.zmm.diary.utils.VerificationUtils;
import com.zmm.diary.utils.config.CommonConfig;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/9
 * Email:65489469@qq.com
 */
public class CorrelateAdapter extends BaseQuickAdapter<CorrelateBean,BaseViewHolder>{


    private OnCorrelateStatusClickListener mOnCorrelateStatusClickListener;
    private String mNickname;
    private int mType;//0:关注 1：推荐关注

    public void setOnCorrelateStatusClickListener(OnCorrelateStatusClickListener onCorrelateStatusClickListener) {
        mOnCorrelateStatusClickListener = onCorrelateStatusClickListener;
    }

    public CorrelateAdapter(int type){
        super(R.layout.item_correlate);

        mType = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, final CorrelateBean item) {

        //头像
        final ImageView icon = helper.getView(R.id.iv_correlate_icon);

        GlideUtils.loadCircleImage(mContext,CommonConfig.BASE_PIC_URL + item.getIcon(),icon);


        //昵称
        mNickname = item.getNickname();
        if(TextUtils.isEmpty(mNickname)){
            mNickname = VerificationUtils.hidePhoneNumber(item.getUsername());
        }

        helper.setText(R.id.tv_correlate_name, mNickname);

        //标签
        helper.setText(R.id.tv_correlate_sign,item.getSign());

        //发布
        helper.setText(R.id.tv_correlate_release_count,item.getReleases()+"");

        //粉丝
        helper.setText(R.id.tv_correlate_fun_count,item.getFuns()+"");

        //关注状态
        if(mType == 0){
            helper.setText(R.id.tv_correlate_followers_status,item.isAttention() ? "取消关注" : "关注");
        }else {
            helper.setText(R.id.tv_correlate_followers_status,item.isAttention() ? "已关注" : "关注");
        }

        //移除和关注
        helper.getView(R.id.ll_correlate_followers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mOnCorrelateStatusClickListener != null){
                    mOnCorrelateStatusClickListener.OnCorrelateStatus(item.getId(), mNickname,item.isAttention());
                }
            }
        });



    }


    public interface OnCorrelateStatusClickListener{

        void OnCorrelateStatus(String id,String username,boolean attention);
    }

}
