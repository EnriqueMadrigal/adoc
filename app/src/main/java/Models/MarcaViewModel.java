package Models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MarcaViewModel extends ViewModel {


    private MutableLiveData<List<Marca>> marcas;

    public LiveData<List<Marca>> getMarcas() {
        if (marcas == null) {
            marcas = new MutableLiveData<List<Marca>>();
            loadMarcas();
        }
        return marcas;
    }


    private void loadMarcas() {
        // Do an asynchronous operation to fetch users.

        Marca marca1 = new Marca("Adoc", 1);
        Marca marca2 = new Marca("Hush Puppies", 2);
        Marca marca3 = new Marca("CAT", 3);
        Marca marca4 = new Marca("The North Face", 4);
        Marca marca5 = new Marca("Par2", 5);

        List<Marca> nuevas_marcas = new ArrayList<Marca>();
        nuevas_marcas.add(marca1);
        nuevas_marcas.add(marca2);
        nuevas_marcas.add(marca3);
        nuevas_marcas.add(marca4);
        nuevas_marcas.add(marca5);

        marcas.postValue(nuevas_marcas);

    }



}
