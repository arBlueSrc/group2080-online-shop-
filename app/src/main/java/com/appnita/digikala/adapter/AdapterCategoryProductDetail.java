package com.appnita.digikala.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appnita.digikala.R;
import com.appnita.digikala.retrofit.pojoProducts.CategoriesItem;

import java.util.List;

public class AdapterCategoryProductDetail extends RecyclerView.Adapter<AdapterCategoryProductDetail.CategoyViewHolder> {

    List<CategoriesItem> list;
    Context context;

    public AdapterCategoryProductDetail(List<CategoriesItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_category_detail,parent,false);
        return new CategoyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoyViewHolder holder, int position) {
        CategoriesItem categoryClass = list.get(position);
        holder.category.setText(categoryClass.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CategoyViewHolder extends RecyclerView.ViewHolder {
        TextView category;
        public CategoyViewHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.txt_cat);
        }
    }
}
