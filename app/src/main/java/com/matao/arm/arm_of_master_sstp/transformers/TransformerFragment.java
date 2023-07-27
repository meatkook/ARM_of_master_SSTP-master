package com.matao.arm.arm_of_master_sstp.transformers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.matao.arm.arm_of_master_sstp.models.Transformer;
import com.matao.arm.arm_of_master_sstp.tools.AddToolActivity;
import com.matao.arm.arm_of_master_sstp.tools.ToolAdapter;
import com.matao.arm.arm_of_master_sstp.tools.ToolFragment;

import java.util.List;


public class TransformerFragment extends AbstractTabFragment {
    private static final  int LAYOUT = R.layout.fragment_transformer;
    RecyclerView transformerlList;
    TextView header;
    EditText tFilter;
    LayoutInflater inflater;

    public TransformerFragment() {

    }

    public static TransformerFragment newInstance(String param1, String param2) {
        TransformerFragment fragment = new TransformerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        header = view.findViewById(R.id.search_transformer_result_count);
        transformerlList = view.findViewById(R.id.recycler);
        tFilter = view.findViewById(R.id.transformer_filter);
        this.inflater = inflater;

        view.findViewById(R.id.transformerfab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddTransformerActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
    public static TransformerFragment getInstance (Context context){
        Bundle args = new Bundle();
        TransformerFragment fragment = new TransformerFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_transformer));
        return  fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        // слушатель изменения текста в фильтре
        tFilter.addTextChangedListener(new TextWatcher(){
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
        List<Transformer> transformers = HelperFactory.getHelper().getTransformerDao().getDinamicFiltered(tFilter.getText().toString());
        header.setText(String.valueOf(transformers.size()));
        transformerlList.setAdapter(new TransformerAdapter(transformers, inflater, new TransformerAdapter.OnTransformerClick() {
            @Override
            public void onTransformerClick(View v, Transformer transformer) {
                Intent intent = new Intent(view.getContext(), AddTransformerActivity.class);
                intent.putExtra(AddTransformerActivity.ARG_TRANSFORMER_ID, transformer.getId());
                startActivity(intent);
            }
        }));
    }
}
