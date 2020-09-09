package com.appnita.digikala.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.appnita.digikala.R;
import com.appnita.digikala.databinding.ActivityMainMajorBinding;
import com.appnita.digikala.retrofit.retrofit.ApiService;
import com.appnita.digikala.retrofit.retrofit.RetrofitSetting;
import com.appnita.digikala.retrofit.retrofit.WooRetrofit;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView headerLogin, headerLogedIn;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    NavigationView navigationView;

    SharedPreferences sharedPreferences;

    RetrofitSetting retrofit;
    ApiService apiService;

    ActivityMainMajorBinding binding;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainMajorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbarmain);
        setSupportActionBar(toolbar);

//        drawerLayout = findViewById(R.id.drawer_layout);
//        navigationView = findViewById(R.id.navigation_view);

        //bottom navigation
        SetupBottomNavigation();

        //toolbar
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //shared preference
        SharedPreferencesSetting();

        //retrofit config
        RerofitSetting();


    }

    private void SetupBottomNavigation() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_container);
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navHostFragment.getNavController());

    }

    private void RerofitSetting() {
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

    private void SharedPreferencesSetting() {
        sharedPreferences = getSharedPreferences("userNameShared", Context.MODE_PRIVATE);
        Toast.makeText(this, sharedPreferences.getString("username", "nothing"), Toast.LENGTH_SHORT).show();

        if (sharedPreferences.getString("username", "no").equals("no")) {
//            headerLogedIn.setVisibility(View.GONE);
//            headerLogin.setVisibility(View.VISIBLE);
        } else {
//            headerLogedIn.setText("0" + sharedPreferences.getString("username", "noname"));
//            headerLogedIn.setVisibility(View.VISIBLE);
//            headerLogin.setVisibility(View.GONE);
        }

//        headerLogin.setOnClickListener(v -> {
//            startActivityForResult(new Intent(MainActivity.this, Login.class), 0);
//        });
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

}