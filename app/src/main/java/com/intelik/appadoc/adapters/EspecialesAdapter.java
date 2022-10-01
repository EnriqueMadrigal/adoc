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

public class EspecialesAdapter extends RecyclerView.Adapter<EspecialesAdapter.ViewHolder>{
    private Context _context;
    private IViewHolderClick _listener;
    private ArrayList<Custom> _items;
    Integer row_index = -1;




    public EspecialesAdapter(Context context, ArrayList<Custom> items, IViewHolderClick listener) {
        _context = context;
        _items = items;
        _listener = listener;


    }

    @Override
    public int getItemCount() {
        return _items.size();
    }

    public void removeItem(int position) {
        _items.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Custom item, int position) {
        _items.add(position, item);
        notifyItemInserted(position);
    }

    public ArrayList<Custom> getData() {
        return _items;
    }




    @Override
    public EspecialesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(_context).inflate(R.layout.especiales_item, parent, false);
        EspecialesAdapter.ViewHolder viewHolder = new EspecialesAdapter.ViewHolder(view, new IViewHolderClick() {
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
    public void onBindViewHolder(EspecialesAdapter.ViewHolder holder, final int position) {
        Custom curCustom = _items.get(position);

        holder.get_labelDescription().setText(curCustom.get_desc());
        holder.get_labelName().setText(curCustom.get_name());

        //Si tiene imagen

        if (curCustom.getLink().length()>1) {
            Picasso.get()
                    .load(curCustom.getLink())
                    .placeholder(R.drawable.user_placeholder)
                    //.error(R.drawable.user_placeholder_error)
                    .into(holder.mainImage);
        }

        else
        {
            holder.mainImage.setImageResource(R.drawable.user_placeholder);
        }

        //holder.mainImage.

        int curValue = curCustom.getValue1();


        switch (curValue){

            case 1000:
                holder.mainImage.setImageResource(R.drawable.star1000);
                break;

            case 2000:
                holder.mainImage.setImageResource(R.drawable.start2000);
                break;


        }



    }




    ////////////////


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView get_labelDescription() {
            return _labelDescription;
        }
        public TextView get_labelName() { return _labelName;
        }



        private TextView _labelDescription;
        private TextView _labelName;


        private int _index;
        private IViewHolderClick _listener;

        public ImageView getMainImage() {
            return mainImage;
        }

        private ImageView mainImage;


        private androidx.appcompat.widget.AppCompatButton button;


        public ViewHolder(View view, IViewHolderClick listener) {
            super(view);
            //view.setOnClickListener(this);
            _labelDescription = (TextView) view.findViewById(R.id.especiales_lblDescr);
            mainImage = (ImageView) view.findViewById(R.id.especiales_image);
            _labelName = (TextView) view.findViewById(R.id.especiales_lblName);

            _listener = listener;

           // button.setOnClickListener(this);
        }


        public int getIndex() {
            return _index;
        }
        public void setIndex(int index) {
            _index = index;
        }

        @Override
        public void onClick(View v) {
        }
    }



}
