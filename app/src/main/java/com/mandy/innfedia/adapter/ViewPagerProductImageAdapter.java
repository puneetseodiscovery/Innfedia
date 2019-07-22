package com.mandy.innfedia.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mandy.innfedia.R;
import com.mandy.innfedia.adapter.main2.ZoomingAdapter;

import java.util.ArrayList;

public class ViewPagerProductImageAdapter extends PagerAdapter {

    ArrayList<Integer> arrayList = new ArrayList<>();
    Context context;

    public ViewPagerProductImageAdapter(Context context, ArrayList<Integer> arrayList) {
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemview = inflater.inflate(R.layout.item, container, false);
        trailimg = itemview.findViewById(R.id.trailImage);

        trailimg.setImageResource(arrayList.get(position));

        trailimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        ((ViewPager) container).addView(itemview);

        return itemview;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);
    }


    private void showDialog() {
        ViewPager viewPager1;
        ImageView imageView;

        final Dialog dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.viewphoto);

        viewPager1 = dialog.findViewById(R.id.dialog_viewPager);
        imageView = dialog.findViewById(R.id.imageClose);
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(R.drawable.womantop);
        arrayList.add(R.drawable.women);
        arrayList.add(R.drawable.kid);


        PagerAdapter adapter = new ZoomingAdapter(context, arrayList);
        viewPager1.setAdapter(adapter);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();


    }


}
