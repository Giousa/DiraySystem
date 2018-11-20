package com.zmm.diary.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zmm.diary.R;
import com.zmm.diary.bean.NoteBean;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/9
 * Email:65489469@qq.com
 */
public class NoteAdapter extends BaseQuickAdapter<NoteBean,BaseViewHolder>{


    public NoteAdapter(){
        super(R.layout.item_note);
    }

    @Override
    protected void convert(BaseViewHolder helper, final NoteBean item) {
        helper.setText(R.id.tv_note_title,item.getTitle());
        helper.setText(R.id.tv_note_content,item.getContent());


//        helper.getView(R.id.content).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ToastUtils.SimpleToast("content click");
//            }
//        });

    }

}
