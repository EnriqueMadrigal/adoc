package com.intelik.appadoc.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.intelik.appadoc.R;

import java.util.ArrayList;

import Models.Custom;

public class CustomAdapter2 extends BaseAdapter {
    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    private ArrayList<Custom> values;
    LayoutInflater inflater;


    public CustomAdapter2(Context context, ArrayList<Custom> values) {
        //super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount(){
        return values.size();
    }

    @Override
    public Custom getItem(int position){
        return values.get(position);

    }

    @Override
    public long getItemId(int position){
        return position;
    }


    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item

        view = inflater.inflate(R.layout.spinner_phonecodes, null);

        TextView label = (TextView) view.findViewById(R.id.spinner_text);
        ImageView img = (ImageView) view.findViewById(R.id.spinner_img);

        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)

        label.setText("+"+ values.get(position).get_id().toString());
        img.setImageResource(context.getResources().getIdentifier(values.get(position).get_name(), "drawable",context.getPackageName()));

        // And finally return your dynamic (or custom) view for each spinner item
        return view;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want



}
