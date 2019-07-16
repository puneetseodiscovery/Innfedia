package com.mandy.innfedia.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mandy.innfedia.Activities.YourOrderDetailsActivity;
import com.mandy.innfedia.R;

import java.util.ArrayList;

public class YourOrderAdapter extends RecyclerView.Adapter<YourOrderAdapter.ViewHolder> {

    Context context;
    ArrayList<String> arrayList = new ArrayList<>();
    String des;

    public YourOrderAdapter(Context context, ArrayList<String> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custom_your_order, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.textDispatch.setText(arrayList.get(i));

        if (viewHolder.textDispatch.equals(des)) {
            viewHolder.textDispatch.setTextColor(context.getResources().getColor(R.color.green));
            viewHolder.textName.setTextColor(context.getResources().getColor(R.color.green));
        } else {
            viewHolder.textDispatch.setTextColor(context.getResources().getColor(R.color.black));
            viewHolder.textName.setTextColor(context.getResources().getColor(R.color.black));
        }


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, YourOrderDetailsActivity.class));
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textDispatch, textName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textDispatch = itemView.findViewById(R.id.txtDispatch);
            textName = itemView.findViewById(R.id.txtData);
        }

    }
}
