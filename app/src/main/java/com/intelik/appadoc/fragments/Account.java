package com.intelik.appadoc.fragments;

import static android.text.InputType.TYPE_NULL;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.intelik.appadoc.Common;
import com.intelik.appadoc.R;
import com.intelik.appadoc.RegisterActivity;
import com.intelik.appadoc.adapters.CustomAdapter;
import com.intelik.appadoc.adapters.CustomAdapter2;
import com.intelik.appadoc.interfaces.NavigationInterface;
import com.intelik.appadoc.utils.DatePickerFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Models.Comm_User;
import Models.Country;
import Models.CountryViewModel;
import Models.Custom;
import Models.SugarContact;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.intelik.appadoc.utils.HttpClient;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Account#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Account extends Fragment implements  View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int _curPos = 0;
    private int _curPais = -1;
    private String _nombrePais = "";


    private Common datos;

    private Context MyContext;
    private Comm_User usuario;

    private EditText name;
    private EditText lastname;
    private EditText fechanac;

    private EditText input_account_numdocumento;
    private EditText input_account_phone;
    private EditText input_account_email;

    private Spinner Sp_countries;
    private Spinner Sp_phones;
    private EditText input_account_documento;


    //private EditText input_account_password;
    //private EditText input_account_cpassword;


    //private CountryViewModel countries;


    private CustomAdapter _customAdapter1;
    private Spinner sp_documentos;
    private ArrayList<Custom> _documentos;
    private int _curDocumento = -1;
    private String nombreDocumento = "";


    private String TAG = "AccountPage";
    private String newPassword = "";

    private boolean checked1;
    private boolean checked2;
    private boolean checked3;
    private boolean checked4;
    private boolean checked5;


    private String PaisGuardado = "";

    private ImageButton editImage;

    private ImageButton account_check1;
    private ImageButton account_check2;
    private ImageButton account_check3;
    private ImageButton account_check4;
    private ImageButton account_check5;




    private Button saveChanges;

    private List<String> marcas_favoritas;

    private NavigationInterface navigationInterface;
    private FirebaseAuth mAuth;

    private ArrayList<Custom> _paises;
    private CustomAdapter _customAdapter;
    private int Pais_id = 0;
    private Custom curPais;

    private EditText _nit;

    private LinearLayout layout_nit;


    private Spinner Sp_generos;
    private ArrayList<Custom> _generos;
    private CustomAdapter _customAdapter2;
    private int Genero_id = 0;
    private Custom curGenero;


    private ArrayList<Custom> _phonescodes;
    private CustomAdapter2 _customAdapter3;
    private int Phone_id = 0;
    private Custom curPhone;


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

        datos = Common.getInstance();
        Common.getCommonUserValues(MyContext);

        mAuth = FirebaseAuth.getInstance();

        //usuario = Common.getUserValues(MyContext);


        name = (EditText) _view.findViewById(R.id.input_account_name);
        lastname = (EditText) _view.findViewById(R.id.input_account_lastname);
        fechanac = (EditText) _view.findViewById(R.id.input_account_fechanac);

        input_account_numdocumento = (EditText) _view.findViewById(R.id.input_account_numdocumento);
        input_account_phone = (EditText) _view.findViewById(R.id.input_account_phone);
        input_account_email = (EditText) _view.findViewById(R.id.input_account_email);

        Sp_countries = (Spinner) _view.findViewById(R.id.edit_spinner_countries);
        Sp_generos = (Spinner) _view.findViewById(R.id.edit_spinner_generos);
        Sp_phones = (Spinner) _view.findViewById(R.id.edit_spinner_phones);



        //input_account_password = (EditText) _view.findViewById(R.id.input_account_password);
        //input_account_cpassword = (EditText) _view.findViewById(R.id.input_account_cpassword);

        //Sp_countries = (Spinner) _view.findViewById(R.id.input_spinner_countries);
        sp_documentos = (Spinner) _view.findViewById(R.id.edit_spinner_documentos);


        saveChanges = (Button) _view.findViewById(R.id.button_savechanges);

        account_check1 = (ImageButton) _view.findViewById(R.id.account_check1);
        account_check2 = (ImageButton) _view.findViewById(R.id.account_check2);
        account_check3 = (ImageButton) _view.findViewById(R.id.account_check3);
        account_check4 = (ImageButton) _view.findViewById(R.id.account_check4);
        account_check5 = (ImageButton) _view.findViewById(R.id.account_check5);

        _nit = (EditText) _view.findViewById(R.id.edit_account_nit);
        layout_nit = (LinearLayout) _view.findViewById(R.id.layout_edit_nit);


        account_check1.setOnClickListener(this);
        account_check2.setOnClickListener(this);
        account_check3.setOnClickListener(this);
        account_check4.setOnClickListener(this);
        account_check5.setOnClickListener(this);


        //name.setInputType(TYPE_NULL);
        //lastname.setInputType(TYPE_NULL);
        //fechanac.setInputType(TYPE_NULL);


        //Comm_User usuario = Common.getUserValues(MyContext);


