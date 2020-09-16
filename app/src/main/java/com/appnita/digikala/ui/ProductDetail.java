package com.appnita.digikala.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.appnita.digikala.BasketClass;
import com.appnita.digikala.Lists;
import com.appnita.digikala.R;
import com.appnita.digikala.databinding.LayoutProductDetailsBinding;
import com.squareup.picasso.Picasso;

public class ProductDetail extends AppCompatActivity {

    LayoutProductDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = LayoutProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        binding.title.setText(intent.getStringExtra("title"));
        binding.content.setText(intent.getStringExtra("title"));
        Picasso.get()
                .load(intent.getStringExtra("image"))
                .error(R.drawable.error)
                .resize(1000, 1000)
                .onlyScaleDown()
                .into(binding.image);

        String id = intent.getStringExtra("id");

        binding.btnAddBasket.setOnClickListener(v -> {
            Lists.basketClass.add(new BasketClass(id));
        });
    }
}