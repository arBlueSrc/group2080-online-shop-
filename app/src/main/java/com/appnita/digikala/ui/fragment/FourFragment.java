package com.appnita.digikala.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appnita.digikala.R;
import com.appnita.digikala.databinding.FragmentFourBinding;
import com.appnita.digikala.ui.LoginActivity;


public class FourFragment extends Fragment {

    FragmentFourBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFourBinding.inflate(inflater);

        binding.btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), LoginActivity.class));
        });

        return binding.getRoot();
    }
}