//        name.setText(usuario.first_name);
//        lastname.setText(usuario.last_name);
//        fechanac.setText(usuario.birthday);


        marcas_favoritas = new ArrayList<String>();

        name.setText(datos.first_name);
        lastname.setText(datos.last_name);

        String fecha_nac = datos.birthdate;

        if (fecha_nac.length()>6) {
            fecha_nac = Common.convertDateFormat (fecha_nac);
            fechanac.setText(fecha_nac);
        }

        _nit.setText(datos.nit);


        input_account_email.setText(datos.email1);



        String curPais = datos.primary_address_country;
        Pais_id = 0;




        _paises = new ArrayList<>();
        _paises = Common.getCountries();

        for (Custom pais: _paises) {
            if (pais.get_name().equals(curPais))
            {
                Pais_id = pais.get_id();
            }

        }


        _customAdapter = new CustomAdapter(MyContext, R.layout.spinner_item, _paises);
        _customAdapter.setDropDownViewResource(R.layout.spinner_item);
        Sp_countries.setAdapter(_customAdapter);

        Common.selectSpinnerItemByValue(Sp_countries, Pais_id);

        if (Phone_id == 5) {
            layout_nit.setVisibility(View.VISIBLE);
        }


        _documentos = new ArrayList<>();
        _documentos = Common.getDocumentos(Pais_id);

        String curDoc = datos.tipo_documento_identidad_c;
        int Doc_id = 0;





        for (Custom doc: _documentos) {
            if (doc.get_name().equals(curDoc))
            {
                Doc_id = doc.get_id();
            }

        }



        //countries = new ViewModelProvider(this).get(CountryViewModel.class);

        _customAdapter1 = new CustomAdapter(MyContext, R.layout.spinner_item, _documentos);
        _customAdapter1.setDropDownViewResource(R.layout.spinner_item);
        sp_documentos.setAdapter(_customAdapter1);


        Common.selectSpinnerItemByValue(sp_documentos, Doc_id);


        //Generos

        _generos = new ArrayList<>();
        _generos = Common.getGeneros();

        if (datos.gender_c.equals("M")) {
            Genero_id = 1;
        }
        else if (datos.gender_c.equals("F")) {
            Genero_id = 2;
        }

        else {
            Genero_id = 0;
        }

        _customAdapter2 = new CustomAdapter(MyContext, R.layout.spinner_item, _generos);
        _customAdapter2.setDropDownViewResource(R.layout.spinner_item);
        Sp_generos.setAdapter(_customAdapter2);
        Common.selectSpinnerItemByValue(Sp_generos, Genero_id);


        //Phonescodes

        _phonescodes = new ArrayList<>();
        _phonescodes = Common.getPhoneCodes();

        _customAdapter3 = new CustomAdapter2(MyContext, _phonescodes);

        Sp_phones.setAdapter(_customAdapter3);

        Phone_id = Common.getPhoneCode(datos.phone_mobile);

        if (Phone_id > 0) {

            Common.selectSpinnerItemByValue2(Sp_phones, Phone_id);

        }

        input_account_phone.setText(Common.removePhoneCode(datos.phone_mobile));


        for (String marca: datos.marcas_favoritas_c)
        {

            switch (marca) {
                case "ADOC":
                    checked1 = true;
                    account_check1.setImageResource(R.drawable.check_on);
                    break;

                case "Hush Puppies":
                    checked2 = true;
                    account_check2.setImageResource(R.drawable.check_on);
                    break;

                case "CAT":
                    checked3 = true;
                    account_check3.setImageResource(R.drawable.check_on);
                    break;

                case "PAR2":
                    checked4 = true;
                    account_check4.setImageResource(R.drawable.check_on);
                    break;

                case "The North Face":
                    checked5 = true;
                    account_check5.setImageResource(R.drawable.check_on);
                    break;


            }

        }

        input_account_numdocumento.setText(datos.no_documento_c);





         fechanac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"FechaNac");

                showDatePickerDialog();

            }

        });




        Sp_countries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.AppAdocGray));
                Custom curcountry = _customAdapter.getItem(position);
                // Here you can do the action you want to...
                _curPais = curcountry.get_id();
               _nombrePais = curcountry.get_name();

                Log.d(TAG,String.valueOf(_curPais));

                _curPos = position;

                if (_curPais ==5) {
                    layout_nit.setVisibility(View.VISIBLE);
                }
                else {
                    layout_nit.setVisibility(View.GONE);
                }



                if (_curPais != Pais_id) {
                    _customAdapter1.clear();
                    _documentos.clear();
                    _documentos.addAll(Common.getDocumentos(_curPais));
                    _customAdapter1.notifyDataSetChanged();
                    //sp_documentos.setEnabled(true);
                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });


        sp_documentos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.AppAdocGray));
                Custom curDoc = _customAdapter1.getItem(position);
                // Here you can do the action you want to...
                _curDocumento = curDoc.get_id();
                nombreDocumento = curDoc.get_name();


                Log.d(TAG,String.valueOf(_curDocumento));

                //_curPos = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });

