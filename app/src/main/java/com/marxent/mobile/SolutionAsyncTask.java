package com.marxent.mobile;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by shephdo on 10/31/16.
 * Demo purposes only for Marxent Labs.
 */

public class SolutionAsyncTask extends AsyncTask<String, String, ArrayList<String>> {

    public static final String TAG = "SolutionAsyncTask";

    /**
     * Retrieve the Solution Names from the provided URL
     * @param params - Position 0 is the URL for the Solution JSON results
     * @return ArrayList of Strings containing the Solution Names
     */
    @Override
    protected ArrayList<String> doInBackground(String... params) {

        ArrayList<String> results = new ArrayList<>();
        InputStream stream;
        URL url = null;

        try {
            url = new URL(params[0]);
            String solutionResults = getSolutionResults(url);
            getSoultionNamesFromResults(results, solutionResults);
        } catch (Exception e) {
           Log.e(TAG, "Error getting Solutions from Server Url: " + url, e  );
        }

        return results;
    }

    @NonNull
    private String getSolutionResults(URL url) throws IOException {
        InputStream stream;HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        stream = new BufferedInputStream(urlConnection.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder builder = new StringBuilder();

        String inputString;
        while ((inputString = bufferedReader.readLine()) != null) {
            builder.append(inputString);
        }
        urlConnection.disconnect();
        return builder.toString();
    }

    private void getSoultionNamesFromResults(ArrayList<String> results, String solutionResults) throws JSONException {
        JSONObject topLevel = new JSONObject(solutionResults.toString());
        JSONArray jsonSolutionArray = topLevel.getJSONObject("_embedded").getJSONArray("solutions");

        if (jsonSolutionArray != null) {
            for (int i = 0 ; i < jsonSolutionArray.length(); i++) {
                JSONObject solution = (JSONObject) jsonSolutionArray.get(i);
                String solutionName = solution.getString("solutionName");
                results.add(solutionName);
            }
        }
    }

}
