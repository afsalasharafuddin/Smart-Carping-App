package com.example.scps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class hisAdapter extends RecyclerView.Adapter<hisAdapter.MyViewHolder> {

    Context context;
    ArrayList<histo> histoArrayList;

    public hisAdapter(Context context, ArrayList<histo> histoArrayList) {
        this.context = context;
        this.histoArrayList = histoArrayList;
    }

    @NonNull
    @Override
    public hisAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.item2,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull hisAdapter.MyViewHolder holder, int position) {
  histo his=histoArrayList.get(position);
  holder.date.setText(his.date);
  holder.vno.setText(his.vehicleNo);
  holder.startTime.setText(his.startTime);
  holder.endTime.setText(his.endTime);
  holder.amount.setText(his.fee);

    }

    @Override
    public int getItemCount() {
        return histoArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView date,vno,startTime,endTime,amount;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.udate);
            vno=itemView.findViewById(R.id.uvno);
            startTime=itemView.findViewById(R.id.ustart);
            endTime=itemView.findViewById(R.id.uend);
            amount=itemView.findViewById(R.id.uamount);

        }
    }
}
