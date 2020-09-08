package com.appnita.digikala.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.appnita.digikala.R;
import com.appnita.digikala.databinding.FragmentHomeBinding;

import java.text.SimpleDateFormat;
import java.util.Date;


public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    private Handler handler;
    private Runnable runnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater);

        countDownStart();


        return binding.getRoot();
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