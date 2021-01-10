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
        holder.place_name.setText(myReviewList.get(holder.getAdapterPosition()).place_name);
        holder.avgRating.setText(String.valueOf(myReviewList.get(holder.getAdapterPosition()).avgRating));
        holder.date_time.setText(myReviewList.get(holder.getAdapterPosition()).date_time);
    }

    @Override
    public int getItemCount() {
        return myReviewList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView place_name;
        public TextView avgRating;
        public TextView date_time;

        public LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            place_name = (TextView) itemView.findViewById(R.id.place_name);
            avgRating = (TextView) itemView.findViewById(R.id.txtRating);
            date_time = (TextView) itemView.findViewById(R.id.date_time);

            linearLayout = (LinearLayout) itemView.findViewById(R.id.reviewLinearLayout);
        }
    }
}
