package com.example.languageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView Txt1 = (TextView) findViewById(R.id.colors);
        Txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, colorsAcitvity.class);
                startActivity(intent);
            }
        });
        TextView Txt2= (TextView) findViewById(R.id.family);
        Txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, familyAcitvity.class);
                startActivity(intent);
            }
        });
        TextView Txt3 = (TextView) findViewById(R.id.numbers);
        Txt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, numbersActivity.class);
                startActivity(intent);
            }
        });
        TextView Txt4 = (TextView) findViewById(R.id.phrases);
        Txt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, phrasesActivity.class);
                startActivity(intent);
            }
        });



    }
}