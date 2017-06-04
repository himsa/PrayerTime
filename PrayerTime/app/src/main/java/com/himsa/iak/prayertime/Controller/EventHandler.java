package com.himsa.iak.prayertime.Controller;

import android.util.Log;

import com.himsa.iak.prayertime.Kelas.Pray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by hacked on 5/25/2017.
 */

public class EventHandler {
    public ArrayList setToArray(ArrayList<Pray> a, String x) throws JSONException {
        a = new ArrayList();
        JSONObject all_data = new JSONObject(x);
        JSONArray array_item = all_data.getJSONArray("data");
        for (int i =0; i<array_item.length();i++){
            Pray pray = new Pray();
            JSONObject object_repo = array_item.getJSONObject(i);
            JSONObject timings_repo = object_repo.getJSONObject("timings");
            JSONObject date_repo = object_repo.getJSONObject("date");

            pray.setReadable(date_repo.getString("readable"));
            pray.setFajr(timings_repo.getString("Fajr"));
            pray.setSunrise(timings_repo.getString("Sunrise"));
            pray.setDhuhr(timings_repo.getString("Dhuhr"));
            pray.setAsr(timings_repo.getString("Asr"));
            pray.setSunset(timings_repo.getString("Sunset"));
            pray.setMaghrib(timings_repo.getString("Maghrib"));
            pray.setIsha(timings_repo.getString("Isha"));
            pray.setImsak(timings_repo.getString("Imsak"));
            pray.setMidnight(timings_repo.getString("Midnight"));

            Log.d("asd", "setToArray: ");
            a.add(pray);
        }
        return a;
    }
}
