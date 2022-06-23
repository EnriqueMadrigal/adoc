package com.intelik.appadoc.fragments;

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
import android.widget.TextView;

import com.intelik.appadoc.Common;
import com.intelik.appadoc.R;
import com.intelik.appadoc.adapters.MirPuntosAdapter;

import java.util.ArrayList;
import java.util.List;

import Models.Country;
import Models.CountryViewModel;
import Models.Custom;
import Models.IViewHolderClick;
import Models.MisPuntosViewModel;

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

    private MirPuntosAdapter _adapter;

    private ArrayList<Custom> _mispuntos;

    private MisPuntosViewModel puntos;


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


        mainTitle = (TextView) _view.findViewById(R.id.fragment_mainTItle);

        _recyclerview = (RecyclerView) _view.findViewById(R.id.recycler_main);


        puntos = new ViewModelProvider(this).get(MisPuntosViewModel.class);


        _mispuntos = new ArrayList<>();

        _adapter = new MirPuntosAdapter(getActivity(), _mispuntos, new IViewHolderClick() {
            @Override
            public void onClick(int position) {
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
                _adapter.notifyDataSetChanged();
            }
        });




        //Recycler


        _linearLayoutManager = new LinearLayoutManager( getActivity() );

        _recyclerview.setHasFixedSize( true );
        _recyclerview.setAdapter( _adapter );
        _recyclerview.setLayoutManager( _linearLayoutManager );



        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_main, container, false);

        String fullName = "";
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences( MyContext );

        String name = sharedPref.getString(Common.VAR_USER_NAME, "");
        String last_name = sharedPref.getString(Common.VAR_USER_APELLIDOS, "");

        fullName = name + " " + last_name;

        mainTitle.setText(fullName);


        return _view;
    }

    @Override
    public void onAttach(Context context) {
        MyContext = context;
        super.onAttach(context);

    }


}