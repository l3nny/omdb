package com.example.yotis.omdb.models;

public class Ratings {
    String Source;
    String Value;

    public Ratings() {
    }

    public Ratings(String source, String value) {
        Source = source;
        Value = value;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}
