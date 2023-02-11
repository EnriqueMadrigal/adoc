package Models;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.google.firebase.functions.HttpsCallableResult;
import com.google.gson.Gson;
import com.intelik.appadoc.Common;
import com.intelik.appadoc.R;
import com.intelik.appadoc.utils.HttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ContactViewModel extends ViewModel {


    private MutableLiveData<User_Intelik> Contacto;
    private FirebaseAuth mAuth;
    private FirebaseFunctions mFunctions;


    private String currentEmail;

    public ContactViewModel()
    {
        mAuth = FirebaseAuth.getInstance();
        mFunctions = FirebaseFunctions.getInstance();


    }



    public LiveData<User_Intelik> getContacto(String email) {
        if (Contacto == null) {
            Contacto = new MutableLiveData<User_Intelik>();
            //loadContacto(email);
            //GetContacto(email);
            this.currentEmail = email;
           new ContactoLoad(email).execute();
        }
        return Contacto;
    }

    public LiveData<User_Intelik> refresh(String email) {
        if (Contacto == null) {
            Contacto = new MutableLiveData<User_Intelik>();
            //loadContacto(email);
            //GetContacto(email);
            this.currentEmail = email;
            new ContactoLoad(email).execute();
        }
        return Contacto;
    }

    private void loadContacto(String email) {
        // Do an asynchronous operation to fetch users.

        //  marcas.postValue(nuevas_marcas);


        BusquedaEmail busqueda = new BusquedaEmail();
        List<Email_buscar> buscar = new ArrayList<Email_buscar>();

        //Email_buscar email_ = new Email_buscar();
        //email_.data = email;
        //buscar.add(email_);

        //busqueda.filter = buscar;

        //email = "emadriga@gmail.com";

        //String jsonString = new Gson().toJson(email_);
        //String jsonString = "emadriga@gmail.com";

        //String jsonString = "{\"data\" : \"" + email + "\"}";

        JSONObject jsonParam = new JSONObject();

        String jsonString = "";

        try {
            jsonParam.put("data", email);

            jsonString = jsonParam.toString();
        }

        catch (Exception e)
        {
            return;
        }


        addMessage(jsonString)
                .addOnCompleteListener(new OnCompleteListener<Object>() {


                    @Override
                    public void onComplete(@NonNull Task<Object> task) {


                        if (!task.isSuccessful()) {
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

                            User_Intelik curUser = new User_Intelik();

                            //JSONArray jarray = (JSONArray) result;
                            try {

                                ArrayList<Object> mapa = (ArrayList<Object>) result;
                            //    if (mapa.size() == 0) return;

                                Map<String, String> hashMap = (Map<String, String>) mapa.get(0);

                                curUser.id = hashMap.get("id");
                                curUser.name = hashMap.get("name");
                                curUser.email1 = hashMap.get("email1");

                                curUser.description = hashMap.get("description");
                                curUser.first_name = hashMap.get("first_name");
                                curUser.last_name = hashMap.get("last_name");
                                curUser.phone_mobile = hashMap.get("phone_mobile");


                                curUser.nivel_anterior_c = hashMap.get("nivel_anterior_c");
                                curUser.puntos_disponibles_c = hashMap.get("puntos_disponibles_c");
                                curUser.puntos_redimidos_c = hashMap.get("puntos_redimidos_c");
                                curUser.puntos_acumulados_c = hashMap.get("puntos_acumulados_c");
                                curUser.nivel_del_cliente_c = hashMap.get("nivel_del_cliente_c");
                                curUser.gender_c = hashMap.get("gender_c");
                                curUser.nit_c  = hashMap.get("nit_c");

                                Contacto.postValue(curUser);

                            }

                            catch (Exception e)
                            {
                                Contacto.postValue(curUser);
                            }




                        }

                    }
                });



    }


    private Task<Object> addMessage(String text) {
        // Create the arguments to the callable function.
        Map<String, Object> data = new HashMap<>();
        data.put("data", text);
        //data.put("push", true);

        return mFunctions
                .getHttpsCallable("getContact")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, Object>() {
                    @Override
                    public Object then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        //Object result = (Object) task.getResult().getData();
                        Object result = (Object) task.getResult();
                        return result;
                    }
                });
    }



    private void GetContacto(String email)
    {

        new ContactoLoad(email).execute();

    }


    private void handleSentContacto(HttpClient.HttpResponse response)
    {
        if( response.getCode() == 200 )
        {
            try
            {
                JSONObject json = new JSONObject( response.getResponse() );

                JSONArray Jarray = json.getJSONArray("result");

                JSONObject result = Jarray.getJSONObject(0);

                User_Intelik curUser = new User_Intelik();


                curUser.id =  result.getString("id");
                curUser.name = result.getString("name");
                curUser.email1 = result.getString("email1");

                curUser.description = result.getString("description");
                curUser.first_name = result.getString("first_name");
                curUser.last_name = result.getString("last_name");
                curUser.phone_mobile = result.getString("phone_mobile");
                curUser.nivel_del_cliente_c = result.getString("nivel_del_cliente_c");

                curUser.nivel_anterior_c = result.getString("nivel_anterior_c");
                curUser.puntos_disponibles_c = result.getString("puntos_disponibles_c");
                curUser.puntos_redimidos_c = result.getString("puntos_redimidos_c");
                curUser.puntos_acumulados_c = result.getString("puntos_acumulados_c");
                curUser.birthdate = result.getString("birthdate");
                curUser.gender_c = result.getString("gender_c");
                curUser.nit_c  = result.getString("nit_c");


                Contacto.postValue(curUser);


                ///////////








            }
            catch( Exception e )
            {
                android.util.Log.e( "JSONParser", "Cant parse: " + e.getMessage() );
                //common.showWarningDialog("! No valido ¡", "No se pudo actualizar", myContext);
            }
        }
        else {
            //Common.alert( this, "No se ha podido registrar, por favor intenta nuevamente más tarde." );
        }



    }

    private class ContactoLoad extends  AsyncTask<Void, Void, HttpClient.HttpResponse> {

        //Context _context;
        String _email;

        public ContactoLoad(String emailBusqueda)
        {
            _email = emailBusqueda;
            //_context = context;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected HttpClient.HttpResponse doInBackground( Void... arg0 )
        {

            JSONObject jsonParam = new JSONObject();

            try {
                jsonParam.put("data", _email);

            }


            catch (Exception e)
            {
                return null;
            }


            HttpClient.HttpResponse response = HttpClient.postJson( Common.GetContact_link, jsonParam );
            android.util.Log.d( "TEST", String.format( "HTTP CODE: %d RESPONSE: %s", response.getCode(), response.getResponse() ) );

            return response;
        }


        @Override
        protected void onPostExecute( HttpClient.HttpResponse result )
        {
            super.onPostExecute( result );
            //handleSentContacto( result );
/*
            User_Intelik curUser = new User_Intelik();
            Contacto.postValue(curUser);


 */
            User_Intelik curUser = new User_Intelik();

            if( result.getCode() == 200 )
            {
                try
                {
                    JSONObject json = new JSONObject( result.getResponse() );

                    JSONArray Jarray = json.getJSONArray("result");

                    JSONObject resultado = Jarray.getJSONObject(0);




                    curUser.id =  resultado.getString("id");
                    //curUser.name = resultado.getString("name");
                    curUser.email1 = resultado.getString("email1");
                    curUser.birthdate = (resultado.getString("birthdate") != null ?resultado.getString("birthdate") : "");

                    //curUser.description = resultado.getString("description");
                    curUser.first_name = resultado.getString("first_name");
                    curUser.last_name = resultado.getString("last_name");
                    //curUser.phone_mobile = resultado.getString("phone_mobile");
                    curUser.nivel_del_cliente_c = resultado.getString("nivel_del_cliente_c");


                    curUser.nivel_anterior_c = resultado.getString("nivel_anterior_c");
                    curUser.puntos_disponibles_c = resultado.getString("puntos_disponibles_c");
                    curUser.puntos_redimidos_c = resultado.getString("puntos_redimidos_c");
                    curUser.puntos_acumulados_c = resultado.getString("puntos_acumulados_c");


                    curUser.puntos_disponibles_promo_c = resultado.getString("puntos_disponibles_promo_c");
                    curUser.puntos_acumulados_promo_c = resultado.getString("puntos_acumulados_promo_c");
                    curUser.puntos_redimidos_promo_c = resultado.getString("puntos_redimidos_promo_c");


                    curUser.avance_nivel_c = resultado.getString("avance_nivel_c");
                    curUser.puntos_mantener_nivel_c = resultado.getString("puntos_mantener_nivel_c");
                    curUser.porcentaje_subir_c = resultado.getString("porcentaje_subir_c");
                    curUser.puntos_subir_nivel_c = resultado.getString("puntos_subir_nivel_c");

                    curUser.primary_address_country =  resultado.getString("primary_address_country");
                    curUser.phone_mobile =  resultado.getString("phone_mobile");
                    curUser.no_documento_c = resultado.getString("no_documento_c");
                    curUser.doc_identidad_c = resultado.getString("doc_identidad_c");

                    curUser.gender_c = resultado.getString("gender_c");
                    curUser.nit_c  = resultado.getString("nit_c");

                    //Obtener las marcas
                    JSONArray Jarray_marcas = resultado.getJSONArray("marcas_favoritas_c");

                    for(int i = 0; i < Jarray_marcas.length(); i++)
                        curUser.marcas_favoritas_c.add(Jarray_marcas.getString(i));


                    //falta vigencia_puntos_promo_c

                    if (curUser.puntos_redimidos_c.equals("null"))
                    {
                        curUser.puntos_redimidos_c = "0";
                    }

                    if (curUser.puntos_redimidos_promo_c.equals("null"))
                    {
                        curUser.puntos_redimidos_promo_c = "0";
                    }



                    Contacto.postValue(curUser);


                    ///////////








                }
                catch( Exception e )
                {
                    android.util.Log.e( "JSONParser", "Cant parse: " + e.getMessage() );
                    //common.showWarningDialog("! No valido ¡", "No se pudo actualizar", myContext);
                    Contacto.postValue(curUser);
                }
            }



        }


    }

}
