package com.appnita.digikala.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.appnita.digikala.R;
import com.appnita.digikala.databinding.ActivityMainMajorBinding;
import com.appnita.digikala.retrofit.retrofit.ApiService;
import com.appnita.digikala.retrofit.retrofit.NewsRetrofit;
import com.appnita.digikala.retrofit.retrofit.RetrofitSetting;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView headerLogin, headerLogedIn;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    NavigationView navigationView;

    private static final String CHANNEL_ID = "channel_id";

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

        //bottom navigation
        SetupBottomNavigation();

        //shared preference
        SharedPreferencesSetting();

        //notification
        Notification();

        //float action button
        binding.floatingActionButton.setOnClickListener(v -> {
            startActivity(new Intent(this,Basket.class));
        });


    }

    private void SetupBottomNavigation() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_container);
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navHostFragment.getNavController());

    }

    private void SharedPreferencesSetting() {
        sharedPreferences = getSharedPreferences("userNameShared", Context.MODE_PRIVATE);
        Toast.makeText(this, sharedPreferences.getString("username", "nothing"), Toast.LENGTH_SHORT).show();

        if (sharedPreferences.getString("username", "no").equals("no")) {

        } else {

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (sharedPreferences.getString("username", "no").equals("no")) {

        } else {

        }
    }

    public void Notification() {
        //server config
        RetrofitSetting retrofit = new RetrofitSetting("https://www.group2080.ir/api/");
        ApiService apiService = retrofit.getApiService();


        Call<NewsRetrofit> call = apiService.notification(396);
        call.enqueue(new Callback<NewsRetrofit>() {
            @Override
            public void onResponse(Call<NewsRetrofit> call, Response<NewsRetrofit> response) {
                if (response.isSuccessful()) {
                    List<NewsRetrofit.posts> list = new ArrayList<>();
                    list = response.body().getNews();

                    String sourceContent = list.get(0).getContent();
                    Spanned finalContent;

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        finalContent = Html.fromHtml(sourceContent, Html.FROM_HTML_MODE_COMPACT);
                    } else {
                        finalContent = Html.fromHtml(sourceContent);
                    }

                    //region notification
                    createNotificationChannel();

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_notification)
                            .setContentTitle(list.get(0).getTitle())
                            .setContentText(finalContent)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                    //show the notification
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);

                    // notificationId is a unique int for each notification that you must define
                    notificationManager.notify(0, builder.build());

                } else {
                    Toast.makeText(MainActivity.this, "failure", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsRetrofit> call, Throwable t) {
                Toast.makeText(MainActivity.this, "failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}