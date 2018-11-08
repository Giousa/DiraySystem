package com.zmm.diary.ui.activity;

import android.widget.FrameLayout;

import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;
import com.zmm.diary.R;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.utils.ToastUtils;

import butterknife.BindView;

public class MainActivity extends BaseActivity {


    @BindView(R.id.frame_layout)
    FrameLayout mFrameLayout;
    @BindView(R.id.bbl)
    BottomBarLayout mBbl;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void setupActivityComponent(HttpComponent httpComponent) {

    }

    @Override
    protected void init() {

        mBbl.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(BottomBarItem bottomBarItem, int prePosition, int currentPosition) {
                ToastUtils.SimpleToast("currentPosition = "+currentPosition);
            }
        });
    }

}
