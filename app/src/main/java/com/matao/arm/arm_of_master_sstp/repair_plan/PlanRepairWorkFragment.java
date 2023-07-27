package com.matao.arm.arm_of_master_sstp.repair_plan;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.matao.arm.arm_of_master_sstp.DB.HelperFactory;
import com.matao.arm.arm_of_master_sstp.R;
import com.matao.arm.arm_of_master_sstp.databinding.FragmentPlanRepairWorkBinding;
import com.matao.arm.arm_of_master_sstp.fragments.AbstractTabFragment;
import com.matao.arm.arm_of_master_sstp.history.AddHardwareToArchiveActivity;
import com.matao.arm.arm_of_master_sstp.models.Hardware;
import com.matao.arm.arm_of_master_sstp.models.LPS;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class PlanRepairWorkFragment extends AbstractTabFragment {


    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private LayoutInflater mInflater;

    private FragmentPlanRepairWorkBinding mBinding;

    private Date mStartDate = getDefaultStartDate().getTime();
    private Date mEndDate = getDefaultEndDate().getTime();
    private Integer mLpsId = HelperFactory.getHelper().getLpsDao().getAllLps().get(0).getId();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mInflater = inflater;
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_plan_repair_work, container, false);

        mBinding.notPlanWorkLps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectStartDate();
            }
        });

        mBinding.repairYears.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectEndDate();
            }
        });

        mBinding.notPlanWorkLps.setText(dateFormat.format(mStartDate));
        mBinding.repairYears.setText(dateFormat.format(mEndDate));

        final List<LPS> allLps = HelperFactory.getHelper().getLpsDao().getAllLps();
        mBinding.repairLpsList.setAdapter(new ArrayAdapter<>(getContext(), R.layout.item_spinner_lps, allLps));
        mBinding.repairLpsList.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        updateList(mStartDate, mEndDate, allLps.get(position).getId());
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                }
        );
        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateList(mStartDate, mEndDate, mLpsId);
    }

    public void selectStartDate() {
        Calendar calendar = getDefaultStartDate();
        new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Date newDate = buildCalendar(year, month, dayOfMonth).getTime();
                updateList(newDate, mEndDate, mLpsId);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void selectEndDate() {
        Calendar calendar = getDefaultEndDate();
        new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Date newDate = buildCalendar(year, month, dayOfMonth).getTime();
                updateList(mStartDate, newDate, mLpsId);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private Calendar getDefaultStartDate() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.add(Calendar.MONTH, -2);
        return calendar;
    }

    private Calendar getDefaultEndDate() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.add(Calendar.MONTH, 2);
        return calendar;
    }

    private Calendar buildCalendar(int year, int month, int dayOfMonth) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return calendar;
    }

    private void updateList(Date start, Date end, Integer lpsId) {

        mStartDate = start;
        mEndDate = end;
        mLpsId = lpsId;

        mBinding.notPlanWorkLps.setText(dateFormat.format(mStartDate));
        mBinding.repairYears.setText(dateFormat.format(mEndDate));

        List<Hardware> items = HelperFactory.getHelper()
                .getHardwareDao()
                .getHardwarePlan(start, end, lpsId);

        RecyclerView.Adapter adapter = new HardwareAdapter(items, mInflater, new HardwareAdapter.OnHardwareClick() {
            @Override
            public void onHardwareClick(View v, Hardware hardware) {
                Intent intent = new Intent(v.getContext(), AddHardwareToArchiveActivity.class);
                intent.putExtra(AddHardwareToArchiveActivity.ARG_HARDWARE_ID, hardware.getId());
                v.getContext().startActivity(intent);
            }
        });
        if (mBinding.recycler.getAdapter() == null) {
            mBinding.recycler.setAdapter(adapter);
        } else {
            mBinding.recycler.swapAdapter(adapter, true);
        }
    }
}
