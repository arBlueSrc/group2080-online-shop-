package com.appnita.digikala.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.appnita.digikala.databinding.FragmentHomeBinding;
import com.appnita.digikala.retrofit.RecyclerAdapter;
import com.appnita.digikala.retrofit.RecyclerObjectClass;
import com.appnita.digikala.retrofit.retrofit.ApiService;
import com.appnita.digikala.retrofit.retrofit.NewsRetrofit;
import com.appnita.digikala.retrofit.retrofit.RetrofitSetting;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    private Handler handler;
    private Runnable runnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater);

        //timer for event
        countDownStart();

        //retrofit setting
        RetrofitConfiguration();


        return binding.getRoot();
    }

    private void RetrofitConfiguration() {
        //server config
        RetrofitSetting retrofit = new RetrofitSetting("https://www.group2080.ir/api/");
        ApiService apiService = retrofit.getApiService();


        Call<NewsRetrofit> call = apiService.news(393);
        call.enqueue(new Callback<NewsRetrofit>() {
            @Override
            public void onResponse(Call<NewsRetrofit> call, Response<NewsRetrofit> response) {
                if (response.isSuccessful()) {
                    List<NewsRetrofit.posts> list = new ArrayList<>();
                    list = response.body().getNews();

                    List<RecyclerObjectClass> rvList = new ArrayList<>();

                    for (int i=0; i<list.size();i++){
                        RecyclerObjectClass rvOBJ = new RecyclerObjectClass();
                        rvOBJ.setTitle(list.get(i).getTitle());
                        rvOBJ.setContent(list.get(i).getContent());
                        rvOBJ.setImage(list.get(i).getThumbnail());
                        rvList.add(rvOBJ);
                    }

                    //recycler view
                    RecyclerViewConfiquration(rvList);
                } else {
                    Toast.makeText(getContext(), "failure", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsRetrofit> call, Throwable t) {
                Toast.makeText(getContext(), "failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void RecyclerViewConfiquration(List<RecyclerObjectClass> list) {
        RecyclerAdapter adapter = new RecyclerAdapter(getContext(),list);
        binding.rvNews.setAdapter(adapter);
    }

    public void countDownStart() {
        handler = new Handler();
        runnable = new Runnable() {
            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "yyyy-MM-dd");
                    // Please here set your event date//YYYY-MM-DD
                    Date futureDate = dateFormat.parse("2021-6-2");
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
                        binding.txtTimerDay.setText("" + String.format("%02d", days));
                        binding.txtTimerHour.setText("" + String.format("%02d", hours));
                        binding.txtTimerMinute.setText("" + String.format("%02d", minutes));
                        binding.txtTimerSecond.setText("" + String.format("%02d", seconds));
                    } else {
                        Toast.makeText(getContext(), "کنکور دادی رفت !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1000);
    }

}