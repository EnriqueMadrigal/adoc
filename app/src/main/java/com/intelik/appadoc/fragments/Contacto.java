package com.intelik.appadoc.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.intelik.appadoc.R;
import com.intelik.appadoc.interfaces.ContactoNavigationInterface;
import com.intelik.appadoc.interfaces.NavigationInterface;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Contacto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Contacto extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Context MyContext;
    private ContactoNavigationInterface contactoNavigationInterface;

    private ImageButton contacto_close;

    private String TAG = "Contacto_Fragment";
    private LinearLayout contacto_whatsapp;
    private LinearLayout contacto_email;


    public Contacto() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Contacto.
     */
    // TODO: Rename and change types and number of parameters
    public static Contacto newInstance(String param1, String param2) {
        Contacto fragment = new Contacto();
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
        //return inflater.inflate(R.layout.fragment_contacto, container, false);

        View _view;
        _view = inflater.inflate( R.layout.fragment_contacto, container, false );

        contacto_close = (ImageButton)  _view.findViewById(R.id.contacto_close);

        contacto_whatsapp = (LinearLayout) _view.findViewById(R.id.contacto_whatsapp);
        contacto_email = (LinearLayout) _view.findViewById(R.id.contacto_email);

        contacto_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"close");

                contactoNavigationInterface.Close_action();

            }

        });


        contacto_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://wa.me/50372752074"));
                startActivity(browserIntent);
            }
        });

        contacto_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:servicioalcliente@empresasadoc.com"));
                startActivity(browserIntent);
            }
        });


        return _view;


    }

    @Override
    public void onAttach(Context context) {
        MyContext = context;
        super.onAttach(context);

    }

    public void setOnClickListener(ContactoNavigationInterface listener)
    {
        this.contactoNavigationInterface = listener;
    }



}