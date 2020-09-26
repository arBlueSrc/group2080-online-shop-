package com.appnita.digikala.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.appnita.digikala.R;
import com.appnita.digikala.retrofit.comment.CommentClass;
import com.appnita.digikala.retrofit.retrofit.ApiService;
import com.appnita.digikala.retrofit.retrofit.RetrofitSetting;

import java.util.ArrayList;
import java.util.List;

public class ArticlePage extends AppCompatActivity {

    TextView title, content;
    ImageView back;
    RecyclerView commentrecycler;
    Button showComment;

    RetrofitSetting retrofit ;
    ApiService apiService;
    List<CommentClass> comments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_page);

        title = findViewById(R.id.art_title);
        content = findViewById(R.id.art_content);
        back = findViewById(R.id.art_back);
        commentrecycler = findViewById(R.id.comment_recycler);
        showComment = findViewById(R.id.btn_show_comment);

        Intent intent = getIntent();
        String title1 = intent.getStringExtra("title");
        String content1 = intent.getStringExtra("content");
        int position = intent.getIntExtra("position",0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            title.setText(Html.fromHtml(title1, Html.FROM_HTML_MODE_COMPACT));
        } else {
            title.setText(Html.fromHtml(title1));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            content.setText(Html.fromHtml(content1, Html.FROM_HTML_MODE_COMPACT));
        } else {
            content.setText(Html.fromHtml(content1));
        }

        back.setOnClickListener(v -> {
            this.finish();
        });

        showComment.setOnClickListener(v -> {
            commentrecycler.setVisibility(View.VISIBLE);
        });

//        retrofit = new RetrofitSetting();
//        apiService = retrofit.getApiService();
//
//        Call<ArticleRetrofit> call = apiService.getLastArticle();
//        call.enqueue(new Callback<ArticleRetrofit>() {
//            @Override
//            public void onResponse(Call<ArticleRetrofit> call, Response<ArticleRetrofit> response) {
//                if(response.isSuccessful()){
//                    ArticleRetrofit lastArticles = response.body();
//                    for (int i=0 ; i<lastArticles.getSearch().get(position).getComments().size();i++){
//                        CommentClass comment = new CommentClass();
//                        comment.setName(lastArticles.getSearch().get(position).getComments().get(i).getName());
//                        comment.setContent(lastArticles.getSearch().get(position).getComments().get(i).getContent());
//                        comments.add(comment);
//                    }
//
//                    CommentsAdapter commentsAdapter = new CommentsAdapter(ArticlePage.this,comments);
//                    commentrecycler.setLayoutManager(new LinearLayoutManager(ArticlePage.this,LinearLayoutManager.VERTICAL,false));
//                    commentrecycler.setAdapter(commentsAdapter);
//                    Toast.makeText(ArticlePage.this, "ok"+lastArticles.getSearch().size(), Toast.LENGTH_SHORT).show();}
//                else{
//                    Toast.makeText(ArticlePage.this, "ok but ...", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ArticleRetrofit> call, Throwable t) {
//                Toast.makeText(ArticlePage.this, "shit", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}