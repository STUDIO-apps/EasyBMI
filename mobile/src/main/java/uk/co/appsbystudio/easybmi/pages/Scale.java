package uk.co.appsbystudio.easybmi.pages;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.appsbystudio.easybmi.R;
import uk.co.appsbystudio.easybmi.utils.UpdateScale;

public class Scale extends Fragment {

    public Scale() {
        //Required
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scale, container,false);

        final RecyclerView scaleList = view.findViewById(R.id.scaleList);
        scaleList.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        scaleList.setLayoutManager(layoutManager);

        new UpdateScale(getContext(), scaleList).execute();

        BroadcastReceiver getLatestBMI = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                new UpdateScale(getContext(), scaleList).execute();
            }
        };

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(getLatestBMI, new IntentFilter("update.scale"));

        return view;
    }

}
