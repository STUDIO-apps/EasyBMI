package uk.co.appsbystudio.easybmi.pages.adapters;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import uk.co.appsbystudio.easybmi.R;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList bmi;
    private final ArrayList weight;
    private final ArrayList height;
    private final ArrayList timeStamp;

    public HistoryListAdapter(Context context, ArrayList bmi, ArrayList weight, ArrayList height, ArrayList timeStamp) {
        this.context = context;
        this.bmi = bmi;
        this.weight = weight;
        this.height = height;
        this.timeStamp = timeStamp;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bmiText.setText(context.getString(R.string.bmi_history, bmi.get(position).toString()));
        holder.weightText.setText(context.getString(R.string.weight_history, weight.get(position).toString()));
        holder.heightText.setText(context.getString(R.string.height_history, height.get(position).toString()));
        holder.dateTimeText.setText(timeStamp.get(position).toString());

        if (holder.getAdapterPosition() == 0) {
            //Highlight the latest result
            holder.bmiText.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.colorAccent, null));
        }

    }

    @Override
    public int getItemCount() {
        return bmi.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final TextView bmiText;
        final TextView weightText;
        final TextView heightText;
        final TextView dateTimeText;

        ViewHolder(View itemView) {
            super(itemView);
            bmiText = itemView.findViewById(R.id.bmiText);
            weightText = itemView.findViewById(R.id.weightText);
            heightText = itemView.findViewById(R.id.heightText);
            dateTimeText = itemView.findViewById(R.id.dateTimeText);
        }

    }
}
