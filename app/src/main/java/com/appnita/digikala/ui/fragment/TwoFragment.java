package com.appnita.digikala.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appnita.digikala.R;
import com.appnita.digikala.adapter.AdapterStory;
import com.appnita.digikala.databinding.FragmentThreeBinding;
import com.appnita.digikala.databinding.FragmentTwoBinding;
import com.appnita.digikala.retrofit.pojoPosts.ResponsePosts;
import com.appnita.digikala.retrofit.retrofit.ApiService;
import com.appnita.digikala.retrofit.retrofit.NewsRetrofit;
import com.appnita.digikala.retrofit.retrofit.RetrofitSetting;
import com.appnita.digikala.ui.MainActivity;
import com.appnita.digikala.ui.RecyclerObjectClass;
import com.appnita.digikala.ui.slider.SliderItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TwoFragment extends Fragment {

    FragmentTwoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTwoBinding.inflate(inflater);

        if (ContextCompat.checkSelfPermission(getContext() ,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},0);
        }

        RetrofitConfiguration();

        binding.btnCall.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:+989226762312"));
            startActivity(callIntent);
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private void RetrofitConfiguration() {
        //server config
        RetrofitSetting retrofit = new RetrofitSetting("https://www.group2080.ir/api/");
        ApiService apiService = retrofit.getApiService();


        Call<ResponsePosts> call = apiService.getPostsCategory(402);
        call.enqueue(new Callback<ResponsePosts>() {
            @Override
            public void onResponse(Call<ResponsePosts> call, Response<ResponsePosts> response) {
                if (response.isSuccessful()) {
                    AdapterStory adapter = new AdapterStory(response.body().getPosts(),getContext());
                    binding.rvStory.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ResponsePosts> call, Throwable t) {

            }
        });
    }
}