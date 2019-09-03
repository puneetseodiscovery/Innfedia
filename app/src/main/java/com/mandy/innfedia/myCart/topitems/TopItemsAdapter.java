package com.mandy.innfedia.myCart.topitems;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mandy.innfedia.R;
import com.mandy.innfedia.utils.Config;
import com.mandy.innfedia.productList.ProductListActivity;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

public class TopItemsAdapter extends RecyclerView.Adapter<TopItemsAdapter.ViewHolder> {

    Context context;
    ArrayList<GetTopDataApi.Datum> arrayList = new ArrayList<>();

    public TopItemsAdapter(Context context, ArrayList<GetTopDataApi.Datum> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custom_cart_more, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        final GetTopDataApi.Datum datum = arrayList.get(i);
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


        viewHolder.txtProducName.setText(datum.getProductName());
        viewHolder.txtPrice.setText(Config.GET_RUPPESS_SYMBOL + " " + datum.getSpecialPrice());
        viewHolder.txtProductRatingNumber.setText(datum.getTotalRatingUsers().toString());
        viewHolder.ratingBar.setRating(Float.parseFloat(datum.getAvgRating()));



        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductListActivity.class);
                intent.putExtra("subId", datum.getProductId().toString());
                intent.putExtra("Cid", datum.getProductCatId().toString());
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
        AVLoadingIndicatorView avLoadingIndicatorView;
        TextView txtProducName, txtProductRatingNumber, txtPrice;
        Button btnAddtoCart;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.custom_productImage);
            avLoadingIndicatorView = itemView.findViewById(R.id.avi);
            txtProducName = itemView.findViewById(R.id.custom_producName);
            txtProductRatingNumber = itemView.findViewById(R.id.custom_productRating_number);
            txtPrice = itemView.findViewById(R.id.custom_productPrice);
            btnAddtoCart = itemView.findViewById(R.id.custom_productDilvery);
            ratingBar = itemView.findViewById(R.id.custom_productRating);
        }
    }
}
