package com.zmm.diary.ui.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zmm.diary.R;
import com.zmm.diary.bean.RecordBean;
import com.zmm.diary.ui.adapter.record.SixAdapter;
import com.zmm.diary.utils.UIUtils;

import java.util.ArrayList;
import java.util.Arrays;

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


        RecyclerView recyclerView = helper.getView(R.id.rv_item_list);
        SixAdapter sixAdapter = new SixAdapter();

        recyclerView.setLayoutManager(new GridLayoutManager(UIUtils.getContext(), 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(sixAdapter);


        String pics = item.getPics();
        if(!TextUtils.isEmpty(pics)){

            String[] splitPics = pics.split(",");

            ArrayList<String> piclist = new ArrayList<>();
            for (String pic:splitPics) {
                piclist.add(pic);
            }
            System.out.println("图片："+ Arrays.toString(splitPics));

            sixAdapter.setNewData(piclist);
        }else {
            sixAdapter.setNewData(null);
        }



    }
}
