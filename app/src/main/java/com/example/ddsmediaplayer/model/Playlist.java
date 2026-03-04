package com.example.ddsmediaplayer.model;

import java.util.List;

public class Playlist {

    private String playlist_name;
    private String playlist_id;
    private Integer duration;
    private List<Layout> layouts;   // 👈 IMPORTANT

    public List<Layout> getLayouts() {   // 👈 MUST BE PRESENT
        return layouts;
    }
}
