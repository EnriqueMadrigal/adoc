package com.intelik.appadoc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class Terminos extends AppCompatActivity {

    private TextView terminos_scroll_text;
    //private com.intelik.appadoc.utils.JustifyTextView terminos_scroll_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminos);


        terminos_scroll_text = (TextView) findViewById(R.id.terminos_scroll_text);
        //terminos_scroll_text = (com.intelik.appadoc.utils.JustifyTextView) findViewById(R.id.terminos_scroll_text);
        terminos_scroll_text.setMovementMethod(new ScrollingMovementMethod());



    }
}