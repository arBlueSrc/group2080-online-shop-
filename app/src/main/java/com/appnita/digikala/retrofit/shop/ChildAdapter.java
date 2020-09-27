package com.appnita.digikala.retrofit.shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appnita.digikala.R;
import com.appnita.digikala.retrofit.pojoProducts.ResponseProduct;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ChildViewHolder> {

    List<ResponseProduct> list = new ArrayList<>();
    Context context;

    public ChildAdapter(List<ResponseProduct> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.child_product_list,parent,false);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder holder, int position) {
        ResponseProduct product = list.get(position);
        holder.title.setText(product.getName());
        Picasso.with(context)
                .load(product.getImages().get(0).getSrc())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ChildViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;
        public ChildViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txt_child);
            image = itemView.findViewById(R.id.img_child);
        }
    }
}
