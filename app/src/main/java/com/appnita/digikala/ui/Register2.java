package com.appnita.digikala.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.appnita.digikala.R;
import com.appnita.digikala.retrofit.retrofit.ApiService;
import com.appnita.digikala.retrofit.retrofit.RegisterCondition;
import com.appnita.digikala.retrofit.retrofit.RetrofitSetting;
import com.appnita.digikala.retrofit.retrofit.RetrofitSettingRegister;
import com.appnita.digikala.retrofit.retrofit.Verifycode;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register2 extends AppCompatActivity {

    EditText otp;
    Button verify;

    ApiService apiService;
    RetrofitSetting retrofit;
    RetrofitSettingRegister retrofitSettingRegister;

    String otpCode;
    String phone;
    String username;
    String password;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        otp = findViewById(R.id.edt_opt);
        verify = findViewById(R.id.btn_verify_otp);

        phone = getIntent().getStringExtra("phone");
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");

        verify.setOnClickListener(V -> {
            //server config

            progressDialog = ProgressDialog.show(this, "در حال ارسال اطلاعات", "لطفا کمی صبر کنید ....");

            retrofit = new RetrofitSetting("https://www.group2080.ir/wp-json/digits/v1/");
            apiService = retrofit.getApiService();

            otpCode = otp.getText().toString().trim();


            Call<Verifycode> call = apiService.verifyOtp("+98", phone, "register", otpCode);
            call.enqueue(new Callback<Verifycode>() {
                @Override
                public void onResponse(Call<Verifycode> call, Response<Verifycode> response) {
                    if (response.isSuccessful()) {
                        Verifycode verifycode = response.body();
                        if (verifycode.getCode().equals("1")) {
                            RegClass();
                        } else {
                            progressDialog.cancel();
                            Toast.makeText(Register2.this, "کد وارد شده صحیح نمی باشد.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        progressDialog.cancel();
                        Toast.makeText(Register2.this, "مشکلی پیش آمده است. دوباره تلاش کنید.", Toast.LENGTH_SHORT).show();
                    }
                }


                @Override
                public void onFailure(Call<Verifycode> call, Throwable t) {
                    progressDialog.cancel();
                    Toast.makeText(Register2.this, "مشکلی پیش آمده است. دوباره تلاش کنید.", Toast.LENGTH_SHORT).show();
                }
            });
        });

    }

    public void RegClass() {
        retrofitSettingRegister = new RetrofitSettingRegister("https://www.group2080.ir/wp-json/digits/v1/");
        apiService = retrofitSettingRegister.getApiService();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("digits_reg_name",username)
                .addFormDataPart("digits_reg_countrycode","+98")
                .addFormDataPart("digits_reg_mobile",phone)
                .addFormDataPart("digits_reg_password",password)
                .addFormDataPart("digits_reg_username",phone)
                .addFormDataPart("otp",otpCode)
                .build();

        Call<RegisterCondition> call = apiService.creat(requestBody);
        call.enqueue(new Callback<RegisterCondition>() {
            @Override
            public void onResponse(Call<RegisterCondition> call, Response<RegisterCondition> response) {
                if (response.isSuccessful()) {
                    RegisterCondition registerCondition = response.body();
                    if (registerCondition.getSuccess().equals("true")) {
                        progressDialog.cancel();
                        Toast.makeText(Register2.this, "ثبت نام با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences = getSharedPreferences("userNameShared", Context.MODE_PRIVATE);
                        sharedPreferences.edit().putString("username",username).apply();
                        sharedPreferences.edit().putString("mobile",phone).apply();
                        sharedPreferences.edit().putString("id",response.body().getData().getUserId()).apply();
                        startActivity(new Intent(Register2.this, MainActivity.class));
                    } else {
                        progressDialog.cancel();
                        Toast.makeText(Register2.this, "" + registerCondition.getData().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.cancel();
                    Toast.makeText(Register2.this, "مشکلی پیش آمده است. دوباره تلاش کنید.", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<RegisterCondition> call, Throwable t) {
                Toast.makeText(Register2.this, "shit" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
