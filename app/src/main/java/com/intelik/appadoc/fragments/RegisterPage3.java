package com.intelik.appadoc.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.intelik.appadoc.Common;
import com.intelik.appadoc.R;
import com.intelik.appadoc.interfaces.NavigationInterface;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterPage3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterPage3 extends Fragment implements  View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Context MyContext;

    private TextView pag3ant;

    private ImageButton check_politica;
    private ImageButton check_terminos;

    private boolean checked_politica;
    private boolean checked_terminos;

    private EditText _phone_number;
    private EditText _email;
    private EditText _password;
    private EditText _cpassword;

    private Button Siguiente;

    private NavigationInterface navigationInterface;


    private String TAG = "RegisterPage3";

    public RegisterPage3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterPage3.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterPage3 newInstance(String param1, String param2) {
        RegisterPage3 fragment = new RegisterPage3();
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
        _view = inflater.inflate( R.layout.fragment_register_page3, container, false );

        pag3ant = (TextView) _view.findViewById(R.id.pag_ant3);
        _phone_number = (EditText) _view.findViewById(R.id.input_phone);
        _email = (EditText) _view.findViewById(R.id.input_email);
        _password = (EditText) _view.findViewById(R.id.input_password);
        _cpassword = (EditText) _view.findViewById(R.id.input_cpassword);

        check_terminos = (ImageButton) _view.findViewById(R.id.check_terminos);
        check_politica = (ImageButton) _view.findViewById(R.id.check_politica);

        check_politica.setOnClickListener(this);
        check_terminos.setOnClickListener(this);

        Siguiente = (androidx.appcompat.widget.AppCompatButton) _view.findViewById(R.id.Siguiente_Pag4);


        Common datos = Common.getInstance();
        _phone_number.setText(datos.phone_mobile);
        _email.setText(datos.email1);



        pag3ant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Siguiente");
                  navigationInterface.backFragment();
                /*
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                RegisterPage2 _page2 = RegisterPage2.newInstance("","");
                //resultados _resultados = resultados.newInstance("Herramientas en:" + _obras.get(_curPos).get_desc(), _herramientas);

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations( android.R.anim.slide_in_left, android.R.anim.slide_out_right );
                fragmentTransaction.replace( R.id.fragment_container, _page2, "Pagina 3" );
                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                */

            }

        });



        //Siguiente

        //Siguiente

        Siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Siguiente");

                String phone = _phone_number.getText().toString();
                String email = _email.getText().toString();
                String password = _password.getText().toString();
                String cpassword = _cpassword.getText().toString();


                if (phone.length() <1)
                {
                    Common.showWarningDialog(MyContext.getResources().getString(R.string.aviso),MyContext.getResources().getString(R.string.tuphone), MyContext);
                    return;
                }



                if(email.length() == 0)
                {
                    Common.showWarningDialog(MyContext.getResources().getString(R.string.aviso),MyContext.getResources().getString(R.string.tuemail), MyContext);
                    return;
                }


                if (!Common.isValidMail(email))
                {
                    Common.showWarningDialog(MyContext.getResources().getString(R.string.aviso),MyContext.getResources().getString(R.string.tuemailinvalido), MyContext);
                    return;
                }

                if(password.length() == 0)
                {
                    Common.showWarningDialog(MyContext.getResources().getString(R.string.aviso),MyContext.getResources().getString(R.string.tupass), MyContext);
                    return;
                }

                if(password.length() < 8)
                {
                    Common.showWarningDialog(MyContext.getResources().getString(R.string.aviso),MyContext.getResources().getString(R.string.tuminpass), MyContext);
                    return;
                }

                if (!password.equals(cpassword))
                {
                    Common.showWarningDialog(MyContext.getResources().getString(R.string.aviso),MyContext.getResources().getString(R.string.passcoinciden), MyContext);
                    return;
                }


                if (!checked_politica)
                {
                    Common.showWarningDialog(MyContext.getResources().getString(R.string.aviso),MyContext.getResources().getString(R.string.checkpolitica), MyContext);
                    return;
                }


                if (!checked_terminos)
                {
                    Common.showWarningDialog(MyContext.getResources().getString(R.string.aviso),MyContext.getResources().getString(R.string.checkterminos), MyContext);
                    return;
                }


                Common datos = Common.getInstance();
                datos.phone_mobile = phone;
                datos.email1 = email;
                datos.password = password;

                //getActivity().onBackPressed();
                //getActivity().getFragmentManager().popBackStack();

                navigationInterface.closeFragment();



            }

        });


        return _view;
        //return inflater.inflate(R.layout.fragment_register_page3, container, false);
    }


    @Override
    public void onAttach(Context context) {
        MyContext = context;
        super.onAttach(context);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.check_politica:
                // code for button when user clicks buttonOne.
                checked_politica = !checked_politica;
                if (checked_politica){
                    check_politica.setImageResource(R.drawable.check_on);
                }

                else {
                    check_politica.setImageResource(R.drawable.check_off);
                }


                break;

            case R.id.check_terminos:
                // do your code
                checked_terminos = !checked_terminos;
                if (checked_terminos){
                    check_terminos.setImageResource(R.drawable.check_on);
                }

                else {
                    check_terminos.setImageResource(R.drawable.check_off);
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