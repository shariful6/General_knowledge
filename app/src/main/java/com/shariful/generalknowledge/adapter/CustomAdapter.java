package com.shariful.generalknowledge.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shariful.generalknowledge.R;
import com.shariful.generalknowledge.User2;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{

    Context context;
    ArrayList<User2> profile;

    public CustomAdapter(Context context, ArrayList<User2> profile) {
        this.context = context;
        this.profile = profile;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.mobile.setText("Mobile: "+profile.get(position).getMobile());
        holder.name.setText("Name: "+profile.get(position).getName());
        holder.score.setText("Score: "+profile.get(position).getScore());
        holder.tempScore.setText("Temp Score: "+profile.get(position).getTempScore());
        holder.view.setText("View: "+profile.get(position).getView());

    }

    @Override
    public int getItemCount() {
        return profile.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mobile, name, score, tempScore, view;

        public MyViewHolder(View itemView) {
            super(itemView);

            mobile = itemView.findViewById(R.id.mobile);
            name = itemView.findViewById(R.id.name);
            score = itemView.findViewById(R.id.score);
            tempScore = itemView.findViewById(R.id.temp);
            view = itemView.findViewById(R.id.view);
        }
    }
}
