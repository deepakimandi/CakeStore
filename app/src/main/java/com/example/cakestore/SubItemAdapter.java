package com.example.cakestore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SubItemAdapter extends RecyclerView.Adapter<SubItemAdapter.SubItemViewHolder> {

    private List<SubItem> subItemList;

    SubItemAdapter(List<SubItem> subItemList) {
        this.subItemList = subItemList;
    }

    @NonNull
    @Override
    public SubItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_subitem, viewGroup, false);
        return new SubItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubItemViewHolder subItemViewHolder, int i) {
        SubItem subItem = subItemList.get(i);
        subItemViewHolder.tvSubItemTitle.setText(subItem.getSubItemTitle());
        subItemViewHolder.singleitem_cost.setText(subItem.getCost());
        subItemViewHolder.qty.setText(subItem.getQty());
        subItemViewHolder.totalitem_cost.setText(String.valueOf(Integer.parseInt(subItem.getCost()) * Integer.parseInt(subItem.getQty())));
    }

    @Override
    public int getItemCount() {
        return subItemList.size();
    }

    class SubItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvSubItemTitle;
        TextView singleitem_cost;
        TextView qty;
        TextView totalitem_cost;
        SubItemViewHolder(View itemView) {
            super(itemView);
            tvSubItemTitle = itemView.findViewById(R.id.tv_sub_item_title);
            singleitem_cost = itemView.findViewById(R.id.singleitem_cost);
            qty = itemView.findViewById(R.id.qty);
            totalitem_cost = itemView.findViewById(R.id.totalitem_cost);
        }
    }
}