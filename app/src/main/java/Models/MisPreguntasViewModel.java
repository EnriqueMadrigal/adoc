package Models;
import android.graphics.Point;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MisPreguntasViewModel extends ViewModel {


    private MutableLiveData<List<Custom>> preguntas;

    public LiveData<List<Custom>> getPreguntas() {
        if ( preguntas== null) {
            preguntas = new MutableLiveData<List<Custom>>();
            loadPreguntas();
        }
        return preguntas;
    }


    private void loadPreguntas() {
        // Do an asynchronous operation to fetch users.

        Custom point1 = new Custom();
        point1.set_desc("¿Cuál es tu talla de zapato?");
        point1.set_id(0);
        point1.set_link("example1");
        point1.set_value1(50);


        Custom point2 = new Custom();
        point2.set_desc("¿Cuál fué tu última compra?");
        point2.set_id(1);
        point2.set_link("example2");
        point2.set_value1(80);


        Custom point3 = new Custom();
        point3.set_desc("¿Cuál es tu marca favorita?");
        point3.set_id(2);
        point3.set_link("example3");
        point3.set_value1(100);




        List<Custom> nuevas_customs = new ArrayList<Custom>();

        nuevas_customs.add(point1);
        nuevas_customs.add(point2);
        nuevas_customs.add(point3);


        preguntas.postValue(nuevas_customs);

    }



}