////Generos

        Sp_generos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.AppAdocGray));
                Custom curgenero = _customAdapter2.getItem(position);
                // Here you can do the action you want to...
                Genero_id = curgenero.get_id();
                curGenero = curgenero;
                _curPos = position;

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });


        Sp_phones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                //((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.AppAdocGray));
                Custom curphone = _customAdapter3.getItem(position);
                // Here you can do the action you want to...
                curPhone = curphone;
                Phone_id = curphone.get_id();
                _curPos = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });




        ////Generos



        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"SAveChanges");

                saveUserData();

            }

        });



///Deshabilitar los spinners

      //  Sp_countries.setEnabled(false);
      //  sp_documentos.setEnabled(false);



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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.account_check1:
                // code for button when user clicks buttonOne.
                checked1 = !checked1;
                if (checked1){
                    account_check1.setImageResource(R.drawable.check_on);
                }

                else {
                    account_check1.setImageResource(R.drawable.check_off);
                }

                break;

            case R.id.account_check2:
                // code for button when user clicks buttonOne.
                checked2 = !checked2;
                if (checked2){
                    account_check2.setImageResource(R.drawable.check_on);
                }

                else {
                    account_check2.setImageResource(R.drawable.check_off);
                }

                break;

            case R.id.account_check3:
                // code for button when user clicks buttonOne.
                checked3 = !checked3;
                if (checked3){
                    account_check3.setImageResource(R.drawable.check_on);
                }

                else {
                    account_check3.setImageResource(R.drawable.check_off);
                }

                break;

            case R.id.account_check4:
                // code for button when user clicks buttonOne.
                checked4 = !checked4;
                if (checked4){
                    account_check4.setImageResource(R.drawable.check_on);
                }

                else {
                    account_check4.setImageResource(R.drawable.check_off);
                }

                break;

            case R.id.account_check5:
                // code for button when user clicks buttonOne.
                checked5 = !checked5;
                if (checked5){
                    account_check5.setImageResource(R.drawable.check_on);
                }

                else {
                    account_check5.setImageResource(R.drawable.check_off);
                }

                break;


            default:
                break;
        }
    }




    private void saveUserData()
    {

        Log.d(TAG,"Siguiente");


        String _name = name.getText().toString();
        String _lastname = lastname.getText().toString();
        String numDocumento = input_account_numdocumento.getText().toString();
        String nit = _nit.getText().toString();


        if (_curPais <1)
        {
            Common.showWarningDialog(MyContext.getResources().getString(R.string.aviso),MyContext.getResources().getString(R.string.tupais), MyContext);
            Sp_countries.setBackground(MyContext.getDrawable(R.drawable.roundtextred));
            return;
        }
        else {
            Sp_countries.setBackground(MyContext.getDrawable(R.drawable.roundtext));
        }



        if (_name.length()<3)
        {
            Common.showWarningDialog(MyContext.getResources().getString(R.string.aviso),MyContext.getResources().getString(R.string.tunombre), MyContext);
            name.setBackground(MyContext.getDrawable(R.drawable.roundtextred));
            return;
        }
        else {
            name.setBackground(MyContext.getDrawable(R.drawable.roundtext));
        }

        if (_lastname.length()<5)
        {
            Common.showWarningDialog(MyContext.getResources().getString(R.string.aviso),MyContext.getResources().getString(R.string.tuapellido), MyContext);
            lastname.setBackground(MyContext.getDrawable(R.drawable.roundtextred));
            return;
        }
        else {
            lastname.setBackground(MyContext.getDrawable(R.drawable.roundtext));
        }





        if (_curDocumento <1)
        {
            Common.showWarningDialog(MyContext.getResources().getString(R.string.aviso),MyContext.getResources().getString(R.string.tudocumento), MyContext);
            sp_documentos.setBackground(MyContext.getDrawable(R.drawable.roundtextred));
            return;
        }
        else {
            sp_documentos.setBackground(MyContext.getDrawable(R.drawable.roundtext));
        }




        if(numDocumento.length() < 8)
        {
            Common.showWarningDialog(MyContext.getResources().getString(R.string.aviso),MyContext.getResources().getString(R.string.tudocnumer), MyContext);
            sp_documentos.setBackground(MyContext.getDrawable(R.drawable.roundtextred));
            return;
        }
        else {
            sp_documentos.setBackground(MyContext.getDrawable(R.drawable.roundtext));
        }


        if(nit.length() < 4 && _curPais == 5)
        {
            Common.showWarningDialog(MyContext.getResources().getString(R.string.aviso),MyContext.getResources().getString(R.string.tunit), MyContext);
            _nit.setBackground(MyContext.getDrawable(R.drawable.roundtextred));
            return;
        }
        else {
            _nit.setBackground(MyContext.getDrawable(R.drawable.roundtext));
        }


        //Marcas favoritas

        //["ADOC","PAR2","CAT","Hush Puppies","The North Face"]

        if (checked1)
        {
            //Adoc
            marcas_favoritas.add("ADOC");
        }

        if (checked2) {marcas_favoritas.add("Hush Puppies");}
        if (checked3) {marcas_favoritas.add("CAT");}
        if (checked4) {marcas_favoritas.add("PAR2");}
        if (checked5) {marcas_favoritas.add("The North Face");}




        //String email = _email.getText().toString();

        String fecha_nac = fechanac.getText().toString();

        /*
        if (fecha_nac.length()<2)
        {
            Common.showWarningDialog(MyContext.getResources().getString(R.string.aviso),MyContext.getResources().getString(R.string.tufecha), MyContext);
            return;
        }
*/


/*

    if (password.length() >1) {
        if (password.length() < 8) {
            Common.showWarningDialog(MyContext.getResources().getString(R.string.aviso), MyContext.getResources().getString(R.string.tuminpass), MyContext);
            return;
        }

        if (!password.equals(cpassword)) {
            Common.showWarningDialog(MyContext.getResources().getString(R.string.aviso), MyContext.getResources().getString(R.string.passcoinciden), MyContext);
            return;
        }
    }


    newPassword = password;
*/

    String cur_phone = input_account_phone.getText().toString();

    if (cur_phone.length() >6)
    {
        cur_phone = "+" + String.valueOf(Phone_id) + "-" + cur_phone;

    }
    else {
        cur_phone = "";
    }


        Common datos = Common.getInstance();
        datos.first_name = _name;
        datos.last_name = _lastname;
        datos.country_id = _curPais;
        datos.country = _nombrePais;
        //datos.country = curPais.get_name();
        datos.tipo_documento_identidad_c = nombreDocumento;
        datos.doc_identidad_c = numDocumento;
        datos.phone_mobile = cur_phone;
        datos.birthdate = fecha_nac;
        datos.nit = nit;
        datos.gender_c = curGenero.get_desc();

        //datos.password = password;

        Common.saveUserValue(MyContext);


        //navigationInterface.closeFragment();



        String Birthday = "";

        if (fecha_nac.length()>6) {
        Birthday = Common.convertDateFormat (fecha_nac);
    }
/*
        if (fecha_nac.length()>6) {
            String[] parts = fecha_nac.split("/");

            if (parts.length <2) {
                parts = fecha_nac.split("-");
            }

            if (parts.length >2) {

                String year = parts[2].trim();
                String month = parts[1].trim();
                String day = parts[0].trim();

                if (month.length() == 1) month = "0" + month;
                if (day.length() == 1) day = "0" + day;

                Birthday = year + "-" + month + "-" + day;
            }
            else
            {
                Birthday = "";
            }


        }
*/
        SugarContact user = new SugarContact();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        user.first_name = _name;
        user.last_name = _lastname;
        //user.phone_mobile = input_account_phone.getText().toString();
        user.doc_identidad_c = numDocumento;
        user.tipo_documento_identidad_c = nombreDocumento;

        user.primary_address_street = "";
        user.alt_address_street = "";
        user.primary_address_country = _nombrePais;
        user.birthday = Birthday;
        user.estado_civil_c = "";
        user.primary_address_state = "";
        user.id_user = datos.assigned_user_id;
        user.nit_c = nit;
        user.phone_mobile = cur_phone;
        user.gender_c = curGenero.get_desc();


        String[] marcas = new String[marcas_favoritas.size()];
        for(int i=0;i<marcas_favoritas.size();i++){
            marcas[i] = marcas_favoritas.get(i);
        }

        user.marcas_favoritas_c = marcas;
        datos.marcas_favoritas_c = marcas_favoritas;


        new SugarUpdate(user).execute();



    }

