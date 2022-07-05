package com.intelik.appadoc.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.intelik.appadoc.Common;
import com.intelik.appadoc.R;
import com.intelik.appadoc.adapters.CustomAdapter;
import com.intelik.appadoc.utils.DatePickerFragment;

import java.util.ArrayList;

import Models.Custom;
import com.intelik.appadoc.interfaces.NavigationInterface;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterPage2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterPage2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Context MyContext;


    private Spinner sp_documentos;
    private Spinner sp_generos;
    private androidx.appcompat.widget.AppCompatButton Siguiente;
    private TextView pag2ant;

    private EditText fechaNac;
    private ArrayList<Custom> _documentos;
    private CustomAdapter _customAdapter1;

    private ArrayList<Custom> _generos;
    private CustomAdapter _customAdapter2;

    private String TAG="RegisterPage2";

    private int _curDocumento = -1;
    private int _curGenero = -1;

    private EditText _numDocumento;
    private NavigationInterface navigationInterface;

    Custom curDocumento;
    Custom curGenero;

    public RegisterPage2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterPage2.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterPage2 newInstance(String param1, String param2) {
        RegisterPage2 fragment = new RegisterPage2();
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
        _view = inflater.inflate( R.layout.fragment_register_page2, container, false );

        // Inflate the layout for this fragment


        sp_documentos = (Spinner) _view.findViewById(R.id.spinner_documentos2);
        sp_generos = (Spinner) _view.findViewById(R.id.spinner_generos);
        fechaNac = (EditText) _view.findViewById(R.id.input_fechanac);
        _numDocumento = (EditText) _view.findViewById(R.id.input_numdocumento);


        Common datos = Common.getInstance();
        fechaNac.setText(datos.birthdate);
        _numDocumento.setText(datos.doc_identidad_c);

        Siguiente = (androidx.appcompat.widget.AppCompatButton) _view.findViewById(R.id.Siguiente_Pag3);
        pag2ant = (TextView) _view.findViewById(R.id.pag_ant2);

        _documentos = new ArrayList<>();
        _generos = new ArrayList<>();

        _documentos = Common.getDocumentos(datos.country_id);
        _generos = Common.getGeneros();


        _customAdapter1 = new CustomAdapter(MyContext, R.layout.spinner_item, _documentos);
        _customAdapter1.setDropDownViewResource(R.layout.spinner_item);
        sp_documentos.setAdapter(_customAdapter1);

        _customAdapter2 = new CustomAdapter(MyContext, R.layout.spinner_item, _generos);
        _customAdapter2.setDropDownViewResource(R.layout.spinner_item);
        sp_generos.setAdapter(_customAdapter2);


        sp_documentos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.AppAdocGray));
                Custom curDoc = _customAdapter1.getItem(position);
                // Here you can do the action you want to...
                _curDocumento = curDoc.get_id();
                curDocumento = curDoc;

                Log.d(TAG,String.valueOf(_curDocumento));

                //_curPos = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });


        sp_generos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.AppAdocGray));
                Custom curGen = _customAdapter2.getItem(position);
                // Here you can do the action you want to...
                _curGenero = curGen.get_id();
                curGenero = curGen;

                Log.d(TAG,String.valueOf(_curGenero));

                //_curPos = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });



        //Fecha Nac

        fechaNac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"FechaNac");

                showDatePickerDialog();

            }

        });

        //Siguiente

        Siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Siguiente");

                if (_curDocumento <1)
                {
                    Common.showWarningDialog(MyContext.getResources().getString(R.string.aviso),MyContext.getResources().getString(R.string.tudocumento), MyContext);
                    return;
                }

                String numDocumento = _numDocumento.getText().toString();
                String fecha_nac = fechaNac.getText().toString();


                if(numDocumento.length() < 8)
                {
                    Common.showWarningDialog(MyContext.getResources().getString(R.string.aviso),MyContext.getResources().getString(R.string.tudocnumer), MyContext);
                    return;
                }


                if (_curGenero <1)
                {
                    Common.showWarningDialog(MyContext.getResources().getString(R.string.aviso),MyContext.getResources().getString(R.string.tugenero), MyContext);
                    return;
                }

                if(fecha_nac.length() < 8)
                {
                    Common.showWarningDialog(MyContext.getResources().getString(R.string.aviso),MyContext.getResources().getString(R.string.tufecha), MyContext);
                    return;
                }


                Common datos = Common.getInstance();
                datos.tipo_documento_identidad_c = curDocumento.get_name();
                datos.doc_identidad_c = numDocumento;
                datos.gender_c = curGenero.get_name();
                datos.birthdate = fecha_nac;


                navigationInterface.closeFragment();
                /*
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                RegisterPage3 _page3 = RegisterPage3.newInstance("","");
                //resultados _resultados = resultados.newInstance("Herramientas en:" + _obras.get(_curPos).get_desc(), _herramientas);

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations( android.R.anim.slide_in_left, android.R.anim.slide_out_right );
                fragmentTransaction.replace( R.id.fragment_container, _page3, "Pagina 3" );
                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                */


            }

        });

        pag2ant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Anterior");
                    navigationInterface.backFragment();
                /*
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                RegisterPage1 _page1 = RegisterPage1.newInstance("","");


                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations( android.R.anim.slide_in_left, android.R.anim.slide_out_right );
                fragmentTransaction.replace( R.id.fragment_container, _page1, "Pagina 3" );
                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                */


            }

        });




        //return inflater.inflate(R.layout.fragment_register_page2, container, false);

        return _view;
    }


    @Override
    public void onAttach(Context context) {
        MyContext = context;
        super.onAttach(context);

    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                fechaNac.setText(selectedDate);
            }
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    public void setOnClickListener(NavigationInterface listener)
    {
        this.navigationInterface = listener;
    }


}