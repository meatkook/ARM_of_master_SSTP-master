package com.matao.arm.arm_of_master_sstp.tools;

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
import android.widget.TextView;

import com.matao.arm.arm_of_master_sstp.DB.HelperFactory;
import com.matao.arm.arm_of_master_sstp.R;
import com.matao.arm.arm_of_master_sstp.fragments.AbstractTabFragment;
import com.matao.arm.arm_of_master_sstp.models.Tool;

import java.util.List;


public class ToolFragment extends AbstractTabFragment {


    private static final  int LAYOUT = R.layout.fragment_tool;
    RecyclerView toolList;
    TextView header;
    EditText tFilter;
    LayoutInflater inflater;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        header = view.findViewById(R.id.search_tool_result_count);
        toolList = view.findViewById(R.id.recycler);
        tFilter = view.findViewById(R.id.tool_filter);
        this.inflater = inflater;

        view.findViewById(R.id.toolfab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddToolActivity.class);
                startActivity(intent);
            }
        });

        // слушатель изменения текста в фильтре
        tFilter.addTextChangedListener(new TextWatcher(){
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
        initData(tFilter.getText().toString());
    }

    public static ToolFragment getInstance (Context context){
        Bundle args = new Bundle();
        ToolFragment fragment = new ToolFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_tool));
        return  fragment;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void initData(String filter){
        List<Tool> tools = HelperFactory.getHelper().getToolsDao().getDinamicFiltered(filter);
        header.setText(String.valueOf(tools.size()));
        toolList.setAdapter(new ToolAdapter(tools, inflater, new ToolAdapter.OnToolClick() {
            @Override
            public void onToolClick(View v, Tool tool) {
                Intent intent = new Intent(view.getContext(), AddToolActivity.class);
                intent.putExtra(AddToolActivity.ARG_TOOL_ID, tool.getId());
                startActivity(intent);
            }
        }));
    }
}
