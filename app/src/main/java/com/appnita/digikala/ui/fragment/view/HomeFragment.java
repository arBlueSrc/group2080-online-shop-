package com.appnita.digikala.ui.fragment.view;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.appnita.digikala.R;
import com.appnita.digikala.databinding.FragmentHomeBinding;
import com.appnita.digikala.retrofit.room.PostsDao;
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    private Handler handler;
    RecyclerViewSkeletonScreen skeletonScreen1, skeletonScreen2, skeletonScreen3;

    final static int OFF_SET = 700;


    PostsDao postsDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Toast.makeText(getContext(), "hi new page", Toast.LENGTH_SHORT).show();
        mpchart();

        Animation anim1 = AnimationUtils.loadAnimation(getContext() , R.anim.chart_anim);
        anim1.setDuration(700);
        anim1.setStartOffset(OFF_SET);
        binding.cardView3.setAnimation(anim1);

        Animation anim2 = AnimationUtils.loadAnimation(getContext() , R.anim.chart_anim);
        anim2.setDuration(1300);
        anim2.setStartOffset(OFF_SET);
        binding.cardView.setAnimation(anim2);
        binding.cardView2.setAnimation(anim2);
    }

    private void mpchart() {
        binding.chart.setBackgroundColor(Color.WHITE);

        // disable description text
        binding.chart.getDescription().setEnabled(false);

        // enable touch gestures
        binding.chart.setTouchEnabled(true);

        // set listeners
//        binding.chart.setOnChartValueSelectedListener(this);
        binding.chart.setDrawGridBackground(false);

        // enable scaling and dragging
        binding.chart.setDragEnabled(true);
        binding.chart.setScaleEnabled(true);
        // binding.chart.setScaleXEnabled(true);
        // binding.chart.setScaleYEnabled(true);

        // force pinch zoom along both axis
        binding.chart.setPinchZoom(true);

        XAxis xAxis;
        {   // // X-Axis Style // //
            xAxis = binding.chart.getXAxis();

            // vertical grid lines
            xAxis.enableGridDashedLine(10f, 10f, 0f);
        }

        YAxis yAxis;
        {   // // Y-Axis Style // //
            yAxis = binding.chart.getAxisLeft();

            // disable dual axis (only use LEFT axis)
            binding.chart.getAxisRight().setEnabled(false);

            // horizontal grid lines
            yAxis.enableGridDashedLine(10f, 10f, 0f);

            // axis range
            yAxis.setAxisMaximum(20f);
            yAxis.setAxisMinimum(0f);
        }


        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < 20; i++) {

            float val = (float) (Math.random() * 20);
            values.add(new Entry(i, val));
        }

        LineDataSet set1 = new LineDataSet(values, "DataSet 1");

        set1.setDrawIcons(false);

        // draw dashed line
        set1.enableDashedLine(10f, 5f, 0f);

        // black lines and points
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);

        // line thickness and point size
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);

        // draw points as solid circles
        set1.setDrawCircleHole(false);

        // customize legend entry
        set1.setFormLineWidth(1f);
        set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        set1.setFormSize(15.f);

        // text size of values
        set1.setValueTextSize(9f);

        // draw selection line as dashed
        set1.enableDashedHighlightLine(10f, 5f, 0f);

        // set the filled area
        set1.setDrawFilled(true);
        set1.setFillFormatter(new IFillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return binding.chart.getAxisLeft().getAxisMinimum();
            }
        });

        // set color of filled area
        if (Utils.getSDKInt() >= 18) {
            // drawables only supported on api level 18 and above
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.color.red);
            set1.setFillDrawable(drawable);
        } else {
            set1.setFillColor(Color.BLACK);
        }

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1); // add the data sets

        // create a data object with the data sets
        LineData data = new LineData(dataSets);

        // set data
        binding.chart.setData(data);

        Animation anim1 = AnimationUtils.loadAnimation(getContext() , R.anim.chart_anim);
        anim1.setDuration(1000);
        anim1.setStartOffset(OFF_SET);
        binding.chart.startAnimation(anim1);
//        binding.chart.
    }

