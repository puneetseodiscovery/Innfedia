package com.mandy.innfedia.adapter.main2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mandy.innfedia.ApiModel.GetProductList;
import com.mandy.innfedia.R;
import com.mandy.innfedia.Utils.Config;
import com.mandy.innfedia.fragment.ProductDetailsFragment;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

public class SeeRelatedItemAdapter extends RecyclerView.Adapter<SeeRelatedItemAdapter.ViewHolder> {

    Context context;
    FragmentManager manager;
    ArrayList<GetProductList.Datum> arrayList = new ArrayList<>();

    public SeeRelatedItemAdapter(Context context, ArrayList<GetProductList.Datum> arrayList, FragmentManager manager) {
        this.context = context;
        this.arrayList = arrayList;
        this.manager = manager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custom_simlar, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        viewHolder.txtName.setText(arrayList.get(i).getTitle());
        viewHolder.txtPrice.setText(Config.GET_RUPPESS_SYMBOL + " " + arrayList.get(i).getPrice());

        Glide.with(context).load(Config.GET_PRODUCT_IMAGE + arrayList.get(i).getImage()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                viewHolder.avLoadingIndicatorView.setVisibility(View.GONE);
                return false;
            }
        }).into(viewHolder.imageView);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("subId", arrayList.get(i).getId().toString());
                productDetailsFragment.setArguments(bundle);
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.framelayout, productDetailsFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        AVLoadingIndicatorView avLoadingIndicatorView;
        TextView txtName, txtPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            avLoadingIndicatorView = itemView.findViewById(R.id.avi);
            txtName = itemView.findViewById(R.id.productName);
            txtPrice = itemView.findViewById(R.id.productPrice);
        }
    }
}
