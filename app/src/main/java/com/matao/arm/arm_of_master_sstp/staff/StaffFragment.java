package com.matao.arm.arm_of_master_sstp.staff;

import android.content.Context;
import android.content.Intent;
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

import com.matao.arm.arm_of_master_sstp.DB.HelperFactory;
import com.matao.arm.arm_of_master_sstp.R;
import com.matao.arm.arm_of_master_sstp.fragments.AbstractTabFragment;
import com.matao.arm.arm_of_master_sstp.models.Staff;

import java.util.List;

public class StaffFragment extends AbstractTabFragment {

    private static final  int LAYOUT = R.layout.fragment_extinguishing;
    RecyclerView lpsList;
    LayoutInflater inflater;

    public StaffFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        lpsList = view.findViewById(R.id.recycler);
        this.inflater = inflater;

        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddStaffActivity.class);
                startActivity(intent);
            }
        });


        // слушатель изменения текста в фильтре
        ((EditText)view.findViewById(R.id.filter)).addTextChangedListener(new TextWatcher(){
            @Override
            public void afterTextChanged(Editable s) {
                // Прописываем то, что надо выполнить после изменения текста
                initData(s.toString());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        initData(((EditText)view.findViewById(R.id.filter)).getText().toString());
    }

    private void initData(String filter) {
        List<Staff> lpses = HelperFactory.getHelper().getStaffDao().getDinamicFiltered(filter);
        lpsList.setAdapter(new StaffAdapter(lpses, inflater, new StaffAdapter.OnItemClick() {
            @Override
            public void onItemClick(View v, Staff item) {
                Intent intent = new Intent(view.getContext(), AddStaffActivity.class);
                intent.putExtra(AddStaffActivity.ARG_STAFF_ID, item.getId());
                startActivity(intent);
            }
        }));
    }

    public static StaffFragment getInstance (Context context){
        Bundle args = new Bundle();
        StaffFragment fragment = new StaffFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_exting));
        return  fragment;
    }
    public void setContext(Context context) {
        this.context = context;
    }


}
