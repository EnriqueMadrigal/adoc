package com.intelik.appadoc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.intelik.appadoc.R;

import java.util.ArrayList;

import Models.Custom;
import com.intelik.appadoc.interfaces.IViewHolderClick;

public class MirPuntosAdapter extends RecyclerView.Adapter<MirPuntosAdapter.ViewHolder>{
    private Context _context;
    private IViewHolderClick _listener;
    private ArrayList<Custom> _items;
    Integer row_index = -1;

    public MirPuntosAdapter(Context context, ArrayList<Custom> items, IViewHolderClick listener) {
        _context = context;
        _items = items;
        _listener = listener;


    }

    @Override
    public int getItemCount() {
        return _items.size();
    }


    @Override
    public MirPuntosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(_context).inflate(R.layout.mispuntos_items, parent, false);
        MirPuntosAdapter.ViewHolder viewHolder = new MirPuntosAdapter.ViewHolder(view, new IViewHolderClick() {
            @Override
            public void onClick(int position) {
                //  if (_listener != null) {
                //      _listener.onClick(position);
                //  }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MirPuntosAdapter.ViewHolder holder, final int position) {
        Custom curCustom = _items.get(position);
        holder.get_labelDescription().setText(String.valueOf(curCustom.get_desc()));
        holder.get_labelValue().setText(curCustom.get_name());


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




        public ViewHolder(View view, IViewHolderClick listener) {
            super(view);

            view.setOnClickListener(this);
            _labelDescription = (TextView) view.findViewById(R.id.lblDescr);
            _labelValue = (TextView) view.findViewById(R.id.lblValue);
            _listener = listener;
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
                _listener.onClick(_index);
            }
        }
    }



}
