package com.appnita.digikala.retrofit.retrofit;

import com.appnita.digikala.retrofit.basket.Products;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.internal.concurrent.Task;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @GET("products")
    Call<List<WooRetrofit>> getLastArticle();

    @POST("create_user")
    Call<RegisterCondition> creat (@Body RequestBody body);

    @POST("login_user")
    Call<LoginRetrofit> login (@Body RequestBody body);

    @POST("send_otp")
    Call<Verifycode> sendOtp (@Query("countrycode") String countryCode,
                              @Query("mobileNo") String mobileNo,
                              @Query("type") String type,
                              @Query("username") String username);

    @POST("verify_otp")
    Call<Verifycode> verifyOtp (@Query("countrycode") String countryCode,
                                     @Query("mobileNo") String mobileNo,
                                     @Query("type") String type,
                                     @Query("otp") String username);

    @POST("get_category_posts")
    Call<NewsRetrofit> news (@Query("id") int id);

    @POST("get_category_posts")
    Call<NewsRetrofit> notification (@Query("id") int id);

    @GET("products")
    Call<List<Products>> getProducts();

    @GET("products")
    Call<List<Products>> getProductsBasket(@Query("include[]") List<Integer> id);
}
