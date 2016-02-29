package com.example.ciayco.spacevoi;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.IOException;


public class SesKayit {

    public static final String LOG_TAG = "AudioRecordTest";
    public static String mFileName = null;
    public MediaRecorder mRecorder = null;
    public MediaPlayer mPlayer = null;


    public void startPlaying(String kullanici) {
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/SpaceVoi/"+kullanici;
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    public void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    public boolean playerkontrol() {
        if (mPlayer == null )
            return true;
        else
            return false;
    }

    public boolean kayitkontrol() {
        if (mRecorder == null )
            return true;
        else
            return false;
    }

    public void startRecording(String kullanici) {
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/SpaceVoi/"+kullanici+".amr";
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }
    public void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }



}
