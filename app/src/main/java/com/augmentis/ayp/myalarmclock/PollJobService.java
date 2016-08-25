package com.augmentis.ayp.myalarmclock;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Apinya on 8/25/2016.
 */

@TargetApi(21)
public class PollJobService extends JobService {

    private static final String TAG = "PollJobService";

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }




    public class PollTask extends AsyncTask<JobParameters, Void, Void> {

        @Override
        protected Void doInBackground(JobParameters... params) {


            Log.d(TAG, "Job poll running");
            jobFinished(params[0], false);

            /////

            {
                String query = PhotoGalleryPreference.getStoredSearchKey(PollJobService.this);
                String storedId = PhotoGalleryPreference.getStoredLastId(PollJobService.this);

                List<GalleryItem> galleryItemList = new ArrayList<>();

                FlickrFetcher flickrFetcher = new FlickrFetcher();
                if(query == null) {
                    flickrFetcher.getRecentPhotos(galleryItemList);
                } else {
                    flickrFetcher.searchPhotos(galleryItemList, query);
                }

                if (galleryItemList.size() == 0) {
                    return null;
                }

                Log.i(TAG, "Found search :");

                String newestId = galleryItemList.get(0).getId(); // fetching first item

                if (newestId.equals(storedId)) {
                    Log.i(TAG, "No new item");

                } else {
                    Log.i(TAG, "New item found");

                    Resources res = getResources();
                    Intent i = PhotoGalleryActivity.newIntent(PollJobService.this);
                    PendingIntent pi = PendingIntent.getActivity(PollJobService.this, 0, i, 0);

                    //Build to build notification object
                    NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(PollJobService.this);
                    notiBuilder.setTicker(res.getString(R.string.new_picture_arriving));
                    notiBuilder.setSmallIcon(android.R.drawable.ic_menu_report_image);
                    notiBuilder.setContentTitle(res.getString(R.string.new_picture_title));
                    notiBuilder.setContentText(res.getString(R.string.new_picture_content));
                    notiBuilder.setContentIntent(pi);
                    notiBuilder.setAutoCancel(true); //if it already have it not appear

                    Notification notification = notiBuilder.build(); // << Build notification from builder

                    //Get notification manager from contect
                    NotificationManagerCompat nm = NotificationManagerCompat.from(PollJobService.this);
                    nm.notify(Long.valueOf(newestId).intValue(), notification);

                    new Screen().on(PollJobService.this);

//            nm.notify(0, notification);
                }
                PhotoGalleryPreference.setStoredLastId(PollJobService.this, newestId);
            }

            /////

            return null;
        }
    }
}
