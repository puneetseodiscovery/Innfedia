package com.mandy.innfedia.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.mandy.innfedia.R;
import com.mandy.innfedia.Utils.UtilDialog;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context context;
    String number[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    String num;

    public CartAdapter(Context context) {
        this.context = context;
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

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, number);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewHolder.spinner.setAdapter(arrayAdapter);
        viewHolder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                num = parent.getSelectedItem().toString();
                Toast.makeText(context, "" + num, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = UtilDialog.dialog(context, context.getResources().getString(R.string.areyou), "Delete");
                dialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Spinner spinner;
        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            spinner = itemView.findViewById(R.id.spinnner);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
