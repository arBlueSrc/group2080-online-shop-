package com.appnita.digikala.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appnita.digikala.R;
import com.appnita.digikala.retrofit.pojoPosts.PostsItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterStory extends RecyclerView.Adapter<AdapterStory.StoryViewHolder> {

    List<PostsItem> list;
    Context context;
    OnClickListener onClickListener;

    public AdapterStory(List<PostsItem> list, Context context,OnClickListener onClickListener) {
        this.list = list;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_story_podcast,parent,false);
        return new StoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, int position) {
        PostsItem postsItem = list.get(position);
        holder.title.setText(postsItem.getTitle());
        try{
            Picasso.with(context)
                    .load(postsItem.getThumbnailImages().getMedium().getUrl())
                    .error(R.drawable.error)
                    .into(holder.image);
        }catch (Throwable t){
            holder.image.setImageResource(R.drawable.error);
        }

        holder.itemView.setOnClickListener(v -> onClickListener.onClickItem(postsItem));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class StoryViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        public StoryViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img_story);
            title = itemView.findViewById(R.id.txt_story);
        }
    }

    public interface OnClickListener {
        public void onClickItem(PostsItem postsItem);
    }
}
