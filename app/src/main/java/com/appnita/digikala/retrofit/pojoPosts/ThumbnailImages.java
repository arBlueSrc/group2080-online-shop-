package com.appnita.digikala.retrofit.pojoPosts;

import com.google.gson.annotations.SerializedName;

public class ThumbnailImages{

	@SerializedName("normal")
	private Normal normal;

	@SerializedName("thumbnail")
	private Thumbnail thumbnail;

	@SerializedName("shop_thumbnail")
	private ShopThumbnail shopThumbnail;

	@SerializedName("large")
	private Large large;

	@SerializedName("woocommerce_gallery_thumbnail")
	private WoocommerceGalleryThumbnail woocommerceGalleryThumbnail;

	@SerializedName("woocommerce_thumbnail")
	private WoocommerceThumbnail woocommerceThumbnail;

	@SerializedName("medium")
	private Medium medium;

	@SerializedName("extended")
	private Extended extended;

	@SerializedName("woocommerce_single")
	private WoocommerceSingle woocommerceSingle;

	@SerializedName("shop_single")
	private ShopSingle shopSingle;

	@SerializedName("medium_large")
	private MediumLarge mediumLarge;

	@SerializedName("shop_catalog")
	private ShopCatalog shopCatalog;

	@SerializedName("full")
	private Full full;

	public void setNormal(Normal normal){
		this.normal = normal;
	}

	public Normal getNormal(){
		return normal;
	}

	public void setThumbnail(Thumbnail thumbnail){
		this.thumbnail = thumbnail;
	}

	public Thumbnail getThumbnail(){
		return thumbnail;
	}

	public void setShopThumbnail(ShopThumbnail shopThumbnail){
		this.shopThumbnail = shopThumbnail;
	}

	public ShopThumbnail getShopThumbnail(){
		return shopThumbnail;
	}

	public void setLarge(Large large){
		this.large = large;
	}

	public Large getLarge(){
		return large;
	}

	public void setWoocommerceGalleryThumbnail(WoocommerceGalleryThumbnail woocommerceGalleryThumbnail){
		this.woocommerceGalleryThumbnail = woocommerceGalleryThumbnail;
	}

	public WoocommerceGalleryThumbnail getWoocommerceGalleryThumbnail(){
		return woocommerceGalleryThumbnail;
	}

	public void setWoocommerceThumbnail(WoocommerceThumbnail woocommerceThumbnail){
		this.woocommerceThumbnail = woocommerceThumbnail;
	}

	public WoocommerceThumbnail getWoocommerceThumbnail(){
		return woocommerceThumbnail;
	}

	public void setMedium(Medium medium){
		this.medium = medium;
	}

	public Medium getMedium(){
		return medium;
	}

	public void setExtended(Extended extended){
		this.extended = extended;
	}

	public Extended getExtended(){
		return extended;
	}

	public void setWoocommerceSingle(WoocommerceSingle woocommerceSingle){
		this.woocommerceSingle = woocommerceSingle;
	}

	public WoocommerceSingle getWoocommerceSingle(){
		return woocommerceSingle;
	}

	public void setShopSingle(ShopSingle shopSingle){
		this.shopSingle = shopSingle;
	}

	public ShopSingle getShopSingle(){
		return shopSingle;
	}

	public void setMediumLarge(MediumLarge mediumLarge){
		this.mediumLarge = mediumLarge;
	}

	public MediumLarge getMediumLarge(){
		return mediumLarge;
	}

	public void setShopCatalog(ShopCatalog shopCatalog){
		this.shopCatalog = shopCatalog;
	}

	public ShopCatalog getShopCatalog(){
		return shopCatalog;
	}

	public void setFull(Full full){
		this.full = full;
	}

	public Full getFull(){
		return full;
	}

	@Override
 	public String toString(){
		return 
			"ThumbnailImages{" + 
			"normal = '" + normal + '\'' + 
			",thumbnail = '" + thumbnail + '\'' + 
			",shop_thumbnail = '" + shopThumbnail + '\'' + 
			",large = '" + large + '\'' + 
			",woocommerce_gallery_thumbnail = '" + woocommerceGalleryThumbnail + '\'' + 
			",woocommerce_thumbnail = '" + woocommerceThumbnail + '\'' + 
			",medium = '" + medium + '\'' + 
			",extended = '" + extended + '\'' + 
			",woocommerce_single = '" + woocommerceSingle + '\'' + 
			",shop_single = '" + shopSingle + '\'' + 
			",medium_large = '" + mediumLarge + '\'' + 
			",shop_catalog = '" + shopCatalog + '\'' + 
			",full = '" + full + '\'' + 
			"}";
		}
}