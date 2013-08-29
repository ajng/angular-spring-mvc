package com.splendidcode.angular.sample.home;

public class HomeViewModel {
    public String text;
    private int timesVisited;

    public HomeViewModel(String text) {
        this.text = text;
    }

    public void setTimesVisited(int timesVisited) {
        this.timesVisited = timesVisited;
    }

    public int getTimesVisited() {
        return timesVisited;
    }

}
