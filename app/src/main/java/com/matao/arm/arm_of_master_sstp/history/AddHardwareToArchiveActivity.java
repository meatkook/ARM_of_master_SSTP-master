package com.matao.arm.arm_of_master_sstp.history;

import android.app.DatePickerDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.matao.arm.arm_of_master_sstp.DB.HelperFactory;
import com.matao.arm.arm_of_master_sstp.R;
import com.matao.arm.arm_of_master_sstp.SpinnerItemSelectedAdapter;
import com.matao.arm.arm_of_master_sstp.Utils;
import com.matao.arm.arm_of_master_sstp.databinding.ActivityAddHardwareToArchiveBinding;
import com.matao.arm.arm_of_master_sstp.models.Hardware;
import com.matao.arm.arm_of_master_sstp.models.HardwareHistory;
import com.matao.arm.arm_of_master_sstp.models.LPS;
import com.matao.arm.arm_of_master_sstp.models.Staff;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddHardwareToArchiveActivity extends AppCompatActivity {

    public static String ARG_HISTORY_ID = "arg_history_id";
    public static String ARG_HARDWARE_ID = "arg_hardware_id";

    private ActivityAddHardwareToArchiveBinding mBinding;

    private HardwareHistory mItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_hardware_to_archive);
        ButterKnife.bind(this);
        if(getIntent().hasExtra(ARG_HISTORY_ID)) {
            Integer id = getIntent().getIntExtra(ARG_HISTORY_ID, 0);
            mItem = HelperFactory.getHelper().getHistoryDao().getItem(id);
        } else if(getIntent().hasExtra(ARG_HARDWARE_ID)) {
            Integer id = getIntent().getIntExtra(ARG_HARDWARE_ID, 0);
            mItem = new HardwareHistory();
            Hardware hardware = HelperFactory.getHelper().getHardwareDao().getItem(id);
            mItem.setHardware(hardware);
        }

        if (mItem == null) {
            mItem = new HardwareHistory();
        }
        mBinding.setItem(mItem);

        final List<Staff> allStaff = HelperFactory.getHelper().getStaffDao().getAll();
        mBinding.hardwareResponsible.setAdapter(new ArrayAdapter<>(this, R.layout.item_spinner_lps, allStaff));

        final List<LPS> allLps = HelperFactory.getHelper().getLpsDao().getAllLps();
        mBinding.itemLps.setAdapter(new ArrayAdapter<>(this, R.layout.item_spinner_lps, allLps));

        int lpsId = mItem.getHardware() != null ? mItem.getHardware().getLpsID() : allLps.get(0).getId();
        final List<Hardware> allHardware = HelperFactory.getHelper().getHardwareDao().getHardwareLps(lpsId);
        mBinding.itemHardware.setAdapter(new ArrayAdapter<>(this, R.layout.item_spinner_lps, allHardware));

        if (mItem.getId() != null) {
            mBinding.itemLps.setEnabled(false);
            mBinding.itemHardware.setEnabled(false);
            if (!TextUtils.isEmpty(mItem.getDateMaintest())) {
                mBinding.hardwareFactDate.setText(mItem.getDateMaintest());
            }

            for (int i = 0; i < allStaff.size(); i++) {
                if (allStaff.get(i).getId().equals(mItem.getStaff().getId())) {
                    mBinding.hardwareResponsible.setSelection(i);
                    break;
                }
            }
        } else {
            mItem.setStaff(allStaff.get(0));
        }

        if(mItem.getHardware() != null) {
            for (int i = 0; i < allLps.size(); i++) {
                if (allLps.get(i).getId() == mItem.getHardware().getLpsID()) {
                    mBinding.itemLps.setSelection(i);
                    break;
                }
            }

            for (int i = 0; i < allHardware.size(); i++) {
                if (allHardware.get(i).getId().equals(mItem.getHardware().getId())) {
                    mBinding.itemHardware.setSelection(i);
                    break;
                }
            }
        } else {
            mItem.setHardware(allHardware.get(0));
        }

        mBinding.hardwareResponsible.setOnItemSelectedListener(new SpinnerItemSelectedAdapter() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mItem.setStaff(allStaff.get(position));
            }
        });

        mBinding.itemLps.setOnItemSelectedListener(new SpinnerItemSelectedAdapter() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(mItem.getHardware() == null || mItem.getHardware().getLpsID() != allLps.get(position).getId()) {
                    final List<Hardware> allHardware = HelperFactory.getHelper().getHardwareDao().getHardwareLps(allLps.get(position).getId());
                    mBinding.itemHardware.setAdapter(new ArrayAdapter<>(AddHardwareToArchiveActivity.this, R.layout.item_spinner_lps, allHardware));
                    mBinding.itemHardware.setOnItemSelectedListener(new SpinnerItemSelectedAdapter() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            mItem.setHardware(allHardware.get(position));
                            mBinding.hardwarePlanDate.setText(allHardware.get(position).getDateMaintest());
                        }
                    });
                }

            }
        });

        mBinding.itemHardware.setOnItemSelectedListener(new SpinnerItemSelectedAdapter() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mItem.setHardware(allHardware.get(position));
            }
        });

    }

    @OnClick(R.id.hardware_fact_date)
    protected void onRepairDateClick() {
        Calendar calendar = GregorianCalendar.getInstance();
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = Utils.dateFormat.format(Utils.buildCalendar(year, month, dayOfMonth).getTime());
                mItem.setDateMaintest(date);
                mBinding.hardwareFactDate.setText(date);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick(R.id.acceptButtonHardware)
    protected void onSaveClick() {
        try {
            if (mItem.getId() != null) {
                HelperFactory.getHelper().getHistoryDao().update(mItem);
            } else {
                HelperFactory.getHelper().getHistoryDao().create(mItem);
            }

            if(getIntent().hasExtra(ARG_HARDWARE_ID)) {
                Hardware hardware = mItem.getHardware();
                hardware.setDateMaintest("");
                HelperFactory.getHelper().getHardwareDao().update(hardware);
            }
            finish();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(this, "Не удалось сохранить данные!", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.cancelButtonHardware)
    protected void onCancelClick() {
        finish();
    }
}
