package com.example.ddsmediaplayer.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.ddsmediaplayer.model.MediaItem;
import com.squareup.picasso.Picasso;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

public class MediaViewFactory {

    public static View createView(Context context, MediaItem item) {

        switch (item.getType()) {

            // IMAGE
            case "image":

                ImageView imageView = new ImageView(context);
                imageView.setLayoutParams(
                        new ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                        )
                );

                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                Picasso.get()
                        .load(item.getPath())
                        .into(imageView);

                return imageView;


            // VIDEO
            case "video":

                PlayerView playerView = new PlayerView(context);
                playerView.setLayoutParams(
                        new ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                        )
                );

                playerView.setUseController(false);

                ExoPlayer player = new ExoPlayer.Builder(context).build();
                playerView.setPlayer(player);

                com.google.android.exoplayer2.MediaItem mediaItem =
                        com.google.android.exoplayer2.MediaItem.fromUri(item.getPath());

                player.setMediaItem(mediaItem);
                player.prepare();
                player.play();

                return playerView;


            // WEB
            case "web":

                WebView webView = new WebView(context);

                webView.setLayoutParams(
                        new ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                        )
                );

                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setDomStorageEnabled(true);
                webView.setWebViewClient(new WebViewClient());

                webView.loadUrl(item.getPath());

                return webView;


            default:

                ImageView fallback = new ImageView(context);
                return fallback;
        }
    }
}