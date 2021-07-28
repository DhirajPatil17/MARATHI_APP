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

import java.util.ArrayList;

public class phrasesActivity extends AppCompatActivity {
    AudioManager mAudioManager;
    MediaPlayer media;
    private MediaPlayer.OnCompletionListener completionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseAudio();
        }
    };
     private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener=new AudioManager.OnAudioFocusChangeListener() {
         @Override
         public void onAudioFocusChange(int focusChange) {
             if(focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT||focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)
             {
                 media.pause();
                 media.seekTo(0);
             }
             else if(focusChange==AudioManager.AUDIOFOCUS_GAIN)
             {
                 media.start();
             }
             else if(focusChange==AudioManager.AUDIOFOCUS_LOSS)
             {
                 releaseAudio();
             }
         }
     };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_phrases);
        mAudioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        ArrayList<word> arrayList=new ArrayList<word>();
        arrayList.add(new word("MI JEEVAN KARAT AHE","IAM EATING",R.raw.phrase_eating));
        arrayList.add(new word("MI VIDHYALAY MADHE AHE","IAM GOING TO SCHOOL",R.raw.phrase_school));
        arrayList.add(new word("MI GAAVI JAAT AHE","IAM GOING TO VILLAGE",R.raw.phrase_village));
        arrayList.add(new word("MI CHALAT AHE","IAM WALING",R.raw.phrase_walking));
       wordAdapter root=new wordAdapter(this,arrayList,R.color.phrasesactivity);
        ListView child=(ListView)findViewById(R.id.phrases_list);
        child.setAdapter(root);
        child.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                word w=arrayList.get(position);
                releaseAudio();
                int result=mAudioManager.requestAudioFocus(onAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {


                    media = MediaPlayer.create(phrasesActivity.this, w.getAudio());
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

    private void releaseAudio()
    {
        if(media!=null)
        {
            media.release();
            media=null;
            mAudioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }
}