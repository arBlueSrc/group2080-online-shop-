package com.appnita.digikala.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appnita.digikala.R;
import com.appnita.digikala.databinding.FragmentThreeBinding;
import com.appnita.digikala.databinding.FragmentTwoBinding;
import com.appnita.digikala.ui.MainActivity;


public class TwoFragment extends Fragment {

    FragmentTwoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTwoBinding.inflate(inflater);

        if (ContextCompat.checkSelfPermission(getContext() ,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},0);
        }

        binding.btnCall.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:+989226762312"));
            startActivity(callIntent);
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}