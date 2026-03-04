package com.example.ddsmediaplayer.player;

import android.os.Handler;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.ddsmediaplayer.model.MediaItem;

import java.util.List;

public class ZoneMediaPlayer {

    private FrameLayout container;
    private List<MediaItem> mediaList;

    private int currentIndex = 0;
    private Handler handler = new Handler();

    public ZoneMediaPlayer(FrameLayout container, List<MediaItem> mediaList) {
        this.container = container;
        this.mediaList = mediaList;
    }

    public void start() {
        playNext();
    }

    private void playNext() {

        if (mediaList == null || mediaList.size() == 0) return;

        MediaItem item = mediaList.get(currentIndex);

        container.removeAllViews();

        android.view.View view =
                com.example.ddsmediaplayer.utils.MediaViewFactory
                        .createView(container.getContext(), item);

        container.addView(view);

        int duration = item.getDuration() * 1000;

        handler.postDelayed(() -> {

            currentIndex++;

            if (currentIndex >= mediaList.size()) {
                currentIndex = 0;
            }

            playNext();

        }, duration);
    }
}