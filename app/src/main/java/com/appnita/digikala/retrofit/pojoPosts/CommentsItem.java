package com.appnita.digikala.retrofit.pojoPosts;

import com.google.gson.annotations.SerializedName;

public class CommentsItem{

	@SerializedName("date")
	private String date;

	@SerializedName("parent")
	private int parent;

	@SerializedName("author")
	private Author author;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("url")
	private String url;

	@SerializedName("content")
	private String content;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setParent(int parent){
		this.parent = parent;
	}

	public int getParent(){
		return parent;
	}

	public void setAuthor(Author author){
		this.author = author;
	}

	public Author getAuthor(){
		return author;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return content;
	}

	@Override
 	public String toString(){
		return 
			"CommentsItem{" + 
			"date = '" + date + '\'' + 
			",parent = '" + parent + '\'' + 
			",author = '" + author + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",url = '" + url + '\'' + 
			",content = '" + content + '\'' + 
			"}";
		}
}