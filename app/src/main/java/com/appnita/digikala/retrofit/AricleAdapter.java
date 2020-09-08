package com.appnita.digikala.retrofit;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appnita.digikala.R;

import java.util.List;

class AricleAdapter extends RecyclerView.Adapter<AricleAdapter.MyViewHolder> {

    Context context;
    List<Articles> articles;

    public AricleAdapter(Context context, List<Articles> articles) {
        this.context = context;
        this.articles = articles;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_article,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Articles article = articles.get(position);
        holder.imageview.setImageResource(R.drawable.ic_launcher_background);
        holder.title.setText(article.getTitle());
        holder.content.setText(article.getContent());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.content.setText(Html.fromHtml(article.getContent(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.content.setText(Html.fromHtml(article.getContent()));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ArticlePage.class);
                intent.putExtra("title", article.getTitle());
                intent.putExtra("content", article.getContent());
                intent.putExtra("position",position);
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageview;
        TextView title;
        TextView content;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageview = itemView.findViewById(R.id.article_image);
            title = itemView.findViewById(R.id.article_txt1);
            content = itemView.findViewById(R.id.article_txt2);
        }
    }
}
