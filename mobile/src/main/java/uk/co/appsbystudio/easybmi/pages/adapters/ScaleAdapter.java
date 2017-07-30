package uk.co.appsbystudio.easybmi.pages.adapters;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;

import uk.co.appsbystudio.easybmi.R;

public class ScaleAdapter extends RecyclerView.Adapter<ScaleAdapter.ViewHolder> {

    private final String[] scaleList;
    private final String[] infoList;
    private final Float bmiResult;
    private final Context context;

    public ScaleAdapter(Context context, String[] scaleList, String[] infoList, Float bmiResult) {
        this.context = context;
        this.scaleList = scaleList;
        this.infoList = infoList;
        this.bmiResult = bmiResult;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scale_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.scale.setText(scaleList[position]);
        holder.info.setText(infoList[position]);


        if (bmiResult < 15 && bmiResult > 0 && scaleList[position].equals("Very severely underweight")) {
            holder.bmiValue.setText(String.format(Locale.ENGLISH, "%.2f", bmiResult));
            holder.scale.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.colorAccent, null));
        } else if (bmiResult > 15 && bmiResult < 16  && scaleList[position].equals("Severely underweight")) {
            holder.bmiValue.setText(String.format(Locale.ENGLISH, "%.2f", bmiResult));
            holder.scale.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.colorAccent, null));
        } else if (bmiResult > 16 && bmiResult < 18.5  && scaleList[position].equals("Underweight")) {
            holder.bmiValue.setText(String.format(Locale.ENGLISH, "%.2f", bmiResult));
            holder.scale.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.colorAccent, null));
        } else if (bmiResult > 18.5 && bmiResult < 25  && scaleList[position].equals("Normal")) {
            holder.bmiValue.setText(String.format(Locale.ENGLISH, "%.2f", bmiResult));
            holder.scale.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.colorAccent, null));
        } else if (bmiResult > 25 && bmiResult < 30  && scaleList[position].equals("Overweight")) {
            holder.bmiValue.setText(String.format(Locale.ENGLISH, "%.2f", bmiResult));
            holder.scale.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.colorAccent, null));
        } else if (bmiResult > 30 && bmiResult < 35  && scaleList[position].equals("Obese Class I")) {
            holder.bmiValue.setText(String.format(Locale.ENGLISH, "%.2f", bmiResult));
            holder.scale.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.colorAccent, null));
        } else if (bmiResult > 35 && bmiResult < 40  && scaleList[position].equals("Obese Class II")) {
            holder.bmiValue.setText(String.format(Locale.ENGLISH, "%.2f", bmiResult));
            holder.scale.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.colorAccent, null));
        } else if (bmiResult > 40  && scaleList[position].equals("Obese Class III")) {
            holder.bmiValue.setText(String.format(Locale.ENGLISH, "%.2f", bmiResult));
            holder.scale.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.colorAccent, null));
        }
    }

    @Override
    public int getItemCount() {
        return scaleList.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final TextView bmiValue;
        final TextView scale;
        final TextView info;

        ViewHolder(View itemView) {
            super(itemView);
            bmiValue = itemView.findViewById(R.id.bmiValue);
            scale = itemView.findViewById(R.id.scaleText);
            info = itemView.findViewById(R.id.info);
        }

    }
}