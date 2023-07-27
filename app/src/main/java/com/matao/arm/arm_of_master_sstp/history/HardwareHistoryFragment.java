package com.matao.arm.arm_of_master_sstp.history;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.matao.arm.arm_of_master_sstp.DB.HelperFactory;
import com.matao.arm.arm_of_master_sstp.R;
import com.matao.arm.arm_of_master_sstp.SpinnerItemSelectedAdapter;
import com.matao.arm.arm_of_master_sstp.Utils;
import com.matao.arm.arm_of_master_sstp.databinding.FragmentHardwareHistoryBinding;
import com.matao.arm.arm_of_master_sstp.fragments.AbstractTabFragment;
import com.matao.arm.arm_of_master_sstp.models.HardwareHistory;
import com.matao.arm.arm_of_master_sstp.models.LPS;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class HardwareHistoryFragment extends AbstractTabFragment {


    private LayoutInflater mInflater;

    private FragmentHardwareHistoryBinding mBinding;

    private Date mStartDate = getDefaultStartDate().getTime();
    private Date mEndDate = getDefaultEndDate().getTime();
    private Integer mLpsId = HelperFactory.getHelper().getLpsDao().getAllLps().get(0).getId();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mInflater = inflater;
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_hardware_history, container, false);

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

        mBinding.notPlanWorkLps.setText(Utils.dateFormat.format(mStartDate));
        mBinding.repairYears.setText(Utils.dateFormat.format(mEndDate));

        final List<LPS> allLps = HelperFactory.getHelper().getLpsDao().getAllLps();
        mBinding.repairLpsList.setAdapter(new ArrayAdapter<>(getContext(), R.layout.item_spinner_lps, allLps));
        mBinding.repairLpsList.setOnItemSelectedListener(new SpinnerItemSelectedAdapter() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateList(mStartDate, mEndDate, allLps.get(position).getId());
            }
        });

        mBinding.extingfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(), AddHardwareToArchiveActivity.class));
            }
        });

        mBinding.exportfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<HardwareHistory> history = HelperFactory.getHelper()
                        .getHistoryDao()
                        .getHistory(mStartDate, mEndDate, mLpsId);
                File file = getContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                if(!file.exists()) {
                    file.mkdirs();
                }
                File export = new File(file, "export.csv");
                if(export.exists()) {
                    export.delete();
                }
                try {
                    export.createNewFile();
                    OutputStream stream = new FileOutputStream(export);
                    String[] items = new String[]{"Подстанция", "Оборудование", "Дата ремонта", "Производитель\n"};
                    stream.write(TextUtils.join(";", items).getBytes(Charset.forName("windows-1251")));
                    for (HardwareHistory item: history) {
                        String[] row = {
                            item.getHardware().getLps().getName(),
                            item.getHardware().getName(),
                            item.getDateMaintest(),
                            item.getStaff().toString().concat("\r\n"),
                        };
                        stream.write(TextUtils.join(";", row).getBytes(Charset.forName("windows-1251")));
                    }
                    stream.flush();
                    stream.close();
                    Toast.makeText(getContext(), export.getAbsolutePath(), Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Toast.makeText(getContext(), "Ошибка экспорта!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

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
                Date newDate = Utils.buildCalendar(year, month, dayOfMonth).getTime();
                updateList(newDate, mEndDate, mLpsId);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void selectEndDate() {
        Calendar calendar = getDefaultEndDate();
        new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Date newDate = Utils.buildCalendar(year, month, dayOfMonth).getTime();
                updateList(mStartDate, newDate, mLpsId);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private static Calendar getDefaultStartDate() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.add(Calendar.MONTH, -2);
        return calendar;
    }

    private static Calendar getDefaultEndDate() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.add(Calendar.MONTH, 2);
        return calendar;
    }

    private void updateList(Date start, Date end, Integer lpsId) {

        mStartDate = start;
        mEndDate = end;
        mLpsId = lpsId;

        mBinding.notPlanWorkLps.setText(Utils.dateFormat.format(mStartDate));
        mBinding.repairYears.setText(Utils.dateFormat.format(mEndDate));

        List<HardwareHistory> items = HelperFactory.getHelper()
                .getHistoryDao()
                .getHistory(mStartDate, mEndDate, mLpsId);

        RecyclerView.Adapter adapter = new HardwareHistoryAdapter(items, mInflater);
        if (mBinding.recycler.getAdapter() == null) {
            mBinding.recycler.setAdapter(adapter);
        } else {
            mBinding.recycler.swapAdapter(adapter, true);
        }
    }
}
