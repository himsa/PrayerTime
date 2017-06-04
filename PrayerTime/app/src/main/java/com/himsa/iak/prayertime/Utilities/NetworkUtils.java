package com.himsa.iak.prayertime.Utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by hacked on 5/24/2017.
 */

public class NetworkUtils {

    final static String ALADHAN_BASE_URL =
            "http://api.aladhan.com/calendarByAddress";

    final static String PARAM_ADDRESS = "address";
    final static String PARAM_MONTH = "month";
    final static String PARAM_YEAR = "year";

    final static String PARAM_SORT = "method";
    final static String sortBy = "5";

    public static URL buildUrl(String addressQuery, String monthQuery, String yearQuery) {
        Uri builtUri = Uri.parse(ALADHAN_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_ADDRESS, addressQuery)
                .appendQueryParameter(PARAM_MONTH, monthQuery)
                .appendQueryParameter(PARAM_YEAR, yearQuery)
                .appendQueryParameter(PARAM_SORT, sortBy)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}