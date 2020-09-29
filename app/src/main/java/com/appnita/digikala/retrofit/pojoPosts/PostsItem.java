package com.appnita.digikala.retrofit.pojoPosts;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PostsItem{

	@SerializedName("date")
	private String date;

	@SerializedName("comment_count")
	private int commentCount;

	@SerializedName("thumbnail")
	private String thumbnail;

	@SerializedName("comments")
	private List<Object> comments;

	@SerializedName("attachments")
	private List<AttachmentsItem> attachments;

	@SerializedName("author")
	private Author author;

	@SerializedName("custom_fields")
	private CustomFields customFields;

	@SerializedName("type")
	private String type;

	@SerializedName("title")
	private String title;

	@SerializedName("comment_status")
	private String commentStatus;

	@SerializedName("url")
	private String url;

	@SerializedName("content")
	private String content;

	@SerializedName("tags")
	private List<Object> tags;

	@SerializedName("title_plain")
	private String titlePlain;

	@SerializedName("modified")
	private String modified;

	@SerializedName("id")
	private int id;

	@SerializedName("categories")
	private List<CategoriesItem> categories;

	@SerializedName("excerpt")
	private String excerpt;

	@SerializedName("thumbnail_images")
	private ThumbnailImages thumbnailImages;

	@SerializedName("slug")
	private String slug;

	@SerializedName("status")
	private String status;

	@SerializedName("thumbnail_size")
	private String thumbnailSize;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setCommentCount(int commentCount){
		this.commentCount = commentCount;
	}

	public int getCommentCount(){
		return commentCount;
	}

	public void setThumbnail(String thumbnail){
		this.thumbnail = thumbnail;
	}

	public String getThumbnail(){
		return thumbnail;
	}

	public void setComments(List<Object> comments){
		this.comments = comments;
	}

	public List<Object> getComments(){
		return comments;
	}

	public void setAttachments(List<AttachmentsItem> attachments){
		this.attachments = attachments;
	}

	public List<AttachmentsItem> getAttachments(){
		return attachments;
	}

	public void setAuthor(Author author){
		this.author = author;
	}

	public Author getAuthor(){
		return author;
	}

	public void setCustomFields(CustomFields customFields){
		this.customFields = customFields;
	}

	public CustomFields getCustomFields(){
		return customFields;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setCommentStatus(String commentStatus){
		this.commentStatus = commentStatus;
	}

	public String getCommentStatus(){
		return commentStatus;
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

	public void setTags(List<Object> tags){
		this.tags = tags;
	}

	public List<Object> getTags(){
		return tags;
	}

	public void setTitlePlain(String titlePlain){
		this.titlePlain = titlePlain;
	}

	public String getTitlePlain(){
		return titlePlain;
	}

	public void setModified(String modified){
		this.modified = modified;
	}

	public String getModified(){
		return modified;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setCategories(List<CategoriesItem> categories){
		this.categories = categories;
	}

	public List<CategoriesItem> getCategories(){
		return categories;
	}

	public void setExcerpt(String excerpt){
		this.excerpt = excerpt;
	}

	public String getExcerpt(){
		return excerpt;
	}

	public void setThumbnailImages(ThumbnailImages thumbnailImages){
		this.thumbnailImages = thumbnailImages;
	}

	public ThumbnailImages getThumbnailImages(){
		return thumbnailImages;
	}

	public void setSlug(String slug){
		this.slug = slug;
	}

	public String getSlug(){
		return slug;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setThumbnailSize(String thumbnailSize){
		this.thumbnailSize = thumbnailSize;
	}

	public String getThumbnailSize(){
		return thumbnailSize;
	}

	@Override
 	public String toString(){
		return 
			"PostsItem{" + 
			"date = '" + date + '\'' + 
			",comment_count = '" + commentCount + '\'' + 
			",thumbnail = '" + thumbnail + '\'' + 
			",comments = '" + comments + '\'' + 
			",attachments = '" + attachments + '\'' + 
			",author = '" + author + '\'' + 
			",custom_fields = '" + customFields + '\'' + 
			",type = '" + type + '\'' + 
			",title = '" + title + '\'' + 
			",comment_status = '" + commentStatus + '\'' + 
			",url = '" + url + '\'' + 
			",content = '" + content + '\'' + 
			",tags = '" + tags + '\'' + 
			",title_plain = '" + titlePlain + '\'' + 
			",modified = '" + modified + '\'' + 
			",id = '" + id + '\'' + 
			",categories = '" + categories + '\'' + 
			",excerpt = '" + excerpt + '\'' + 
			",thumbnail_images = '" + thumbnailImages + '\'' + 
			",slug = '" + slug + '\'' + 
			",status = '" + status + '\'' + 
			",thumbnail_size = '" + thumbnailSize + '\'' + 
			"}";
		}
}