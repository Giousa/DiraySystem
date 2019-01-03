package com.zmm.diary.ui.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zmm.diary.R;
import com.zmm.diary.bean.RecordBean;
import com.zmm.diary.utils.ToastUtils;
import com.zmm.diary.utils.UIUtils;

import java.util.ArrayList;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/12/4
 * Email:65489469@qq.com
 */
public class RecordAdapter extends BaseQuickAdapter<RecordBean,BaseViewHolder>{


    private OnRecordItemClickListener mOnRecordItemClickListener;

    public void setOnRecordItemClickListener(OnRecordItemClickListener onRecordItemClickListener) {
        mOnRecordItemClickListener = onRecordItemClickListener;
    }

    public RecordAdapter() {
        super(R.layout.item_record);
    }

    @Override
    protected void convert(BaseViewHolder helper, final RecordBean item) {

        helper.setText(R.id.tv_item_content,item.getContent());
        helper.setText(R.id.tv_item_time,item.getCreateTime());


        RecyclerView recyclerView = helper.getView(R.id.rv_item_list);

        ArrayList<String> piclist = new ArrayList<>();

        String pics = item.getPics();

        if(!TextUtils.isEmpty(pics)){

            recyclerView.setVisibility(View.VISIBLE);

            final String[] splitPics = pics.split(",");

            for (String pic:splitPics) {
                piclist.add(pic);
            }

            RecordPicsAdapter recordPicsAdapter = null;

            switch (piclist.size()){
                case 1:

                    recordPicsAdapter = new RecordPicsAdapter(R.layout.item_record_pics_one,piclist);
                    recyclerView.setLayoutManager(new GridLayoutManager(UIUtils.getContext(), 1));
                    break;

                case 2:
                    recordPicsAdapter = new RecordPicsAdapter(R.layout.item_record_pics_two,piclist);
                    recyclerView.setLayoutManager(new GridLayoutManager(UIUtils.getContext(), 2));
                    break;

                case 3:
                    recordPicsAdapter = new RecordPicsAdapter(R.layout.item_record_pics,piclist);
                    recyclerView.setLayoutManager(new GridLayoutManager(UIUtils.getContext(), 3));
                    break;

                case 4:
                    recordPicsAdapter = new RecordPicsAdapter(R.layout.item_record_pics_two,piclist);
                    recyclerView.setLayoutManager(new GridLayoutManager(UIUtils.getContext(), 2));
                    break;

                case 5:
                    recordPicsAdapter = new RecordPicsAdapter(R.layout.item_record_pics,piclist);
                    recyclerView.setLayoutManager(new GridLayoutManager(UIUtils.getContext(), 3));
                    break;

                case 6:
                    recordPicsAdapter = new RecordPicsAdapter(R.layout.item_record_pics,piclist);
                    recyclerView.setLayoutManager(new GridLayoutManager(UIUtils.getContext(), 3));
                    break;
            }


            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(recordPicsAdapter);

            recordPicsAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                    String pic = (String) adapter.getData().get(position);
//
//                    ToastUtils.SimpleToast(pic);
//                    System.out.println("选择：item = "+item.getContent());

                    if(mOnRecordItemClickListener != null){
                        mOnRecordItemClickListener.OnRecordPicClick(position,splitPics);
                    }

                }
            });

            //删除
            helper.getView(R.id.iv_item_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnRecordItemClickListener != null){
                        mOnRecordItemClickListener.OnRecordDelete(item.getId());
                    }
                }
            });

        }else {
            recyclerView.setVisibility(View.GONE);
        }

    }


    public interface OnRecordItemClickListener{

        void OnRecordPicClick(int position,String[] pics);

        void OnRecordDelete(String id);
    }
}
