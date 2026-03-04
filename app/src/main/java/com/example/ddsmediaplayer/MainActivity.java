package com.example.ddsmediaplayer;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ddsmediaplayer.model.MediaItem;
import com.example.ddsmediaplayer.model.PlayableResponse;
import com.example.ddsmediaplayer.model.Zone;
import com.example.ddsmediaplayer.player.ZoneMediaPlayer;
import com.example.ddsmediaplayer.utils.MediaDownloader;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FrameLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootLayout = findViewById(R.id.main);

        // Load JSON
        String json = loadJSONFromAsset(this);

        if (json == null) {
            Log.e("ERROR", "JSON not loaded");
            return;
        }

        // Parse JSON
        Gson gson = new Gson();
        PlayableResponse response =
                gson.fromJson(json, PlayableResponse.class);

        List<Zone> zones = response
                .getPlayable_data()
                .getPlaylists().get(0)
                .getLayouts().get(0)
                .getZones();

        Log.d("TEST", "Total Zones: " + zones.size());

        // Screen Size
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;

        // Create Zones
        for (Zone zone : zones) {

            float xPercent = zone.getConfig().getX();
            float yPercent = zone.getConfig().getY();
            float wPercent = zone.getConfig().getW();
            float hPercent = zone.getConfig().getH();

            int zoneWidth = (int) (screenWidth * wPercent);
            int zoneHeight = (int) (screenHeight * hPercent);

            int leftMargin = (int) (screenWidth * xPercent);
            int topMargin = (int) (screenHeight * yPercent);

            FrameLayout zoneLayout = new FrameLayout(this);

            FrameLayout.LayoutParams params =
                    new FrameLayout.LayoutParams(zoneWidth, zoneHeight);

            params.leftMargin = leftMargin;
            params.topMargin = topMargin;

            zoneLayout.setLayoutParams(params);

            rootLayout.addView(zoneLayout);

            // Media Items
            List<MediaItem> mediaItems =
                    zone.getSequence().getData();

            // Download all media files
            for (MediaItem item : mediaItems) {

                if (!item.getType().equals("web")) {

                    MediaDownloader.downloadFile(
                            this,
                            item.getPath(),
                            item.getName()
                    );
                }
            }

            // Start Player
            ZoneMediaPlayer player =
                    new ZoneMediaPlayer(zoneLayout, mediaItems);

            player.start();
        }
    }

    // Load JSON
    private String loadJSONFromAsset(Context context) {
        try {
            InputStream is = context.getAssets().open("sample.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}