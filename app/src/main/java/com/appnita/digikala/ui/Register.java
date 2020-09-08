package com.appnita.digikala.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.appnita.digikala.R;
import com.appnita.digikala.retrofit.retrofit.ApiService;
import com.appnita.digikala.retrofit.retrofit.RetrofitSetting;
import com.appnita.digikala.retrofit.retrofit.Verifycode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    private EditText username, password, mobile;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.edt_username);
        password = findViewById(R.id.edt_password);
        mobile = findViewById(R.id.edt_mobile);

        register = findViewById(R.id.btn_register_1);

        register.setOnClickListener(v -> {

            ApiService apiService;
            RetrofitSetting retrofit;

            ProgressDialog dialog = ProgressDialog.show(Register.this, "در حال ارسال اطلاعات",
                    "لطفا صبر کنید ...", true);

            //server config
            retrofit = new RetrofitSetting("https://www.group2080.ir/wp-json/digits/v1/");
            apiService = retrofit.getApiService();

            String uName = username.getText().toString().trim();
            String pass = password.getText().toString().trim();
            String phone = mobile.getText().toString().trim();


            Call<Verifycode> call = apiService.sendOtp("+98",phone,"register",uName);
            call.enqueue(new Callback<Verifycode>() {
                @Override
                public void onResponse(Call<Verifycode> call, Response<Verifycode> response) {
                    if (response.isSuccessful()) {
                        dialog.cancel();
                        Intent intent = new Intent(Register.this, Register2.class);
                        intent.putExtra("phone",phone);
                        intent.putExtra("username",uName);
                        intent.putExtra("password",pass);
                        startActivity(intent);
                    } else {
                        dialog.cancel();
                        Toast.makeText(Register.this, "مشکلی پیش آمده ....", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Verifycode> call, Throwable t) {
                    dialog.cancel();
                    Toast.makeText(Register.this, "لطفا اتصال خود با اینترنت را بررسی کنید.", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}