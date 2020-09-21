package com.appnita.digikala.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.appnita.digikala.MyFilesAdapter;
import com.appnita.digikala.R;
import com.appnita.digikala.databinding.FragmentFiveBinding;
import com.appnita.digikala.retrofit.basket.BuyProduct;
import com.appnita.digikala.retrofit.basket.RetrofitBasket;
import com.appnita.digikala.retrofit.retrofit.ApiService;
import com.appnita.digikala.ui.MainActivity;
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;
import com.ethanhua.skeleton.Skeleton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.ContentValues.TAG;
import static okhttp3.OkHttpClient.*;


public class FiveFragment extends Fragment {

    FragmentFiveBinding binding;
    RecyclerViewSkeletonScreen skeletonScreen;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFiveBinding.inflate(inflater);
        // Inflate the layout for this fragment

        MyFilesAdapter adapter = new MyFilesAdapter();
        skeletonScreen = Skeleton.bind(binding.recyclerView)
                .adapter(adapter)
                .load(R.layout.item_skeleton)
                .show();

        RetrofitConfig();
        isStoragePermissionGranted();
        return binding.getRoot();
    }


    private void isStoragePermissionGranted() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    100);

        } else {
//            DownloadPDF();
//            downloadZipFile();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                DownloadPDF();
//                downloadZipFile();
            } else {
                Toast.makeText(getContext(), "toro khoda persmission bede!!!", Toast.LENGTH_SHORT).show();
                isStoragePermissionGranted();
            }
        }

    }

    private void RetrofitConfig() {
        RetrofitBasket retrofit;
        ApiService apiService;

        retrofit = new RetrofitBasket();
        apiService = retrofit.getApiService();

        Call<List<BuyProduct>> call = apiService.getCustomerProduct(472);
        call.enqueue(new Callback<List<BuyProduct>>() {
            @Override
            public void onResponse(Call<List<BuyProduct>> call, Response<List<BuyProduct>> response) {
                if (response.isSuccessful()) {
                    List<BuyProduct> products = response.body();

                    List<BuyProduct.ProductId> listCustomer = new ArrayList<>();
                    for (int i = 0; i < products.size(); i++) {
                        listCustomer.addAll(products.get(i).getLineItems());
                    }

                    Log.d("test list customer", "onResponse: " + products.size());
//                    skeletonScreen.hide();
                    if (products.size() != 0) {
                        binding.notice.setVisibility(View.GONE);
                        MyFilesAdapter productAdapter = new MyFilesAdapter(getContext(), listCustomer);
                        binding.recyclerView.setAdapter(productAdapter);
                    } else {
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.notice.setVisibility(View.VISIBLE);
                    }

                } else {
                    Toast.makeText(getContext(), "ok but ..." + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<BuyProduct>> call, Throwable t) {
                Toast.makeText(getContext(), "oh  " + t, Toast.LENGTH_LONG).show();
            }
        });
    }


}