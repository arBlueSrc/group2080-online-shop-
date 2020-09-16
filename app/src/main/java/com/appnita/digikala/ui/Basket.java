package com.appnita.digikala.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.appnita.digikala.R;
import com.appnita.digikala.databinding.ActivityBasketBinding;
import com.appnita.digikala.databinding.NavigationBinding;

public class Basket extends AppCompatActivity {

    ActivityBasketBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBasketBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    }
}