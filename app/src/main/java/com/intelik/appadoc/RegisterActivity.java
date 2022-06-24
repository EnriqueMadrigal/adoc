package com.intelik.appadoc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.google.firebase.functions.HttpsCallableResult;
import com.google.gson.Gson;
import com.intelik.appadoc.fragments.*;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import Models.ContactViewModel;
import Models.Marca;
import Models.MarcaViewModel;
import Models.SugarContact;
import Models.UserViewModel;
import Models.User_Intelik;
import com.intelik.appadoc.interfaces.NavigationInterface;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;


public class RegisterActivity extends AppCompatActivity {


    private UserViewModel usuario;
    private MarcaViewModel marcas;

    private String TAG = "RegisterActivity";

    private NavigationInterface navigationInterface;

    Context context;

    RegisterPage1 registerPage1;
    RegisterPage2 registerPage2;
    RegisterPage3 registerPage3;

    private FirebaseAuth mAuth;
    private ContactViewModel contactos;

    private Common datos;

    private Boolean userExists;
    private FirebaseFunctions mFunctions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        context = this;
        datos = Common.getInstance();
        contactos = new ViewModelProvider(this).get(ContactViewModel.class);
        mFunctions = FirebaseFunctions.getInstance();

       // usuario = new UserViewModel(requireActivity())
        usuario = new ViewModelProvider(this).get(UserViewModel.class);
        marcas = new ViewModelProvider(this).get(MarcaViewModel.class);

        usuario.getUser().observe(this, new Observer<User_Intelik>() {
            @Override
            public void onChanged(@Nullable User_Intelik data) {
                // update ui.
                Log.d("RegisterActiviti", "usuario cargado");
            }
        });

        marcas.getMarcas().observe(this, new Observer<List<Marca>>() {
            @Override
            public void onChanged(@Nullable List<Marca> marcas) {
                // update ui.
                Log.d("RegisterActiviti", "Marcas recibidas");
            }
        });

        GotoPage1();

    }


    private void GotoPage1()
    {
        registerPage1 = new RegisterPage1();
        getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).replace(R.id.fragment_container, registerPage1, "RegisterPage1").commit();

        registerPage1.setOnClickListener(new NavigationInterface() {
            @Override
            public void closeFragment() {
                Log.d(TAG, "Se recibio salida pagina 1");
                GotoPage2();

            }

            @Override
            public void backFragment() {

            }


        });

    }


    private void GotoPage2()
    {
       registerPage2 = new RegisterPage2();
        getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).replace(R.id.fragment_container, registerPage2, "RegisterPage2").commit();

        registerPage2.setOnClickListener(new NavigationInterface() {
        @Override
        public void closeFragment() {
            Log.d(TAG, "Se recibio salida pagina 1");
            GotoPage3();

        }

        @Override
        public void backFragment() {
            GotoPage1();
        }


    });


    }

    private void GotoPage3()
    {
        registerPage3 = new RegisterPage3();
        getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).replace(R.id.fragment_container, registerPage3, "RegisterPage3").commit();

        registerPage3.setOnClickListener(new NavigationInterface() {
            @Override
            public void closeFragment() {
                Log.d(TAG, "Se recibio salida pagina 3");
                RegisterUser();
                //onBackPressed();

            }

            @Override
            public void backFragment() {
                GotoPage2();
            }


        });


    }

        private void RegisterUser()
        {

            //Common datos = Common.getInstance();
            ProgressDialog _progressDialog;

            String email = datos.email1;
            String password = datos.password;

            _progressDialog = ProgressDialog.show( context, "Espera un momento..", "Registro de usuarios..", true );

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                Toast.makeText(context, context.getResources().getText(R.string.registroexitoso),  Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                _progressDialog.dismiss();
                                Common.saveUserValue(context);
                                CheckUserInSugar();
                               /*
                                Intent intent
                                        = new Intent(context, MainActivity.class);
                                startActivity(intent);
                                finish();
                                */


                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                _progressDialog.dismiss();
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(context, context.getResources().getText(R.string.registroinvalido),  Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                    });






        }

        private void GotoMain()
        {
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            finish();
        }

        private void CheckUserInSugar()
        {
            ProgressDialog _progressDialog;
            _progressDialog = ProgressDialog.show( context, "Espera un momento..", "Revisando..", true );

            contactos.getContacto(datos.email1).observe(this, new Observer<User_Intelik>() {

                @Override
                public void onChanged(@Nullable User_Intelik contacto) {
                    // update ui.
                    _progressDialog.dismiss();

            if (contacto == null) {
                        RegisterInSugar();
            }

            else {
                String email1 = datos.email1.toLowerCase();
                String email2 = contacto.email1.toLowerCase();

                if (email1.length() == 0 && email2.length() == 0) {
                    RegisterInSugar();
                    return;
                }

                if (email1.equals(email2)) {
                    userExists = true;
                    //Toast.makeText(context, context.getResources().getText(R.string.registroexitoso),  Toast.LENGTH_SHORT).show();
                    GotoMain();
                } else {
                    RegisterInSugar();
                }
            }

                }
            });

        }

        private void RegisterInSugar()
        {

            ProgressDialog _progressDialog;
            _progressDialog = ProgressDialog.show( context, "Espera un momento..", "Registrando en sugar..", true );

            SugarContact user = new SugarContact();

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

            String curBirthday = datos.birthdate;
            String[] parts = curBirthday.split("/");

            String year = parts[2].trim();
            String month = parts[1].trim();
            String day = parts[0].trim();

            if (month.length() ==1) month = "0" + month;
            if (day.length() ==1) day = "0" + day;

            String Birthday = year + "-" + month + "-" + day;


            user.sync_key = "0";
            user.first_name = datos.first_name;
            user.last_name = datos.last_name;
            user.phone_mobile = datos.phone_mobile;
            user.doc_identidad_c = datos.doc_identidad_c;
            user.email1 = datos.email1;
            user.primary_address_street = "";
            user.alt_address_street = "";
            user.birthday = Birthday;
            user.estado_civil_c = "";
            user.primary_address_state = "";
            user.primary_address_country = "";
            user.date_entered = (String) df.format(new Date());

            String jsonString = new Gson().toJson(user);


            RegisterSugar(jsonString)
                    .addOnCompleteListener(new OnCompleteListener<Object>() {


                        @Override
                        public void onComplete(@NonNull Task<Object> task) {
                            _progressDialog.dismiss();

                            if (!task.isSuccessful()) {
                                Toast.makeText(context, context.getResources().getText(R.string.registroinvalido),  Toast.LENGTH_SHORT).show();
                                Exception e = task.getException();
                                if (e instanceof FirebaseFunctionsException) {
                                    FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
                                    FirebaseFunctionsException.Code code = ffe.getCode();
                                    Object details = ffe.getDetails();
                                }
                            }

                            else
                            {
                                GotoMain();

                            }

                        }
                    });




        }


    private Task<Object> RegisterSugar(String text) {
        // Create the arguments to the callable function.
        Map<String, Object> data = new HashMap<>();
        data.put("text", text);
        data.put("push", true);

        return mFunctions
                .getHttpsCallable("saveContact")
                .call(text)
                .continueWith(new Continuation<HttpsCallableResult, Object>() {
                    @Override
                    public Object then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        Object result = (Object) task.getResult().getData();
                        return result;
                    }
                });
    }





}