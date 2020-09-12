package com.appnita.digikala.retrofit;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "posts_table")
public class RecyclerObjectClass {

    @ColumnInfo(name = "post_id")
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "post_image")
    private String image;

    @ColumnInfo(name = "post_title")
    private String title;

    @ColumnInfo(name = "post_content")
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
