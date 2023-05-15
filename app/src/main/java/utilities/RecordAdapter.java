package utilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carsgame.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

import interfaces.RecordCallback;
import models.Record;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordViewHolder> {

    private Context context;

    private ArrayList<Record> recordsList;
    private RecordCallback recordCallback;
    private static final String RED_COLOR = "#E45858";

    public RecordAdapter(Context context, ArrayList<Record> recordsList) {
        this.context = context;
        this.recordsList = recordsList;
    }

    public void setRecordCallback(RecordCallback recordCallback) {
        this.recordCallback = recordCallback;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.record_item, parent, false);
        return new RecordViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        Record record = recordsList.get(position);
        holder.theScore.setText("" + record.getScore());
        if (position == 0) {
            holder.textScore.setText("best score: ");
            holder.textScore.setTextColor(Color.parseColor(RED_COLOR));
            holder.theScore.setTextColor(Color.parseColor(RED_COLOR));


        } else if (position == 1) {
            holder.textScore.setText("2nd score: ");

        } else {
            holder.textScore.setText((position + 1) + "th score: ");
        }
    }

    @Override
    public int getItemCount() {

        return recordsList.size();
    }


    public Record getItem(int position) {
        return this.recordsList.get(position);
    }

    public class RecordViewHolder extends RecyclerView.ViewHolder {


        public MaterialTextView textScore;
        public MaterialTextView theScore;

        @SuppressLint("MissingPermission")
        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            textScore = itemView.findViewById(R.id.text_score);
            theScore = itemView.findViewById(R.id.the_score);

            itemView.setOnClickListener(v -> {
                if (recordCallback != null) {
                    recordCallback.itemClicked(getItem(getAdapterPosition()).getLatitude(),getItem(getAdapterPosition()).getLongitude());
                }

            });

        }
    }
}

