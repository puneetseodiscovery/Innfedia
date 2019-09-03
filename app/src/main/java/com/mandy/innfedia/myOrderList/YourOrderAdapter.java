package com.mandy.innfedia.myOrderList;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mandy.innfedia.myOrderList.myorderdetails.OrderDetailsActivity;
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


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
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
