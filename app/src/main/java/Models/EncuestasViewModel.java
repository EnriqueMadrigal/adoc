package Models;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.intelik.appadoc.Common;
import com.intelik.appadoc.utils.HttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EncuestasViewModel extends ViewModel {

    private MutableLiveData<List<Custom>> Encuestas;


    public EncuestasViewModel(){

    }


    public LiveData<List<Custom>> getEncuestas() {

        if (Encuestas == null){

            Encuestas = new MutableLiveData<List<Custom>>();
            new EncuestasLoad().execute();
        }

        return Encuestas;
    }


    private class EncuestasLoad extends AsyncTask<Void, Void, HttpClient.HttpResponse> {

        //Context _context;


        public EncuestasLoad()
        {

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


            HttpClient.HttpResponse response = HttpClient.get(Common.Encuestas_link, null);
            android.util.Log.d( "TEST", String.format( "HTTP CODE: %d RESPONSE: %s", response.getCode(), response.getResponse() ) );

            return response;
        }


        @Override
        protected void onPostExecute( HttpClient.HttpResponse result )
        {
            super.onPostExecute( result );
            Custom curPrueba = new Custom();
            List<Custom> pruebas = new ArrayList<Custom>();

            if( result.getCode() == 200 )
            {
                try
                {
                    // JSONObject json = new JSONObject( result.getResponse() );

                    JSONArray Jarray =  new JSONArray(result.getResponse());

                    //JSONObject resultado = Jarray.getJSONObject(0);




                   for (int i= 0; i< Jarray.length(); i++)
                   {
                        String title = "";
                        String encuesta_url = "";
                        String image_url = "";
                        int id = 0;

                        Custom newEncuesta = new Custom();

                        JSONObject curEncuesta = Jarray.getJSONObject(i);


                           id = curEncuesta.getInt("id");



                       if (curEncuesta.getJSONObject("title") != null) {
                            title = curEncuesta.getJSONObject("title").getString("rendered").toString();
                        }

                       if (curEncuesta.getJSONObject("excerpt") != null) {
                           encuesta_url = curEncuesta.getJSONObject("excerpt").getString("rendered").toString();
                           encuesta_url = encuesta_url.replace("<p>", "");
                           encuesta_url = encuesta_url.replace("</p>", "");
                           encuesta_url = encuesta_url.replace("\n", "");

                       }

                       if (curEncuesta.get("featured_media_src_url") != null) {
                           image_url = curEncuesta.getString("featured_media_src_url").toString();
                       }

                       newEncuesta.set_id(id);
                       newEncuesta.set_name(title);
                       newEncuesta.set_link(encuesta_url);
                       newEncuesta.set_desc(image_url);
                       newEncuesta.set_value1(100);

                       pruebas.add(newEncuesta);
                   }

/*

                    curUser.id =  resultado.getString("id");
                    //curUser.name = resultado.getString("name");
                    curUser.email1 = resultado.getString("email1");

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

*/






                    ///////////




                //pruebas.add(curPrueba);



                }
                catch( Exception e )
                {
                    android.util.Log.e( "JSONParser", "Cant parse: " + e.getMessage() );
                    //common.showWarningDialog("! No valido ¡", "No se pudo actualizar", myContext);
                    pruebas.add(curPrueba);
                }

                Encuestas.postValue(pruebas);

            }



        }


    }




}
