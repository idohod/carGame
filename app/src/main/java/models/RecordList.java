package models;

import java.util.ArrayList;

public class RecordList {
    private ArrayList<Record> records = new ArrayList<>();

    public RecordList() {
        this.records = records;
    }

    public ArrayList<Record> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<Record> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return "RecordList{" +
                "records=" + records +
                '}';
    }
}
