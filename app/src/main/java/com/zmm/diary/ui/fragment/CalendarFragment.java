package com.zmm.diary.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.necer.ncalendar.listener.OnCalendarChangedListener;
import com.yanzhenjie.sofia.Sofia;
import com.zmm.diary.MyApplication;
import com.zmm.diary.R;
import com.zmm.diary.bean.NoteBean;
import com.zmm.diary.dagger.component.DaggerNoteComponent;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.dagger.module.NoteModule;
import com.zmm.diary.mvp.presenter.NotePresenter;
import com.zmm.diary.mvp.presenter.contract.NoteContract;
import com.zmm.diary.ui.activity.DiaryInfoActivity;
import com.zmm.diary.ui.adapter.HomeAdapter;
import com.zmm.diary.ui.dialog.SimpleConfirmDialog;
import com.zmm.diary.ui.widget.MyNCalendar;
import com.zmm.diary.utils.UIUtils;

import org.joda.time.DateTime;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/8
 * Email:65489469@qq.com
 */
public class CalendarFragment extends BaseFragment<NotePresenter> implements OnCalendarChangedListener,NoteContract.NoteView, HomeAdapter.OnRightMenuClickListener {

    @BindView(R.id.tv_month)
    TextView mTvMonth;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.note_rv_list)
    RecyclerView mRvList;
    @BindView(R.id.my_n_calendar)
    MyNCalendar mMyNCalendar;


    @Inject
    HomeAdapter mHomeAdapter;

    @Override
    protected int setLayout() {
        System.out.println("CalendarFragment setLayout");
        return R.layout.fragment_calendar;
    }

    @Override
    protected void setupActivityComponent(HttpComponent httpComponent) {
        DaggerNoteComponent.builder()
                .httpComponent(httpComponent)
                .noteModule(new NoteModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void init() {

        System.out.println("CalendarFragment  初始化");

        Sofia.with(getActivity()).statusBarBackground(UIUtils.getResources().getColor(R.color.calendar_bg));

        mMyNCalendar.setOnCalendarChangedListener(this);

        initRecyclerView();
    }


    private void initRecyclerView() {

        mRvList.setHasFixedSize(true);
        mRvList.setLayoutManager(new LinearLayoutManager(mContext));

        //添加分割线
        mRvList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

        mRvList.setAdapter(mHomeAdapter);

        //适配器，设置空布局
        mHomeAdapter.setEmptyView(R.layout.empty_content,mRvList);

        mHomeAdapter.setOnRightMenuClickListener(this);


    }

    @Override
    protected void onHide() {
        super.onHide();
        Sofia.with(getActivity()).statusBarBackground(UIUtils.getResources().getColor(R.color.colorPrimary));

    }


    @Override
    protected void onRefresh() {
        super.onRefresh();
        System.out.println("CalendarFragment  onRefresh");

        mMyNCalendar.toToday();
        Sofia.with(getActivity()).statusBarBackground(UIUtils.getResources().getColor(R.color.calendar_bg));

    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("CalendarFragment  onResume");

//        Sofia.with(getActivity()).statusBarBackground(UIUtils.getResources().getColor(R.color.calendar_bg));

    }

    @Override
    public void onCalendarChanged(DateTime dateTime) {
        System.out.println("CalendarFragment dateTime = "+dateTime);
        mTvMonth.setText(dateTime.getMonthOfYear() + "月");
        mTvDate.setText(dateTime.getYear() + "年" + dateTime.getMonthOfYear() + "月" + dateTime.getDayOfMonth() + "日");

        mPresenter.findNotesByCreateTime(MyApplication.userBean.getId(),dateTime.getYear()+"-"+dateTime.getMonthOfYear()+"-"+dateTime.getDayOfMonth());

    }


    @Override
    public void addSuccess() {

    }

    @Override
    public void updateSuccess() {

    }

    @Override
    public void deleteSuccess() {

    }

    @Override
    public void findNoteSuccess(NoteBean noteBean) {

    }

    @Override
    public void findNotesListSuccess(List<NoteBean> noteBeanList) {
        mHomeAdapter.setNewData(noteBeanList);

    }

    @Override
    public void onRightMenuDelete(final String id) {

        final SimpleConfirmDialog simpleConfirmDialog = new SimpleConfirmDialog(mContext,null);
        simpleConfirmDialog.setOnClickListener(new SimpleConfirmDialog.OnClickListener() {
            @Override
            public void onCancel() {
                simpleConfirmDialog.dismiss();
            }

            @Override
            public void onConfirm() {
                simpleConfirmDialog.dismiss();
                mPresenter.deleteNote(id);

            }
        });

        simpleConfirmDialog.show();

    }

    @Override
    public void onRightMenuUpdate(String id) {
        Intent intent = new Intent(mContext,DiaryInfoActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }
}
