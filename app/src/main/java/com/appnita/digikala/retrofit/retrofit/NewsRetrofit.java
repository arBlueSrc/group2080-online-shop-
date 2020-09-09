package com.appnita.digikala.retrofit.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsRetrofit {
    public List<posts> getNews() {
        return posts;
    }

    private List<posts> posts = null;

    public class posts {

        @SerializedName("title")
        @Expose
        private String title;

        @SerializedName("content")
        @Expose
        private String content;

        @SerializedName("comments")
        @Expose
        private List<Comments> comments = null;

        public class Comments {
            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            @SerializedName("name")
            @Expose
            private String name;

            @SerializedName("content")
            @Expose
            private String content;
        }

        @SerializedName("Type")
        @Expose
        private String Type;

        @SerializedName("thumbnail")
        @Expose
        private String thumbnail;

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

        public List<Comments> getComments() {
            return comments;
        }

        public void setComments(List<Comments> comments) {
            this.comments = comments;
        }

        public String getType() {
            return Type;
        }

        public void setType(String type) {
            Type = type;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }


    }
}