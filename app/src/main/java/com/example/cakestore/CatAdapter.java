package com.example.cakestore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.MyViewHolder> {

    private Context mContext;
    private List<Category> mCatList;
    private String user_id;
//    private String email;

    public CatAdapter(Context mContext, List<Category> mCatList, String user_id) {
        this.mContext = mContext;
        this.mCatList = mCatList;
        this.user_id = user_id;
//        this.email = email;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cat_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tv_cat_name.setText(mCatList.get(position).getCat_name());
        holder.iv_cat_image.setImageResource(mCatList.get(position).getCat_image());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, mCatList.get(position).getCat_name(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, FoodMenu.class);
                intent.putExtra("cat_id", mCatList.get(position).getCat_id());
                intent.putExtra("user_id", user_id);
//                intent.putExtra("email", email);
                intent.putExtra("cat_name", mCatList.get(position).getCat_name());
                mContext.startActivity(intent);
//                FoodMenu.overridePendingTransition(0, 0);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mCatList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_cat_name;
        ImageView iv_cat_image;
        CardView cardView;
        public MyViewHolder(View itemView){
            super(itemView);
            tv_cat_name = (TextView) itemView.findViewById(R.id.cat_name);
            iv_cat_image =(ImageView) itemView.findViewById(R.id.cat_image);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);

        }
    }
}
