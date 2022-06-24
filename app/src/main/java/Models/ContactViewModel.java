package Models;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ContactViewModel extends ViewModel {


    private MutableLiveData<User_Intelik> Contacto;
    private FirebaseAuth mAuth;
    private FirebaseFunctions mFunctions;

    public ContactViewModel()
    {
        mAuth = FirebaseAuth.getInstance();
        mFunctions = FirebaseFunctions.getInstance();


    }



    public LiveData<User_Intelik> getContacto(String email) {
        if (Contacto == null) {
            Contacto = new MutableLiveData<User_Intelik>();
            loadContacto(email);
        }
        return Contacto;
    }


    private void loadContacto(String email) {
        // Do an asynchronous operation to fetch users.

        //  marcas.postValue(nuevas_marcas);


        BusquedaEmail busqueda = new BusquedaEmail();
        List<Email_buscar> buscar = new ArrayList<Email_buscar>();

        Email_buscar email_ = new Email_buscar();
        email_.data = email;
        buscar.add(email_);

        busqueda.filter = buscar;

        String jsonString = new Gson().toJson(email_);
        //String jsonString = "emadriga@gmail.com";




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
        data.put("text", text);
        data.put("push", true);

        return mFunctions
                .getHttpsCallable("getContact")
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
