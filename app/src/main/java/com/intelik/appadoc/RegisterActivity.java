package com.intelik.appadoc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import Models.Email_buscar;
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
import com.intelik.appadoc.utils.HttpClient;

import org.json.JSONArray;
import org.json.JSONObject;


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

    private String sugar_id;

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
                //GotoPage2();
                RegisterUser();

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

            if (contacto.email1.equals("")) {
                        RegisterInSugar();
            }

            else {
                sugar_id = contacto.id;
                datos.assigned_user_id = sugar_id;
                Common.saveUserValue(context);

                //RegisterInSugar();
                UpdateInSugar();

            }

                }
            });

        }

        private void RegisterInSugar()
        {

            Log.d(TAG, "Register in sugar1");

            ProgressDialog _progressDialog;
          //  _progressDialog = ProgressDialog.show( context, "Espera un momento..", "Guardando tus datos..", true );

            SugarContact user = new SugarContact();

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);

            /*
            String curBirthday = datos.birthdate;
            String[] parts = curBirthday.split("/");

            String year = parts[2].trim();
            String month = parts[1].trim();
            String day = parts[0].trim();

            if (month.length() ==1) month = "0" + month;
            if (day.length() ==1) day = "0" + day;

            String Birthday = year + "-" + month + "-" + day;
            */


            //user.sync_key = "null";
            user.first_name = datos.first_name;
            user.last_name = datos.last_name;
            user.phone_mobile = datos.phone_mobile;
            user.doc_identidad_c = datos.doc_identidad_c;
            user.tipo_documento_identidad_c = datos.tipo_documento_identidad_c;
            user.email1 = datos.email1;
            user.primary_address_street = "";
            user.alt_address_street = "";
            user.birthday = "";
            user.primary_address_country = datos.country;
            user.estado_civil_c = "";
            user.primary_address_state = "";

            user.date_entered = (String) df.format(new Date());


            String userData = new Gson().toJson(user);

            Email_buscar email_ = new Email_buscar();
            email_.data = user;

            String jsonString = new Gson().toJson(email_);

            //String jsonString = "{\"data\" :\"" + userData + "\"}";

            Log.d(TAG, "Register in sugar2");


            new SugarRegister(user).execute();

            /*
            RegisterSugar(jsonString)
                    .addOnCompleteListener(new OnCompleteListener<Object>() {


                        @Override
                        public void onComplete(@NonNull Task<Object> task) {
                            _progressDialog.dismiss();

                            Log.d(TAG, "Register in sugar completed");
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
                                Object result = task.getResult();
                                GotoMain();

                            }

                        }
                    });
*/



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


    private void UpdateInSugar()
    {

        ProgressDialog _progressDialog;
        _progressDialog = ProgressDialog.show( context, "Espera un momento..", "Actualizando tus datos..", true );

        SugarContact user = new SugarContact();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
/*
        String curBirthday = datos.birthdate;
        String[] parts = curBirthday.split("/");

        String year = parts[2].trim();
        String month = parts[1].trim();
        String day = parts[0].trim();

        if (month.length() ==1) month = "0" + month;
        if (day.length() ==1) day = "0" + day;

        String Birthday = year + "-" + month + "-" + day;
        */

        //user.sync_key = "null";
        user.first_name = datos.first_name;
        user.last_name = datos.last_name;
        user.phone_mobile = datos.phone_mobile;
        user.doc_identidad_c = datos.doc_identidad_c;
        user.tipo_documento_identidad_c = datos.tipo_documento_identidad_c;
        user.email1 = datos.email1;
        user.primary_address_street = "";
        user.alt_address_street = "";
        user.primary_address_country = datos.country;
        //user.birthday = Birthday;
        user.estado_civil_c = "";
        user.primary_address_state = "";

        user.date_entered = (String) df.format(new Date());


        //String userData = new Gson().toJson(user);

        //Email_buscar email_ = new Email_buscar();
        //email_.data = new Gson().toJson(user);;

        //String jsonString = new Gson().toJson(email_);

        //String jsonString = "{\"data\" :" + userData + "}";

        new SugarUpdate(user).execute();

        /*
        UpdateSugar(jsonString)
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

*/


    }



    private Task<Object> UpdateSugar(String text) {
        // Create the arguments to the callable function.
        Map<String, Object> data = new HashMap<>();
        data.put("text", text);
        data.put("push", true);

        return mFunctions
                .getHttpsCallable("updateContact")
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

///// Nuevos metodos para registro

    private class SugarRegister extends AsyncTask<Void, Void, HttpClient.HttpResponse> {

        //Context _context;
        SugarContact sugarUser;
        ProgressDialog _progressDialog;




        public SugarRegister(SugarContact sugaruser)
        {
            sugarUser = sugaruser;
            //_context = context;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            _progressDialog = ProgressDialog.show( context, "Espera un momento..", "Registrando en el programa de lealtad..", true );
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
                jsonUser.put("email1", sugarUser.email1);
                jsonUser.put("primary_address_street", sugarUser.primary_address_street);
                jsonUser.put("alt_address_street", sugarUser.alt_address_street);
                jsonUser.put("birthday", sugarUser.birthday);
                jsonUser.put("estado_civil_c", sugarUser.estado_civil_c);
                jsonUser.put("primary_address_state", sugarUser.primary_address_state);
                jsonUser.put("primary_address_country", sugarUser.primary_address_country);
                jsonUser.put("date_entered", sugarUser.date_entered);
                jsonParam.put("data", jsonUser);

            }


            catch (Exception e)
            {
                _progressDialog.dismiss();
                return null;
            }

            String StrJson = jsonParam.toString();

            HttpClient.HttpResponse response = HttpClient.postJson( Common.Register_link, jsonParam );
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

                     GotoMain();

                }
                catch( Exception e )
                {
                    android.util.Log.e( "JSONParser", "Cant parse: " + e.getMessage() );
                    //common.showWarningDialog("! No valido ¡", "No se pudo actualizar", myContext);
                }
            }



        }


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
            _progressDialog = ProgressDialog.show( context, "Espera un momento..", "Actualizando en sugar..", true );
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
                //jsonUser.put("birthday", sugarUser.birthday);
                //jsonUser.put("estado_civil_c", sugarUser.estado_civil_c);
                //jsonUser.put("primary_address_state", sugarUser.primary_address_state);
                jsonUser.put("primary_address_country", sugarUser.primary_address_country);
                jsonUser.put("id", sugar_id);




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
                    GotoMain();

                }
                catch( Exception e )
                {
                    android.util.Log.e( "JSONParser", "Cant parse: " + e.getMessage() );
                    //common.showWarningDialog("! No valido ¡", "No se pudo actualizar", myContext);
                }
            }



        }


    }

    /// Nuevos metodos


}