package com.appnita.digikala.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appnita.digikala.R;
import com.appnita.digikala.databinding.FragmentChartInfoBinding;

public class ChartInfoFragment extends DialogFragment {

    FragmentChartInfoBinding binding;


    public static ChartInfoFragment newInstance() {
        ChartInfoFragment frag = new ChartInfoFragment();
//        Bundle args = new Bundle();
//        args.putInt("title", title);
//        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChartInfoBinding.inflate(inflater);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }
}