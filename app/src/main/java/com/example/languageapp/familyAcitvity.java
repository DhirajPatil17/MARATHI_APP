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

public class familyAcitvity extends AppCompatActivity {
     MediaPlayer media;
     AudioManager mAudioManager;
     MediaPlayer.OnCompletionListener completionListener=new MediaPlayer.OnCompletionListener() {
         @Override
         public void onCompletion(MediaPlayer mp) {
             releaseAudio();

         }
     };
    AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener=new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
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
        setContentView(R.layout.activity_family_acitvity);
        mAudioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        ArrayList<word> arrayList=new ArrayList<word>();
        arrayList.add(new word("AAI","MOTHER",R.drawable.family_mother,R.raw.family_mother));
        arrayList.add(new word("BABA","FATHER",R.drawable.family_father,R.raw.family_father));
        arrayList.add(new word("BHAU","BROTHER",R.drawable.family_older_brother,R.raw.family_brother));
        arrayList.add(new word("BAHIN","SISTER",R.drawable.family_younger_sister,R.raw.family_sister));
        arrayList.add(new word("AJOBA","GRANDFATHER",R.drawable.family_grandfather,R.raw.family_grandfather));
        arrayList.add(new word("AAJI","GRANDMOTHER",R.drawable.family_grandmother,R.raw.family_grandmother));
        arrayList.add(new word("MULGA","SON",R.drawable.family_son,R.raw.family_son));
        arrayList.add(new word("MULGI","DAUGHTER",R.drawable.family_daughter,R.raw.family_daughter));
        wordAdapter root= new wordAdapter(this,arrayList,R.color.familyactivity);
        ListView child=(ListView)findViewById(R.id.family_list);
        child.setAdapter(root);
        child.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                word w=arrayList.get(position);
                releaseAudio();
                int result=mAudioManager.requestAudioFocus(onAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    media = MediaPlayer.create(familyAcitvity.this, w.getAudio());
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