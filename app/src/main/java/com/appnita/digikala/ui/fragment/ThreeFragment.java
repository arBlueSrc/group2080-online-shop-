package com.appnita.digikala.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appnita.digikala.R;
import com.appnita.digikala.databinding.FragmentThreeBinding;
import com.appnita.digikala.extraApp.Darsad;

public class ThreeFragment extends Fragment {

    FragmentThreeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentThreeBinding.inflate(inflater);

        binding.btnDarsad.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), Darsad.class));
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}