//    private void RetrofitConfiguration() {
//        //server config
//        RetrofitSetting retrofit = new RetrofitSetting("https://www.group2080.ir/api/");
//        ApiService apiService = retrofit.getApiService();
//
//
//        Call<NewsRetrofit> call = apiService.news();
//        call.enqueue(new Callback<NewsRetrofit>() {
//            @Override
//            public void onResponse(Call<NewsRetrofit> call, Response<NewsRetrofit> response) {
//                if (response.isSuccessful()) {
//                    assert response.body() != null;
//                    List<NewsRetrofit.posts> list = response.body().getNews();
//
//
//
//                    if (postsDao.getAllPosts().size() == 0) {
//                        for (int i = list.size() - 1; i >= 0; i--) {//
//                            RecyclerObjectClass rvOBJ = new RecyclerObjectClass();
//                            rvOBJ.setTitle(list.get(i).getTitle());
//                            rvOBJ.setContent(list.get(i).getContent());
//                            rvOBJ.setImage(list.get(i).getThumbnail());
//                            rvOBJ.setUrl(list.get(i).getUrl());
//                            rvOBJ.setCategory(list.get(i).getCategories().get(0).getId());
//                            postsDao.insert(rvOBJ);
//                        }
//                    }
//
//                    Log.d("test1", "onResponse: "+list.get(list.size() - 1).getTitle());
//                    Log.d("test2", "onResponse: "+postsDao.getAllPosts().get(postsDao.getAllPosts().size() - 1).getTitle());
//
//                    if (!list.get(list.size() - 1).getTitle().equals
//                            (postsDao.getAllPosts().get(postsDao.getAllPosts().size() - 1).getTitle())) {
//
//                        postsDao.deleteall();
//                        for (int i = list.size() - 1; i >= 0; i--) {
//                            RecyclerObjectClass rvOBJ = new RecyclerObjectClass();
//                            rvOBJ.setTitle(list.get(i).getTitle());
//                            rvOBJ.setContent(list.get(i).getContent());
//                            rvOBJ.setImage(list.get(i).getThumbnail());
//                            rvOBJ.setUrl(list.get(i).getUrl());
//                            rvOBJ.setCategory(list.get(i).getCategories().get(0).getId());
//                            Log.d("categorys", "onResponse: " + list.get(i).getCategories().get(0).getId());
//                            postsDao.insert(rvOBJ);
//                        }
//                    }
//
//
//                    skeletonScreen3.hide();
//                    //slider
//                    List<SliderItem> list2 = new ArrayList<>();
//                    for (int i = 0; i < postsDao.getCategoraizedPosts(398).size(); i++) {
//                        list2.add(new SliderItem(postsDao.getCategoraizedPosts(398).get(i).getTitle()
//                                , postsDao.getCategoraizedPosts(398).get(i).getImage()));
//                    }
//                    slider(list2);
//
//                    RecyclerViewConfiqurationNews(postsDao.getCategoraizedPosts(393));
//                    Log.d("categorys", "onResponse: "+postsDao.getCategoraizedPosts(398).size());
//                    RecyclerViewConfiqurationConsult(postsDao.getCategoraizedPosts(388));
//                    RecyclerViewConfiqurationClass(postsDao.getCategoraizedPosts(394));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<NewsRetrofit> call, Throwable t) {
//                skeletonScreen3.hide();
//                RecyclerViewConfiqurationNews(postsDao.getCategoraizedPosts(393));
//                RecyclerViewConfiqurationConsult(postsDao.getCategoraizedPosts(388));
//                RecyclerViewConfiqurationClass(postsDao.getCategoraizedPosts(394));
//            }
//
//        });
//    }
//
//    private void RecyclerViewConfiqurationNews(List<RecyclerObjectClass> list) {
//        RecyclerAdapterNews adapter = new RecyclerAdapterNews(getContext(), list);
//        binding.rvNews.setAdapter(adapter);
//
//    }
//
//    private void RecyclerViewConfiqurationConsult(List<RecyclerObjectClass> list) {
//        RecyclerAdapterConsult adapter = new RecyclerAdapterConsult(getContext(), list);
//        binding.rvConsult.setAdapter(adapter);
//    }
//
//    private void RecyclerViewConfiqurationClass(List<RecyclerObjectClass> list) {
//        RecyclerAdapterClass adapter = new RecyclerAdapterClass(getContext(), list);
//        binding.rvClass.setAdapter(adapter);
//    }

//    public void countDownStart() {
//        handler = new Handler();
//        // Please here set your event date//YYYY-MM-DD
//        Runnable runnable = new Runnable() {
//            @SuppressLint({"DefaultLocale", "SetTextI18n"})
//            @Override
//            public void run() {
//                handler.postDelayed(this, 1000);
//                try {
//                    @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat(
//                            "yyyy-MM-dd");
//                    // Please here set your event date//YYYY-MM-DD
//                    Date futureDate;
//                    futureDate = dateFormat.parse("2021-6-2");
//                    Date currentDate = new Date();
//                    if (!currentDate.after(futureDate)) {
//                        assert futureDate != null;
//                        long diff = (futureDate.getTime() + (8 * 60 * 60 * 1000))
//                                - currentDate.getTime();
//                        long days = diff / (24 * 60 * 60 * 1000);
//                        diff -= days * (24 * 60 * 60 * 1000);
//                        long hours = diff / (60 * 60 * 1000);
//                        diff -= hours * (60 * 60 * 1000);
//                        long minutes = diff / (60 * 1000);
//                        diff -= minutes * (60 * 1000);
//                        long seconds = diff / 1000;
//                        binding.txtTimerDay.setText("" + String.format("%02d", days));
//                        binding.txtTimerHour.setText("" + String.format("%02d", hours));
//                        binding.txtTimerMinute.setText("" + String.format("%02d", minutes));
//                        binding.txtTimerSecond.setText("" + String.format("%02d", seconds));
//                    } else {
//                        Toast.makeText(getContext(), "کنکور دادی رفت !", Toast.LENGTH_SHORT).show();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        handler.postDelayed(runnable, 1000);
//    }


}