package com.mandy.innfedia.addressActivity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mandy.innfedia.R;
import com.mandy.innfedia.addressActivity.GetAddressApi;

import java.util.ArrayList;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    Context context;
    ArrayList<GetAddressApi.Datum> arrayList;
    int pos;

    // interface to refresh the list
    private OnItemClick itemClick;

    public interface OnItemClick {
        void onItemClick(int position, String address);
    }

    public void setOnItemClick(OnItemClick listener) {
        itemClick = listener;
    }


    public AddressAdapter(Context context, ArrayList<GetAddressApi.Datum> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custom_address, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        GetAddressApi.Datum datum = arrayList.get(i);
        String address = datum.getDisplayName() + "," + datum.getPhone() + "," + datum.getPostcode() +
                "," + datum.getState() + "," + datum.getCity() + "," + datum.getAddress() + "," + datum.getLandmark();

        viewHolder.textView.setText(address);

        if (pos == i) {
            viewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check, 0, 0, 0);
            if (itemClick != null) {
                int po = i;
                if (po != RecyclerView.NO_POSITION) {
                    itemClick.onItemClick(po, address);
                }
            }
        } else {
            viewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check2, 0, 0, 0);
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
            textView = itemView.findViewById(R.id.address_radio_btn);

        }
    }
}
