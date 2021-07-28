package com.example.languageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class colorsAcitvity extends AppCompatActivity {
    private MediaPlayer media;
    AudioManager mAudioManager;
    private MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseAudio();
        }
    };
    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                media.pause();
                media.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                media.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseAudio();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors_acitvity);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        ArrayList<word> arrayList = new ArrayList<word>();
        arrayList.add(new word("LAAL", "RED", R.drawable.color_red, R.raw.color_red));
        arrayList.add(new word("PIUDA", "YELLOW", R.drawable.color_dusty_yellow, R.raw.color_yellow));
        arrayList.add(new word("KADA", "BLACK", R.drawable.color_black, R.raw.color_black));
        arrayList.add(new word("HIRVA", "GREEN", R.drawable.color_green, R.raw.color_green));
//        arrayList.add(new word("RAKHDI RANG","GREY",R.drawable.color_gray));
        arrayList.add(new word("TAPKIRI RANG", "BROWN", R.drawable.color_brown, R.raw.color_brown));
        arrayList.add(new word("PANDRA", "WHITE", R.drawable.color_white, R.raw.color_white));


        wordAdapter words = new wordAdapter(this, arrayList, R.color.colorsactivity);
        ListView child = (ListView) findViewById(R.id.colorList);
        child.setAdapter(words);
        child.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                word w = arrayList.get(position);
                releaseAudio();
                int result = mAudioManager.requestAudioFocus(onAudioFocusChangeListener,AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {


                    media = MediaPlayer.create(colorsAcitvity.this, w.getAudio());
                    media.start();
                    media.setOnCompletionListener(completionListener);
                }
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseAudio();
    }

    private void releaseAudio() {
        if (media != null) {
            media.release();
            media = null;
            mAudioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }
}