package com.intelik.appadoc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Politica extends AppCompatActivity {

    private TextView politica_scroll_text;
    //private com.intelik.appadoc.utils.JustifyTextView terminos_scroll_text;

    private ImageButton politica_buttonclose;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_politica);


        politica_scroll_text = (TextView) findViewById(R.id.politica_scroll_text);
        //terminos_scroll_text = (com.intelik.appadoc.utils.JustifyTextView) findViewById(R.id.terminos_scroll_text);
        politica_scroll_text.setMovementMethod(new ScrollingMovementMethod());
        politica_buttonclose = (ImageButton) findViewById(R.id.politica_buttonclose);

        politica_buttonclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });



    }
}