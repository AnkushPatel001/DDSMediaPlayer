package com.example.ddsmediaplayer.model;
import java.util.List;

public class Layout {

    private String id;
    private int duration;
    private LayoutConfig config;
    private List<Zone> zones;

    public List<Zone> getZones() {
        return zones;
    }
}
