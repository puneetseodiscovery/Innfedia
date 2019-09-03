package com.mandy.innfedia.home2.adapter;

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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mandy.innfedia.home2.GetSubCategoryApi;
import com.mandy.innfedia.R;
import com.mandy.innfedia.utils.Config;
import com.mandy.innfedia.productList.ProductListActivity;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

public class ExploreMoreAdapter extends RecyclerView.Adapter<ExploreMoreAdapter.ViewHolder> {

    Context context;
    List<GetSubCategoryApi.ExploreMore> arrayList;


    public ExploreMoreAdapter(Context context, List<GetSubCategoryApi.ExploreMore> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

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
        GetSubCategoryApi.ExploreMore datu = arrayList.get(i);

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

        viewHolder.textView.setText(datu.getName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductListActivity.class);
                intent.putExtra("SubId", arrayList.get(i).getId().toString());
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
        TextView textView;
        ImageView imageView;
        AVLoadingIndicatorView avLoadingIndicatorView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avLoadingIndicatorView = itemView.findViewById(R.id.avi);
            textView = itemView.findViewById(R.id.dashText);
            imageView = itemView.findViewById(R.id.dashImage);
        }
    }
}
