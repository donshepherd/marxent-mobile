package com.marxent.mobile;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickSolutions(View view) {

        buildSolutionsAlertDialog();

        SolutionAsyncTask jsonAsynTask = new SolutionAsyncTask() {
            @Override
            protected void onPreExecute() {
                alertDialog.show();
            }

            @Override
            protected void onCancelled() {
                alertDialog.dismiss();
            }

            @Override
            protected void onPostExecute(ArrayList<String> response) {
                alertDialog.dismiss();
                Intent solutionsIntent = new Intent(MainActivity.this, SolutionsActivity.class);
                solutionsIntent.putStringArrayListExtra(getString(R.string.solution_key),response);
                startActivity(solutionsIntent);
            }
        };

        //Use 10.0.2.2 when running Spring Boot and Android Simulator on same PC
        jsonAsynTask.execute("http://10.0.2.2:8080//solutions");

    }

    public void onClickAbout(View view) {
        Intent browseIntent = new Intent(MainActivity.this, WebViewActivity.class);
        browseIntent.putExtra(getString(R.string.url_key), getString(R.string.about_url_path));
        startActivity(browseIntent);
    }

    private void buildSolutionsAlertDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        alertBuilder.setCancelable(true)
                .setMessage("Loading Solutions...")
                .setTitle("Please wait");
        alertDialog = alertBuilder.create();
    }
}
