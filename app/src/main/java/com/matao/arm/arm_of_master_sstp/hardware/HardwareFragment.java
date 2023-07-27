package com.matao.arm.arm_of_master_sstp.hardware;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.matao.arm.arm_of_master_sstp.DB.HelperFactory;
import com.matao.arm.arm_of_master_sstp.R;
import com.matao.arm.arm_of_master_sstp.fragments.AbstractTabFragment;
import com.matao.arm.arm_of_master_sstp.models.Hardware;
import com.matao.arm.arm_of_master_sstp.repair_plan.HardwareAdapter;

import java.util.List;

public class HardwareFragment extends AbstractTabFragment {


    private static final  int LAYOUT = R.layout.fragment_hardware;
    RecyclerView hwList;
    TextView header;
    EditText hwFilter;

    LayoutInflater inflater;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        header = view.findViewById(R.id.search_hardware_result_count);
        hwList = view.findViewById(R.id.recycler);
        hwFilter = view.findViewById(R.id.hardware_filter);
        this.inflater = inflater;

        view.findViewById(R.id.extingfab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddHardwareActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public static HardwareFragment getInstance(Context context) {
        Bundle args = new Bundle();
        HardwareFragment fragment = new HardwareFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_exting));
        return  fragment;
    }

     @Override
    public void onResume() {
        super.onResume();
         // слушатель изменения текста в фильтре
         hwFilter.addTextChangedListener(new TextWatcher(){
             @Override
             public void afterTextChanged(Editable s) {
                 // Прописываем то, что надо выполнить после изменения текста
                 initData();
             }
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                 initData();
             }
             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {
                 initData();
             }
         });
         initData();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void initData(){
        List<Hardware> hardware = HelperFactory.getHelper().getHardwareDao().getDinamicFiltered(hwFilter.getText().toString());

        header.setText(String.valueOf(hardware.size()));
        hwList.setAdapter(new HardwareAdapter(hardware, inflater, new HardwareAdapter.OnHardwareClick() {
            @Override
            public void onHardwareClick(View v, Hardware hardware) {
                Intent intent = new Intent(view.getContext(), AddHardwareActivity.class);
                intent.putExtra(AddHardwareActivity.ARG_HARDWARE_ID, hardware.getId());
                startActivity(intent);
            }
        }));
    }
}
