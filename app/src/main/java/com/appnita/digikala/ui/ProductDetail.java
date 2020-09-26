package com.appnita.digikala.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.appnita.digikala.retrofit.basket.BasketClass;
import com.appnita.digikala.Lists;
import com.appnita.digikala.R;
import com.appnita.digikala.databinding.LayoutProductDetailsBinding;
import com.squareup.picasso.Picasso;

public class ProductDetail extends AppCompatActivity {

    LayoutProductDetailsBinding binding;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = LayoutProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        binding.title.setText(intent.getStringExtra("title"));
        binding.content.setText(intent.getStringExtra("title"));
        Picasso.with(this)
                .load(intent.getStringExtra("image"))
                .error(R.drawable.error)
                .resize(1000, 1000)
                .into(binding.image);
        binding.detailPrice.setText(intent.getStringExtra("price")+" تومان");

        int id = intent.getIntExtra("id",0);

        binding.btnAddBasket.setOnClickListener(v -> {
            Lists.basketClass.add(new BasketClass(id));
            Toast.makeText(this, "به سبد شما افزوده شد", Toast.LENGTH_SHORT).show();
        });
    }
}