private void UpdatePassword()
{

    FirebaseUser user = mAuth.getCurrentUser();
    ProgressDialog _progressDialog;

    _progressDialog = ProgressDialog.show( MyContext, "Espera un momento..", "Actualizando contraseña..", true );


    user.updatePassword(newPassword)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        _progressDialog.dismiss();
                        Log.d(TAG, "User password updated.");
                        navigationInterface.closeFragment();
                    }
                    else
                    {
                        _progressDialog.dismiss();
                        Toast.makeText(MyContext, MyContext.getResources().getText(R.string.noactpassword),  Toast.LENGTH_SHORT).show();
                        navigationInterface.closeFragment();
                    }
                }
            });


}


    private class SugarUpdate extends AsyncTask<Void, Void, HttpClient.HttpResponse> {

        //Context _context;
        SugarContact sugarUser;
        ProgressDialog _progressDialog;




        public SugarUpdate(SugarContact sugaruser)
        {
            sugarUser = sugaruser;
            //_context = context;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            _progressDialog = ProgressDialog.show( MyContext, "Espera un momento..", "Actualizando tus datos..", true );
        }

        @Override
        protected HttpClient.HttpResponse doInBackground( Void... arg0 )
        {

            JSONObject jsonParam = new JSONObject();
            JSONObject jsonUser = new JSONObject();

            try {


                jsonUser.put("first_name", sugarUser.first_name);
                jsonUser.put("last_name", sugarUser.last_name);
                jsonUser.put("phone_mobile", sugarUser.phone_mobile);
                jsonUser.put("no_documento_c", sugarUser.doc_identidad_c);
                jsonUser.put("doc_identidad_c", sugarUser.tipo_documento_identidad_c);

                //jsonUser.put("email1", sugarUser.email1);
                //jsonUser.put("primary_address_street", sugarUser.primary_address_street);
                //jsonUser.put("alt_address_street", sugarUser.alt_address_street);
                jsonUser.put("birthday", sugarUser.birthday);
                jsonUser.put("nit_c", sugarUser.nit_c);
                jsonUser.put("gender_c", sugarUser.gender_c);


                //jsonUser.put("estado_civil_c", sugarUser.estado_civil_c);
                //jsonUser.put("primary_address_state", sugarUser.primary_address_state);
                jsonUser.put("primary_address_country", sugarUser.primary_address_country);
                jsonUser.put("id", sugarUser.id_user);

                jsonUser.put("marcas_favoritas_c",new JSONArray(marcas_favoritas));


                jsonParam.put("data", jsonUser);

            }


            catch (Exception e)
            {
                _progressDialog.dismiss();
                return null;
            }

            String StrJson = jsonParam.toString();

            HttpClient.HttpResponse response = HttpClient.postJson( Common.Update_link, jsonParam );
            android.util.Log.d( "TEST", String.format( "HTTP CODE: %d RESPONSE: %s", response.getCode(), response.getResponse() ) );

            return response;
        }


        @Override
        protected void onPostExecute( HttpClient.HttpResponse result )
        {
            super.onPostExecute( result );

            _progressDialog.dismiss();

            if( result.getCode() == 200 )
            {
                try
                {
                    JSONObject json = new JSONObject( result.getResponse() );
                    datos.first_name = sugarUser.first_name;

                    if (newPassword.length()>7)
                    {
                        UpdatePassword();
                    }

                    else {
                        navigationInterface.closeFragment();
                    }

                }
                catch( Exception e )
                {
                    android.util.Log.e( "JSONParser", "Cant parse: " + e.getMessage() );
                    //common.showWarningDialog("! No valido ¡", "No se pudo actualizar", myContext);
                    navigationInterface.closeFragment();
                }
            }



        }


    }



}