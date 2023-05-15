package models;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class RecordList {

    private ArrayList<Record> allRecords;

    public RecordList() {
        this.allRecords = new ArrayList<>();    }

    public  ArrayList<Record> getAllRecords() {
        return allRecords;
    }

    public void setAllRecords(ArrayList<Record> allRecords) {
        this.allRecords = allRecords;
    }

    @NonNull
    @Override
    public String toString() {
        return "RecordList{" +
                "allRecords=" + allRecords +
                '}';
    }
}
