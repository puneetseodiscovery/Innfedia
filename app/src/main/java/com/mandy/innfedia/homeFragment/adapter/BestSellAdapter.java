package com.mandy.innfedia.homeFragment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mandy.innfedia.R;

import java.util.ArrayList;

public class BestSellAdapter extends RecyclerView.Adapter<BestSellAdapter.ViewHolder> {

    Context context;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<Integer> arrayImage = new ArrayList<>();
    FragmentManager manager;

    public BestSellAdapter(Context context, ArrayList<String> arrayList, ArrayList<Integer> arrayImage, FragmentManager manager) {
        this.context = context;
        this.arrayList = arrayList;
        this.arrayImage = arrayImage;
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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.imageView.setImageResource(arrayImage.get(i));
        viewHolder.textView.setText(arrayList.get(i));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }


    @Override
    public int getItemCount() {
        return arrayImage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.dashText);
            imageView = itemView.findViewById(R.id.dashImage);
        }
    }
}