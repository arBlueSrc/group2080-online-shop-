package com.appnita.digikala.retrofit.pojoPosts;

import com.google.gson.annotations.SerializedName;

public class AttachmentsItem{

	@SerializedName("parent")
	private int parent;

	@SerializedName("images")
	private Images images;

	@SerializedName("mime_type")
	private String mimeType;

	@SerializedName("description")
	private String description;

	@SerializedName("caption")
	private String caption;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	@SerializedName("url")
	private String url;

	@SerializedName("slug")
	private String slug;

	public void setParent(int parent){
		this.parent = parent;
	}

	public int getParent(){
		return parent;
	}

	public void setImages(Images images){
		this.images = images;
	}

	public Images getImages(){
		return images;
	}

	public void setMimeType(String mimeType){
		this.mimeType = mimeType;
	}

	public String getMimeType(){
		return mimeType;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setCaption(String caption){
		this.caption = caption;
	}

	public String getCaption(){
		return caption;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setSlug(String slug){
		this.slug = slug;
	}

	public String getSlug(){
		return slug;
	}

	@Override
 	public String toString(){
		return 
			"AttachmentsItem{" + 
			"parent = '" + parent + '\'' + 
			",images = '" + images + '\'' + 
			",mime_type = '" + mimeType + '\'' + 
			",description = '" + description + '\'' + 
			",caption = '" + caption + '\'' + 
			",id = '" + id + '\'' + 
			",title = '" + title + '\'' + 
			",url = '" + url + '\'' + 
			",slug = '" + slug + '\'' + 
			"}";
		}
}