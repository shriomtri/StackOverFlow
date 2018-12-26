package com.stackflow.app.service.model;

public class SelectedInterest {
    private String tag;
    private int position;

    public SelectedInterest(String tag, int position) {
        this.tag = tag;
        this.position = position;
    }

    public String getTag() {
        return tag;
    }

    public int getPosition() {
        return position;
    }
}
