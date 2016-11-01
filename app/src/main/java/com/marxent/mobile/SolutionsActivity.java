package com.marxent.mobile;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by shephdo on 10/31/16.
 * Demo purposes only for Marxent Labs.
 */

public class SolutionsActivity extends ListActivity {

    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<String> solutionsArrayList = getIntent().getStringArrayListExtra(getString(R.string.solution_key));

        if (solutionsArrayList == null || solutionsArrayList.isEmpty()) {
            showNoSolutionsReturnedDialog();
        } else {
            setListAdapter(solutionsArrayList);
        }
    }

    private void setListAdapter(ArrayList<String> solutionsArrayList) {
        ListView solutionsListView = getListView();
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, solutionsArrayList);
        solutionsListView.setAdapter(listAdapter);
    }

    private void showNoSolutionsReturnedDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(SolutionsActivity.this);
        alertBuilder.setCancelable(true)
                .setMessage("Please ensure server is running")
                .setTitle("No Response Returned From Server").setOnCancelListener(
                  new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Intent returnToMainIntent = new Intent(SolutionsActivity.this, MainActivity.class);
                        startActivity(returnToMainIntent);
                    }
                });
        alertDialog = alertBuilder.create();
        alertDialog.show();
    }

}
