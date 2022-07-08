package com.intelik.appadoc.fragments;

import static android.text.InputType.TYPE_NULL;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.intelik.appadoc.Common;
import com.intelik.appadoc.R;
import com.intelik.appadoc.RegisterActivity;
import com.intelik.appadoc.interfaces.NavigationInterface;
import com.intelik.appadoc.utils.DatePickerFragment;

import Models.Comm_User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Account#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Account extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private Context MyContext;
    private Comm_User usuario;

    private EditText name;
    private EditText lastname;
    private EditText fechanac;

    private ImageButton editImage;
    private ImageButton editName;
    private ImageButton editLastName;
    private ImageButton editFechaNac;

    private Button saveChanges;


    private String TAG ="Account";
    private NavigationInterface navigationInterface;

    public Account() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Account.
     */
    // TODO: Rename and change types and number of parameters
    public static Account newInstance(String param1, String param2) {
        Account fragment = new Account();
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
        View _view;
        _view = inflater.inflate( R.layout.fragment_account, container, false );


        usuario = Common.getUserValues(MyContext);


        name = (EditText) _view.findViewById(R.id.input_account_name);
        lastname = (EditText) _view.findViewById(R.id.input_account_lastname);
        fechanac = (EditText) _view.findViewById(R.id.input_account_fechanac);

        editName = (ImageButton) _view.findViewById(R.id.button_editname);
        editLastName = (ImageButton) _view.findViewById(R.id.button_editlastname);
        editFechaNac = (ImageButton) _view.findViewById(R.id.button_editfechac);

        saveChanges = (Button) _view.findViewById(R.id.button_savechanges);


        name.setInputType(TYPE_NULL);
        lastname.setInputType(TYPE_NULL);
        fechanac.setInputType(TYPE_NULL);


        Comm_User usuario = Common.getUserValues(MyContext);


        name.setText(usuario.first_name);
        lastname.setText(usuario.last_name);
        fechanac.setText(usuario.birthday);



        //edit nombre
        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    name.setInputType(InputType.TYPE_CLASS_TEXT);
                    name.requestFocus();

            }
        });

        //Edit lastname
        editLastName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastname.setInputType(InputType.TYPE_CLASS_TEXT);
                lastname.requestFocus();

            }
        });



        //Fecha Nac
        editFechaNac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"FechaNac");

                showDatePickerDialog();

            }

        });


        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"SAveChanges");

                Comm_User newUser = new Comm_User();

                newUser.first_name = name.getText().toString();
                newUser.last_name = lastname.getText().toString();
                newUser.birthday = fechanac.getText().toString();

                Common.saveUserValue(newUser,MyContext);

                navigationInterface.closeFragment();

            }

        });







        return _view;
        //return inflater.inflate(R.layout.fragment_account, container, false);
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
                fechanac.setText(selectedDate);
            }
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    public void setOnClickListener(NavigationInterface listener)
    {
        this.navigationInterface = listener;
    }


}