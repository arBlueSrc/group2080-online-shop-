package com.appnita.digikala.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.appnita.digikala.adapter.BasketAdapter;
import com.appnita.digikala.Lists;
import com.appnita.digikala.databinding.ActivityBasketBinding;
import com.appnita.digikala.retrofit.basket.RetrofitBasket;
import com.appnita.digikala.retrofit.pojoProducts.ResponseProduct;
import com.appnita.digikala.retrofit.retrofit.ApiService;
import com.zarinpal.ewallets.purchase.OnCallbackRequestPaymentListener;
import com.zarinpal.ewallets.purchase.OnCallbackVerificationPaymentListener;
import com.zarinpal.ewallets.purchase.PaymentRequest;
import com.zarinpal.ewallets.purchase.ZarinPal;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Basket extends AppCompatActivity {

    ActivityBasketBinding binding;

    int totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBasketBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        totalPrice = 0;
        RetrofitConfig();

        //zarinpal payment
        Uri data = getIntent().getData();
        ZarinPal.getPurchase(this).verificationPayment(data, new OnCallbackVerificationPaymentListener() {
            @Override
            public void onCallbackResultVerificationPayment(boolean isPaymentSuccess, String refID, PaymentRequest paymentRequest) {
                if(isPaymentSuccess){
                    Toast.makeText(Basket.this, "پرداخت موفقیت آمیز بود." + refID, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Basket.this, "پرداخت با خطا مواجه شد!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.goToPay.setOnClickListener(v -> {
            myPayment( 500L);
        });
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

        Call<List<ResponseProduct>> call = apiService.getProductsBasket(a);
        call.enqueue(new Callback<List<ResponseProduct>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<ResponseProduct>> call, Response<List<ResponseProduct>> response) {
                if (response.isSuccessful()) {
                    List<ResponseProduct> products = response.body();

                    BasketAdapter adapter = new BasketAdapter(Basket.this, products);
                    binding.rvBasket.setAdapter(adapter);


                    for (int i = 0; i < products.size(); i++) {
                        totalPrice = totalPrice + Integer.parseInt(products.get(i).getPrice());
                    }

                    String tPrice = String.valueOf(totalPrice);

                    binding.totalPrice.setText(tPrice + "تومان");

                } else {
                    Toast.makeText(Basket.this, "ok but ..." + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ResponseProduct>> call, Throwable t) {
                Toast.makeText(Basket.this, "oh  " + t, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void myPayment(Long amount) {
        ZarinPal purchase = ZarinPal.getPurchase(this);
        PaymentRequest payment = ZarinPal.getPaymentRequest();

        payment.setMerchantID("ea80cb7e-a4c4-11e9-97b4-000c29344814");
        payment.setAmount(amount);
        payment.setDescription("خرید توسط اپلیکیشن ۲۰۸۰");

        payment.setCallbackURL("return://app");
        purchase.startPayment(payment, new OnCallbackRequestPaymentListener() {
            @Override
            public void onCallbackResultPaymentRequest(int status, String authority, Uri paymentGatewayUri, Intent intent) {
                if (status == 100){
                    startActivity(intent);
                }else{
                    Toast.makeText(Basket.this, "خطا در پرداخت", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}