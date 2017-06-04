package com.himsa.iak.prayertime.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.himsa.iak.prayertime.DetailPrayer;
import com.himsa.iak.prayertime.Kelas.Pray;
import com.himsa.iak.prayertime.R;

import java.util.ArrayList;

public class PrayAdapter extends RecyclerView.Adapter<PrayAdapter.Wadah> {
    ArrayList<Pray> all_data;
    Context context;

    public PrayAdapter(ArrayList<Pray> all_data, Context context) {
        this.all_data = all_data;
        this.context = context;
    }

    @Override
    public PrayAdapter.Wadah onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_pray_adapter, parent, false);
        return new Wadah(v);
    }

    @Override
    public void onBindViewHolder(PrayAdapter.Wadah holder, int position) {
        final Pray pray = all_data.get(position);
        holder.date.setText(pray.getReadable());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle bundle = new Bundle();
                bundle.putString("readable", pray.getReadable());
                bundle.putString("Fajr", pray.getFajr());
                bundle.putString("Sunrise", pray.getSunrise());
                bundle.putString("Dhuhr", pray.getDhuhr());
                bundle.putString("Asr", pray.getAsr());
                bundle.putString("Sunset", pray.getSunset());
                bundle.putString("Maghrib", pray.getMaghrib());
                bundle.putString("Isha", pray.getIsha());
                bundle.putString("Imsak", pray.getImsak());
                bundle.putString("Midnight", pray.getMidnight());

                Intent intent = new Intent(context, DetailPrayer.class);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return all_data.size();
    }

    public class Wadah extends RecyclerView.ViewHolder {
        TextView date;
        public Wadah(View itemView) {
            super(itemView);
            date= (TextView) itemView.findViewById(R.id.tv_date);
        }
    }
}
