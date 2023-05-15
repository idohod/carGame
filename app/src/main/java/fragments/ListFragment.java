package fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carsgame.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;

import interfaces.RecordCallback;
import models.MenuActivity;
import models.Record;
import models.recordsActivity;
import utilities.RecordAdapter;
import models.RecordList;
import utilities.MySharedPreferences;


public class ListFragment extends Fragment {

    private ArrayList<Record> allRecords;
    private RecyclerView recyclerView;
    private RecordCallback recordCallback;

    public View view;
    public void setCallBack(RecordCallback recordCallback) {

        this.recordCallback = recordCallback;
    }
        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            // Inflate the layout for this fragment
             view = inflater.inflate(R.layout.fragment_list, container, false);

            load();
            findView(view);
            initView();
            return view;
        }

   

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        RecordAdapter recordAdapter = new RecordAdapter(getContext(), allRecords);
        recyclerView.setAdapter(recordAdapter);


        recordAdapter.setRecordCallback(new RecordCallback() {
            @Override
            public void itemClicked(double x,double y) {
                if (recordCallback != null ) {
                    recordCallback.itemClicked(x,y);
                }
            }
        });

    }
    private void findView(View view) {
        recyclerView = view.findViewById(R.id.recordList);
    }


    private void load(){

        String fromSP =  MySharedPreferences.getInstance().getString("RECORDS","");
        RecordList recordListFromJson = new Gson().fromJson(fromSP,RecordList.class );

        allRecords = recordListFromJson.getAllRecords();
        if(allRecords==null){
            allRecords = new ArrayList<>();
        }

    }
}

