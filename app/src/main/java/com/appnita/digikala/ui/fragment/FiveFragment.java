package com.appnita.digikala.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.PowerManager;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.appnita.digikala.BuyProductClassForRecycler;
import com.appnita.digikala.adapter.MyFilesAdapter;
import com.appnita.digikala.databinding.FragmentFiveBinding;
import com.appnita.digikala.retrofit.basket.RetrofitBasket;
import com.appnita.digikala.retrofit.pojoProducts.ResponseProduct;
import com.appnita.digikala.retrofit.retrofit.ApiService;
import com.appnita.digikala.test.BuyProduct;
import com.appnita.digikala.ui.LoginActivity;
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
    ProgressDialog mProgressDialog;

    FragmentFiveBinding binding;
    RecyclerViewSkeletonScreen skeletonScreen;
    MyFilesAdapter productAdapter;

    int counter;
    final static List<String> backList = new ArrayList<>();

    SharedPreferences sharedpreferences;

    boolean getKeys = false;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFiveBinding.inflate(inflater);
        // Inflate the layout for this fragment

        //
        String folder_main = "2080";

        File f = new File(Environment.getExternalStorageDirectory(), folder_main);
        if (!f.exists()) {
            f.mkdirs();
        }

        //check permission
        isReadStoragePermissionGranted();


        //userlogin
        binding.btnUserSetting.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), LoginActivity.class));
        });

        RetrofitConfig();

        binding.instagramBtn2.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/group2080_ir/"));
            startActivity(intent);
        });

        binding.telegramBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/GROUP2080"));
            startActivity(intent);
        });



        return binding.getRoot();
    }


    public void isReadStoragePermissionGranted() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                Toast.makeText(getContext(), "permission is granted !", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;
        private String name;

        public DownloadTask(Context context, String name) {
            this.context = context;
            this.name = name;
        }

        @SuppressLint("SdCardPath")
        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream("/sdcard/2080/" + name);

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            Toast.makeText(context, "" + progress[0], Toast.LENGTH_SHORT).show();
            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            mProgressDialog.dismiss();
            if (result != null)
                Toast.makeText(context, "مشکل در هنگام دانلود : " + result, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context, "دانلود فایل با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                productAdapter.notifyDataSetChanged();
        }
    }

    private void initialize(String url, String name) {


        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage("در حال دانلود ... ");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);

        // execute this when the downloader must be fired
        final DownloadTask downloadTask = new DownloadTask(getContext(), name);
        downloadTask.execute(url);

        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                downloadTask.cancel(true); //cancel the task
            }


        });
    }

    private void RetrofitConfig() {
        RetrofitBasket retrofit;
        ApiService apiService;

        retrofit = new RetrofitBasket();
        apiService = retrofit.getApiService();

        sharedpreferences = getContext().getSharedPreferences("userNameShared", Context.MODE_PRIVATE);
        String id = sharedpreferences.getString("id", "");

        if (!id.equals("")) {
            binding.imgBox.setVisibility(View.GONE);
            binding.notice.setVisibility(View.GONE);

            Call<List<BuyProduct>> call = apiService.getCustomerProduct(Integer.parseInt(id));
            call.enqueue(new Callback<List<BuyProduct>>() {
                @Override
                public void onResponse(Call<List<BuyProduct>> call, Response<List<BuyProduct>> response) {
                    if (response.isSuccessful()) {
                        List<BuyProduct> products = response.body();

                        List<Integer> proID = new ArrayList<>();
                        for (int i = 0; i < products.size(); i++) {
                            for (int j = 0; j < products.get(i).getLineItems().size(); j++) {
                                proID.add(products.get(i).getLineItems().get(j).getProductId());
                            }
                        }
                        getProductKey(proID, products);
                    } else {
                        Toast.makeText(getContext(), "ok but ..." + response.message(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<BuyProduct>> call, Throwable t) {
//                    skeletonScreen.hide();
                    Toast.makeText(getContext(), "oh  " + t, Toast.LENGTH_LONG).show();
                }
            });
        }
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
                    Log.i(TAG, "onResponse: " + product.get(0).getId());
                    List<BuyProductClassForRecycler> listCustomer;

                    for (int i = 0; i < product.size(); i++) {
                        backList.add(product.get(i).getDownloads().get(0).getId());
                    }

                    if (backList.size() != 0) {
                        counter = 0;
                        listCustomer = new ArrayList<>();
                        for (int i = 0; i < products.size(); i++) {
                            for (int j = 0; j < products.get(i).getLineItems().size(); j++) {
                                listCustomer.add(new BuyProductClassForRecycler(products.get(i).getOrderKey(),
                                        String.valueOf(product.get(counter).getId()),
                                        product.get(counter).getImages().get(0).getSrc(),
                                        products.get(i).getBilling().getEmail(),
                                        String.valueOf(backList.get(counter)),
                                        product.get(counter).getName(),
                                        product.get(counter).getDownloads().get(0).getName()));
                                counter++;
                            }
                        }
                        if (products.size() != 0) {
                            binding.notice.setVisibility(View.GONE);
                             productAdapter = new MyFilesAdapter(getContext(), listCustomer, new MyFilesAdapter.onClickListener() {
                                @Override
                                public void onDownload(BuyProductClassForRecycler file) {
                                    downloadZipFile(file);
                                }

                                @Override
                                public void onSeefile(BuyProductClassForRecycler file) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setDataAndType(Uri.parse("/sdcard/2080/"+file.getFileName()),"application/pdf");
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    try {
                                        startActivity(intent);
                                    } catch (ActivityNotFoundException e) {
                                        Toast.makeText(getContext(), "No Application available to view pdf", Toast.LENGTH_LONG).show();
                                    }

                                }
                            });
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

    private void downloadZipFile(BuyProductClassForRecycler file) {

        String urlDownload = "https://www.group2080.ir/?download_file=" + file.getProductID()
                + "&order=" + file.getOrderKey()
                + "&email=" + file.getEmail()
                + "&key=" + file.getKey();
        Log.i(TAG, "downloadZipFile: "+urlDownload);
        initialize(urlDownload, file.getFileName());

    }

}