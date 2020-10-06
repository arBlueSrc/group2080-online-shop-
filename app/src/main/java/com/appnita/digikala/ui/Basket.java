package com.appnita.digikala.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.appnita.digikala.adapter.BasketAdapter;
import com.appnita.digikala.Lists;
import com.appnita.digikala.buy.Billing;
import com.appnita.digikala.buy.LineItemsItem;
import com.appnita.digikala.buy.RequestBuy;
import com.appnita.digikala.databinding.ActivityBasketBinding;
import com.appnita.digikala.retrofit.basket.RetrofitBasket;
import com.appnita.digikala.retrofit.pojoProducts.ResponseProduct;
import com.appnita.digikala.retrofit.retrofit.ApiService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zarinpal.ewallets.purchase.OnCallbackRequestPaymentListener;
import com.zarinpal.ewallets.purchase.OnCallbackVerificationPaymentListener;
import com.zarinpal.ewallets.purchase.PaymentRequest;
import com.zarinpal.ewallets.purchase.ZarinPal;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Basket extends AppCompatActivity {

    ActivityBasketBinding binding;
    private static final String TAG = "Basket";
    private static long totalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBasketBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //ui visibility
        uiVisibility();

        //zarinpal payment
        zarinpal();

    }

    private void uiVisibility() {
        if(Lists.basketClass.size()==0){
            binding.rvBasket.setVisibility(View.GONE);
            binding.linearLayout.setVisibility(View.GONE);

            binding.imgNull.setVisibility(View.VISIBLE);
            binding.txtNull.setVisibility(View.VISIBLE);

        }else{
            binding.rvBasket.setVisibility(View.VISIBLE);
            binding.linearLayout.setVisibility(View.VISIBLE);
            binding.progressBar3.setVisibility(View.VISIBLE);

            binding.imgNull.setVisibility(View.GONE);
            binding.txtNull.setVisibility(View.GONE);

            totalPrice = 0;
            RetrofitConfig();
        }

    }

    private void zarinpal() {
        Uri data = getIntent().getData();
        ZarinPal.getPurchase(this).verificationPayment(data, (isPaymentSuccess, refID, paymentRequest) -> {
            if(isPaymentSuccess){
                Toast.makeText(Basket.this, "پرداخت موفقیت آمیز بود.", Toast.LENGTH_SHORT).show();
                RetrofitConfigBuy(refID);
                Lists.basketClass.clear();
            }else {
                Toast.makeText(Basket.this, "پرداخت با خطا مواجه شد!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.goToPay.setOnClickListener(v -> myPayment( totalPrice));
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
        purchase.startPayment(payment, (status, authority, paymentGatewayUri, intent) -> {
            if (status == 100){
                startActivity(intent);
            }else{
                Toast.makeText(Basket.this, "خطا در پرداخت", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void RetrofitConfig() {
        RetrofitBasket retrofit;
        ApiService apiService;

        retrofit = new RetrofitBasket();
        apiService = retrofit.getApiService();


        Log.i(TAG, "RetrofitConfig: "+Lists.basketClass.toString());

        Call<List<ResponseProduct>> call = apiService.getProductsBasket(Lists.basketClass);
        call.enqueue(new Callback<List<ResponseProduct>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<List<ResponseProduct>> call, @NotNull Response<List<ResponseProduct>> response) {
                if (response.isSuccessful()) {
                    List<ResponseProduct> products = response.body();

                    Log.i(TAG, "onResponse: retrofit " + response.toString());

                    BasketAdapter adapter = new BasketAdapter(Basket.this, products, responseProduct -> {
                        Lists.basketClass.remove(Integer.valueOf(responseProduct.getId()));
                        uiVisibility();
                    });
                    binding.rvBasket.setAdapter(adapter);
                    binding.progressBar3.setVisibility(View.GONE);

                    if (products != null) {
                        for (int i = 0; i < products.size(); i++) {
                            totalPrice = totalPrice + Integer.parseInt(products.get(i).getPrice());
                        }
                    }

                    String tPrice = String.valueOf(totalPrice);

                    binding.totalPrice.setText(tPrice + "تومان");

                } else {
                    Toast.makeText(Basket.this, "ok but ..." + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<ResponseProduct>> call, @NotNull Throwable t) {
                Toast.makeText(Basket.this, "oh  " + t, Toast.LENGTH_LONG).show();
            }
        });


    }

    private void RetrofitConfigBuy(String refId) {
        RetrofitBasket retrofit;
        ApiService apiService;

        retrofit = new RetrofitBasket();
        apiService = retrofit.getApiService();

        SharedPreferences sharedpreferences = getSharedPreferences("userNameShared", Context.MODE_PRIVATE);
        String username = sharedpreferences.getString("username","");
        String mobile = sharedpreferences.getString("mobile","");
        String id = sharedpreferences.getString("id","");

        RequestBuy requestBuy = new RequestBuy();
        if (id != null) {
            requestBuy.setCustomerId(Integer.parseInt(id));
        }
        requestBuy.setBilling(new Billing(mobile,"",username,"group2080@gmail.com"));
        requestBuy.setStatus("processing");

             List<LineItemsItem> lineitem = new ArrayList<>();
             for(int i = 0 ; i < Lists.basketClass.size() ; i++){
                 lineitem.add(new LineItemsItem(Lists.basketClass.get(i)));
             }

        requestBuy.setLineItems(lineitem);
        requestBuy.setPaymentMethod("WC_ZPal");
        requestBuy.setPaymentMethodTitle("پرداخت از اپلیکیشن");

            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
            String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

        requestBuy.setCustomerIpAddress(ipAddress);
        requestBuy.setTransactionId(refId);

        //send data for register a rezerve should be json object
        Gson g = new Gson();
        String json = g.toJson(requestBuy);
        JsonParser parser = new JsonParser();
        JsonObject jsonObject1 = (JsonObject) parser.parse(json);

        Log.i(TAG, "RetrofitConfigBuy: " + jsonObject1.toString());

        Call<RequestBuy> call = apiService.buyRequest(jsonObject1);
        call.enqueue(new Callback<RequestBuy>() {
            @Override
            public void onResponse(@NotNull Call<RequestBuy> call, @NotNull Response<RequestBuy> response) {

            }

            @Override
            public void onFailure(@NotNull Call<RequestBuy> call, @NotNull Throwable t) {

            }
        });

    }

}