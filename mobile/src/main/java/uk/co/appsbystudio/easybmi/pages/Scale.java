package uk.co.appsbystudio.easybmi.pages;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import uk.co.appsbystudio.easybmi.R;

public class Scale extends Fragment {

    private ListView scaleList;
    private ListAdapter mAdapter;
    String[] scaleValues = new String[] {"Very severely underweight", "Severely underweight", "Underweight", "Normal", "Overweight", "Obese"};

    public Scale() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scale, container,false);

        mAdapter = new ArrayAdapter<String>(getActivity(), R.layout.scale_list, R.id.scaleText, scaleValues);

        scaleList = (ListView) view.findViewById(R.id.scaleList);
        scaleList.setAdapter(mAdapter);

        scaleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                detailView();
            }
        });

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mScaleOpen, new IntentFilter("open.scale"));

        return view;
    }

    private BroadcastReceiver mScaleOpen = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            detailView();
        }
    };

    public void detailView() {
        FragmentManager fragmentManager = getFragmentManager();
        DialogFragment detailFragment = new BmiScaleDetail();
        detailFragment.show(fragmentManager, "");
    }
}
