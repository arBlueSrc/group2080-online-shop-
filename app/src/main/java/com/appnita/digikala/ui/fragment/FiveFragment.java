package com.appnita.digikala.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;

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

import com.appnita.digikala.BuyProductClassForRecycler;
import com.appnita.digikala.adapter.MyFilesAdapter;
import com.appnita.digikala.R;
import com.appnita.digikala.databinding.FragmentFiveBinding;
import com.appnita.digikala.retrofit.basket.BuyProduct;
import com.appnita.digikala.retrofit.basket.RetrofitBasket;
import com.appnita.digikala.retrofit.pojoProducts.ResponseProduct;
import com.appnita.digikala.retrofit.retrofit.ApiService;
import com.appnita.digikala.ui.LoginActivity;
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


public class FiveFragment extends Fragment {

    private static final String TAG = "FiveFragment";
    DownloadZipFileTask downloadZipFileTask;

    FragmentFiveBinding binding;
    RecyclerViewSkeletonScreen skeletonScreen;

    int counter;
    final static List<String> backList = new ArrayList<>();

    boolean getKeys = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFiveBinding.inflate(inflater);
        // Inflate the layout for this fragment

        //userlogin
        binding.btnUserSetting.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), LoginActivity.class));
        });

        RetrofitConfig();
        return binding.getRoot();
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

                    List<Integer> proID = new ArrayList<>();
                    for (int i = 0; i < products.size(); i++) {
                        for (int j = 0; j < products.get(i).getLineItems().size(); j++) {
                            proID.add(Integer.valueOf(products.get(i).getLineItems().get(j).getProductId()));
                        }
                    }

                    getProductKey(proID, products);

                } else {
                    skeletonScreen.hide();
                    Toast.makeText(getContext(), "ok but ..." + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<BuyProduct>> call, Throwable t) {
                skeletonScreen.hide();
                Toast.makeText(getContext(), "oh  " + t, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getProductKey(List<Integer> a, List<BuyProduct> products) {
        RetrofitBasket retrofit;
        ApiService apiService;

        retrofit = new RetrofitBasket();
        apiService = retrofit.getApiService();

        Call<List<ResponseProduct>> call = apiService.getProductsBasket(a);
        call.enqueue(new Callback<List<ResponseProduct>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<ResponseProduct>> call, Response<List<ResponseProduct>> response) {
                if (response.isSuccessful()) {
                    getKeys = true;
                    List<ResponseProduct> product = response.body();
                    List<BuyProductClassForRecycler> listCustomer;

                    for (int i = 0; i < product.size(); i++) {
                        backList.add(product.get(i).getDownloads().get(0).getId());
                    }

                    if(backList.size()!=0) {
                        counter = 0;
                        listCustomer = new ArrayList<>();
                        for (int i = 0; i < products.size(); i++) {
                            for (int j = 0; j < products.get(i).getLineItems().size(); j++) {
                                listCustomer.add(new BuyProductClassForRecycler(products.get(i).getOrderKey(),
                                        products.get(i).getLineItems().get(j).getProductId(),
                                        products.get(0).getBilling().getEmail(),
                                        String.valueOf(backList.get(counter))));
                                counter++;
                            }
                        }
                        if (products.size() != 0) {
                            binding.notice.setVisibility(View.GONE);
                            MyFilesAdapter productAdapter = new MyFilesAdapter(getContext(), listCustomer);
                            binding.recyclerView.setAdapter(productAdapter);
                        } else {
                            binding.recyclerView.setVisibility(View.GONE);
                            binding.notice.setVisibility(View.VISIBLE);
                        }
                    }

                    Log.d("test list2 : ", backList.toString());

                } else {
                    Toast.makeText(getContext(), "ok but ..." + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ResponseProduct>> call, Throwable t) {
                Toast.makeText(getContext(), "oh shit  " + t, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void downloadZipFile() {

        ApiService downloadService = createService(ApiService.class, "https://www.group2080.ir/");
        Call<ResponseBody> call = downloadService.downloadFileByUrl("arashmirzaie.1997%40gmail.com",
                4838, "wc_order_gpnR9KV49XWKn", "af7a9926-8066-4270-8dec-de38cf94ea7b");

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Got the body for the file");

                    Toast.makeText(getContext(), "Downloading...", Toast.LENGTH_SHORT).show();

                    downloadZipFileTask = new DownloadZipFileTask();
                    downloadZipFileTask.execute(response.body());

                } else {
                    Log.d(TAG, "Connection failed " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public <T> T createService(Class<T> serviceClass, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(new OkHttpClient.Builder().build())
                .build();
        return retrofit.create(serviceClass);
    }

    private class DownloadZipFileTask extends AsyncTask<ResponseBody, Pair<Integer, Long>, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(ResponseBody... urls) {
            //Copy you logic to calculate progress and call
            saveToDisk(urls[0], "journaldev-project.pdf");
            return null;
        }

        protected void onProgressUpdate(Pair<Integer, Long>... progress) {

            Log.d("API123", progress[0].second + " ");

            if (progress[0].first == 100)
                Toast.makeText(getContext(), "File downloaded successfully", Toast.LENGTH_SHORT).show();


            if (progress[0].second > 0) {
                int currentProgress = (int) ((double) progress[0].first / (double) progress[0].second * 100);
//                progressBar.setProgress(currentProgress);
//
//                txtProgressPercent.setText("Progress " + currentProgress + "%");

            }

            if (progress[0].first == -1) {
                Toast.makeText(getContext(), "Download failed", Toast.LENGTH_SHORT).show();
            }

        }

        public void doProgress(Pair<Integer, Long> progressDetails) {
            publishProgress(progressDetails);
        }

        @Override
        protected void onPostExecute(String result) {

        }
    }

    private void saveToDisk(ResponseBody body, String filename) {
        try {

            File destinationFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filename);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(destinationFile);
                byte data[] = new byte[4096];
                int count;
                int progress = 0;
                long fileSize = body.contentLength();
                Log.d(TAG, "File Size=" + fileSize);
                while ((count = inputStream.read(data)) != -1) {
                    outputStream.write(data, 0, count);
                    progress += count;
                    Pair<Integer, Long> pairs = new Pair<>(progress, fileSize);
                    downloadZipFileTask.doProgress(pairs);
                    Log.d(TAG, "Progress: " + progress + "/" + fileSize + " >>>> " + (float) progress / fileSize);
                }

                outputStream.flush();

                Log.d(TAG, destinationFile.getParent());
                Pair<Integer, Long> pairs = new Pair<>(100, 100L);
                downloadZipFileTask.doProgress(pairs);
                return;
            } catch (IOException e) {
                e.printStackTrace();
                Pair<Integer, Long> pairs = new Pair<>(-1, Long.valueOf(-1));
                downloadZipFileTask.doProgress(pairs);
                Log.d(TAG, "Failed to save the file!");
                return;
            } finally {
                if (inputStream != null) inputStream.close();
                if (outputStream != null) outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "Failed to save the file!");
            return;
        }
    }

    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
            }
        } else if (ContextCompat.checkSelfPermission(getContext(), permission) == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(getContext(), "Permission was denied", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(getContext(), permissions[0]) == PackageManager.PERMISSION_GRANTED) {

            if (requestCode == 101)
                Toast.makeText(getContext(), "Permission granted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }
}