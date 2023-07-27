package com.matao.arm.arm_of_master_sstp.lps;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.matao.arm.arm_of_master_sstp.DB.HelperFactory;
import com.matao.arm.arm_of_master_sstp.R;
import com.matao.arm.arm_of_master_sstp.databinding.ActivityAddLpsBinding;
import com.matao.arm.arm_of_master_sstp.models.LPS;

import java.sql.SQLException;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddLpsActivity extends AppCompatActivity {

    public static String ARG_LPS_ID = "arg_lps_id";
    private ActivityAddLpsBinding mBinding;
    private LPS mItem;

    public AddLpsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_add_lps);
        ButterKnife.bind(this);

        if(getIntent().hasExtra(ARG_LPS_ID)) {
            Integer id = getIntent().getIntExtra(ARG_LPS_ID, 0);
            mItem = HelperFactory.getHelper().getLpsDao().getItem(id);
        }

        if (mItem == null) {
            mItem = new LPS();
        }
        mBinding.setItem(mItem);
    }

    @OnClick(R.id.saveButton)
    protected void onSaveClick() {
        try {
            mItem.setName(mBinding.lpsAddName.getText().toString());
            mItem.setAddress(mBinding.lpsAddAddress.getText().toString());
            mItem.setStockNumber(mBinding.lpsAddStockNumber.getText().toString());
            if (mItem.getId() != null) {
                HelperFactory.getHelper().getLpsDao().update(mItem);
            } else {
                HelperFactory.getHelper().getLpsDao().create(mItem);
            }
            finish();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(this, "Не удалось сохранить данные!", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.deleteButton)
    protected void onCancelClick() {
        if (mItem.getId() != null) {
            try {
                HelperFactory.getHelper().getLpsDao().delete(mItem);
                Toast.makeText(this, "Запись удалена!", Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                Toast.makeText(this, "Не удалось удалить запись!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        finish();
    }
}
