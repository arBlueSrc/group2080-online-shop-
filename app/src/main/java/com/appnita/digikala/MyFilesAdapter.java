package com.appnita.digikala;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appnita.digikala.retrofit.basket.BuyProduct;
import com.appnita.digikala.retrofit.basket.Products;

import java.util.List;

public class MyFilesAdapter extends RecyclerView.Adapter<MyFilesAdapter.ViewHolder> {

    Context context;
    List<BuyProduct.ProductId> list;

    public MyFilesAdapter(Context context, List<BuyProduct.ProductId> list) {
        this.context = context;
        this.list = list;
    }


    public MyFilesAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_file_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BuyProduct.ProductId file = list.get(position);
        holder.title.setText(file.getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txt_download_file);
            button = itemView.findViewById(R.id.btn_download);
        }
    }
}