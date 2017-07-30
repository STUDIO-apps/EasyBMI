package uk.co.appsbystudio.easybmi.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import uk.co.appsbystudio.easybmi.database.DatabaseHelper;
import uk.co.appsbystudio.easybmi.database.SavedItemsModel;
import uk.co.appsbystudio.easybmi.pages.adapters.HistoryListAdapter;

public class GetBMI extends AsyncTask<Void, Void, Void> {

    private final Context context;
    private final RecyclerView recyclerView;

    private final ArrayList<Float> bmi = new ArrayList<>();
    private final ArrayList<Float> weight = new ArrayList<>();
    private final ArrayList<Float> height = new ArrayList<>();
    private final ArrayList<String> timeStamp = new ArrayList<>();

    private DatabaseHelper db;
    private List<SavedItemsModel> savedItemsModels;

    public GetBMI(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    protected void onPreExecute() {
        db = new DatabaseHelper(context);
        savedItemsModels = db.getAllData();
    }

    @Override
    protected Void doInBackground(Void... params) {
        for (SavedItemsModel savedItemsModel : savedItemsModels) {
            bmi.add(savedItemsModel.getBmi());
            weight.add(savedItemsModel.getWeight());
            height.add(savedItemsModel.getHeight());
            timeStamp.add(savedItemsModel.getDateTime());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        HistoryListAdapter historyListAdapter = new HistoryListAdapter(context, bmi, weight, height, timeStamp);
        recyclerView.setAdapter(historyListAdapter);

        db.close();
    }
}
