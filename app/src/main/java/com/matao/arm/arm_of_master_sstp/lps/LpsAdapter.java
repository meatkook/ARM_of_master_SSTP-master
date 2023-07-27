package com.matao.arm.arm_of_master_sstp.lps;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matao.arm.arm_of_master_sstp.databinding.ItemLpsBinding;
import com.matao.arm.arm_of_master_sstp.models.LPS;
import com.matao.arm.arm_of_master_sstp.lps.LpsAdapter;

import java.util.ArrayList;
import java.util.List;

public class LpsAdapter extends RecyclerView.Adapter<LpsAdapter.LpsViewHolder> {
    private List<LPS> mList = new ArrayList<>();
    private LayoutInflater mInflater;
    private LpsAdapter.OnLpsClick onLpsClick;

    public LpsAdapter(List<LPS> list, LayoutInflater inflater, LpsAdapter.OnLpsClick onLpsClick) {
        mList.addAll(list);
        mInflater = inflater;
        this.onLpsClick = onLpsClick;
    }

    @NonNull
    @Override
    public LpsAdapter.LpsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LpsAdapter.LpsViewHolder(ItemLpsBinding.inflate(mInflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LpsAdapter.LpsViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class LpsViewHolder extends RecyclerView.ViewHolder {
        ItemLpsBinding mBinding;

        public LpsViewHolder(ItemLpsBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        private void bind(final LPS lps) {
            mBinding.setLps(lps);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onLpsClick != null){
                        onLpsClick.onLpsClick(v, lps);
                    }
                }
            });
        }
    }
    public interface OnLpsClick {
        void onLpsClick(View v, LPS lps);

    }

}
