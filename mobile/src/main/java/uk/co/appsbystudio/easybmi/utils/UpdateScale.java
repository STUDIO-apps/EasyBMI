package uk.co.appsbystudio.easybmi.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import uk.co.appsbystudio.easybmi.database.DatabaseHelper;
import uk.co.appsbystudio.easybmi.pages.adapters.ScaleAdapter;

public class UpdateScale extends AsyncTask<Void, Void, Void> {

    //This class is called every time a new BMI has been calculated.

    private final Context context;
    private final RecyclerView recyclerView;

    //Setup strings for the BMI scale
    private final String[] scaleValues = new String[] {"Very severely underweight", "Severely underweight", "Underweight", "Normal", "Overweight", "Obese Class I", "Obese Class II", "Obese Class III"};
    private final String[] infoValues = new String[] {"0 - 15", "15 - 16", "16 - 18.5", "18.5 - 25", "25 - 30", "30 - 35", "35 - 40", "40 <"};
    private Float bmi;

    private DatabaseHelper db;

    public UpdateScale(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    protected void onPreExecute() {
        db = new DatabaseHelper(context);
    }

    @Override
    protected Void doInBackground(Void... params) {
        //We get the latest entry from the database and display that value in the correct item of the RecyclerView
        bmi = db.getLatestEntry();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        ScaleAdapter scaleAdapter = new ScaleAdapter(context, scaleValues, infoValues, bmi);
        recyclerView.setAdapter(scaleAdapter);

        db.close();
    }
}