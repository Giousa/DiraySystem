package com.zmm.diary.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zmm.diary.R;
import com.zmm.diary.bean.RecordBean;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/12/4
 * Email:65489469@qq.com
 */
public class RecordAdapter extends BaseQuickAdapter<RecordBean,BaseViewHolder>{

    public RecordAdapter() {
        super(R.layout.item_record);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecordBean item) {

        helper.setText(R.id.tv_item_content,item.getContent());
        helper.setText(R.id.tv_item_time,item.getCreateTime());


        String pics = item.getPics();


    }
}
