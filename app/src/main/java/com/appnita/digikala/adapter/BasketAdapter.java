package com.appnita.digikala.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.appnita.digikala.R;
import com.appnita.digikala.retrofit.pojoProducts.ResponseProduct;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ViewHolder> {

    Context context;
    List<ResponseProduct> list;
    onCLickBasket onCLickBasket;

    public BasketAdapter(Context context, List<ResponseProduct> list, BasketAdapter.onCLickBasket onCLickBasket) {
        this.context = context;
        this.list = list;
        this.onCLickBasket = onCLickBasket;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_basket_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResponseProduct recyclerClass = list.get(position);
        holder.title.setText(recyclerClass.getName());
        holder.price.setText(recyclerClass.getPrice());

        Picasso.with(context)
                .load(recyclerClass.getImages().get(0).getSrc())
                .into(holder.productimage);

        holder.delete.setOnLongClickListener(v -> {
            onCLickBasket.onCLickDelete(recyclerClass);
            list.remove(recyclerClass);
            notifyDataSetChanged();
            return true;
        });

        holder.delete.setOnClickListener(v -> {
            Toast.makeText(context, "برای حذف، دکمه را نگه دارید", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,price;
        ImageView delete,productimage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_my_file);
            delete = itemView.findViewById(R.id.btn_delete);
            price = itemView.findViewById(R.id.rv_price);
            productimage = itemView.findViewById(R.id.img_my_file);


        }
    }

    public interface onCLickBasket {
        public void onCLickDelete(ResponseProduct responseProduct);

    }
}