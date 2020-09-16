package com.appnita.digikala.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.appnita.digikala.BasketAdapter;
import com.appnita.digikala.Lists;
import com.appnita.digikala.R;
import com.appnita.digikala.databinding.ActivityBasketBinding;
import com.appnita.digikala.databinding.NavigationBinding;
import com.appnita.digikala.retrofit.basket.AdapterProducts;
import com.appnita.digikala.retrofit.basket.Products;
import com.appnita.digikala.retrofit.basket.RetrofitBasket;
import com.appnita.digikala.retrofit.retrofit.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Basket extends AppCompatActivity {

    ActivityBasketBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBasketBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RetrofitConfig();


    }

    private void RetrofitConfig() {
        RetrofitBasket retrofit;
        ApiService apiService;

        retrofit = new RetrofitBasket();
        apiService = retrofit.getApiService();

        List<Integer> a = new ArrayList<>();


        for (int i = 0; i < Lists.basketClass.size(); i++) {
            if (Lists.basketClass.size() > 0) {
                a.add(Lists.basketClass.get(i).getId());
            }
        }

        Call<List<Products>> call = apiService.getProductsBasket(a);
        call.enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                if (response.isSuccessful()) {
                    List<Products> products = response.body();

                    BasketAdapter adapter = new BasketAdapter(Basket.this, products);
                    binding.rvBasket.setAdapter(adapter);

                } else {
                    Toast.makeText(Basket.this, "ok but ..." + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                Toast.makeText(Basket.this, "oh  " + t, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}