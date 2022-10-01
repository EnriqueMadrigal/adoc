package com.intelik.appadoc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.intelik.appadoc.R;

import java.util.ArrayList;

import Models.Custom;
import com.intelik.appadoc.interfaces.IViewHolderClick;
import com.squareup.picasso.Picasso;

public class MainQuestionsAdapter extends RecyclerView.Adapter<MainQuestionsAdapter.ViewHolder>{
    private Context _context;
    private IViewHolderClick _listener;
    private ArrayList<Custom> _items;
    Integer row_index = -1;




    public MainQuestionsAdapter(Context context, ArrayList<Custom> items, IViewHolderClick listener) {
        _context = context;
        _items = items;
        _listener = listener;


    }

    @Override
    public int getItemCount() {
        return _items.size();
    }


    @Override
    public MainQuestionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(_context).inflate(R.layout.main_questions, parent, false);
        MainQuestionsAdapter.ViewHolder viewHolder = new MainQuestionsAdapter.ViewHolder(view, new IViewHolderClick() {
            @Override
            public void onClick(int position) {
                  if (_listener != null) {
                      _listener.onClick(position);
                  }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MainQuestionsAdapter.ViewHolder holder, final int position) {
        Custom curCustom = _items.get(position);
        holder.get_labelDescription().setText(String.valueOf(curCustom.get_name()));
        int curValue = curCustom.getValue1();
        holder.get_labelValue().setText(String.valueOf(curValue) + " " + "pts");

        holder.getButton().setTag(curCustom.get_id());


        String curImage = curCustom.get_desc();

        if (curImage.length()>6) {
            Picasso.get()
                    .load(curImage)
                    .placeholder(R.drawable.user_placeholder)
                    //.error(R.drawable.user_placeholder_error)
                    .into(holder.mainImage);
        }

        else
        {
            holder.mainImage.setImageResource(R.drawable.user_placeholder);
        }



        /*

        switch (curImage){

            case "example1":
                holder.mainImage.setImageResource(R.drawable.example1);
                break;

            case "example2":
                holder.mainImage.setImageResource(R.drawable.example2);
                break;

            case "example3":
                holder.mainImage.setImageResource(R.drawable.example3);
                break;

        }
    */


    }




    ////////////////


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView get_labelDescription() {
            return _labelDescription;
        }

        public TextView get_labelValue() {
            return _labelValue;
        }



        private TextView _labelDescription;
        private TextView _labelValue;

        private int _index;
        private IViewHolderClick _listener;

        public ImageView getMainImage() {
            return mainImage;
        }

        private ImageView mainImage;

        public AppCompatButton getButton() {
            return button;
        }

        private androidx.appcompat.widget.AppCompatButton button;


        public ViewHolder(View view, IViewHolderClick listener) {
            super(view);
            //view.setOnClickListener(this);
            _labelDescription = (TextView) view.findViewById(R.id.main_question);
            _labelValue = (TextView) view.findViewById(R.id.main_points);
            mainImage = (ImageView) view.findViewById(R.id.main_image);
            button = (androidx.appcompat.widget.AppCompatButton) view.findViewById(R.id.main_responder);
            _listener = listener;

            button.setOnClickListener(this);
        }





        public int getIndex() {
            return _index;
        }
        public void setIndex(int index) {
            _index = index;
        }

        @Override
        public void onClick(View v) {
            if (_listener != null) {

                if (v instanceof androidx.appcompat.widget.AppCompatButton)

                   //int curTag = ((androidx.appcompat.widget.AppCompatButton) v).getTag();

                //_listener.onClick(_index);
                    _listener.onClick((int )v.getTag());
            }
        }
    }



}
