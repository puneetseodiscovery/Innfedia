package com.mandy.innfedia.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mandy.innfedia.ApiModel.GetProductDetails;
import com.mandy.innfedia.R;
import com.mandy.innfedia.Utils.Config;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerProductImageAdapter extends PagerAdapter {

    List<GetProductDetails.ProductImage> arrayList;
    Context context;

    public ViewPagerProductImageAdapter(Context context, List<GetProductDetails.ProductImage> arrayList) {
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
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView trailimg;
        final AVLoadingIndicatorView avLoadingIndicatorView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemview = inflater.inflate(R.layout.item, container, false);
        trailimg = itemview.findViewById(R.id.trailImage);
        avLoadingIndicatorView = itemview.findViewById(R.id.avi);

        if (arrayList.isEmpty()) {
            avLoadingIndicatorView.setVisibility(View.GONE);
            trailimg.setImageResource(R.drawable.kid);
        } else {
            Glide.with(context).load(Config.GET_PRODUCT_IMAGE + arrayList.get(position).getMedia()).listener(new RequestListener<Drawable>() {
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
        }
        ((ViewPager) container).addView(itemview);

        return itemview;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);
    }


}
