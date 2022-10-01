package com.intelik.appadoc.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.intelik.appadoc.Common;
import com.intelik.appadoc.R;
import com.intelik.appadoc.adapters.EspecialesAdapter;
import com.intelik.appadoc.interfaces.IViewHolderClick;
import com.intelik.appadoc.utils.DBHelper;
import com.intelik.appadoc.utils.SwipeToDeleteCallback;

import java.util.ArrayList;

import Models.Custom;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Notifications#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Notifications extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Context MyContext;

    private RecyclerView recycler_notificaciones;
    private LinearLayoutManager _linearLayoutManager;

    private EspecialesAdapter adapter;

    private ArrayList<Custom> _misnotificaciones;
    private CoordinatorLayout coordinatorLayout;

    private DBHelper db;

    Common datos;


    public Notifications() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Notificactions.
     */
    // TODO: Rename and change types and number of parameters
    public static Notifications newInstance(String param1, String param2) {
        Notifications fragment = new Notifications();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_notifications, container, false);

        datos = Common.getInstance();
        db = new DBHelper(MyContext);

        View _view;
        _view = inflater.inflate( R.layout.fragment_notifications, container, false );

        recycler_notificaciones = (RecyclerView) _view.findViewById(R.id.recycler_notificaciones);
        coordinatorLayout = _view.findViewById(R.id.coordinatorLayout);


        _linearLayoutManager = new LinearLayoutManager( getActivity() , LinearLayoutManager.VERTICAL, false);


        _misnotificaciones = new ArrayList<>();

        Custom custom1 =  new Custom();
        Custom custom2 =  new Custom();

        custom1.set_name("Obten 100 puntos");
        custom1.set_desc("Al recomendar a un amigo");
        custom1.set_link("https://i.imgur.com/xduM49P.jpeg");

        custom2.set_name("Obten 200 puntos");
        custom2.set_desc("Al recomendar a un amigo");
        custom2.set_link("");


        _misnotificaciones = db.getAllNotifications();
        db.close();

    //_misnotificaciones.add(custom1);
    //    _misnotificaciones.add(custom2);


        adapter = new EspecialesAdapter(getActivity(), _misnotificaciones, new IViewHolderClick() {
            @Override
            public void onClick(int position) {
            }
        });





        recycler_notificaciones.setHasFixedSize( true );
        recycler_notificaciones.setAdapter( adapter );
        recycler_notificaciones.setLayoutManager( _linearLayoutManager );

        enableSwipeToDeleteAndUndo();

        return _view;
    }



    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(MyContext) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final Custom item = adapter.getData().get(position);

                DBHelper db = new DBHelper(MyContext);

                adapter.removeItem(position);
                db.deleteNotification(item.get_id());
                db.close();

                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        adapter.restoreItem(item, position);
                        recycler_notificaciones.scrollToPosition(position);

                        DBHelper db2 = new DBHelper(MyContext);
                        db2.insertNotification(item.get_name(), item.get_desc(), item.getLink());
                        db2.close();
                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recycler_notificaciones);
    }



    @Override
    public void onAttach(Context context) {
        MyContext = context;
        super.onAttach(context);

    }


}