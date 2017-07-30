package uk.co.appsbystudio.easybmi.pages;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.appsbystudio.easybmi.R;

public class BmiScaleDetail extends DialogFragment {

    public BmiScaleDetail() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.dialog_fragment_detail, container, false);
    }
}
