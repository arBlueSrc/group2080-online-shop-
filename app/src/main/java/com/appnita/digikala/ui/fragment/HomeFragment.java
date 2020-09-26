package com.appnita.digikala.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.appnita.digikala.R;
import com.appnita.digikala.databinding.FragmentHomeBinding;
import com.appnita.digikala.extraApp.Darsad;
import com.appnita.digikala.adapter.RecyclerAdapterClass;
import com.appnita.digikala.adapter.RecyclerAdapterConsult;
import com.appnita.digikala.adapter.RecyclerAdapterNews;
import com.appnita.digikala.ui.RecyclerObjectClass;
import com.appnita.digikala.retrofit.retrofit.ApiService;
import com.appnita.digikala.retrofit.retrofit.NewsRetrofit;
import com.appnita.digikala.retrofit.retrofit.RetrofitSetting;
import com.appnita.digikala.retrofit.room.PostsDao;
import com.appnita.digikala.retrofit.room.PostsDatabase;
import com.appnita.digikala.ui.slider.SliderItem;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;
import com.ethanhua.skeleton.Skeleton;

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
    RecyclerViewSkeletonScreen skeletonScreen1, skeletonScreen2, skeletonScreen3;


    PostsDao postsDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater);

        //slider


        //timer for event
        countDownStart();

        //skeleton
        skeleton();

        //Room
        postsDao = PostsDatabase.getDatabase(getContext()).postsDao();


        //image darsad
        binding.btnDarsad.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), Darsad.class));
        });

        RetrofitConfiguration();
        return binding.getRoot();
    }

    private void slider(List<SliderItem> sliderList) {

        for (int i = 0; i < sliderList.size(); i++) {
            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView.image(sliderList.get(i).getImage())
                    .setScaleType(BaseSliderView.ScaleType.FitCenterCrop);
            textSliderView.description(sliderList.get(i).getTitle());
            binding.slider.addSlider(textSliderView);
        }

    }

    private void skeleton() {
        RecyclerAdapterNews adapter = new RecyclerAdapterNews();
        skeletonScreen1 = Skeleton.bind(binding.rvNews)
                .adapter(adapter)
                .duration(2000)
                .color(R.color.skeleton)
                .load(R.layout.item_skeleton)
                .show();

        RecyclerAdapterConsult adapter2 = new RecyclerAdapterConsult();
        skeletonScreen2 = Skeleton.bind(binding.rvConsult)
                .adapter(adapter2)
                .duration(2000)
                .color(R.color.skeleton)
                .load(R.layout.item_skeleton)
                .show();

        RecyclerAdapterClass adapter3 = new RecyclerAdapterClass();
        skeletonScreen3 = Skeleton.bind(binding.rvClass)
                .adapter(adapter3)
                .duration(2000)
                .color(R.color.skeleton)
                .load(R.layout.item_skeleton)
                .show();
    }

    private void RetrofitConfiguration() {
        //server config
        RetrofitSetting retrofit = new RetrofitSetting("https://www.group2080.ir/api/");
        ApiService apiService = retrofit.getApiService();


        Call<NewsRetrofit> call = apiService.news();
        call.enqueue(new Callback<NewsRetrofit>() {
            @Override
            public void onResponse(Call<NewsRetrofit> call, Response<NewsRetrofit> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    List<NewsRetrofit.posts> list = response.body().getNews();



                    if (postsDao.getAllPosts().size() == 0) {
                        for (int i = list.size() - 1; i >= 0; i--) {//
                            RecyclerObjectClass rvOBJ = new RecyclerObjectClass();
                            rvOBJ.setTitle(list.get(i).getTitle());
                            rvOBJ.setContent(list.get(i).getContent());
                            rvOBJ.setImage(list.get(i).getThumbnail());
                            rvOBJ.setUrl(list.get(i).getUrl());
                            rvOBJ.setCategory(list.get(i).getCategories().get(0).getId());
                            postsDao.insert(rvOBJ);
                        }
                    }

                    Log.d("test1", "onResponse: "+list.get(list.size() - 1).getTitle());
                    Log.d("test2", "onResponse: "+postsDao.getAllPosts().get(postsDao.getAllPosts().size() - 1).getTitle());

                    if (!list.get(list.size() - 1).getTitle().equals
                            (postsDao.getAllPosts().get(postsDao.getAllPosts().size() - 1).getTitle())) {

                        postsDao.deleteall();
                        for (int i = list.size() - 1; i >= 0; i--) {
                            RecyclerObjectClass rvOBJ = new RecyclerObjectClass();
                            rvOBJ.setTitle(list.get(i).getTitle());
                            rvOBJ.setContent(list.get(i).getContent());
                            rvOBJ.setImage(list.get(i).getThumbnail());
                            rvOBJ.setUrl(list.get(i).getUrl());
                            rvOBJ.setCategory(list.get(i).getCategories().get(0).getId());
                            Log.d("categorys", "onResponse: " + list.get(i).getCategories().get(0).getId());
                            postsDao.insert(rvOBJ);
                        }
                    }


                    skeletonScreen3.hide();
                    //slider
                    List<SliderItem> list2 = new ArrayList<>();
                    for (int i = 0; i < postsDao.getCategoraizedPosts(398).size(); i++) {
                        list2.add(new SliderItem(postsDao.getCategoraizedPosts(398).get(i).getTitle()
                                , postsDao.getCategoraizedPosts(398).get(i).getImage()));
                    }
                    slider(list2);

                    RecyclerViewConfiqurationNews(postsDao.getCategoraizedPosts(393));
                    Log.d("categorys", "onResponse: "+postsDao.getCategoraizedPosts(398).size());
                    RecyclerViewConfiqurationConsult(postsDao.getCategoraizedPosts(388));
                    RecyclerViewConfiqurationClass(postsDao.getCategoraizedPosts(394));
                }
            }

            @Override
            public void onFailure(Call<NewsRetrofit> call, Throwable t) {
                skeletonScreen3.hide();
                RecyclerViewConfiqurationNews(postsDao.getCategoraizedPosts(393));
                RecyclerViewConfiqurationConsult(postsDao.getCategoraizedPosts(388));
                RecyclerViewConfiqurationClass(postsDao.getCategoraizedPosts(394));
            }

        });
    }

    private void RecyclerViewConfiqurationNews(List<RecyclerObjectClass> list) {
        RecyclerAdapterNews adapter = new RecyclerAdapterNews(getContext(), list);
        binding.rvNews.setAdapter(adapter);

    }

    private void RecyclerViewConfiqurationConsult(List<RecyclerObjectClass> list) {
        RecyclerAdapterConsult adapter = new RecyclerAdapterConsult(getContext(), list);
        binding.rvConsult.setAdapter(adapter);
    }

    private void RecyclerViewConfiqurationClass(List<RecyclerObjectClass> list) {
        RecyclerAdapterClass adapter = new RecyclerAdapterClass(getContext(), list);
        binding.rvClass.setAdapter(adapter);
    }

    public void countDownStart() {
        handler = new Handler();
        // Please here set your event date//YYYY-MM-DD
        Runnable runnable = new Runnable() {
            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "yyyy-MM-dd");
                    // Please here set your event date//YYYY-MM-DD
                    Date futureDate;
                    futureDate = dateFormat.parse("2021-6-2");
                    Date currentDate = new Date();
                    if (!currentDate.after(futureDate)) {
                        assert futureDate != null;
                        long diff = (futureDate.getTime() + (8 * 60 * 60 * 1000))
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