package com.example.languageapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class wordAdapter extends ArrayAdapter<word> {
    private final int mColorId;
    private static  final String LOG_TAG=word.class.getSimpleName();
    public wordAdapter(Activity context, ArrayList<word> arraylist,int ColorId)
    {

        super(context,0,arraylist);
        mColorId=ColorId;
    }
    @NonNull
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        View listItemView=convertView;
        if(listItemView==null)
        {
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.list_view,parent,false);
        }
        word currentword=getItem(position);
        TextView txt1=(TextView) listItemView.findViewById(R.id.txt1);
        txt1.setText(currentword.getMarathi());
        TextView txt2=(TextView)listItemView.findViewById(R.id.txt2);
        txt2.setText(currentword.getEnglish());
        ImageView img1=(ImageView)listItemView.findViewById(R.id.img1);
        if(currentword.isImage())
        {
            img1.setImageResource(currentword.getImageId());
        }
        else
        {
            img1.setVisibility(View.GONE);
        }
        View textContainer=(View)listItemView.findViewById(R.id.text_container);
        int color= ContextCompat.getColor(getContext(),mColorId);
        textContainer.setBackgroundColor(color);

        return listItemView;
    }
}
