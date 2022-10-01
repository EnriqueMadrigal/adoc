package com.intelik.appadoc.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.intelik.appadoc.Common;
import com.intelik.appadoc.R;
import com.intelik.appadoc.interfaces.NavigationInterface;
import com.intelik.appadoc.interfaces.PerfilNavigationInterface;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Perfil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Perfil extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Context MyContext;
    private PerfilNavigationInterface perfilNavigationInterface;

    androidx.appcompat.widget.AppCompatButton editar_perfil;
    androidx.appcompat.widget.AppCompatButton configurar_alertas;
    androidx.appcompat.widget.AppCompatButton contactanos;
    androidx.appcompat.widget.AppCompatButton cerrar_sesion;
    androidx.appcompat.widget.AppCompatButton acercade;
    androidx.appcompat.widget.AppCompatButton button_refiere;

    TextView MainTitle;


    public Perfil() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Perfil.
     */
    // TODO: Rename and change types and number of parameters
    public static Perfil newInstance(String param1, String param2) {
        Perfil fragment = new Perfil();
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
        //return inflater.inflate(R.layout.fragment_perfil, container, false);

        View _view;
        _view = inflater.inflate( R.layout.fragment_perfil, container, false );

        editar_perfil = (androidx.appcompat.widget.AppCompatButton) _view.findViewById(R.id.button_perfil_editar);
        configurar_alertas = (androidx.appcompat.widget.AppCompatButton) _view.findViewById(R.id.button_perfil_alertas);
        contactanos = (androidx.appcompat.widget.AppCompatButton) _view.findViewById(R.id.button_perfil_contacto);
        cerrar_sesion = (androidx.appcompat.widget.AppCompatButton) _view.findViewById(R.id.button_perfil_cerrar);
        acercade = (androidx.appcompat.widget.AppCompatButton) _view.findViewById(R.id.button_perfil_acerca_de);
        button_refiere = (androidx.appcompat.widget.AppCompatButton) _view.findViewById(R.id.button_perfil_refiere);

        MainTitle = (TextView) _view.findViewById(R.id.fragment_perfilTItle);

        Common datos = Common.getInstance();

        MainTitle.setText(" " + datos.first_name + "!");


        contactanos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                perfilNavigationInterface.Contacto_action();

            }
        });

        configurar_alertas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perfilNavigationInterface.Configurar_action();
            }
        });

        editar_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perfilNavigationInterface.Edit_action();
            }
        });


        acercade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perfilNavigationInterface.AcercaDe_action();
            }
        });



        cerrar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perfilNavigationInterface.Cerrar_action();
            }
        });

        button_refiere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mensaje = "!" + datos.first_name + " ";
                mensaje = mensaje + getString(R.string.teinvito);
                mensaje = mensaje + "\n";
                mensaje = mensaje + "\n";
                mensaje = mensaje + getString(R.string.gana1000);
                mensaje = mensaje + "\n";
                mensaje = mensaje + Common.Reviere_link + datos.assigned_user_id;


                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, mensaje);
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);

            }
        });



        return _view;
    }

    @Override
    public void onAttach(Context context) {
        MyContext = context;
        super.onAttach(context);

    }

    public void setOnClickListener(PerfilNavigationInterface listener)
    {
        this.perfilNavigationInterface = listener;
    }





}