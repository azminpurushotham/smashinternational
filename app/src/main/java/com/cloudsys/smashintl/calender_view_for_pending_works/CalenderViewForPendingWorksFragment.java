package com.cloudsys.smashintl.calender_view_for_pending_works;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.AppBaseActionView;
import com.cloudsys.smashintl.base.AppBaseFragment;
import com.cloudsys.smashintl.sheduledwork_datevice.ScheduledWorkDateViceFragment;
import com.stacktips.view.CalendarListener;
import com.stacktips.view.CustomCalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by User on 11/30/2017.
 */

public class CalenderViewForPendingWorksFragment extends AppBaseFragment {
    @BindView(R.id.calendarView)
    CustomCalendarView calendarView;
    @BindView(R.id.parent)
    LinearLayout parent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View mView = inflater.inflate(R.layout.fragment_simple_calendar, container, false);
        ButterKnife.bind(this, mView);
        initParentView(parent);

        //Initialize calendar with date
        Calendar currentCalendar = Calendar.getInstance(Locale.getDefault());

        //Show monday as first date of week
        calendarView.setFirstDayOfWeek(Calendar.MONDAY);

        //Show/hide overflow days of a month
        calendarView.setShowOverflowDate(false);

        //call refreshCalendar to update calendar the view
        calendarView.refreshCalendar(currentCalendar);

        //Handling custom calendar events
        calendarView.setCalendarListener(new CalendarListener() {
            @Override
            public void onDateSelected(Date date) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String date_ = df.format(date);
                AppBaseFragment mFragment = new ScheduledWorkDateViceFragment();
                Bundle mBundle = new Bundle();
                mBundle.putString("date", date_);
                mFragment.setArguments(mBundle);
                getFragmentSwitch().onFragmentSwitch(
                        mFragment,
                        true,
                        getString(R.string.date_vice_pending_work),
                        true,
                        getString(R.string.date_vice_pending_work));
            }

            @Override
            public void onMonthChanged(Date date) {
                SimpleDateFormat df = new SimpleDateFormat("MM-yyyy");
                Toast.makeText(getActivity(), df.format(date), Toast.LENGTH_SHORT).show();
            }
        });
        return mView;
    }
}
