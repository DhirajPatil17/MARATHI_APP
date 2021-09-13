package com.example.languageapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class ColorFragment extends Fragment {
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



    public ColorFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View rootView= inflater.inflate(R.layout.word, container, false);
        mAudioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);

        ArrayList<word> arrayList = new ArrayList<word>();
        arrayList.add(new word("LAAL", "RED", R.drawable.color_red, R.raw.color_red));
        arrayList.add(new word("PIUDA", "YELLOW", R.drawable.color_dusty_yellow, R.raw.color_yellow));
        arrayList.add(new word("KADA", "BLACK", R.drawable.color_black, R.raw.color_black));
        arrayList.add(new word("HIRVA", "GREEN", R.drawable.color_green, R.raw.color_green));
//        arrayList.add(new word("RAKHDI RANG","GREY",R.drawable.color_gray));
        arrayList.add(new word("TAPKIRI RANG", "BROWN", R.drawable.color_brown, R.raw.color_brown));
        arrayList.add(new word("PANDRA", "WHITE", R.drawable.color_white, R.raw.color_white));


        wordAdapter words = new wordAdapter(getActivity(), arrayList, R.color.colorsactivity);
        ListView child = (ListView)rootView.findViewById(R.id.list);
        child.setAdapter(words);
        child.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                word w = arrayList.get(position);
                releaseAudio();
                int result = mAudioManager.requestAudioFocus(onAudioFocusChangeListener,AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {


                    media = MediaPlayer.create(getActivity(), w.getAudio());
                    media.start();
                    media.setOnCompletionListener(completionListener);
                }
            }
        });

        return rootView;
    }
    @Override
    public void onStop() {
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
