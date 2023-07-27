package com.matao.arm.arm_of_master_sstp.extinguishers;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.matao.arm.arm_of_master_sstp.DB.HelperFactory;
import com.matao.arm.arm_of_master_sstp.R;
import com.matao.arm.arm_of_master_sstp.SpinnerItemSelectedAdapter;
import com.matao.arm.arm_of_master_sstp.databinding.ActivityAddExtinguishingBinding;
import com.matao.arm.arm_of_master_sstp.models.Extinguisher;
import com.matao.arm.arm_of_master_sstp.models.LPS;

import java.sql.SQLException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddExtinguishersActivity extends AppCompatActivity {

    public static String ARG_EXTINGUISHERS_ID = "arg_extinguishers_id";
    private ActivityAddExtinguishingBinding mBinding;
    private Extinguisher mItem;

    public AddExtinguishersActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_add_extinguishing);
        ButterKnife.bind(this);

        if(getIntent().hasExtra(ARG_EXTINGUISHERS_ID)) {
            Integer id = getIntent().getIntExtra(ARG_EXTINGUISHERS_ID, 0);
            mItem = HelperFactory.getHelper().getExtinguisherDao().getItem(id);
        }

        if (mItem == null) {
            mItem = new Extinguisher();
        }
        mBinding.setItem(mItem);

        final List<LPS> allLps = HelperFactory.getHelper().getLpsDao().getAllLps();
        mBinding.exAddLpsName.setAdapter(new ArrayAdapter<>(this, R.layout.item_spinner_lps, allLps));
        mBinding.exAddLpsName.setOnItemSelectedListener(new SpinnerItemSelectedAdapter() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mItem.setLps(allLps.get(position));
                mItem.setLpsID(allLps.get(position).getId());
            }
        });

        for (int i = 0; i < allLps.size(); i++) {
            if (allLps.get(i).getId() == mItem.getLpsID()) {
                mBinding.exAddLpsName.setSelection(i);
                break;
            }
        }

    }

    @OnClick(R.id.saveButton)
    protected void onSaveClick() {
        try {
            mItem.setName(mBinding.exAddName.getText().toString());
            mItem.setNumber(mBinding.exAddStockNomber.getText().toString());
            if (mItem.getId() != null) {
                HelperFactory.getHelper().getExtinguisherDao().update(mItem);
            } else {
                HelperFactory.getHelper().getExtinguisherDao().create(mItem);
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
                HelperFactory.getHelper().getExtinguisherDao().delete(mItem);
                Toast.makeText(this, "Запись удалена!", Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                Toast.makeText(this, "Не удалось удалить запись!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        finish();
    }
}
