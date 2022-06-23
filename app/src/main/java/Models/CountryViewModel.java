package Models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class CountryViewModel extends ViewModel {


    private MutableLiveData<List<Country>> countries;

    public LiveData<List<Country>> getCountries() {
        if ( countries== null) {
            countries = new MutableLiveData<List<Country>>();
            loadCountries();
        }
        return countries;
    }


    private void loadCountries() {
        // Do an asynchronous operation to fetch users.
        Country country0 = new Country("País" ,0);
        Country country1 = new Country("Costa Rica" ,1);
        Country country2 = new Country("El Salvador" ,2);
        Country country3 = new Country("Honduras" ,3);
        Country country4 = new Country("Nicaragua" ,4);
        Country country5 = new Country("Guatemala" ,5);

        List<Country> nuevas_countries = new ArrayList<Country>();
        nuevas_countries.add(country0);
        nuevas_countries.add(country1);
        nuevas_countries.add(country2);
        nuevas_countries.add(country3);
        nuevas_countries.add(country4);
        nuevas_countries.add(country5);

        countries.postValue(nuevas_countries);

    }



}
