package com.appnita.digikala.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.appnita.digikala.databinding.ActivityLoginBinding;
import com.appnita.digikala.retrofit.basket.RetrofitBasket;
import com.appnita.digikala.retrofit.pojoid.ResponseId;
import com.appnita.digikala.retrofit.retrofit.ApiService;
import com.appnita.digikala.retrofit.retrofit.LoginRetrofit;
import com.appnita.digikala.retrofit.retrofit.RetrofitSettingRegister;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    SharedPreferences sharedPreferences;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("userNameShared", Context.MODE_PRIVATE);

        binding.btnPreRegister.setOnClickListener(V -> startActivity(new Intent(LoginActivity.this, Register.class)));

        binding.btnLogin.setOnClickListener(v -> {
            ApiService apiService;
            RetrofitSettingRegister retrofitSettingRegister;

            dialog = ProgressDialog.show(LoginActivity.this, "در حال بررسی",
                    "لطفا صبر کنید ...", true);

            retrofitSettingRegister = new RetrofitSettingRegister("https://www.group2080.ir/wp-json/digits/v1/");
            apiService = retrofitSettingRegister.getApiService();

            String user = binding.edtPhone.getText().toString().trim();
            String pass = binding.edtPassword.getText().toString().trim();

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("user", user)
                    .addFormDataPart("countrycode", "+98")
                    .addFormDataPart("password", pass)
                    .build();

            Call<LoginRetrofit> call = apiService.login(requestBody);
            call.enqueue(new Callback<LoginRetrofit>() {
                @Override
                public void onResponse(@NotNull Call<LoginRetrofit> call, @NotNull Response<LoginRetrofit> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getSuccess().equals("true")) {

                                getid();

                            } else {
                                dialog.cancel();
                                Toast.makeText(LoginActivity.this, "شماره موبایل یا رمز نادرست است. دوباره تلاش کنید.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<LoginRetrofit> call, @NotNull Throwable t) {
                    dialog.cancel();
                    Toast.makeText(LoginActivity.this, "اتصال خود با اینترنت را بررسی کنید.", Toast.LENGTH_SHORT).show();
                }
            });
        });

    }

    private void getid() {
        RetrofitBasket retrofitSettingRegister = new RetrofitBasket();
        ApiService apiService = retrofitSettingRegister.getApiService();

        String user = binding.edtPhone.getText().toString().trim();

        Call<List<ResponseId>> call = apiService.getid(user);
        call.enqueue(new Callback<List<ResponseId>>() {
            @Override
            public void onResponse(@NotNull Call<List<ResponseId>> call, @NotNull Response<List<ResponseId>> response) {
                if (response.isSuccessful()) {
                    SharedPreferences sharedPreferences = getSharedPreferences("userNameShared", Context.MODE_PRIVATE);
                    if (response.body() != null) {
                        sharedPreferences.edit().putString("username", response.body().get(0).getFirstName()).apply();
                        sharedPreferences.edit().putString("mobile", response.body().get(0).getUsername()).apply();
                        sharedPreferences.edit().putString("id", String.valueOf(response.body().get(0).getId())).apply();
                    }

                    Log.i("arash", "onResponse: " + sharedPreferences.getString("id", "nothing"));

                    dialog.cancel();

                    Toast.makeText(LoginActivity.this, "ورود با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_CANCELED, returnIntent);
                    finish();
                }else{
                    Log.i("arash2", "onResponse: "+response.message() + response.toString());
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<ResponseId>> call, @NotNull Throwable t) {
                Toast.makeText(LoginActivity.this, "اتصال خود با اینترنت را بررسی کنید.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}