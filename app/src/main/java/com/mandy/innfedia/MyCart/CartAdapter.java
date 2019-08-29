package com.mandy.innfedia.MyCart;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mandy.innfedia.R;
import com.mandy.innfedia.Utils.Config;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context context;
    String number[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    String num;
    ArrayList<GetCartDataApi.TotalCartProduct> arrayList = new ArrayList<>();


    private OnItemClickListener itemClickListener;
    private OnItemClickDelete itemClickDelete;

    public interface OnItemClickListener {
        void onItemClick(String number, String id);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }


    public interface OnItemClickDelete {
        void onItemDelete(String id);
    }

    public void setOnItemdelte(OnItemClickDelete onItemdelte) {
        itemClickDelete = onItemdelte;
    }

    public CartAdapter(Context context, ArrayList<GetCartDataApi.TotalCartProduct> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custom_cart, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        if (arrayList.isEmpty()) {
            Toast.makeText(context, "Empty my cart list", Toast.LENGTH_SHORT).show();
        } else {
            final GetCartDataApi.TotalCartProduct datam = arrayList.get(i);
            viewHolder.txtName.setText(datam.getProductName() + " " + datam.getProductSlug());
            viewHolder.txtPrice.setText(Config.GET_RUPPESS_SYMBOL + " " + datam.getSpecialPrice());
            viewHolder.txtStock.setText(datam.getStock());
            Glide.with(context).load(Config.GET_PRODUCT_IMAGE + datam.getImage()).dontAnimate().dontTransform().into(viewHolder.imageView);


            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, number);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            viewHolder.spinner.setAdapter(arrayAdapter);
            viewHolder.spinner.setSelection(Integer.parseInt(datam.getProductOrderQuantity()));

            viewHolder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    num = parent.getSelectedItem().toString();
                    if (!num.equals(datam.getProductOrderQuantity())) {
                        if (itemClickListener != null) {
                            String postin = num;
                            itemClickListener.onItemClick(postin, datam.getCartId().toString());
                        }
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickDelete != null) {
                        itemClickDelete.onItemDelete(datam.getCartId().toString());
                    }
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Spinner spinner;
        Button btnDelete;
        ImageView imageView;
        TextView txtName, txtPrice, txtStock;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            spinner = itemView.findViewById(R.id.spinnner);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtStock = itemView.findViewById(R.id.txtStock);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
