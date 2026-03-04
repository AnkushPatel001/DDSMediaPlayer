package com.example.ddsmediaplayer.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

public class MediaDownloader {

    public static void downloadFile(Context context, String url, String fileName) {

        // File location
        File file = new File(
                context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
                fileName
        );

        // If file already downloaded → skip download
        if (file.exists()) {
            return;
        }

        DownloadManager.Request request =
                new DownloadManager.Request(Uri.parse(url));

        request.setNotificationVisibility(
                DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
        );

        request.setDestinationInExternalFilesDir(
                context,
                Environment.DIRECTORY_DOWNLOADS,
                fileName
        );

        DownloadManager manager =
                (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        manager.enqueue(request);
    }
}