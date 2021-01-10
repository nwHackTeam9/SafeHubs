package com.team9.safehubs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    List<ReviewBlock> myReviewList;

    public RecyclerAdapter(List<ReviewBlock> list) {
        this.myReviewList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.review_block, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.avgRating.setText(String.valueOf(myReviewList.get(holder.getAdapterPosition()).getAvgRating()));
        holder.textComment.setText(String.valueOf(myReviewList.get(holder.getAdapterPosition()).getAdditionalComment()));
    }

    @Override
    public int getItemCount() {
        return myReviewList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView avgRating;
        public TextView textComment;

        public LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avgRating = (TextView) itemView.findViewById(R.id.txtRating);
            textComment = (TextView) itemView.findViewById(R.id.textText);

            linearLayout = (LinearLayout) itemView.findViewById(R.id.reviewLinearLayout);
        }
    }
}
