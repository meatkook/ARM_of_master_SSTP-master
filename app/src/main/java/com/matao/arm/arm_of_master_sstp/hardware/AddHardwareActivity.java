package com.matao.arm.arm_of_master_sstp.hardware;

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
import com.matao.arm.arm_of_master_sstp.databinding.ActivityAddHardwareBinding;
import com.matao.arm.arm_of_master_sstp.models.Hardware;
import com.matao.arm.arm_of_master_sstp.models.HardwareHistory;
import com.matao.arm.arm_of_master_sstp.models.LPS;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddHardwareActivity extends AppCompatActivity {

    public static String ARG_HARDWARE_ID = "arg_hardware_id";

    private ActivityAddHardwareBinding mBinding;

    private Hardware mItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_hardware);
        ButterKnife.bind(this);

        if(getIntent().hasExtra(ARG_HARDWARE_ID)) {
            Integer id = getIntent().getIntExtra(ARG_HARDWARE_ID, 0);
            mItem = HelperFactory.getHelper().getHardwareDao().getItem(id);
        }

        if (mItem == null) {
            mItem = new Hardware();
        }

        mBinding.setItem(mItem);
        if (!TextUtils.isEmpty(mItem.getDateMaintest())) {
            mBinding.hardwareAddMaintenance.setText(mItem.getDateMaintest());
        }

        final List<LPS> allLps = HelperFactory.getHelper().getLpsDao().getAllLps();
        mBinding.hardwareAddLpsName.setAdapter(new ArrayAdapter<>(this, R.layout.item_spinner_lps, allLps));
        mBinding.hardwareAddLpsName.setOnItemSelectedListener(new SpinnerItemSelectedAdapter() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mItem.setLps(allLps.get(position));
                mItem.setLpsID(allLps.get(position).getId());
            }
        });

        for (int i = 0; i < allLps.size(); i++) {
            if (allLps.get(i).getId() == mItem.getLpsID()) {
                mBinding.hardwareAddLpsName.setSelection(i);
                break;
            }
        }
    }

    @OnClick(R.id.hardware_add_maintenance)
    protected void onRepairDateClick() {
        Calendar calendar = GregorianCalendar.getInstance();
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = Utils.dateFormat.format(Utils.buildCalendar(year, month, dayOfMonth).getTime());
                mItem.setDateMaintest(date);
                mBinding.hardwareAddMaintenance.setText(date);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick(R.id.saveButtonHW)
    protected void onSaveClick() {
        try {
            mItem.setName(mBinding.hardwareAddAddName.getText().toString());
            if (mItem.getId() != null) {
                HelperFactory.getHelper().getHardwareDao().update(mItem);
            } else {
                HelperFactory.getHelper().getHardwareDao().create(mItem);
            }
            finish();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(this, "Не удалось сохранить данные!", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.deleteButtonHW)
    protected void onCancelClick() {
        if (mItem.getId() != null) {
            try {
                HelperFactory.getHelper().getHardwareDao().delete(mItem);
                Toast.makeText(this, "Запись удалена!", Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                Toast.makeText(this, "Не удалось удалить запись!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        finish();
    }
}
