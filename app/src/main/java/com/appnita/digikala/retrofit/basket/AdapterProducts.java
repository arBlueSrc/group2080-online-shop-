package com.appnita.digikala.retrofit.basket;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appnita.digikala.R;
import com.appnita.digikala.retrofit.RecyclerAdapterClass;
import com.appnita.digikala.retrofit.RecyclerObjectClass;
import com.appnita.digikala.ui.ProductDetail;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterProducts extends RecyclerView.Adapter<AdapterProducts.ViewHolder> {

    Context context;
    List<Products> list;

    public AdapterProducts() {
    }

    public AdapterProducts(Context context, List<Products> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Products products = list.get(position);

        String imageUrl = products.getImages().get(0).getSrc();
        Picasso.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.error)
                .resize(1000, 1000)
                .into(holder.image);
        holder.name.setText(products.getName());
        holder.price.setText(products.getPrice());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetail.class);
            intent.putExtra("title",products.getName());
            intent.putExtra("content",products.getDescription());
            intent.putExtra("price",products.getPrice());
            intent.putExtra("image",products.getImages().get(0).getSrc());
            intent.putExtra("id",products.getId());
            intent.putExtra("price",products.getPrice());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.rv_title);
            price = itemView.findViewById(R.id.rv_content);
            image = itemView.findViewById(R.id.rv_image);
        }
    }


}
