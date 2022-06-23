package com.intelik.appadoc.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.intelik.appadoc.Common;
import com.intelik.appadoc.R;
import com.intelik.appadoc.adapters.CustomAdapter;
import com.intelik.appadoc.interfaces.NavigationInterface;

import java.util.ArrayList;
import java.util.List;

import Models.Country;
import Models.CountryViewModel;
import Models.Custom;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterPage1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterPage1 extends Fragment implements  View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int _curPos = 0;
    private int _curPais = -1;

    private Context MyContext;

    private Spinner Sp_countries;

    private androidx.appcompat.widget.AppCompatButton Siguiente;
    private ImageButton check1;
    private ImageButton check2;
    private ImageButton check3;
    private ImageButton check4;
    private ImageButton check5;

    private CountryViewModel countries;

    private ArrayList<Custom> _paises;
    private CustomAdapter _customAdapter;

    private String TAG = "RegisterPage1";

    private boolean checked1;
    private boolean checked2;
    private boolean checked3;
    private boolean checked4;
    private boolean checked5;

    private Custom curPais;

    private EditText _name;
    private EditText _lastname;

    private NavigationInterface navigationInterface;

    public RegisterPage1() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static RegisterPage1 newInstance() {
        RegisterPage1 fragment = new RegisterPage1();
        Bundle args = new Bundle();
       // args.putString(ARG_PARAM1, param1);
       // args.putString(ARG_PARAM2, param2);
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
        View _view;
        _view = inflater.inflate( R.layout.fragment_register_page1, container, false );

        Sp_countries = (Spinner) _view.findViewById(R.id.spinner_countries);
        Siguiente = (androidx.appcompat.widget.AppCompatButton) _view.findViewById(R.id.Siguiente_Pag2);

        check1 = (ImageButton) _view.findViewById(R.id.check1);
        check2 = (ImageButton) _view.findViewById(R.id.check2);
        check3 = (ImageButton) _view.findViewById(R.id.check3);
        check4 = (ImageButton) _view.findViewById(R.id.check4);
        check5 = (ImageButton) _view.findViewById(R.id.check5);
        _name = (EditText) _view.findViewById(R.id.input_name);
        _lastname = (EditText) _view.findViewById(R.id.input_lastname);



        countries = new ViewModelProvider(this).get(CountryViewModel.class);



        _paises = new ArrayList<>();

        Common datos = Common.getInstance();
        _name.setText(datos.first_name);
        _lastname.setText(datos.last_name);


        _customAdapter = new CustomAdapter(MyContext, R.layout.spinner_item, _paises);
        _customAdapter.setDropDownViewResource(R.layout.spinner_item);
        Sp_countries.setAdapter(_customAdapter);

        Sp_countries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view,
                               int position, long id) {
        // Here you get the current item (a User object) that is selected by its position
        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.AppAdocGray));
        Custom curcountry = _customAdapter.getItem(position);
        // Here you can do the action you want to...
        _curPais = curcountry.get_id();
        curPais = curcountry;

        Log.d(TAG,String.valueOf(_curPais));

        _curPos = position;
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapter) {  }
});


        //Obtener los paises
        countries.getCountries().observe(getViewLifecycleOwner(), new Observer<List<Country>>() {
            @Override
            public void onChanged(@Nullable List<Country> countries) {
                // update ui.
                Log.d("RegisterActiviti", "Marcas recibidas");

                for (Country curCountry: countries) {
                    Custom newCustom = new Custom();
                    newCustom.set_id(curCountry.id);
                    newCustom.set_name(curCountry.nombre);

                    _paises.add(newCustom);
                }
                _customAdapter.notifyDataSetChanged();
            }
        });



        //Botón de siguiente

        Siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Siguiente");

                if (_curPais <1)
                {
                    Common.showWarningDialog(MyContext.getResources().getString(R.string.aviso),MyContext.getResources().getString(R.string.tupais), MyContext);
                    return;
                }

                 String name = _name.getText().toString();
                 String lastname = _lastname.getText().toString();

                 if (name.length()<3)
                 {
                     Common.showWarningDialog(MyContext.getResources().getString(R.string.aviso),MyContext.getResources().getString(R.string.tunombre), MyContext);
                     return;
                 }

                if (lastname.length()<5)
                {
                    Common.showWarningDialog(MyContext.getResources().getString(R.string.aviso),MyContext.getResources().getString(R.string.tuapellido), MyContext);
                    return;
                }


                Common datos = Common.getInstance();
                datos.first_name = name;
                datos.last_name = lastname;
                datos.country_id = _curPais;
                datos.country = curPais.get_name();

                navigationInterface.closeFragment();

                /*
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                RegisterPage2 _page2 = RegisterPage2.newInstance("","");

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations( android.R.anim.slide_in_left, android.R.anim.slide_out_right );
                fragmentTransaction.replace( R.id.fragment_container, _page2, "Pagina 2" );
                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                */



            }

        });

check1.setOnClickListener(this);
check2.setOnClickListener(this);
check3.setOnClickListener(this);
check4.setOnClickListener(this);
check5.setOnClickListener(this);

/*
        check1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                aviso_checked = !aviso_checked;
                if (aviso_checked){
                    check1.setImageResource(R.drawable.check_on);
                }

                else {
                    check1.setImageResource(R.drawable.check_off);
                }


            }

        });
*/




        //return inflater.inflate(R.layout.fragment_register_page1, container, false);
        return _view;
    }

    @Override
    public void onAttach(Context context) {
        MyContext = context;
        super.onAttach(context);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.check1:
                // code for button when user clicks buttonOne.
                checked1 = !checked1;
                if (checked1){
                    check1.setImageResource(R.drawable.check_on);
                }

                else {
                    check1.setImageResource(R.drawable.check_off);
                }


                break;

            case R.id.check2:
                // do your code
                checked2 = !checked2;
                if (checked2){
                    check2.setImageResource(R.drawable.check_on);
                }

                else {
                    check2.setImageResource(R.drawable.check_off);
                }

                break;

            case R.id.check3:
                // do your code
                checked3 = !checked3;
                if (checked3){
                    check3.setImageResource(R.drawable.check_on);
                }

                else {
                    check3.setImageResource(R.drawable.check_off);
                }

                break;

            case R.id.check4:
                // do your code
                checked4 = !checked4;
                if (checked4){
                    check4.setImageResource(R.drawable.check_on);
                }

                else {
                    check4.setImageResource(R.drawable.check_off);
                }

                break;

            case R.id.check5:
                // do your code
                checked5 = !checked5;
                if (checked5){
                    check5.setImageResource(R.drawable.check_on);
                }

                else {
                    check5.setImageResource(R.drawable.check_off);
                }

                break;

            default:
                break;
        }
    }


    public void setOnClickListener(NavigationInterface listener)
    {
        this.navigationInterface = listener;
    }


}