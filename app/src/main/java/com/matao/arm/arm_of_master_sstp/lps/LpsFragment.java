package com.matao.arm.arm_of_master_sstp.lps;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matao.arm.arm_of_master_sstp.DB.HelperFactory;
import com.matao.arm.arm_of_master_sstp.R;
import com.matao.arm.arm_of_master_sstp.fragments.AbstractTabFragment;
import com.matao.arm.arm_of_master_sstp.models.LPS;

import java.util.List;

public class LpsFragment extends AbstractTabFragment {
    private static final  int LAYOUT = R.layout.fragment_lps;
    RecyclerView lpsList;
    LayoutInflater inflater;
    public LpsFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        lpsList = view.findViewById(R.id.recycler);
        this.inflater = inflater;

        view.findViewById(R.id.lpsfab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddLpsActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        List<LPS> lpses = HelperFactory.getHelper().getLpsDao().getAllLps();
        lpsList.setAdapter(new LpsAdapter(lpses, inflater, new LpsAdapter.OnLpsClick() {
            @Override
            public void onLpsClick(View v, LPS lps) {
                Intent intent = new Intent(view.getContext(), AddLpsActivity.class);
                intent.putExtra(AddLpsActivity.ARG_LPS_ID, lps.getId());
                startActivity(intent);
            }
        }));
    }
    public static LpsFragment getInstance (Context context){
        Bundle args = new Bundle();
        LpsFragment fragment = new LpsFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_lps));
        return  fragment;
    }
    public void setContext(Context context) {
        this.context = context;
    }


}
