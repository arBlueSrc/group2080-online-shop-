package com.appnita.digikala.retrofit.pojoPosts;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponsePosts{

	@SerializedName("count_total")
	private int countTotal;

	@SerializedName("pages")
	private int pages;

	@SerializedName("query")
	private Query query;

	@SerializedName("count")
	private int count;

	@SerializedName("posts")
	private List<PostsItem> posts;

	@SerializedName("status")
	private String status;

	public void setCountTotal(int countTotal){
		this.countTotal = countTotal;
	}

	public int getCountTotal(){
		return countTotal;
	}

	public void setPages(int pages){
		this.pages = pages;
	}

	public int getPages(){
		return pages;
	}

	public void setQuery(Query query){
		this.query = query;
	}

	public Query getQuery(){
		return query;
	}

	public void setCount(int count){
		this.count = count;
	}

	public int getCount(){
		return count;
	}

	public void setPosts(List<PostsItem> posts){
		this.posts = posts;
	}

	public List<PostsItem> getPosts(){
		return posts;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"ResponsePosts{" + 
			"count_total = '" + countTotal + '\'' + 
			",pages = '" + pages + '\'' + 
			",query = '" + query + '\'' + 
			",count = '" + count + '\'' + 
			",posts = '" + posts + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}