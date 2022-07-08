package com.intelik.appadoc;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Models.Comm_User;
import Models.Custom;
import Models.User_Intelik;

public class Common {

    private static Common instance;

    private static User_Intelik user;

    public int created_by = 1;
    public String description=  "Registrado desde puntos ADOC";
    public String first_name = "";
    public String last_name = "";
    public String country = "";
    public int country_id = 0;
    public String phone_mobile= "";
    public String primary_address_country = "";
    public String lead_source = "web_page";
    public String birthdate = "";
    public Boolean cookie_consent = false;
    public String cookie_consent_received_on = "";
    public String dp_consent_last_updated = "";
    public int assigned_user_id = 1;
    public String team_count = "";
    public String email1 = "";
    public Boolean email_subscription_c = true;
    public String gender_c = "";
    public String doc_identidad_c = "";
    public String tipo_documento_identidad_c = "";
    public Boolean filtro_sync_market_c = false;
    public String pertenece_programa_c = "1";

    public String Token = "";
    public String RefreshToken = "";
    public Date TokenTime;
    public int token_expires_in = 0;
    public String password = "";

    public int Curpage = 0;

    public static final String GetContact_link = "https://us-central1-puntos-adoc.cloudfunctions.net/getContact";
    public static final String Register_link = "https://us-central1-puntos-adoc.cloudfunctions.net/saveContact";
    public static final String Update_link = "https://us-central1-puntos-adoc.cloudfunctions.net/updateContact";


    public boolean notificaciones_sms = false;
    public boolean notificaciones_push = false;
    public boolean notificaciones_email = false;


    //shared values
    public static final String VAR_USER_ID = "USER_ID";
    public static final String VAR_USER_NAME = "USER_NAME";
    public static final String VAR_LOGIN_NAME = "LOGIN_NAME";
    public static final String VAR_USER_APELLIDOS = "USER_APELLIDOS";
    public static final String VAR_USER_GENDER = "USER_GENDER";
    public static final String VAR_USER_BIRTHDAY = "USER_BIRTHDAY";

    
    public static void initInstance()
    {
        if (instance == null)
        {
            // Create the instance
            instance = new Common();
        }
    }

    public static Common getInstance()
    {
        if (instance == null)
        {
            // Create the instance
            instance = new Common();
        }


        // Return the instance
        return instance;
    }

    private Common()
    {
        // Constructor hidden because this is a singleton
        user = new User_Intelik();
    }

    public static ArrayList<Custom> getDocumentos(int pais)
    {
        ArrayList<Custom> documentos = new ArrayList<>();

        documentos.add(new Custom(0, "Tipo de Documento *"));

        switch (pais){
            case 1:  //Costa rica
                documentos.add(new Custom(1, "DUI"));
                documentos.add(new Custom(2, "PASAPORTE"));
                break;

            case 2:  //Guatemala
                documentos.add(new Custom(1, "DPI"));
                documentos.add(new Custom(2, "PASAPORTE"));
                break;

            case 3:  //Honduras
                documentos.add(new Custom(1, "DNI"));
                documentos.add(new Custom(2, "PASAPORTE"));
                break;

            case 4:  //Nicaragua
                documentos.add(new Custom(1, "CED"));
                documentos.add(new Custom(2, "PASAPORTE"));
                break;

            case 5:  //Nicaragua
                documentos.add(new Custom(1, "CED"));
                documentos.add(new Custom(2, "PASAPORTE"));
                break;

            default:

            break;

        }

    return documentos;

    }

    public static ArrayList<Custom> getDocumentosTodos()
    {
        ArrayList<Custom> documentos = new ArrayList<>();

        documentos.add(new Custom(0, "Tipo de Documento *"));

        documentos.add(new Custom(1, "PASAPORTE"));
        documentos.add(new Custom(2, "DUI"));
        documentos.add(new Custom(3, "DPI"));
        documentos.add(new Custom(4, "DNI"));
        documentos.add(new Custom(5, "CED"));

        return documentos;

    }



    public static ArrayList<Custom> getGeneros() {
        ArrayList<Custom> generos = new ArrayList<>();
        generos.add(new Custom(0, "Género"));
        generos.add(new Custom(1, "MASCULINO"));
        generos.add(new Custom(2, "FEMENINO"));
        generos.add(new Custom(3, "No especificar"));

        return generos;

    }


    public static void showWarningDialog(String title ,String message, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    public static boolean isValidMail(String email) {
        boolean check;
        Pattern p;
        Matcher m;

        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        p = Pattern.compile(EMAIL_STRING);

        m = p.matcher(email);
        check = m.matches();

        /*
        if(!check) {
            //       txtEmail.setError("Not Valid Email");
            Log.d(TAG,"Not Valid Email");
        }
        */

        return check;
    }




        public static void saveUserValue(Context context)
        {

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences( context );
            SharedPreferences.Editor editor = sharedPref.edit();

            Common datos = Common.getInstance();

            editor.putInt(VAR_USER_ID, 0);
            editor.putString(VAR_USER_NAME, datos.first_name);
            editor.putString(VAR_LOGIN_NAME, datos.email1);
            editor.putString(VAR_USER_APELLIDOS, datos.last_name);
            editor.putString(VAR_USER_GENDER, datos.gender_c);
            editor.putString(VAR_USER_BIRTHDAY, datos.birthdate);
            editor.commit();

        }


    public static void saveUserValue(Comm_User usuario, Context context)
    {

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences( context );
        SharedPreferences.Editor editor = sharedPref.edit();

        boolean hasChanges = false;

        editor.putInt(VAR_USER_ID, 0);

        if (usuario.first_name.length()>0) {editor.putString(VAR_USER_NAME, usuario.first_name); hasChanges = true;}
        if (usuario.last_name.length()>0) {editor.putString(VAR_USER_APELLIDOS, usuario.last_name); hasChanges = true;}
        if (usuario.birthday.length()>0) {editor.putString(VAR_USER_BIRTHDAY, usuario.birthday); hasChanges = true;}

        if (hasChanges) {
            editor.commit();
        }

    }



        public static Comm_User getUserValues(Context context)
        {
            Comm_User curUser = new Comm_User();

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences( context );
            SharedPreferences.Editor editor = sharedPref.edit();

            Common datos = Common.getInstance();

            curUser.User_id = sharedPref.getInt(VAR_USER_ID,0);
            curUser.login_name = sharedPref.getString(VAR_LOGIN_NAME, "");
            curUser.first_name = sharedPref.getString(VAR_USER_NAME, "");
            curUser.last_name = sharedPref.getString(VAR_USER_APELLIDOS, "");
            curUser.user_gender = sharedPref.getString(VAR_USER_GENDER, "");
            curUser.birthday = sharedPref.getString(VAR_USER_BIRTHDAY, "");

            return curUser;
        }



}
