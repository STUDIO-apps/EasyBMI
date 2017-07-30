package uk.co.appsbystudio.easybmi.pages;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;

import uk.co.appsbystudio.easybmi.R;
import uk.co.appsbystudio.easybmi.database.DatabaseHelper;
import uk.co.appsbystudio.easybmi.database.SavedItemsModel;

public class Calculator extends Fragment {

    private EditText weightText;
    private EditText heightText;
    private String weightString;
    private String heightString;

    private TextView bmiResult;
    private TextView weightResult;
    private TextView heightResult;
    private ConstraintLayout resultsLayout;

    private CheckBox remember;

    private LinearLayout autoSave;

    private DatabaseHelper db;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public Calculator() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_calculator, container, false);

        sharedPreferences = getActivity().getSharedPreferences("remember", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        db = new DatabaseHelper(getActivity());

        Button calculate = view.findViewById(R.id.calculate);

        weightText = view.findViewById(R.id.weightEntry);
        heightText = view.findViewById(R.id.heightEntry);

        remember = view.findViewById(R.id.rememberValue);

        autoSave = view.findViewById(R.id.autoSave);

        bmiResult = view.findViewById(R.id.bmiResult);
        weightResult = view.findViewById(R.id.weightResult);
        heightResult = view.findViewById(R.id.heightResult);
        resultsLayout = view.findViewById(R.id.results);

        switch (sharedPreferences.getInt("remember", 0)) {
            case 1:
                remember.setChecked(true);
                break;
            case 2:
                remember.setChecked(false);
                break;
        }

        heightText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                    calculateBmi();
                    return true;
                }
                return false;
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBmi();
            }
        });

        view.findViewById(R.id.autoSaveYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putInt("remember", 1).apply();
                autoSave.setVisibility(View.INVISIBLE);
            }
        });

        view.findViewById(R.id.autoSaveNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putInt("remember", 2).apply();
                autoSave.setVisibility(View.INVISIBLE);
            }
        });

        return view;
    }

    private void calculateBmi() {
        String weightValueString = weightText.getText().toString();
        String heightValueString = heightText.getText().toString();
        float bmi;

        if (weightValueString.matches("") && heightValueString.matches("")) {
            weightText.setError("Please enter your weight!");
            heightText.setError("Please enter your height!");
        } else if (weightValueString.matches("")) {
            weightText.setError("Please enter your weight!");
        } else if (heightValueString.matches("")) {
            heightText.setError("Please enter your height!");
        } else {
            Float weightValue = Float.parseFloat(weightText.getText().toString());
            Float heightValue = Float.parseFloat(heightText.getText().toString());

            bmi = (float) ((double) Math.round(10D * (double) (weightValue / (heightValue * heightValue))) / 10D);

            if (remember.isChecked()) {
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new java.util.Date());

                SavedItemsModel savedItemsModel = new SavedItemsModel(bmi, weightValue, heightValue, currentDateTimeString);
                db.saveData(savedItemsModel);

                //TODO: Show remember to remember
                if (sharedPreferences.getInt("remember", 0) == 0) {
                    autoSave.setVisibility(View.VISIBLE);
                }

                Intent intent = new Intent("update.scale");
                LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);

            }

            weightText.setText("");
            heightText.setText("");

            bmiResult.setText(getString(R.string.your_bmi_is, bmi));
            weightResult.setText(getString(R.string.weight_value, weightValue));
            heightResult.setText(getString(R.string.height_value, heightValue));
            resultsLayout.setVisibility(View.VISIBLE);

            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(weightText.getWindowToken(), 0);
            imm.hideSoftInputFromWindow(heightText.getWindowToken(), 0);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (weightText != null) weightString = weightText.getText().toString();
        if (heightText != null) heightString = heightText.getText().toString();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (weightString != null) weightText.setText(weightString);
        if (heightString != null) heightText.setText(heightString);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        db.close();
    }
}
