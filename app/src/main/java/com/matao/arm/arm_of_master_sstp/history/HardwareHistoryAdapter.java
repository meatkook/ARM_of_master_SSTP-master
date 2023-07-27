package com.matao.arm.arm_of_master_sstp.history;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matao.arm.arm_of_master_sstp.databinding.ItemHardwareHistoryBinding;
import com.matao.arm.arm_of_master_sstp.models.HardwareHistory;

import java.util.ArrayList;
import java.util.List;

public class HardwareHistoryAdapter extends RecyclerView.Adapter<HardwareHistoryAdapter.PlanViewHolder> {

    private List<HardwareHistory> mList = new ArrayList<>();

    private LayoutInflater mInflater;

    public HardwareHistoryAdapter(List<HardwareHistory> list, LayoutInflater inflater) {
        mList.addAll(list);
        mInflater = inflater;
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlanViewHolder(ItemHardwareHistoryBinding.inflate(mInflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlanViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class PlanViewHolder extends RecyclerView.ViewHolder {

        ItemHardwareHistoryBinding mBinding;

        public PlanViewHolder(ItemHardwareHistoryBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        private void bind(final HardwareHistory hardware) {
            mBinding.setHistory(hardware);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), AddHardwareToArchiveActivity.class);
                    intent.putExtra(AddHardwareToArchiveActivity.ARG_HISTORY_ID, hardware.getId());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
