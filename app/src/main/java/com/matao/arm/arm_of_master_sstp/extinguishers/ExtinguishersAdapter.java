package com.matao.arm.arm_of_master_sstp.extinguishers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matao.arm.arm_of_master_sstp.databinding.ItemExtingBinding;
import com.matao.arm.arm_of_master_sstp.models.Extinguisher;

import java.util.ArrayList;
import java.util.List;

public class ExtinguishersAdapter extends RecyclerView.Adapter<ExtinguishersAdapter.ViewHolder> {
    private List<Extinguisher> mList = new ArrayList<>();
    private LayoutInflater mInflater;
    private OnItemClick onClick;

    public ExtinguishersAdapter(List<Extinguisher> list, LayoutInflater inflater, OnItemClick onClick) {
        mList.addAll(list);
        mInflater = inflater;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemExtingBinding.inflate(mInflater, parent, false));
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
        ItemExtingBinding mBinding;

        public ViewHolder(ItemExtingBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        private void bind(final Extinguisher item) {
            mBinding.setItem(item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClick != null){
                        onClick.onLpsClick(v, item);
                    }
                }
            });
        }
    }
    public interface OnItemClick {
        void onLpsClick(View v, Extinguisher item);

    }

}
