package com.matao.arm.arm_of_master_sstp.transformers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matao.arm.arm_of_master_sstp.databinding.ItemTransformerBinding;
import com.matao.arm.arm_of_master_sstp.models.Transformer;

import java.util.ArrayList;
import java.util.List;

public class TransformerAdapter extends RecyclerView.Adapter<TransformerAdapter.TransformerViewHolder> {
private List<Transformer> mList = new ArrayList<>();
private LayoutInflater mInflater;
private OnTransformerClick onTransformerClick;

public TransformerAdapter(List<Transformer> list, LayoutInflater inflater, OnTransformerClick onTransformerClick) {
        mList.addAll(list);
        mInflater = inflater;
        this.onTransformerClick = onTransformerClick;
        }

@NonNull
@Override
public TransformerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransformerViewHolder(ItemTransformerBinding.inflate(mInflater, parent, false));
        }

@Override
public void onBindViewHolder(@NonNull TransformerViewHolder holder, int position) {
        holder.bind(mList.get(position));
        }

@Override
public int getItemCount() {
        return mList.size();
        }

class TransformerViewHolder extends RecyclerView.ViewHolder {
    ItemTransformerBinding mBinding;
    public TransformerViewHolder(ItemTransformerBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    private void bind(final Transformer transformer) {
        mBinding.setTransformer(transformer);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onTransformerClick != null){
                    onTransformerClick.onTransformerClick(v, transformer);
                }
            }
        });
    }
}

public interface OnTransformerClick {
    void onTransformerClick(View v, Transformer transformer);

}

}
