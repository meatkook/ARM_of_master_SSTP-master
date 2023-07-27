package com.matao.arm.arm_of_master_sstp.staff;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.matao.arm.arm_of_master_sstp.DB.HelperFactory;
import com.matao.arm.arm_of_master_sstp.R;
import com.matao.arm.arm_of_master_sstp.databinding.ActivityAddStaffBinding;
import com.matao.arm.arm_of_master_sstp.models.Staff;

import java.sql.SQLException;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddStaffActivity extends AppCompatActivity {

    public static String ARG_STAFF_ID = "arg_staff_id";
    private ActivityAddStaffBinding mBinding;
    private Staff mItem;

    public AddStaffActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_add_staff);
        ButterKnife.bind(this);

        if(getIntent().hasExtra(ARG_STAFF_ID)) {
            Integer id = getIntent().getIntExtra(ARG_STAFF_ID, 0);
            mItem = HelperFactory.getHelper().getStaffDao().getItem(id);
        }

        if (mItem == null) {
            mItem = new Staff();
        }
        mBinding.setItem(mItem);
    }

    @OnClick(R.id.saveButton)
    protected void onSaveClick() {
        try {
            mItem.setName(mBinding.name.getText().toString());
            mItem.setSurname(mBinding.surname.getText().toString());
            mItem.setPatronymic(mBinding.patronymic.getText().toString());
            mItem.setBirthday(mBinding.birthday.getText().toString());
            mItem.setHomePhone(mBinding.homePhone.getText().toString());
            mItem.setMobilePhoneOne(mBinding.mobilePhone1.getText().toString());
            mItem.setMobilePhoneOne(mBinding.mobilePhone1.getText().toString());
            if (mItem.getId() != null) {
                HelperFactory.getHelper().getStaffDao().update(mItem);
            } else {
                HelperFactory.getHelper().getStaffDao().create(mItem);
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
                HelperFactory.getHelper().getStaffDao().delete(mItem);
                Toast.makeText(this, "Запись удалена!", Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                Toast.makeText(this, "Не удалось удалить запись!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        finish();
    }
}
