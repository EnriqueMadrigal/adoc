package com.intelik.appadoc.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.intelik.appadoc.Common;
import com.intelik.appadoc.R;
import com.intelik.appadoc.adapters.MainQuestionsAdapter;
import com.intelik.appadoc.adapters.MirPuntosAdapter;

import java.util.ArrayList;
import java.util.List;

import Models.ContactViewModel;
import Models.Custom;
import com.intelik.appadoc.interfaces.IViewHolderClick;

import Models.MisPreguntasViewModel;
import Models.MisPuntosViewModel;
import Models.User_Intelik;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Context MyContext;
    private TextView mainTitle;

    private RecyclerView _recyclerview;
    private LinearLayoutManager _linearLayoutManager;

    //private MirPuntosAdapter _adapter;
    private MainQuestionsAdapter _adapter;

    private ArrayList<Custom> _mispuntos;

    private ArrayList<Custom> _mispreguntas;

    private MisPuntosViewModel puntos;
    private MisPreguntasViewModel preguntas;
    private String TAG = "MainFragment";

    private ContactViewModel contactos;
    private FirebaseAuth mAuth;

    private TextView main_currentpoints;


    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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

        View _view;
        _view = inflater.inflate( R.layout.fragment_main, container, false );

        mAuth = FirebaseAuth.getInstance();

        mainTitle = (TextView) _view.findViewById(R.id.fragment_mainTItle);

        _recyclerview = (RecyclerView) _view.findViewById(R.id.recycler_main);

        main_currentpoints = (TextView) _view.findViewById(R.id.main_currentpoints);

        puntos = new ViewModelProvider(this).get(MisPuntosViewModel.class);
        preguntas = new ViewModelProvider(this).get(MisPreguntasViewModel.class);


        _mispuntos = new ArrayList<>();
        _mispreguntas = new ArrayList<>();

        /*
        _adapter = new MirPuntosAdapter(getActivity(), _mispuntos, new IViewHolderClick() {
            @Override
            public void onClick(int position) {
            }
        });
        */


        _adapter = new MainQuestionsAdapter(getActivity(), _mispreguntas, new IViewHolderClick() {
            @Override
            public void onClick(int position) {
                Log.d(TAG, "CLick");

                //Mostrar el dialogo

                AlertDialog.Builder builder = new AlertDialog.Builder(MyContext);
                builder.setTitle("Envia tu respuesta");

                View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.dialog_responder, (ViewGroup) getView(), false);
// Set up the input
                ImageView  mainImage = (ImageView) viewInflated.findViewById(R.id.dialog_mainimage);
                TextView mainText = (TextView) viewInflated.findViewById(R.id.dialog_mainquestion);
                TextView mainPts = (TextView) viewInflated.findViewById(R.id.dialog_points);
                EditText curResp = (EditText) viewInflated.findViewById(R.id.dialog_inputresp);

                ImageButton closeDialog = (ImageButton) viewInflated.findViewById(R.id.dialog_buttonclose);
                androidx.appcompat.widget.AppCompatButton sendResp = (androidx.appcompat.widget.AppCompatButton) viewInflated.findViewById(R.id.dialog_responder);

                final AlertDialog dialog = builder.create();


                builder.setView(viewInflated);
                curResp.setText("");


                Custom currentCustom = new Custom();

                for(Custom cust : _mispreguntas) {
                    if(cust.get_id().equals(position)) {
                        currentCustom = cust;
                    }
                }


                if (!currentCustom.getLink().equals(""))
                {
                    String curLink = currentCustom.getLink();
                    mainPts.setText(String.valueOf(currentCustom.getValue1()));

                    switch (curLink) {
                        case "example1":
                            mainImage.setImageResource(R.drawable.example1);
                            mainText.setText(MyContext.getResources().getString(R.string.question1));
                            break;

                        case "example2":
                            mainImage.setImageResource(R.drawable.example2);
                            mainText.setText(MyContext.getResources().getString(R.string.question2));
                            break;

                        case "example3":
                            mainImage.setImageResource(R.drawable.example3);
                            mainText.setText(MyContext.getResources().getString(R.string.question3));
                            break;
                    }





                }


                final AlertDialog alertDialog = builder.show();
                //builder.show();

                closeDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            alertDialog.dismiss();
                    }
                });

                sendResp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });







            }
        });




        //Obtener los puntos

        puntos.getPuntos().observe(getViewLifecycleOwner(), new Observer<List<Custom>>() {
            @Override
            public void onChanged(@Nullable List<Custom> customs) {
                // update ui.
                Log.d("RegisterActiviti", "Marcas recibidas");



                for (Custom curCustom: customs) {
                    Custom newCustom = new Custom();
                    newCustom.set_id(0);
                    newCustom.set_name(curCustom.get_name());
                    newCustom.set_desc(curCustom.get_desc());
                    _mispuntos.add(newCustom);
                }
                //_adapter.notifyDataSetChanged();
            }
        });

//Obtener las preguntas

        preguntas.getPreguntas().observe(getViewLifecycleOwner(), new Observer<List<Custom>>() {
            @Override
            public void onChanged(@Nullable List<Custom> customs) {
                // update ui.
                Log.d("RegisterActiviti", "Marcas recibidas");



                for (Custom curCustom: customs) {
                    Custom newCustom = new Custom();
                    newCustom.set_id(curCustom.get_id());
                    newCustom.set_name(curCustom.get_name());
                    newCustom.set_desc(curCustom.get_desc());
                    newCustom.set_link(curCustom.getLink());
                    newCustom.set_value1(curCustom.getValue1());
                    _mispreguntas.add(newCustom);
                }
                _adapter.notifyDataSetChanged();
            }
        });


        contactos = new ViewModelProvider(this).get(ContactViewModel.class);
        String user_email = mAuth.getCurrentUser().getEmail();

        contactos.getContacto(user_email).observe(getActivity(), new Observer<User_Intelik>() {
            @Override
            public void onChanged(@Nullable User_Intelik contacto) {
                // update ui.
                Log.d("RegisterActiviti", "Marcas recibidas");
                mainTitle.setText("!Hola, " + contacto.first_name );

                String Puntos_acumulados = contacto.puntos_acumulados_c;

                 int totalPuntos = 0;
                try{
                    totalPuntos = Integer.parseInt(Puntos_acumulados);

                }
                catch (NumberFormatException ex){
                    ex.printStackTrace();
                }

                main_currentpoints.setText(String.valueOf(totalPuntos) + " pts");


            }
        });



        //Recycler


        _linearLayoutManager = new LinearLayoutManager( getActivity() , LinearLayoutManager.HORIZONTAL, false);

        _recyclerview.setHasFixedSize( true );
        _recyclerview.setAdapter( _adapter );
        _recyclerview.setLayoutManager( _linearLayoutManager );



        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_main, container, false);


        //SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences( MyContext );

        //String name = sharedPref.getString(Common.VAR_USER_NAME, "");

        //mainTitle.setText("!Hola, " + name );


        return _view;
    }

    @Override
    public void onAttach(Context context) {
        MyContext = context;
        super.onAttach(context);

    }


}