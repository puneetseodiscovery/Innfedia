package com.mandy.innfedia.homeFragment.adapter;


import android.content.Context;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mandy.innfedia.homeFragment.apis.BannerApi;
import com.mandy.innfedia.R;
import com.mandy.innfedia.productList.ProductListActivity;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {

    ArrayList<BannerApi.Datum> arrayList = new ArrayList<>();
    Context context;


    public ViewPagerAdapter(Context context, ArrayList<BannerApi.Datum> arrayList) {
        this.context = context;
        this.arrayList = arrayList;


    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((RelativeLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        ImageView trailimg;
        final AVLoadingIndicatorView avLoadingIndicatorView;
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemview = inflater.inflate(R.layout.item, container, false);
        trailimg = itemview.findViewById(R.id.trailImage);
        avLoadingIndicatorView = itemview.findViewById(R.id.avi);

        Glide.with(context).load(arrayList.get(position).getBanner()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                avLoadingIndicatorView.setVisibility(View.GONE);
                return false;
            }
        }).into(trailimg);

        trailimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("SubId", arrayList.get(position).getProductCatIds().toString());
                intent.putExtra("type", "0");
                context.startActivity(intent);
            }
        });

        ((ViewPager) container).addView(itemview);

        return itemview;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);
    }
}