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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AcercaDe#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AcercaDe extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Common datos;

    private Context MyContext;
    private String TAG = "AcercaDe";

    private TextView mainTitle;

    private androidx.appcompat.widget.AppCompatButton button_refiere;
    private androidx.appcompat.widget.AppCompatButton acercade_puntosadoc;
    private androidx.appcompat.widget.AppCompatButton acercade_preguntas;
    private androidx.appcompat.widget.AppCompatButton acercade_terminos;
    private androidx.appcompat.widget.AppCompatButton acercade_politica;


    public AcercaDe() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AcercaDe.
     */
    // TODO: Rename and change types and number of parameters
    public static AcercaDe newInstance(String param1, String param2) {
        AcercaDe fragment = new AcercaDe();
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
        //return inflater.inflate(R.layout.fragment_acerca_de, container, false);

        datos = Common.getInstance();




        View _view;
        _view = inflater.inflate( R.layout.fragment_acerca_de, container, false );

    mainTitle = (TextView)  _view.findViewById(R.id.acercade_perfilTItle);
    button_refiere = (androidx.appcompat.widget.AppCompatButton) _view.findViewById(R.id.button_acercade_refiere);

        acercade_puntosadoc  = (androidx.appcompat.widget.AppCompatButton) _view.findViewById(R.id.acercade_puntosadoc);
        acercade_preguntas  = (androidx.appcompat.widget.AppCompatButton) _view.findViewById(R.id.acercade_preguntas);
        acercade_terminos  = (androidx.appcompat.widget.AppCompatButton) _view.findViewById(R.id.acercade_terminos);
        acercade_politica  = (androidx.appcompat.widget.AppCompatButton) _view.findViewById(R.id.acercade_politica);



        mainTitle.setText(getString(R.string.masinformacion));

        button_refiere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.sendRefiere(MyContext);

            }
        });

        acercade_puntosadoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Common.PuntosAdoc_link));
                startActivity(browserIntent);

            }
        });

        acercade_preguntas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Common.PuntosAdoc_link));
                startActivity(browserIntent);

            }
        });

        acercade_terminos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Common.PuntosAdoc_link + "terminos-condiciones/"));
                startActivity(browserIntent);

            }
        });
        acercade_politica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Common.PuntosAdoc_link + "politicas-privacidad/"));
                startActivity(browserIntent);

            }
        });





        return _view;

    }

    public void onAttach(Context context) {
        MyContext = context;
        super.onAttach(context);

    }


}