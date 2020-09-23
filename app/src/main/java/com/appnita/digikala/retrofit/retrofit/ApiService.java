package com.appnita.digikala.retrofit.retrofit;

import com.appnita.digikala.retrofit.basket.BuyProduct;
import com.appnita.digikala.retrofit.basket.Products;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

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

    @POST("get_posts")
    Call<NewsRetrofit> news ();

    @POST("get_category_posts")
    Call<NewsRetrofit> notification (@Query("id") int id);

    @GET("products")
    Call<List<Products>> getProducts();

    @GET("products")
    Call<List<Products>> getProductsBasket(@Query("include[]") List<Integer> id);

    @GET("orders")
    Call<List<BuyProduct>> getCustomerProduct(@Query("customer") int id);

    @Streaming
    @GET(".")
    Call<ResponseBody> downloadFileByUrl(@Query("email") String email,
                                         @Query("download_file") int downloadFile,
                                         @Query("order") String order,
                                         @Query("key") String key);

}
