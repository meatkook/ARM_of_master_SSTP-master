package com.matao.arm.arm_of_master_sstp.staff;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matao.arm.arm_of_master_sstp.databinding.ItemStaffBinding;
import com.matao.arm.arm_of_master_sstp.models.Staff;

import java.util.ArrayList;
import java.util.List;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.ViewHolder> {
    private List<Staff> mList = new ArrayList<>();
    private LayoutInflater mInflater;
    private OnItemClick onClick;

    public StaffAdapter(List<Staff> list, LayoutInflater inflater, OnItemClick onClick) {
        mList.addAll(list);
        mInflater = inflater;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemStaffBinding.inflate(mInflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemStaffBinding mBinding;

        public ViewHolder(ItemStaffBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        private void bind(final Staff item) {
            mBinding.setItem(item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClick != null){
                        onClick.onItemClick(v, item);
                    }
                }
            });
        }
    }
    public interface OnItemClick {
        void onItemClick(View v, Staff item);
    }

}
