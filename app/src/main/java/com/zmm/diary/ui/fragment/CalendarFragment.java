package com.zmm.diary.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.necer.ncalendar.listener.OnCalendarChangedListener;
import com.zmm.diary.MyApplication;
import com.zmm.diary.R;
import com.zmm.diary.bean.NoteBean;
import com.zmm.diary.dagger.component.DaggerNoteComponent;
import com.zmm.diary.dagger.component.HttpComponent;
import com.zmm.diary.dagger.module.NoteModule;
import com.zmm.diary.mvp.presenter.NotePresenter;
import com.zmm.diary.mvp.presenter.contract.NoteContract;
import com.zmm.diary.ui.widget.MyNCalendar;

import org.joda.time.DateTime;

import java.util.List;

import butterknife.BindView;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/8
 * Email:65489469@qq.com
 */
public class CalendarFragment extends BaseFragment<NotePresenter> implements OnCalendarChangedListener,NoteContract.NoteView{

    @BindView(R.id.tv_month)
    TextView mTvMonth;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.note_rv_list)
    RecyclerView mRvList;
    @BindView(R.id.my_n_calendar)
    MyNCalendar mMyNCalendar;

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
        mMyNCalendar.setOnCalendarChangedListener(this);

    }

    @Override
    protected void refresh() {
        mMyNCalendar.toToday();
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("CalendarFragment  onResume");

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
    public void findTodayNotesSuccess(List<NoteBean> noteBeanList) {

    }
}
