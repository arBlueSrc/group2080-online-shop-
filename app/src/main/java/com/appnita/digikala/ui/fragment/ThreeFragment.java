package com.appnita.digikala.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.appnita.digikala.R;
import com.appnita.digikala.databinding.FragmentThreeBinding;
import com.appnita.digikala.extraApp.Darsad;
import com.appnita.digikala.retrofit.basket.AdapterProducts;
import com.appnita.digikala.retrofit.basket.Products;
import com.appnita.digikala.retrofit.basket.RetrofitBasket;
import com.appnita.digikala.retrofit.retrofit.ApiService;
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThreeFragment extends Fragment {

    FragmentThreeBinding binding;
    RecyclerViewSkeletonScreen skeletonScreen;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentThreeBinding.inflate(inflater);

        binding.btnDarsad.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), Darsad.class));
        });

        AdapterProducts adapter = new AdapterProducts();
        skeletonScreen = Skeleton.bind(binding.rvProducts)
                .adapter(adapter)
                .load(R.layout.item_skeleton)
                .show();

        RetrofitConfig();

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private void RetrofitConfig() {
        RetrofitBasket retrofit;
        ApiService apiService;

        retrofit = new RetrofitBasket();
        apiService = retrofit.getApiService();

        Call<List<Products>> call = apiService.getProducts();
        call.enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                if (response.isSuccessful()) {
                    List<Products> products = response.body();

                    skeletonScreen.hide();
                    AdapterProducts productAdapter = new AdapterProducts(getContext(), products);
                    binding.rvProducts.setAdapter(productAdapter);

                } else {
                    Toast.makeText(getContext(), "ok but ..." + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                Toast.makeText(getContext(), "oh  " + t, Toast.LENGTH_LONG).show();
            }
        });
    }
}