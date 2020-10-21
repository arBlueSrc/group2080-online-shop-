package com.appnita.digikala.ui.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.appnita.digikala.R;
import com.appnita.digikala.StopwatchService;
import com.appnita.digikala.databinding.FragmentHomeBinding;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    LineDataSet set1;
    ArrayList<Entry> values = new ArrayList<>();
    LineData data;

    SharedPreferences sharedPreferences;
    long section;
    float time;

    Entry e1,e2,e3,e4,e5,e6,e7,e8;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mpchart();

        firstTime();

        sharedPreferences = getContext().getSharedPreferences("time", MODE_PRIVATE);

        binding.buttonStart.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), StopwatchService.class);
            ContextCompat.startForegroundService(getContext(), intent);
            sharedPreferences.edit().putFloat("sectionTime1", time).apply();
        });

        binding.buttonStop.setOnClickListener(v -> {
            getContext().stopService(new Intent(getContext(), StopwatchService.class));

            updateChartData();
            mpchart();
        });

        binding.btnInfo.setOnClickListener(v -> {
            DialogFragment newFragment = ChartInfoFragment.newInstance();
            newFragment.show(getFragmentManager(), "dialog");
        });
    }

    private void updateChartData() {
        sharedPreferences.edit().putFloat("time", time).apply();
        sharedPreferences.edit().putFloat("sectionTime2", time).apply();

        section = (long) getContext().getSharedPreferences("time", MODE_PRIVATE).getFloat("sectionTime2", 0)
                - (long) getContext().getSharedPreferences("time", MODE_PRIVATE).getFloat("sectionTime1", 0);

        binding.chart.removeAllViews();
        set1.clear();
        data.clearValues();

        e1 = e2;
        sharedPreferences.edit().putInt("x1", (int) e1.getY()).apply();
        e2 = e3;
        sharedPreferences.edit().putInt("x2", (int) e2.getY()).apply();
        e3 = e4;
        sharedPreferences.edit().putInt("x3", (int) e3.getY()).apply();
        e4 = e5;
        sharedPreferences.edit().putInt("x4", (int) e4.getY()).apply();
        e5 = e6;
        sharedPreferences.edit().putInt("x5", (int) e5.getY()).apply();
        e6 = e7;
        sharedPreferences.edit().putInt("x6", (int) e6.getY()).apply();
        e7 = e8;
        sharedPreferences.edit().putInt("x7" , (int) e7.getY()).apply();

        e8 = new Entry(7, ((int) ((section/1000)/60)));
        sharedPreferences.edit().putInt("x8" , (int) e8.getY()).apply();
    }

    private final BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateGUI(intent);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        getContext().registerReceiver(br, new IntentFilter(StopwatchService.STOPWATCH_BR));
    }

    @Override
    public void onStop() {
        getContext().unregisterReceiver(br);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @SuppressLint("SetTextI18n")
    private void updateGUI(Intent intent) {
        if (intent.getExtras() != null) {
            String hours = intent.getStringExtra("hours");
            String minutes = intent.getStringExtra("minutes");
            String seconds = intent.getStringExtra("seconds");
            time = intent.getFloatExtra("time", 0);
            binding.textViewTimer.setText(hours + " : " + minutes + " : " + seconds);
        }
    }

    @SuppressLint("SetTextI18n")
    public void firstTime() {
        time = getContext().getSharedPreferences("time", MODE_PRIVATE).getFloat("time", 0);
        long secs = (long) (time / 1000) ;
        long mins = (long) ((time / 1000) / 60) ;
        long hrs = (long) (((time / 1000) / 60) / 60);

        secs = secs % 60;
        String seconds = String.valueOf(secs);
        if (secs == 0) {
            seconds = "00";
        }
        if (secs < 10 && secs > 0) {
            seconds = "0" + seconds;
        }

        /* Convert the minutes to String and format the String */
        mins = mins % 60;
        String minutes = String.valueOf(mins);
        if (mins == 0) {
            minutes = "00";
        }
        if (mins < 10 && mins > 0) {
            minutes = "0" + minutes;
        }

        /* Convert the hours to String and format the String */
        String hours = String.valueOf(hrs);
        if (hrs == 0) {
            hours = "00";
        }
        if (hrs < 10 && hrs > 0) {
            hours = "0" + hours;
        }
        binding.textViewTimer.setText(hours + " : " + minutes + " : " + seconds);
    }


    private void mpchart() {
        binding.chart.setBackgroundColor(Color.WHITE);

        // disable description text
        binding.chart.getDescription().setEnabled(false);

        // enable touch gestures
        binding.chart.setTouchEnabled(false);

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
        {
            // // Y-Axis Style // //
            yAxis = binding.chart.getAxisLeft();

            // disable dual axis (only use LEFT axis)
            binding.chart.getAxisRight().setEnabled(false);

            // horizontal grid lines
            yAxis.enableGridDashedLine(10f, 10f, 0f);

            // axis range
            yAxis.setAxisMaximum(180f);
            yAxis.setAxisMinimum(0f);
        }

        int x1 = getContext().getSharedPreferences("time", MODE_PRIVATE).getInt("x1", 0);
        e1 = new Entry(0, x1);
        int x2 = getContext().getSharedPreferences("time", MODE_PRIVATE).getInt("x2", 0);
        e2 = new Entry(1, x2);
        int x3 = getContext().getSharedPreferences("time", MODE_PRIVATE).getInt("x3", 0);
        e3 = new Entry(2, x3);
        int x4 = getContext().getSharedPreferences("time", MODE_PRIVATE).getInt("x4", 0);
        e4 = new Entry(3, x4);
        int x5 = getContext().getSharedPreferences("time", MODE_PRIVATE).getInt("x5", 0);
        e5 = new Entry(4, x5);
        int x6 = getContext().getSharedPreferences("time", MODE_PRIVATE).getInt("x6", 0);
        e6 = new Entry(5, x6);
        int x7 = getContext().getSharedPreferences("time", MODE_PRIVATE).getInt("x7", 0);
        e7 = new Entry(6, x7);
        int x8 = getContext().getSharedPreferences("time", MODE_PRIVATE).getInt("x8", 0);
        e8 = new Entry(7, x8);

        values.add(e1);
        values.add(e2);
        values.add(e3);
        values.add(e4);
        values.add(e5);
        values.add(e6);
        values.add(e7);
        values.add(e8);

        set1 = new LineDataSet(values, "آخرین مقاومت های مطالعاتی شما برای مطالعه :)");

        set1.setDrawIcons(false);

        // draw dashed line
        set1.enableDashedLine(10f, 5f, 0f);

        // black lines and points
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);

        // line thickness and point size
        set1.setLineWidth(0f);
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
        set1.setFillFormatter((dataSet, dataProvider) -> binding.chart.getAxisLeft().getAxisMinimum());

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
        data = new LineData(dataSets);

        // set data
        binding.chart.setData(data);

        binding.chart.animate().setDuration(2000);
        binding.chart.animate().start();

    }


}



