package com.appnita.digikala.adapter;

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

import com.appnita.digikala.BuyProductClassForRecycler;
import com.appnita.digikala.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyFilesAdapter extends RecyclerView.Adapter<MyFilesAdapter.ViewHolder> {

    Context context;
    List<BuyProductClassForRecycler> list = new ArrayList<>();
    onClickListener onClickListener;

    public MyFilesAdapter(Context context, List<BuyProductClassForRecycler> list, MyFilesAdapter.onClickListener onClickListener) {
        this.context = context;
        this.list = list;
        this.onClickListener = onClickListener;
    }

    public MyFilesAdapter(){
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_file_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BuyProductClassForRecycler file = list.get(position);
        holder.title.setText(file.getName());

        File file2 = new File("/sdcard/2080/"+file.getFileName());
        if (file2.exists()) {
            holder.button.setVisibility(View.GONE);
            holder.button2.setVisibility(View.VISIBLE);
        }else{
            holder.button.setVisibility(View.VISIBLE);
            holder.button2.setVisibility(View.GONE);
        }

        holder.button.setOnClickListener(v -> onClickListener.onDownload(file));
        holder.button2.setOnClickListener(v -> onClickListener.onSeefile(file));

        Picasso.with(context)
                .load(file.getImage())
                .into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView imageView;
        Button button, button2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_my_file);
            button = itemView.findViewById(R.id.btn_download);
            button2 = itemView.findViewById(R.id.btn_seefile);
            imageView = itemView.findViewById(R.id.img_my_file);
        }
    }

    public interface onClickListener {
        public void onDownload (BuyProductClassForRecycler file);
        public void onSeefile (BuyProductClassForRecycler file);
    }
}