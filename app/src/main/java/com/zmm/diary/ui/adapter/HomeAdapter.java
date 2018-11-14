package com.zmm.diary.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guanaj.easyswipemenulibrary.EasySwipeMenuLayout;
import com.zmm.diary.R;
import com.zmm.diary.bean.NoteBean;
import com.zmm.diary.utils.ToastUtils;
import com.zmm.diary.utils.UIUtils;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/9
 * Email:65489469@qq.com
 */
public class HomeAdapter extends BaseQuickAdapter<NoteBean,BaseViewHolder>{


    private OnRightMenuClickListener mOnRightMenuClickListener;

    public void setOnRightMenuClickListener(OnRightMenuClickListener onRightMenuClickListener) {
        mOnRightMenuClickListener = onRightMenuClickListener;
    }

    public HomeAdapter(){
        super(R.layout.item_home);
    }

    @Override
    protected void convert(BaseViewHolder helper, final NoteBean item) {
        helper.setText(R.id.tv_home_title,item.getTitle());
        helper.setText(R.id.tv_home_content,item.getContent());

        ImageView icon = helper.getView(R.id.iv_home_icon);
        String type = item.getType();
        if(type.equals("公")){
            icon.setImageDrawable(UIUtils.getResources().getDrawable(R.drawable.work_icon));
        }else {
            icon.setImageDrawable(UIUtils.getResources().getDrawable(R.drawable.personal_icon));
        }

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
                easySwipeMenuLayout.resetStatus();
                if(mOnRightMenuClickListener != null){
                    mOnRightMenuClickListener.onRightMenuDelete(item.getId());
                }
            }
        });

        helper.getView(R.id.tv_right_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                easySwipeMenuLayout.resetStatus();
                if(mOnRightMenuClickListener != null){
                    mOnRightMenuClickListener.onRightMenuUpdate(item.getId());
                }
            }
        });
    }

    public interface OnRightMenuClickListener{

        void onRightMenuDelete(String id);

        void onRightMenuUpdate(String id);
    }
}
