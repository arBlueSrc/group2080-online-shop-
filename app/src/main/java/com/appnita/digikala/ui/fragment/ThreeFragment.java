package com.appnita.digikala.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.appnita.digikala.R;
import com.appnita.digikala.databinding.FragmentThreeBinding;
import com.appnita.digikala.retrofit.basket.AdapterProducts;
import com.appnita.digikala.retrofit.basket.RetrofitBasket;
import com.appnita.digikala.retrofit.pojoProducts.ResponseProduct;
import com.appnita.digikala.retrofit.retrofit.ApiService;
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;
import com.ethanhua.skeleton.Skeleton;

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

        Call<List<ResponseProduct>> call = apiService.getProducts();
        call.enqueue(new Callback<List<ResponseProduct>>() {
            @Override
            public void onResponse(Call<List<ResponseProduct>> call, Response<List<ResponseProduct>> response) {
                if (response.isSuccessful()) {
                    List<ResponseProduct> products = response.body();

                    skeletonScreen.hide();
                    AdapterProducts productAdapter = new AdapterProducts(getContext(), products);
                    binding.rvProducts.setAdapter(productAdapter);

                } else {
                    Toast.makeText(getContext(), "ok but ..." + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ResponseProduct>> call, Throwable t) {
                Toast.makeText(getContext(), "oh  " + t, Toast.LENGTH_LONG).show();
            }
        });
    }
}