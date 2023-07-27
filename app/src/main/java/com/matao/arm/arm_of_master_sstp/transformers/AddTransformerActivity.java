package com.matao.arm.arm_of_master_sstp.transformers;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import com.matao.arm.arm_of_master_sstp.databinding.ActivityAddTransformerBinding;
import com.matao.arm.arm_of_master_sstp.models.HardwareHistory;
import com.matao.arm.arm_of_master_sstp.models.LPS;
import com.matao.arm.arm_of_master_sstp.models.Tool;
import com.matao.arm.arm_of_master_sstp.models.Transformer;
import com.matao.arm.arm_of_master_sstp.models.TransformerTypes;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddTransformerActivity extends AppCompatActivity {

    public static String ARG_TRANSFORMER_ID = "arg_transformer_id";
    private ActivityAddTransformerBinding mBinding;
    private Transformer mItem;

    public AddTransformerActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_transformer);
        ButterKnife.bind(this);

        if(getIntent().hasExtra(ARG_TRANSFORMER_ID)) {
            Integer id = getIntent().getIntExtra(ARG_TRANSFORMER_ID, 0);
            mItem = HelperFactory.getHelper().getTransformerDao().getItem(id);
        }

        if (mItem == null) {
            mItem = new Transformer();
        }

        mBinding.setItem(mItem);
        if (!TextUtils.isEmpty(mItem.getNextVerification())) {
            mBinding.transformerAddVerification.setText(mItem.getNextVerification());
            mBinding.transformerAddAddName.setText(mItem.getName());
            mBinding.transformerAddStockNumber.setText(mItem.getNumber());
            mBinding.transformerRatioAdd.setText(mItem.getRatio());
            mBinding.transformerAddPlacement.setText(mItem.getPlacement());
        }

        final List<TransformerTypes> allTypes = HelperFactory.getHelper().getTransformerTypeDao().getAllTransformerTypes();
        mBinding.transformerAddType.setAdapter(new ArrayAdapter<>(this, R.layout.item_spinner_lps, allTypes));
        mBinding.transformerAddType.setOnItemSelectedListener(new SpinnerItemSelectedAdapter() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mItem.setType(allTypes.get(position));
                mItem.setTypeID(allTypes.get(position).getId());
            }
        });
        for (int i = 0; i < allTypes.size(); i++) {
            if (allTypes.get(i).getId() == mItem.getLpsID()) {
                mBinding.transformerAddType.setSelection(i);
                break;
            }
        }

        final List<LPS> allLps = HelperFactory.getHelper().getLpsDao().getAllLps();
        mBinding.transformerAddLpsName.setAdapter(new ArrayAdapter<>(this, R.layout.item_spinner_lps, allLps));
        mBinding.transformerAddLpsName.setOnItemSelectedListener(new SpinnerItemSelectedAdapter() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mItem.setLps(allLps.get(position));
                mItem.setLpsID(allLps.get(position).getId());
            }
        });
        for (int i = 0; i < allLps.size(); i++) {
            if (allLps.get(i).getId() == mItem.getLpsID()) {
                mBinding.transformerAddLpsName.setSelection(i);
                break;
            }
        }

    }

    @OnClick(R.id.transformer_add_verification)
    protected void onRepairDateClick() {
        Calendar calendar = GregorianCalendar.getInstance();
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = Utils.dateFormat.format(Utils.buildCalendar(year, month, dayOfMonth).getTime());
                mItem.setNextVerification(date);
                mBinding.transformerAddVerification.setText(date);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick(R.id.saveTransformerButton)
    protected void onSaveClick() {
        try {
            mItem.setName(mBinding.transformerAddAddName.getText().toString());
            mItem.setNextVerification(mBinding.transformerAddVerification.getText().toString());
            mItem.setRatio(mBinding.transformerRatioAdd.getText().toString());
            mItem.setNumber(mBinding.transformerAddStockNumber.getText().toString());
            if (mItem.getId() != null) {
                HelperFactory.getHelper().getTransformerDao().update(mItem);
            } else {
                HelperFactory.getHelper().getTransformerDao().create(mItem);
            }
            finish();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(this, "Не удалось сохранить данные!", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.deleteTransformerButton)
    protected void onCancelClick() {
        if (mItem.getId() != null) {
            try {
                HelperFactory.getHelper().getTransformerDao().delete(mItem);
                Toast.makeText(this, "Запись удалена!", Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                Toast.makeText(this, "Не удалось удалить запись!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        finish();
    }

}
