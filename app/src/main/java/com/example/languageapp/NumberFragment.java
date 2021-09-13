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


public class NumberFragment extends Fragment {
    MediaPlayer media;
    AudioManager mAudioManager;
    MediaPlayer.OnCompletionListener completionListener= new MediaPlayer.OnCompletionListener() {
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


    public NumberFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View rootView=inflater.inflate(R.layout.word, container, false);
        mAudioManager=(AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        ArrayList<word> arrayList=new ArrayList<word>();
        arrayList.add(new word("EKK","ONE",R.drawable.number_one,R.raw.number_one));
        arrayList.add(new word("DOON","TWO",R.drawable.number_two,R.raw.number_two));
        arrayList.add(new word("TEEN","THREE",R.drawable.number_three,R.raw.number_three));
        arrayList.add(new word("CHAR","FOUR",R.drawable.number_four,R.raw.number_char));
        arrayList.add(new word("PAACH","FIVE",R.drawable.number_five,R.raw.number_five));
        arrayList.add(new word("SAHHA","SIX",R.drawable.number_six,R.raw.number_six));
        arrayList.add(new word("SAAT","SEVEN",R.drawable.number_seven,R.raw.number_seven));
        arrayList.add(new word("EAAT","EIGHT",R.drawable.number_eight,R.raw.number_eight));
        arrayList.add(new word("NAAU","NINE",R.drawable.number_nine,R.raw.number_nine));
        arrayList.add(new word("DAHA","TEN",R.drawable.number_ten,R.raw.number_ten));
        wordAdapter root=new wordAdapter(getActivity(), arrayList,R.color.numbersactivity);
        ListView child=(ListView)rootView.findViewById(R.id.list);
        child.setAdapter(root);
        child.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                word w=arrayList.get(position);
                releaseAudio();
                int result=mAudioManager.requestAudioFocus(onAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {


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