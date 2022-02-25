package com.example.quizapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Score> list;

    public MyAdapter(Context context, ArrayList<Score> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Score score = list.get(position);
        //holder.user_id.setText(score.getUserId());
        holder.date_taken.setText(score.getDate_taken());
        holder.subject.setText(score.getSubject());
        holder.final_score.setText(score.getFinal_score());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView user_id, subject, date_taken, final_score;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            date_taken = itemView.findViewById(R.id.view_datetaken);
            subject = itemView.findViewById(R.id.view_subject);
            final_score = itemView.findViewById(R.id.view_finalscore);

        }
    }

}