package com.mandy.innfedia.productList.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mandy.innfedia.productDetails.ProductDetailsActivity;
import com.mandy.innfedia.productList.GetProductList;
import com.mandy.innfedia.R;
import com.mandy.innfedia.utils.Config;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    Context context;
    List<GetProductList.Datum> arrayList = new ArrayList<>();
    String id;


    public ProductListAdapter(Context context, List<GetProductList.Datum> arrayList, String id) {
        this.context = context;
        this.arrayList = arrayList;
        this.id = id;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custom_products, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        final GetProductList.Datum datum = arrayList.get(i);

        viewHolder.txtProductName.setText(datum.getTitle());
        viewHolder.txtPrice.setText(Config.GET_RUPPESS_SYMBOL + datum.getPrice());
        viewHolder.ratingBar.setRating(Float.parseFloat(datum.getAvgRating()));
        viewHolder.txtRatingUser.setText(datum.getTotalUsers().toString());

        Glide.with(context).load(Config.GET_PRODUCT_IMAGE + datum.getImage()).listener(new RequestListener<Drawable>() {
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
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("subId", datum.getId().toString());
                intent.putExtra("Cid", id);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtProductName, txtRatingUser, txtPrice;
        RatingBar ratingBar;
        AVLoadingIndicatorView avLoadingIndicatorView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.custom_productImage);
            txtProductName = itemView.findViewById(R.id.custom_productName);
            txtRatingUser = itemView.findViewById(R.id.custom_productRating_number);
            txtPrice = itemView.findViewById(R.id.custom_productPrice);
            avLoadingIndicatorView = itemView.findViewById(R.id.avi);
            ratingBar = itemView.findViewById(R.id.custom_productRating);
        }
    }
}
