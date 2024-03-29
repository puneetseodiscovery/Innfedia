package com.mandy.innfedia.productDetails.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.mandy.innfedia.productDetails.onClick;
import com.mandy.innfedia.productList.GetProductList;
import com.mandy.innfedia.R;
import com.mandy.innfedia.utils.Config;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

public class SeeRelatedItemAdapter extends RecyclerView.Adapter<SeeRelatedItemAdapter.ViewHolder> {

    Context context;

    ArrayList<GetProductList.Datum> arrayList = new ArrayList<>();

    public onClick clike;

    public void setOnClick(onClick clik) {
        clike = clik;
    }

    public SeeRelatedItemAdapter(Context context, ArrayList<GetProductList.Datum> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

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
        viewHolder.txtPrice.setText(Config.GET_RUPPESS_SYMBOL + " " + arrayList.get(i).getSpecialPrice());

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
                clike.GetId(arrayList.get(i).getId().toString());
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
