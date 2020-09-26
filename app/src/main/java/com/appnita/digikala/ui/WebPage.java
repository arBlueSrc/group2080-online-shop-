package com.appnita.digikala.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.webkit.ClientCertRequest;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.appnita.digikala.databinding.ActivityWebViewBinding;

import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static android.webkit.WebView.RENDERER_PRIORITY_BOUND;

public class WebPage extends AppCompatActivity {

    ActivityWebViewBinding binding;

//    @SuppressLint("SetJavaScriptEnabled")
//    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();

        WebSettings webSettings = binding.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
//
//        if (18 < Build.VERSION.SDK_INT ){
//            //18 = JellyBean MR2, KITKAT=19
//            binding.webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//        }
//        if (Build.VERSION.SDK_INT >= 19) {
//            binding.webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        }



        binding.webView.setWebViewClient(new mWebViewClient());

        binding.webView.loadUrl(intent.getStringExtra("url"));

//        binding.webView.loadUrl("Https://www.google.com");
//        Log.i("url is :", intent.getStringExtra("url"));

    }

    private class mWebViewClient extends WebViewClient {


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        boolean timeout;

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    timeout = true;

                    try {
                        Thread.sleep(15000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(timeout) {
                        // do what you want
                    }
                }
            }).start();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            timeout = false;
        }

        //        @Override
//        public void onReceivedSslError(android.webkit.WebView view, SslErrorHandler handler, SslError error) {
//            handler.proceed(); // Ignore SSL certificate errors
//        }
//
//
//        @RequiresApi(api = Build.VERSION_CODES.M)
//        @Override
//        public void onReceivedError(android.webkit.WebView view, WebResourceRequest request, WebResourceError error) {
//            Toast.makeText(WebView.this, "Your Internet Connection May not be active Or " + error.getDescription(), Toast.LENGTH_LONG).show();
//        }
    }
}
