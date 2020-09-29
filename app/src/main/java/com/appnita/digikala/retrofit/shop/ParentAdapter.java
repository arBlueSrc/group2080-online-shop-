package com.appnita.digikala.retrofit.shop;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appnita.digikala.R;
import com.appnita.digikala.retrofit.basket.RetrofitBasket;
import com.appnita.digikala.retrofit.pojoProductCategory.ResponseProductCategory;
import com.appnita.digikala.retrofit.pojoProducts.ResponseProduct;
import com.appnita.digikala.retrofit.retrofit.ApiService;
import com.appnita.digikala.ui.ProductDetail;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParentAdapter extends RecyclerView.Adapter<ParentAdapter.ParentViewHolder> {
    RetrofitBasket retrofit;
    ApiService apiService;
    List<ResponseProductCategory> list = new ArrayList<>();
    Context context;

    public ParentAdapter(List<ResponseProductCategory> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ParentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.parent_product_list,parent,false);
        return new ParentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentViewHolder holder, int position) {
        ResponseProductCategory productCategory = list.get(position);
        holder.title.setText(productCategory.getName());

        retrofit = new RetrofitBasket();
        apiService = retrofit.getApiService();

        Call<List<ResponseProduct>> call = apiService.getProductsByCategory(productCategory.getId());
        call.enqueue(new Callback<List<ResponseProduct>>() {
            @Override
            public void onResponse(Call<List<ResponseProduct>> call, Response<List<ResponseProduct>> response) {
                if (response.isSuccessful()) {
                    List<ResponseProduct> products = response.body();
                    Log.d("test list : ", products.toString());
                    ChildAdapter childAdapter = new ChildAdapter(products,context);
                    holder.recyclerView.setAdapter(childAdapter);

                } else {
                    Toast.makeText(context, "ok but ..." + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ResponseProduct>> call, Throwable t) {
                Toast.makeText(context, "oh  " + t, Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ParentViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        RecyclerView recyclerView;
        public ParentViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txt_parent_title);
            recyclerView = itemView.findViewById(R.id.rv_parent);
        }
    }
}
