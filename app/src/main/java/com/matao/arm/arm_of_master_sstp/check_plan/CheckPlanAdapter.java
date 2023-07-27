package com.matao.arm.arm_of_master_sstp.check_plan;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.matao.arm.arm_of_master_sstp.databinding.ItemCheckPlanBinding;

import java.util.ArrayList;
import java.util.List;

public class CheckPlanAdapter extends RecyclerView.Adapter<CheckPlanAdapter.PlanViewHolder> {

    private List<CheckModel> mList = new ArrayList<>();

    private LayoutInflater mInflater;

    public CheckPlanAdapter(List<CheckModel> list, LayoutInflater inflater) {
        mList.addAll(list);
        mInflater = inflater;
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlanViewHolder(ItemCheckPlanBinding.inflate(mInflater, parent, false));
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

        ItemCheckPlanBinding mBinding;

        public PlanViewHolder(ItemCheckPlanBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        private void bind(final CheckModel checkModel) {
            mBinding.setCheckModel(checkModel);
        }
    }
}
