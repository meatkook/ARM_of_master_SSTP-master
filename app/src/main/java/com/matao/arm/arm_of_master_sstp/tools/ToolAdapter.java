package com.matao.arm.arm_of_master_sstp.tools;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matao.arm.arm_of_master_sstp.databinding.ItemToolsBinding;
import com.matao.arm.arm_of_master_sstp.models.Tool;

import java.util.ArrayList;
import java.util.List;

public class ToolAdapter extends RecyclerView.Adapter<ToolAdapter.ToolViewHolder> {
    private List<Tool> mList = new ArrayList<>();
    private LayoutInflater mInflater;
    private OnToolClick onToolClick;

    public ToolAdapter(List<Tool> list, LayoutInflater inflater, OnToolClick onToolClick) {
        mList.addAll(list);
        mInflater = inflater;
        this.onToolClick = onToolClick;
    }

    @NonNull
    @Override
    public ToolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ToolViewHolder(ItemToolsBinding.inflate(mInflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ToolViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ToolViewHolder extends RecyclerView.ViewHolder {
        ItemToolsBinding mBinding;

        public ToolViewHolder(ItemToolsBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        private void bind(final Tool tool) {
            mBinding.setTool(tool);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onToolClick != null){
                        onToolClick.onToolClick(v, tool);
                    }
                }
            });
        }
    }

    public interface OnToolClick {
        void onToolClick(View v, Tool tool);

    }

}
