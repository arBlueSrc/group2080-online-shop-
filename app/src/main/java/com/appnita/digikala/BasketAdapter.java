package com.appnita.digikala;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.appnita.digikala.retrofit.basket.Products;

import java.util.List;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ViewHolder> {

    Context context;
    List<Products> list;


    public BasketAdapter (Context context, List<Products> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_basket_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Products recyclerClass = list.get(position);
        holder.title.setText(recyclerClass.getName());
        holder.price.setText(recyclerClass.getPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,price;
        ImageView delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_text);
            delete = itemView.findViewById(R.id.btn_delete);
            price = itemView.findViewById(R.id.rv_price);

            delete.setOnLongClickListener(v -> {
                Products recyclerClass = list.get(getAdapterPosition());
                list.remove(recyclerClass);
                notifyDataSetChanged();
                return true;
            });

            delete.setOnClickListener(v -> {
                Toast.makeText(context, "برای حذف، دکمه را نگه دارید", Toast.LENGTH_SHORT).show();
            });

        }
    }

}