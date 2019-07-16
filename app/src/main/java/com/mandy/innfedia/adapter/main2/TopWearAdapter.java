package com.mandy.innfedia.adapter.main2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mandy.innfedia.R;
import com.mandy.innfedia.fragment.ProductListFragment;

import java.util.ArrayList;

public class TopWearAdapter extends RecyclerView.Adapter<TopWearAdapter.ViewHolder> {

    Context context;
    ArrayList<String> arrayList;
    ArrayList<Integer> arrayImage;
    FragmentManager manager;

    public TopWearAdapter(Context context, ArrayList<String> arrayList, ArrayList<Integer> arrayImage, FragmentManager manager) {
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
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.framelayout, new ProductListFragment());
                transaction.addToBackStack(null);
                transaction.commit();
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
