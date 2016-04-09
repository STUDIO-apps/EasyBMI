package uk.co.appsbystudio.easybmi.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.sql.Date;
import java.text.DateFormat;

import uk.co.appsbystudio.easybmi.R;
import uk.co.appsbystudio.easybmi.database.DatabaseHelper;
import uk.co.appsbystudio.easybmi.database.SavedItemsModel;

public class Calculator extends Fragment {

    Spinner weightSpinner;
    Spinner heightSpinner;

    Button calculate;

    EditText weightText;
    EditText heightText;

    Integer weightType = 0;
    Integer heightType = 0;

    Float weightValue;
    Float heightValue;

    CheckBox remember;

    DatabaseHelper db;

    public Calculator() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator, container, false);

        final Intent changeTab = new Intent("change.tab");
        final Intent openScaleSection = new Intent("open.scale");

        calculate = (Button) view.findViewById(R.id.calculate);

        weightText = (EditText) view.findViewById(R.id.weightEntry);
        heightText = (EditText) view.findViewById(R.id.heightEntry);

        weightSpinner = (Spinner) view.findViewById(R.id.weightUnit);
        heightSpinner = (Spinner) view.findViewById(R.id.heightUnit);

        remember = (CheckBox) view.findViewById(R.id.rememberValue);

        ArrayAdapter<CharSequence> weightAdapter = ArrayAdapter.createFromResource(getContext(), R.array.weight_units, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> heightAdapter = ArrayAdapter.createFromResource(getContext(), R.array.height_units, android.R.layout.simple_spinner_item);

        weightAdapter.setDropDownViewResource(android.R.layout.select_dialog_item);
        heightAdapter.setDropDownViewResource(android.R.layout.select_dialog_item);

        weightSpinner.setAdapter(weightAdapter);
        heightSpinner.setAdapter(heightAdapter);

        weightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    weightType = 0;
                } else if (position == 1) {
                    weightType = 1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        heightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    heightType = 0;
                } else if (position == 1) {
                    heightType = 1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DatabaseHelper(getActivity());

                String weightValueString = weightText.getText().toString();
                String heightValueString = heightText.getText().toString();
                float bmi;
                String bmiValueString;

                if (weightValueString.matches("") && heightValueString.matches("")) {
                    weightText.setError("Please enter your weight!");
                    heightText.setError("Please enter your height!");
                } else if (weightValueString.matches("")) {
                    weightText.setError("Please enter your weight!");
                } else if (heightValueString.matches("")) {
                    heightText.setError("Please enter your height!");
                } else {
                    weightValue = Float.parseFloat(weightText.getText().toString());
                    heightValue = Float.parseFloat(heightText.getText().toString());

                    bmi = (float) ((double) Math.round(10D * (double) (weightValue / (heightValue * heightValue))) / 10D);
                    bmiValueString = String.valueOf(bmi);



                    if (remember.isChecked()) {
                        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new java.util.Date());

                        SavedItemsModel savedItemsModel = new SavedItemsModel(null, bmi, weightValue, heightValue, currentDateTimeString);
                        db.saveData(savedItemsModel);
                    }

                    LocalBroadcastManager.getInstance(getContext()).sendBroadcast(new Intent(changeTab));
                    LocalBroadcastManager.getInstance(getContext()).sendBroadcast(new Intent(openScaleSection));
                }

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(weightText.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(heightText.getWindowToken(), 0);
            }
        });

        return view;
    }

}
