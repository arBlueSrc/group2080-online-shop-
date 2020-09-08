package com.appnita.digikala.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.NoCopySpan;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.appnita.digikala.R;
import com.appnita.digikala.retrofit.Articles;
import com.appnita.digikala.retrofit.retrofit.ApiService;
import com.appnita.digikala.retrofit.retrofit.ArticleRetrofit;
import com.appnita.digikala.retrofit.retrofit.RetrofitSetting;
import com.appnita.digikala.retrofit.retrofit.WooRetrofit;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView txtTimerDay, txtTimerHour, txtTimerMinute, txtTimerSecond, tvEvent, headerLogin, headerLogedIn;
    private Handler handler;
    private Runnable runnable;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    NavigationView navigationView;

    SharedPreferences sharedPreferences;


    RetrofitSetting retrofit;
    ApiService apiService;
    List<ArticleRetrofit> list = new ArrayList<>();

    List<Articles> lastArticles1 = new ArrayList<>();


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_major);

        txtTimerDay = findViewById(R.id.txtTimerDay);
        txtTimerHour = findViewById(R.id.txtTimerHour);
        txtTimerMinute = findViewById(R.id.txtTimerMinute);
        txtTimerSecond = findViewById(R.id.txtTimerSecond);
//        tvEvent = findViewById(R.id.tvhappyevent);

        toolbar = findViewById(R.id.toolbarmain);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);



        //navigation drawer
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle drawertoggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        drawertoggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(drawertoggle);
        drawertoggle.syncState();

        View header = navigationView.getHeaderView(0);
        headerLogin = header.findViewById(R.id.btn_login);
        headerLogedIn = header.findViewById(R.id.btn_loged_in);

        //shared preference
        sharedPreferences = getSharedPreferences("userNameShared", Context.MODE_PRIVATE);
        Toast.makeText(this, sharedPreferences.getString("username","nothing"), Toast.LENGTH_SHORT).show();
        if (sharedPreferences.getString("username", "no").equals("no")) {
            headerLogedIn.setVisibility(View.GONE);
            headerLogin.setVisibility(View.VISIBLE);
        } else {
            headerLogedIn.setText("0"+sharedPreferences.getString("username", "noname"));
            headerLogedIn.setVisibility(View.VISIBLE);
            headerLogin.setVisibility(View.GONE);
        }

        headerLogin.setOnClickListener(v -> {
                startActivityForResult(new Intent(MainActivity.this, Login.class),0);
        });

        //timer konkoor
        countDownStart();

        //server config
        retrofit = new RetrofitSetting("http://192.168.0.3/wordpress/wp-json/wc/v3/");
        apiService = retrofit.getApiService();

        String name = "ck_bdc9ddab522e630d2699593a7beb06e61e8b867e";
        String pass = "cs_62933284bfe4c94468f47f2f26fa0b347088399b";

        String base = name + ":" + pass;

        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        Call<List<WooRetrofit>> call = apiService.getLastArticle();
        call.enqueue(new Callback<List<WooRetrofit>>() {
            @Override
            public void onResponse(Call<List<WooRetrofit>> call, Response<List<WooRetrofit>> response) {
                if (response.isSuccessful()) {
//                ArticleRetrofit lastArticles = response.body();
//                for (int i=0 ; i<lastArticles.getSearch().size();i++){
//                    Articles articles1 = new Articles();
//                    articles1.setTitle(lastArticles.getSearch().get(i).getTitle());
//                    articles1.setContent(lastArticles.getSearch().get(i).getContent());
//                    articles1.setId(i);
//                    lastArticles1.add(articles1);
//                }
//                    RecyclerView recyclerView = findViewById(R.id.recycler1);
//                    AricleAdapter aricleAdapter = new AricleAdapter(MainActivity.this,lastArticles1);
//                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
//                    recyclerView.setAdapter(aricleAdapter);
                    WooRetrofit wooRetrofit = response.body().get(0);
                    wooRetrofit.setName(wooRetrofit.getName());
                    Toast.makeText(MainActivity.this, "ok" + wooRetrofit.getName(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "ok but ..." + response.body(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<WooRetrofit>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "shit" + t, Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (sharedPreferences.getString("username", "no").equals("no")) {
            headerLogedIn.setVisibility(View.GONE);
            headerLogin.setVisibility(View.VISIBLE);
        } else {
            headerLogin.setText(sharedPreferences.getString("username", "noname"));
            headerLogedIn.setVisibility(View.VISIBLE);
            headerLogin.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.site:
                Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.saved_voice:
                Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.ravandnama:
                Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.karsanj:
                Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void countDownStart() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "yyyy-MM-dd");
                    // Please here set your event date//YYYY-MM-DD
                    Date futureDate = dateFormat.parse("2020-12-2");
                    Date currentDate = new Date();
                    if (!currentDate.after(futureDate)) {
                        long diff = futureDate.getTime() + (8 * 60 * 60 * 1000)
                                - currentDate.getTime();
                        long days = diff / (24 * 60 * 60 * 1000);
                        diff -= days * (24 * 60 * 60 * 1000);
                        long hours = diff / (60 * 60 * 1000);
                        diff -= hours * (60 * 60 * 1000);
                        long minutes = diff / (60 * 1000);
                        diff -= minutes * (60 * 1000);
                        long seconds = diff / 1000;
                        txtTimerDay.setText("" + String.format("%02d", days));
                        txtTimerHour.setText("" + String.format("%02d", hours));
                        txtTimerMinute.setText("" + String.format("%02d", minutes));
                        txtTimerSecond.setText("" + String.format("%02d", seconds));
                    } else {
                        tvEvent.setVisibility(View.VISIBLE);
                        tvEvent.setText("The event started!");
//                        textViewGone();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1 * 1000);
    }

    public void textViewGone() {
//        findViewById(R.id.LinearLayout10).setVisibility(View.GONE);
//        findViewById(R.id.LinearLayout11).setVisibility(View.GONE);
//        findViewById(R.id.LinearLayout12).setVisibility(View.GONE);
//        findViewById(R.id.LinearLayout13).setVisibility(View.GONE);
    }
}