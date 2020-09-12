package com.appnita.digikala.extraApp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.appnita.digikala.R;
import com.appnita.digikala.databinding.ActivityDarsadBinding;

import java.text.DecimalFormat;

public class Darsad extends AppCompatActivity {

    ActivityDarsadBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDarsadBinding.inflate(getLayoutInflater());

        binding.btnCalculator.setOnClickListener(v -> {
            if(!binding.edtCorrect.getText().toString().equals("") &&
                   !binding.edtFalse.getText().toString().equals("") &&
                    !binding.edtAll.getText().toString().equals("") ) {
                double correctCount = Integer.parseInt(binding.edtCorrect.getText().toString());
                double falseCount = Integer.parseInt(binding.edtFalse.getText().toString());
                double allCount = Integer.parseInt(binding.edtAll.getText().toString());

                double darsad = (((correctCount * 3) - falseCount) / (allCount * 3) * 100);

                DecimalFormat df = new DecimalFormat("#.##");
                String dx = df.format(darsad);

                String finalDarsad = String.valueOf(dx + "%");
                binding.btnAnswer.setText(finalDarsad);
            }else{
                Toast.makeText(this, "تمامی فیلد هارا به درستی تکمیل کنید.", Toast.LENGTH_SHORT).show();
            }
        });

        setContentView(binding.getRoot());
    }
}