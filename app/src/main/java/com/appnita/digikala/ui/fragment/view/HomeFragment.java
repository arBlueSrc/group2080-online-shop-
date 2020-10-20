package com.appnita.digikala.ui.fragment.view;

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
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.appnita.digikala.R;
import com.appnita.digikala.StopwatchService;
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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    TextView textViewTimer = null;
    Button buttonStart, buttonStop;

    SharedPreferences sharedPreferences;

    float time;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        textViewTimer = view.findViewById(R.id.textViewTimer);
        buttonStart = view.findViewById(R.id.buttonStart);
        buttonStop = view.findViewById(R.id.buttonStop);

        sharedPreferences = getContext().getSharedPreferences("time",MODE_PRIVATE);

        buttonStart.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), StopwatchService.class);
            ContextCompat.startForegroundService(getContext(),intent);
        });

        buttonStop.setOnClickListener(v -> {
            getContext().stopService(new Intent(getContext(), StopwatchService.class));
            sharedPreferences.edit().putFloat("time" , time).apply();
//                textViewTimer.setText("00:00:00.0");
        });
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
            String milliseconds = intent.getStringExtra("milliseconds");
            time = intent.getFloatExtra("time",0);
            textViewTimer.setText(hours + " : " + minutes + " : " + seconds + " . " + milliseconds);
        }
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
        LineData data = new LineData(dataSets);

        // set data
        binding.chart.setData(data);

        Animation anim1 = AnimationUtils.loadAnimation(getContext(), R.anim.chart_anim);
        anim1.setDuration(1000);
        binding.chart.startAnimation(anim1);

//        binding.chart.
    }


}



