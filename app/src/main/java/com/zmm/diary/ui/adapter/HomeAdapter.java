package com.zmm.diary.ui.adapter;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guanaj.easyswipemenulibrary.EasySwipeMenuLayout;
import com.zmm.diary.R;
import com.zmm.diary.bean.NoteBean;
import com.zmm.diary.utils.ToastUtils;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/9
 * Email:65489469@qq.com
 */
public class HomeAdapter extends BaseQuickAdapter<NoteBean,BaseViewHolder>{

    private Context mContext;

    public HomeAdapter(Context context){
        super(R.layout.item_home);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, NoteBean item) {
        helper.setText(R.id.tv_home_title,item.getTitle());
        helper.setText(R.id.tv_home_content,item.getContent());
        final EasySwipeMenuLayout easySwipeMenuLayout = helper.getView(R.id.es);


//        helper.getView(R.id.content).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ToastUtils.SimpleToast("content click");
//            }
//        });

        helper.getView(R.id.tv_right_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.SimpleToast("删除");

                easySwipeMenuLayout.resetStatus();
            }
        });

        helper.getView(R.id.tv_right_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.SimpleToast("更新");

                easySwipeMenuLayout.resetStatus();
            }
        });
    }
}
