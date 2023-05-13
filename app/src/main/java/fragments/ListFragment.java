package fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.carsgame.R;
import com.google.gson.Gson;

import java.util.ArrayList;

import interfaces.RecordCallback;
import models.Record;
import models.RecordAdapter;
import models.RecordList;
import models.recordsActivity;
import utilities.MySP;


public class ListFragment extends Fragment {

    private ArrayList<Record> allRecords;
    private RecyclerView recyclerView;
    private RecordCallback recordCallback;

    public void setCallBack(RecordCallback recordCallback) {
        this.recordCallback = recordCallback;
    }
        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_list, container, false);

            load();

            recyclerView = view.findViewById(R.id.recordList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            recyclerView.setHasFixedSize(true);
            RecordAdapter recordAdapter = new RecordAdapter(getContext(), allRecords);
            recyclerView.setAdapter(recordAdapter);
            return view;
        }





    private void load(){

        String fromSP =  MySP.getInstance().getString("RECORDS","");
        RecordList recordListFromJson = new Gson().fromJson(fromSP,RecordList.class );

        allRecords = recordListFromJson.getAllRecords();
        if(allRecords==null){
            allRecords = new ArrayList<>();
        }


    }
}