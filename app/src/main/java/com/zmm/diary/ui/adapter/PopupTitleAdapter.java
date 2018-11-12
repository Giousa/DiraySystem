package com.zmm.diary.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zmm.diary.R;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/11
 * Email:65489469@qq.com
 */
public class PopupTitleAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public PopupTitleAdapter() {
        super(R.layout.item_diary_popup);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        helper.setText(R.id.tv_item_popup_title, item);
    }
}
