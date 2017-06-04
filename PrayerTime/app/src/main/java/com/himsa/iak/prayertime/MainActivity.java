package com.himsa.iak.prayertime;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.himsa.iak.prayertime.Adapter.PrayAdapter;
import com.himsa.iak.prayertime.Controller.EventHandler;
import com.himsa.iak.prayertime.Kelas.Pray;
import com.himsa.iak.prayertime.Utilities.NetworkUtils;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static com.himsa.iak.prayertime.R.id.Fajr;
import static com.himsa.iak.prayertime.R.id.Sunrise;

public class MainActivity extends AppCompatActivity {
    String prayerMainQuery;
    String prayerMonthQuery;
    String prayerYearQuery;
    String [] SPINNERLISTMONTH = {"January","February","March","April","May","June","July","August","September","October","November","December"};
    String[] SPINNERLISTYEAR ={"2000","2001","2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","2013","2014","2015","2016","2017","2018","2019","2020"};

    private MaterialBetterSpinner betterSpinnerMonth;
    private MaterialBetterSpinner betterSpinnerYear;
    private EditText mSearchBoxEditText;

    private TextView mUrlDisplayTextView;

    EventHandler evt;

    RecyclerView rv;
    ArrayList<Pray> all_data;
    PrayAdapter adapter;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        evt = new EventHandler();

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, SPINNERLISTMONTH);
        betterSpinnerMonth= (MaterialBetterSpinner) findViewById(R.id.spinner_month);
        betterSpinnerMonth.setAdapter(monthAdapter);

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, SPINNERLISTYEAR);
        betterSpinnerYear= (MaterialBetterSpinner) findViewById(R.id.spinner_year);
        betterSpinnerYear.setAdapter(yearAdapter);

        mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);

        mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);
        mUrlDisplayTextView.setText("Source : Egyptian General Authority of Survey");

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        if(savedInstanceState != null){
            prayerMainQuery = savedInstanceState.getString("mainQuery");
            prayerMonthQuery = savedInstanceState.getString("monthQuery");
            prayerYearQuery = savedInstanceState.getString("yearQuery");
            URL prayerUrl = NetworkUtils.buildUrl(prayerMainQuery,prayerMonthQuery, prayerYearQuery);
            mUrlDisplayTextView.setText(prayerUrl.toString());
            makeGithubSearchQuery(true);
        }

        rv = (RecyclerView) findViewById(R.id.rv);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuku, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            makeGithubSearchQuery(false);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void makeGithubSearchQuery(boolean a) {
        String mainQuery = mSearchBoxEditText.getText().toString();
        String monthQuery = betterSpinnerMonth.getText().toString();
        switch (monthQuery){
            case "January":
                monthQuery ="1";
                break;
            case "February":
                monthQuery ="2";
                break;
            case "March":
                monthQuery ="3";
                break;
            case "April":
                monthQuery ="4";
                break;
            case "May":
                monthQuery ="5";
                break;
            case "June":
                monthQuery ="6";
                break;
            case "July":
                monthQuery ="7";
                break;
            case "August":
                monthQuery ="8";
                break;
            case "September":
                monthQuery ="9";
                break;
            case "October":
                monthQuery ="10";
                break;
            case "November":
                monthQuery ="11";
                break;
            case "December":
                monthQuery ="12";
                break;
        }
        String yearQuery = betterSpinnerYear.getText().toString();
        URL prayerSearchUrl = NetworkUtils.buildUrl(mainQuery,monthQuery,yearQuery);
        //mUrlDisplayTextView.setText(prayerSearchUrl.toString());
        new GithubQueryTask().execute(prayerSearchUrl);
    }

    private void showJsonDataView() {
        // First, make sure the error is invisible
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
    }

    private void showErrorMessage() {
        // Then, show the error
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    public class GithubQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String githubSearchResults = null;
            try {
                githubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return githubSearchResults;
        }
        @Override
        protected void onPostExecute(String s) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (s != null && !s.equals("")) {
                showJsonDataView();
                try {
                    all_data = evt.setToArray(all_data, s);
                    adapter = new PrayAdapter(all_data, getBaseContext());
                    rv.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                showErrorMessage();
            }
        }

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String mainQueryUrl = mSearchBoxEditText.getText().toString();
        String monthQueryUrl = betterSpinnerMonth.getText().toString();
        String yearQueryUrl = betterSpinnerYear.getText().toString();
        outState.putString("mainQuery", mainQueryUrl);
        outState.putString("monthQuery", monthQueryUrl);
        outState.putString("yearQuery", yearQueryUrl);
    }
}