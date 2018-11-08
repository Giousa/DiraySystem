package com.zmm.diary.ui.activity;

import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;
import com.zmm.diary.R;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.ui.fragment.BaseFragment;
import com.zmm.diary.ui.fragment.FragmentFactory;
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

        changeFragment(FragmentFactory.createFragment(0));


        mBbl.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(BottomBarItem bottomBarItem, int prePosition, int currentPosition) {
                ToastUtils.SimpleToast("currentPosition = "+currentPosition);

                changeFragment(FragmentFactory.createFragment(currentPosition));

            }
        });
    }


    private void changeFragment(BaseFragment targetFragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
