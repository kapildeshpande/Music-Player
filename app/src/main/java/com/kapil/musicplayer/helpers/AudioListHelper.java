package com.kapil.musicplayer.helpers;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.kapil.musicplayer.model.Audio;

import java.util.ArrayList;

/*
 * This class contains members and methods related to loading and storing audioList
 * It follows singleton pattern so both mainActivity and mediaPlayerService access same instance
 * Todo: Use Loaders to load data asynchronously
 */


public class AudioListHelper {

    private static final String TAG = "AudioList";

    public static AudioListHelper audioListHelper = new AudioListHelper();
    public int currIndex;
    public ArrayList<Audio> audioArrayList;
    private Context context;

    private void loadCurrIndex () {

        Log.d(TAG, "loadCurrIndex: ");
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        currIndex = pref.getInt("currIndex",0);
    }

    public void saveCurrIndex () {

        Log.d(TAG, "saveCurrIndex: ");
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("currIndex",currIndex);
        editor.apply();
    }

    private String convertMilliToTime (String milliStr) {
        int milli = Integer.parseInt(milliStr);

        int seconds = (milli / 1000);
        int minutes = seconds / 60;
        seconds %= 60;
        String sec;
        if (seconds > 9)
            sec = Integer.toString(seconds);
        else
            sec = "0" + Integer.toString(seconds);

        String min;
        if (minutes > 9)
            min = Integer.toString(minutes);
        else
            min = "0" + Integer.toString(minutes);

        return (min + ":" + sec);
    }

    public void loadAudio (Context context) {

        Log.d(TAG, "loadAudio: start loading data");

        this.context = context;
        currIndex = 0;
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE;
        String path = null;

        Cursor cursor = contentResolver.query(uri,null,selection,null,sortOrder);

        if (cursor != null && cursor.getCount() > 0) {
            audioArrayList = new ArrayList<>();
            while (cursor.moveToNext()) {
                Audio audio = new Audio();
                audio.setData(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
                audio.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
                audio.setAlbum(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
                audio.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));

                Cursor cursorAlbum = context.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                        new String[] {MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                        MediaStore.Audio.Albums._ID+ "=?",
                        new String[] {cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))},
                        null);

                if (cursorAlbum != null && cursorAlbum.getCount() > 0 && cursorAlbum.moveToFirst()) {
                    path = cursorAlbum.getString(cursorAlbum.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                }

                audio.setAlbumArtUri(path);

                cursorAlbum.close();


                audio.setSongDuration(convertMilliToTime(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))));

                audioArrayList.add(audio);
            }
        }
        cursor.close();

        Log.d(TAG, "loadAudio: loading completed");
        loadCurrIndex();

    }


}
