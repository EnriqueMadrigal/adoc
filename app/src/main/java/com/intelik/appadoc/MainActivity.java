package com.intelik.appadoc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;



import com.google.android.material.navigation.NavigationView;
import com.intelik.appadoc.fragments.Account;
import com.intelik.appadoc.fragments.MainFragment;
import com.intelik.appadoc.fragments.Notifications;
import com.intelik.appadoc.fragments.RegisterPage1;
import com.intelik.appadoc.fragments.RegisterPage2;
import com.intelik.appadoc.interfaces.NavigationInterface;

import java.util.List;

import Models.ContactViewModel;
import Models.Country;
import Models.CountryViewModel;
import Models.Custom;
import Models.Marca;
import Models.User_Intelik;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public DrawerLayout drawerLayout;
    //public ActionBarDrawerToggle actionBarDrawerToggle;
    BottomNavigationView bottomNavigationView;
    public NavigationView navigationView;
    androidx.appcompat.widget.Toolbar toolbar;

    private FirebaseAuth mAuth;

    Context context;
    MainFragment mainFragment;
    Notifications notifications;

    private Account account;
    private String TAG = "MainActivity";

    private ImageButton ver_notificaciones;

    private ContactViewModel contactos;

    private User_Intelik curUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        context = this;
        mAuth = FirebaseAuth.getInstance();


        Common datos = Common.getInstance();


        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        //drawerLayout = findViewById(R.id.my_drawer_layout);
        //actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        ver_notificaciones = findViewById(R.id.button_notifications);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);



        contactos = new ViewModelProvider(this).get(ContactViewModel.class);


        // Obtener el contacto


        contactos.getContacto(datos.email1).observe(this, new Observer<User_Intelik>() {
            @Override
            public void onChanged(@Nullable User_Intelik contacto) {
                // update ui.
                Log.d("RegisterActiviti", "Marcas recibidas");

            }
        });




        ver_notificaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GotoNotificaciones();
            }
        });



        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button

        //drawerLayout.addDrawerListener(actionBarDrawerToggle);
        //actionBarDrawerToggle.syncState();

        //navigationView = (NavigationView)findViewById(R.id.navview);

        // to make the Navigation drawer icon always appear on the action bar
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ///////////

        /*
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {


                        switch (menuItem.getItemId()) {

                            case R.id.nav_home:

                                //  MainFragment = new mainf();

                                //    getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).replace(R.id.fragment_container, MainFragment, "HOME").commit();

                                break;

                            case R.id.nav_settings:

                                break;

                            case R.id.nav_user:
                                confirmLogout();

                                break;
                        }

                        drawerLayout.closeDrawers();

                        return true;
                    }
                });

        /////////
            */



        GotoMain();


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_user:
               // getSupportFragmentManager().beginTransaction().replace(R.id.container, firstFragment).commit();

                //registerPage2 = new RegisterPage2();
                account = new Account();
                getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).replace(R.id.fragment_containerMain, account, "Account").commit();


                account.setOnClickListener(new NavigationInterface() {
                    @Override
                    public void closeFragment() {
                        Log.d(TAG, "Se recibio salida pagina account");

                        GotoMain();


                    }

                    @Override
                    public void backFragment() {
                        confirmLogout();
                    }


                });




                return true;

            case R.id.nav_home:
              GotoMain();
                return true;

            case R.id.nav_settings:
                //getSupportFragmentManager().beginTransaction().replace(R.id.container, thirdFragment).commit();
                return true;
        }
        return false;
    }


/*
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/

    public void confirmLogout()
    {
        new AlertDialog.Builder( this )
                .setMessage( "¿Estás seguro de que deseas cerrar tu sesión?" )
                .setCancelable( false )
                .setPositiveButton( "Si", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id )
                    {
                        logout();
                    }
                } )
                .setNegativeButton( "No", null )
                .show();
    }


    private void logout()
    {
        /*
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences( this );
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt(common.VAR_USER_ID, 0);
        editor.putInt(common.VAR_USER_PERMISOS, 0);
        editor.putString(common.VAR_USER_NAME, "");
        editor.putString(common.VAR_USER_APELLIDOS, "");
        editor.commit();
         */


        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences( context );
        SharedPreferences.Editor editor = sharedPref.edit();


        editor.putInt(Common.VAR_USER_ID, 0);
        editor.putString(Common.VAR_USER_NAME, "");
        editor.putString(Common.VAR_LOGIN_NAME, "");
        editor.putString(Common.VAR_USER_APELLIDOS, "");
        editor.commit();



        mAuth.signOut();

        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    private void GotoMain()
    {

        mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).replace(R.id.fragment_containerMain, mainFragment, "MainPage1").commit();

    }


    private void GotoNotificaciones()
    {

        notifications = new Notifications();
        getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).replace(R.id.fragment_containerMain, notifications, "Notificaciones").commit();

    }



}