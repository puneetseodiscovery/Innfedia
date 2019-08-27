package com.mandy.innfedia.adapter.main2;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mandy.innfedia.ApiModel.GetSubCategory;
import com.mandy.innfedia.R;
import com.mandy.innfedia.Utils.Config;
import com.mandy.innfedia.fragment.ProductListFragment;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

public class BottomWearAdapter extends RecyclerView.Adapter<BottomWearAdapter.ViewHolder> {

    Context context;
    List<GetSubCategory.BottomWear> arrayList;
    FragmentManager manager;

    public BottomWearAdapter(Context context, List<GetSubCategory.BottomWear> arrayList, FragmentManager manager) {
        this.context = context;
        this.arrayList = arrayList;
        this.manager = manager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custom_new_arrival, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        GetSubCategory.BottomWear datum = arrayList.get(i);

        Glide.with(context).load(Config.GET_CATEGORY_IMAGE + arrayList.get(i).getImage()).listener(new RequestListener<Drawable>() {
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


        viewHolder.textView.setText(datum.getName());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductListFragment productListFragment = new ProductListFragment();
                Bundle bundle = new Bundle();
                bundle.putString("SubId", arrayList.get(i).getId().toString());
                productListFragment.setArguments(bundle);
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.framelayout, productListFragment);
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
        TextView textView;
        ImageView imageView;
        AVLoadingIndicatorView avLoadingIndicatorView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.dashText);
            imageView = itemView.findViewById(R.id.dashImage);
            avLoadingIndicatorView = itemView.findViewById(R.id.avi);
        }
    }
}
