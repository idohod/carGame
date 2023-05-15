package models;

public class Record {

    private int score;
    private double latitude;
    private double longitude;

    public Record(int score, double x, double y) {
        this.score = score;
        this.latitude = x;
        this.longitude = y;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
