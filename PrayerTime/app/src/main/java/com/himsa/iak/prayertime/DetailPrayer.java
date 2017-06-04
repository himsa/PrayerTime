package com.himsa.iak.prayertime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailPrayer extends AppCompatActivity {
    String a,b,c,d;
    TextView date, Fajr, Sunrise, Dhuhr, Asr, Sunset, Maghrib, Isha, Imsak, Midnight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_prayer);

        date = (TextView) findViewById(R.id.detail_date);
        Fajr = (TextView) findViewById(R.id.Fajr);
        Sunrise = (TextView) findViewById(R.id.Sunrise);
        Dhuhr = (TextView) findViewById(R.id.Dhuhr);
        Asr = (TextView) findViewById(R.id.Asr);
        Sunset = (TextView) findViewById(R.id.Sunset);
        Maghrib = (TextView) findViewById(R.id.Maghrib);
        Isha = (TextView) findViewById(R.id.Isha);
        Imsak = (TextView) findViewById(R.id.Imsak);
        Midnight = (TextView) findViewById(R.id.Midnight);

        Intent intent = getIntent();

        date.setText(intent.getStringExtra("readable"));
        Fajr.setText(intent.getStringExtra("Fajr"));
        Sunrise.setText(intent.getStringExtra("Sunrise"));
        Dhuhr.setText(intent.getStringExtra("Dhuhr"));
        Asr.setText(intent.getStringExtra("Asr"));
        Sunset.setText(intent.getStringExtra("Sunset"));
        Maghrib.setText(intent.getStringExtra("Maghrib"));
        Isha.setText(intent.getStringExtra("Isha"));
        Imsak.setText(intent.getStringExtra("Imsak"));
        Midnight.setText(intent.getStringExtra("Midnight"));
    }
}
