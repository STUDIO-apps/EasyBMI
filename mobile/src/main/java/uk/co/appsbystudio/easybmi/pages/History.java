package uk.co.appsbystudio.easybmi.pages;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import uk.co.appsbystudio.easybmi.R;
import uk.co.appsbystudio.easybmi.database.DatabaseHelper;
import uk.co.appsbystudio.easybmi.database.SavedItemsModel;

public class History extends Fragment {

    private ListView historyList;
    private ListAdapter historyListAdapter;

    List bmiVales;
    List dateValues;

    DatabaseHelper db;

    public History() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        bmiVales = new ArrayList<>();
        dateValues = new ArrayList<>();

        historyList = (ListView) view.findViewById(R.id.historyList);

        db = new DatabaseHelper(getActivity());

        List<SavedItemsModel> savedItemsModels = db.getBMI();
        for (SavedItemsModel id : savedItemsModels) {
            bmiVales.add(id.getBmi());
            dateValues.add(id.getDateTime());
        }

        db.close();

        historyListAdapter = new ArrayAdapter<String>(getActivity(), R.layout.history_list, R.id.oldBmiValue, bmiVales);

        historyList.setAdapter(historyListAdapter);

        return view;
    }

}
