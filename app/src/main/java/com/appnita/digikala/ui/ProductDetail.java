package com.appnita.digikala.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import com.appnita.digikala.adapter.AdapterCategoryProductDetail;
import com.appnita.digikala.retrofit.basket.BasketClass;
import com.appnita.digikala.Lists;
import com.appnita.digikala.R;
import com.appnita.digikala.databinding.LayoutProductDetailsBinding;
import com.appnita.digikala.retrofit.basket.RetrofitBasket;
import com.appnita.digikala.retrofit.pojoProducts.ResponseProduct;
import com.appnita.digikala.retrofit.retrofit.ApiService;
import com.appnita.digikala.retrofit.shop.ChildAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetail extends AppCompatActivity {

    LayoutProductDetailsBinding binding;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = LayoutProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //back button in toolbar
        setSupportActionBar(binding.toolbar4);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        binding.toolbar4.setNavigationIcon(R.drawable.ic_back);
        binding.toolbar4.setNavigationOnClickListener(v -> {
            finish();
        });

        Intent intent = getIntent();

        ResponseProduct product = (ResponseProduct) intent.getSerializableExtra("product");

        binding.title.setText(product.getName());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.content.setText(Html.fromHtml(product.getDescription(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            binding.content.setText(Html.fromHtml(product.getDescription()));
        }

        Picasso.with(this)
                .load(product.getImages().get(0).getSrc())
                .error(R.drawable.error)
                .resize(1000, 1000)
                .into(binding.image);

        binding.detailPrice.setText(product.getPrice()+" تومان");

        binding.txtCountBuy.setText(String.valueOf(product.getTotalSales())+" نفر این محصول را خریداری کرده اند.");

        RetrofitBasket retrofit = new RetrofitBasket();
        ApiService apiService = retrofit.getApiService();

        Call<List<ResponseProduct>> call = apiService.getProductsBasket(product.getRelatedIds());
        call.enqueue(new Callback<List<ResponseProduct>>() {
            @Override
            public void onResponse(Call<List<ResponseProduct>> call, Response<List<ResponseProduct>> response) {
                if (response.isSuccessful()) {
                    List<ResponseProduct> products = response.body();
                    Log.d("test list : ", products.toString());
                    ChildAdapter childAdapter = new ChildAdapter(products,ProductDetail.this);
                    binding.rvRelated.setAdapter(childAdapter);

                } else {
                    Toast.makeText(ProductDetail.this, "ok but ..." + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ResponseProduct>> call, Throwable t) {
                Toast.makeText(ProductDetail.this, "oh  " + t, Toast.LENGTH_LONG).show();
            }
        });

        binding.rvCategory.setAdapter(new AdapterCategoryProductDetail(product.getCategories(),ProductDetail.this));


        int id = intent.getIntExtra("id",0);

        binding.btnAddBasket.setOnClickListener(v -> {
            Lists.basketClass.add(new BasketClass(id));
            Toast.makeText(this, "به سبد شما افزوده شد", Toast.LENGTH_SHORT).show();
        });
    }
}