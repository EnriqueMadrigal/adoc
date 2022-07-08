package com.intelik.appadoc.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.intelik.appadoc.Common;
import com.intelik.appadoc.R;
import com.intelik.appadoc.interfaces.AlertasNavigationInterface;
import com.intelik.appadoc.interfaces.ContactoNavigationInterface;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Alertas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Alertas extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Context MyContext;
    private AlertasNavigationInterface alertasNavigationInterface;

    private ImageButton button_sms;
    private ImageButton button_push;
    private ImageButton button_email;

    private ImageButton alertas_close;

    private Common datos;

    private boolean notific_sms = false;
    private boolean notific_push = false;
    private boolean notific_email = false;



    public Alertas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Alertas.
     */
    // TODO: Rename and change types and number of parameters
    public static Alertas newInstance(String param1, String param2) {
        Alertas fragment = new Alertas();
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
        //return inflater.inflate(R.layout.fragment_alertas, container, false);

        datos = Common.getInstance();

        View _view;
        _view = inflater.inflate( R.layout.fragment_alertas, container, false );

        button_sms = (ImageButton) _view.findViewById(R.id.button_SMS);
        button_push = (ImageButton) _view.findViewById(R.id.button_push);
        button_email = (ImageButton) _view.findViewById(R.id.button_email);


        if (datos.notificaciones_sms){
            notific_sms = true;
            button_sms.setImageResource(R.drawable.toggle_on);
        }

        if (datos.notificaciones_push){
            notific_push = true;
            button_push.setImageResource(R.drawable.toggle_on);
        }


        if (datos.notificaciones_email){
            notific_email = true;
            button_email.setImageResource(R.drawable.toggle_on);
        }


        alertas_close = (ImageButton) _view.findViewById(R.id.alertas_close);


        alertas_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertasNavigationInterface.Close_action();
            }
        });


        button_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notific_email = !notific_email;
                if (notific_email){
                    button_email.setImageResource(R.drawable.toggle_on);
                }
                else
                {
                    button_email.setImageResource(R.drawable.toggle_off);
                }
                datos.notificaciones_email = notific_email;
            }
        });

        button_push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notific_push = !notific_push;
                if (notific_push){
                    button_push.setImageResource(R.drawable.toggle_on);
                }
                else
                {
                    button_push.setImageResource(R.drawable.toggle_off);
                }

                datos.notificaciones_push = notific_push;
            }
        });

    button_sms.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        notific_sms = !notific_sms;
        if (notific_sms){
            button_sms.setImageResource(R.drawable.toggle_on);
        }
        else
        {
            button_sms.setImageResource(R.drawable.toggle_off);
        }

        datos.notificaciones_sms = notific_sms;
    }
});




        return _view;
    }

    @Override
    public void onAttach(Context context) {
        MyContext = context;
        super.onAttach(context);

    }

    public void setOnClickListener(AlertasNavigationInterface listener)
    {
        this.alertasNavigationInterface = listener;
    }



}