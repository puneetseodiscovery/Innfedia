package com.mandy.innfedia.commentActivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mandy.innfedia.R;
import com.mandy.innfedia.retrofit.ServiceGenerator;
import com.mandy.innfedia.utils.Config;

import java.util.ArrayList;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    Context context;
    ArrayList<CommentsApi.Datum> arrayList = new ArrayList<>();

    public CommentsAdapter(Context context, ArrayList<CommentsApi.Datum> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custom_comments, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        CommentsApi.Datum datum = arrayList.get(i);
        viewHolder.txtName.setText(datum.getUsername());
        viewHolder.ratingBar.setRating(datum.getRating());
        viewHolder.readMoreTextView.setText(datum.getComment());
        if (datum.getUserimage() != null) {
            Glide.with(context).load(ServiceGenerator.BASE_API_PROFILE_IMAGE + datum.getUserimage()).dontTransform().dontAnimate()
                    .into(viewHolder.roundedImageView);
        }


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView roundedImageView;
        RatingBar ratingBar;
        TextView txtName;
        ReadMoreTextView readMoreTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            roundedImageView = itemView.findViewById(R.id.roundimageView);
            txtName = itemView.findViewById(R.id.txtName);
            ratingBar = itemView.findViewById(R.id.ratingbar);
            readMoreTextView = itemView.findViewById(R.id.txtComment);
        }
    }
}
