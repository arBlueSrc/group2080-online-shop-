package com.appnita.digikala.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.appnita.digikala.databinding.ActivityLoginBinding;
import com.appnita.digikala.retrofit.retrofit.ApiService;
import com.appnita.digikala.retrofit.retrofit.LoginRetrofit;
import com.appnita.digikala.retrofit.retrofit.RetrofitSettingRegister;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    ActivityLoginBinding binding;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("userNameShared", Context.MODE_PRIVATE);

        binding.btnPreRegister.setOnClickListener(V -> {
            startActivity(new Intent(Login.this, Register.class));
        });

        binding.btnLogin.setOnClickListener(v -> {
            ApiService apiService;
            RetrofitSettingRegister retrofitSettingRegister;

            ProgressDialog dialog = ProgressDialog.show(Login.this, "در حال بررسی",
                    "لطفا صبر کنید ...", true);

            retrofitSettingRegister = new RetrofitSettingRegister("https://www.group2080.ir/wp-json/digits/v1/");
            apiService = retrofitSettingRegister.getApiService();

            String user = binding.edtPhone.getText().toString().trim();
            String pass = binding.edtPassword.getText().toString().trim();

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("user",user)
                    .addFormDataPart("countrycode","+98")
                    .addFormDataPart("password",pass)
                    .build();

            Call<LoginRetrofit> call = apiService.login(requestBody);
            call.enqueue(new Callback<LoginRetrofit>() {
                @Override
                public void onResponse(Call<LoginRetrofit> call, Response<LoginRetrofit> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getSuccess().equals("true")) {
                            dialog.cancel();
                            Toast.makeText(Login.this, "ورود با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                            sharedPreferences.edit().putString("username",user).apply();
                            Intent returnIntent = new Intent();
                            setResult(Activity.RESULT_CANCELED, returnIntent);
                            finish();
                        } else {
                            dialog.cancel();
                            Toast.makeText(Login.this, "شماره موبایل یا رمز نادرست است. دوباره تلاش کنید.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginRetrofit> call, Throwable t) {
                    dialog.cancel();
                    Toast.makeText(Login.this, "اتصال خود با اینترنت را بررسی کنید.", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}