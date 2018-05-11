package com.example.yotis.omdb.models;

import com.example.yotis.omdb.repository.Repository;

public class movieSearch extends Repository {
    String t;
    String y;
    String plot;

    public movieSearch(String t, String y, String plot) {
        this.t = t;
        this.y = y;
        this.plot = plot;
    }

    public movieSearch() {
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }
}
