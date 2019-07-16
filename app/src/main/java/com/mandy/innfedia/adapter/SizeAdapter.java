package com.mandy.innfedia.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mandy.innfedia.R;

import java.util.ArrayList;

public class SizeAdapter extends RecyclerView.Adapter<SizeAdapter.ViewHolder> {

    Context context;
    ArrayList<String> arrayList;
    int pos;

    // interface to refresh the list
    private ColorAdapter.OnItemClick itemClick;

    public interface OnItemClick {
        void onItemClick(int position);
    }

    public void setOnItemClick(ColorAdapter.OnItemClick listener) {
        itemClick = listener;
    }


    public SizeAdapter(Context context, ArrayList<String> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custom_size, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.textView.setText(arrayList.get(i));

        if (pos == i) {
            viewHolder.textView.setBackgroundResource(R.drawable.custom_size);
            viewHolder.textView.setTextColor(context.getResources().getColor(R.color.black));
            if (itemClick != null) {
                int po = i;
                if (po != RecyclerView.NO_POSITION) {
                    itemClick.onItemClick(po);
                }
            }
        } else {
            viewHolder.textView.setBackgroundResource(R.drawable.custom_size_close);
        }

        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = i;
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
        }
    }
}
