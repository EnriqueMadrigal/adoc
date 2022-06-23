package Models;
import android.graphics.Point;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MisPuntosViewModel extends ViewModel {


    private MutableLiveData<List<Custom>> puntos;

    public LiveData<List<Custom>> getPuntos() {
        if ( puntos== null) {
            puntos = new MutableLiveData<List<Custom>>();
            loadPuntos();
        }
        return puntos;
    }


    private void loadPuntos() {
        // Do an asynchronous operation to fetch users.

        Custom point1 = new Custom();
            point1.set_desc("Puntos Acumulados:");
            point1.set_name("0");

        Custom point2 = new Custom();
        point2.set_desc("Puntos Redimidos:");
        point2.set_name("0");

        Custom point3 = new Custom();
        point3.set_desc("Puntos Disponibles:");
        point3.set_name("0");

        Custom point4 = new Custom();
        point4.set_desc("Total de compra acumulada:");
        point4.set_name("0");

        Custom point5 = new Custom();
        point5.set_desc("Nivel Actual:");
        point5.set_name("0");


        Custom point6 = new Custom();
        point6.set_desc("% Avance Nivel:");
        point6.set_name("0%");

        Custom point7 = new Custom();
        point7.set_desc("Puntos necesarios para mantener el nivel:");
        point7.set_name("0");

        Custom point8 = new Custom();
        point8.set_desc("% para subir de nivel:");
        point8.set_name("0%");

        Custom point9 = new Custom();
        point9.set_desc("Puntos necesarios para subir el nivel:");
        point9.set_name("0");

        Custom point10 = new Custom();
        point10.set_desc("Compras realizadas en el último nivel:");
        point10.set_name("0");

        Custom point11 = new Custom();
        point11.set_desc("Nivel anterior del cliente:");
        point11.set_name("0");




        List<Custom> nuevas_customs = new ArrayList<Custom>();

        nuevas_customs.add(point1);
        nuevas_customs.add(point2);
        nuevas_customs.add(point3);
        nuevas_customs.add(point4);
        nuevas_customs.add(point5);
        nuevas_customs.add(point6);
        nuevas_customs.add(point7);
        nuevas_customs.add(point8);
        nuevas_customs.add(point9);
        nuevas_customs.add(point10);
        nuevas_customs.add(point11);



        puntos.postValue(nuevas_customs);

    }



}
