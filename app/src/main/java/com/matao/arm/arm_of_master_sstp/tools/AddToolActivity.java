package com.matao.arm.arm_of_master_sstp.tools;

import android.app.DatePickerDialog;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.matao.arm.arm_of_master_sstp.models.LPS;
import com.matao.arm.arm_of_master_sstp.models.Tool;
import com.matao.arm.arm_of_master_sstp.databinding.ActivityAddToolBinding;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddToolActivity extends AppCompatActivity {

    public static String ARG_TOOL_ID = "arg_tool_id";
    private ActivityAddToolBinding mBinding;
    private Tool mItem;

    public AddToolActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_tool);
        ButterKnife.bind(this);

        if(getIntent().hasExtra(ARG_TOOL_ID)) {
            Integer id = getIntent().getIntExtra(ARG_TOOL_ID, 0);
            mItem = HelperFactory.getHelper().getToolsDao().getItem(id);
        }

        if (mItem == null) {
            mItem = new Tool();
        }

        mBinding.setItem(mItem);
        if (!TextUtils.isEmpty(mItem.getNextVerification())) {
            mBinding.toolAddVerification.setText(mItem.getNextVerification());
        }
        final List<LPS> allLps = HelperFactory.getHelper().getLpsDao().getAllLps();
        mBinding.toolAddLpsName.setAdapter(new ArrayAdapter<>(this, R.layout.item_spinner_lps, allLps));

        mBinding.toolAddLpsName.setOnItemSelectedListener(new SpinnerItemSelectedAdapter() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mItem.setLps(allLps.get(position));
                mItem.setLpsID(allLps.get(position).getId());
            }
        });
        for (int i = 0; i < allLps.size(); i++) {
            if (allLps.get(i).getId() == mItem.getLpsID()) {
                mBinding.toolAddLpsName.setSelection(i);
                break;
            }
        }

    }

    @OnClick(R.id.tool_add_verification)
    protected void onRepairDateClick() {
        Calendar calendar = GregorianCalendar.getInstance();
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = Utils.dateFormat.format(Utils.buildCalendar(year, month, dayOfMonth).getTime());
                mItem.setNextVerification(date);
                mBinding.toolAddVerification.setText(date);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick(R.id.saveToolButton)
    protected void onSaveClick() {
        try {
            mItem.setName(mBinding.toolAddAddName.getText().toString());
            mItem.setNumber(mBinding.toolAddStockNumber.getText().toString());
            if (mItem.getId() != null) {
                HelperFactory.getHelper().getToolsDao().update(mItem);
            } else {
                HelperFactory.getHelper().getToolsDao().create(mItem);
            }
            finish();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(this, "Не удалось сохранить данные!", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.deleteToolButton)
    protected void onCancelClick() {
        if (mItem.getId() != null) {
            try {
                HelperFactory.getHelper().getToolsDao().delete(mItem);
                Toast.makeText(this, "Запись удалена!", Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                Toast.makeText(this, "Не удалось удалить запись!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        finish();
    }

}
