package com.intelik.appadoc.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Layout;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.intelik.appadoc.Common;
import com.intelik.appadoc.R;
import com.intelik.appadoc.adapters.EspecialesAdapter;
import com.intelik.appadoc.adapters.MainQuestionsAdapter;
import com.intelik.appadoc.adapters.MirPuntosAdapter;

import java.util.ArrayList;
import java.util.List;

import Models.ContactViewModel;
import Models.Custom;
import com.intelik.appadoc.interfaces.IViewHolderClick;

import Models.EncuestasViewModel;
import Models.MisPreguntasViewModel;
import Models.MisPuntosViewModel;
import Models.User_Intelik;
import com.google.firebase.auth.FirebaseAuth;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Context MyContext;
    private TextView mainTitle;

    private RecyclerView _recyclerview;
    private LinearLayoutManager _linearLayoutManager;



    //private MirPuntosAdapter _adapter;
    private MainQuestionsAdapter _adapter;
    private EspecialesAdapter _adapter1;

    private ArrayList<Custom> _mispuntos;


    private ArrayList<Custom> _mispreguntas;

    private MisPuntosViewModel puntos;
    private MisPreguntasViewModel preguntas;
    private String TAG = "MainFragment";

    private ContactViewModel contactos;
    private FirebaseAuth mAuth;


    private EncuestasViewModel encuestas;

    private TextView main_currentpoints;
    private TextView main_currentlevel;

    private ImageView image_meter;

    private androidx.appcompat.widget.AppCompatButton button_tablapuntos;
    private LinearLayout stack_puntos;
    private boolean isVisible_stack_puntos = false;

    private androidx.appcompat.widget.AppCompatButton button_puntosespeciales;
    private LinearLayout stack_puntosespeciale;
    private boolean isVisible_stack_puntosespeciales = false;

    private androidx.appcompat.widget.AppCompatButton button_tiendas;
    private androidx.appcompat.widget.AppCompatButton button_refiere;
    private LinearLayout stack_tiendas;
    private boolean isVisible_tiendas = false;


    private SwipeRefreshLayout swipeRefreshLayout;

    private TextView puntos_parasubir;
    private TextView puntos_parasubir_desc;

    private TextView puntos_redimidos;
    private TextView puntos_redimidos_desc;

    private TextView puntos_tienes;
    private TextView puntos_tienes_desc;
    private TextView puntos_disponibles_act;


    private TextView puntos_esp_parasubir;
    private TextView puntos_esp_redimidos;
    private TextView puntos_esp_tienes;
    //private TextView puntos_esp_vigencia;

    private TextView fragment_main_refiereTitle;

    private ImageView tienda_adoc;
    private ImageView tienda_cat;
    private ImageView tienda_hush;
    private ImageView tienda_north;
    private ImageView tienda_par2;

    private LinearLayout progrees_mainview;

    private Common datos;

    private Boolean errorServer = false;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View _view;
        _view = inflater.inflate( R.layout.fragment_main, container, false );

        datos = Common.getInstance();

        mAuth = FirebaseAuth.getInstance();

        mainTitle = (TextView) _view.findViewById(R.id.fragment_mainTItle);

        _recyclerview = (RecyclerView) _view.findViewById(R.id.recycler_main);

        main_currentpoints = (TextView) _view.findViewById(R.id.main_currentpoints);
        main_currentlevel = (TextView) _view.findViewById(R.id.main_currentlevel);
        image_meter = (ImageView) _view.findViewById(R.id.image_meter);
        progrees_mainview = (LinearLayout) _view.findViewById(R.id.progrees_mainview);
        swipeRefreshLayout = (SwipeRefreshLayout) _view.findViewById(R.id.swipe_container);



        puntos = new ViewModelProvider(this).get(MisPuntosViewModel.class);
        preguntas = new ViewModelProvider(this).get(MisPreguntasViewModel.class);

        button_tablapuntos = (androidx.appcompat.widget.AppCompatButton) _view.findViewById(R.id.button_tablapuntos);
        stack_puntos = (LinearLayout) _view.findViewById(R.id.stack_tablapuntos);

        button_puntosespeciales = (androidx.appcompat.widget.AppCompatButton) _view.findViewById(R.id.button_puntos_especiales);
        stack_puntosespeciale = (LinearLayout) _view.findViewById(R.id.stack_puntosespeciales);

        button_refiere = (androidx.appcompat.widget.AppCompatButton) _view.findViewById(R.id.button_main_refiere);
        button_tiendas = (androidx.appcompat.widget.AppCompatButton) _view.findViewById(R.id.button_nuestras_tiendas);
        stack_tiendas = (LinearLayout) _view.findViewById(R.id.stack_tiendas);


        //Tabla de puntos
        puntos_parasubir = (TextView) _view.findViewById(R.id.puntos_parasubir);
        puntos_parasubir_desc = (TextView) _view.findViewById(R.id.puntos_parasubir_desc);

        puntos_redimidos = (TextView) _view.findViewById(R.id.puntos_redimidos);
        puntos_redimidos_desc = (TextView) _view.findViewById(R.id.puntos_redimidos_desc);

        puntos_tienes = (TextView) _view.findViewById(R.id.puntos_tienes);
        puntos_tienes_desc = (TextView) _view.findViewById(R.id.puntos_tienes_desc);

        //PUntos Especiales
        puntos_esp_parasubir = (TextView) _view.findViewById(R.id.puntos_esp_parasubir);
        puntos_esp_redimidos = (TextView) _view.findViewById(R.id.puntos_esp_redimidos);
        puntos_esp_tienes = (TextView) _view.findViewById(R.id.puntos_esp_tienes);
        puntos_disponibles_act = (TextView) _view.findViewById(R.id.puntos_disponibles_act);
        //puntos_esp_vigencia = (TextView) _view.findViewById(R.id.puntos_esp_vigencia);

        tienda_adoc = (ImageView) _view.findViewById(R.id.tienda_adoc);
        tienda_cat = (ImageView) _view.findViewById(R.id.tienda_cat);
        tienda_hush = (ImageView) _view.findViewById(R.id.tienda_hush);
        tienda_north = (ImageView) _view.findViewById(R.id.tienda_north);
        tienda_par2 = (ImageView) _view.findViewById(R.id.tienda_par2);

        fragment_main_refiereTitle = (TextView) _view.findViewById(R.id.fragment_main_refiereTitle);



        _mispuntos = new ArrayList<>();
        _mispreguntas = new ArrayList<>();

        String user_email = mAuth.getCurrentUser().getEmail();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                progrees_mainview.setVisibility(View.VISIBLE);

                errorServer = true;
                Start();


                //////////
                contactos.getContacto(user_email).observe(getActivity(), new Observer<User_Intelik>() {
                    @Override
                    public void onChanged(@Nullable User_Intelik contacto) {
                        // update ui.
                        swipeRefreshLayout.setRefreshing(false);
                        progrees_mainview.setVisibility(View.GONE);


                        Log.d("RegisterActiviti", "Marcas recibidas refresh");
                        errorServer = false;
                        setData(contacto);
                    }
                });

                ///////////

            }
        });


        button_refiere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Common.sendRefiere(MyContext);


            }
        });

        fragment_main_refiereTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.sendRefiere(MyContext);


            }
        });


        //Ocultar las encuestas
        /*
        _adapter = new MainQuestionsAdapter(getActivity(), _mispreguntas, new IViewHolderClick() {
            @Override
            public void onClick(int position) {
                Log.d(TAG, "CLick");

                Custom currentCustom = new Custom();

                for(Custom cust : _mispreguntas) {
                    if(cust.get_id().equals(position)) {
                        currentCustom = cust;
                    }
                }

                if (currentCustom.get_id() == 0)
                {
                    return;
                }


                String curLink = currentCustom.getLink();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(curLink));
                startActivity(browserIntent);

            }
        });
*/
//Ocultar las encuestas

///--- Ya no esta en uso
        //Obtener los puntos

        /*
        puntos.getPuntos().observe(getViewLifecycleOwner(), new Observer<List<Custom>>() {
            @Override
            public void onChanged(@Nullable List<Custom> customs) {
                // update ui.
                Log.d("RegisterActiviti", "Puntos recibidos");



                for (Custom curCustom: customs) {
                    Custom newCustom = new Custom();
                    newCustom.set_id(0);
                    newCustom.set_name(curCustom.get_name());
                    newCustom.set_desc(curCustom.get_desc());
                    _mispuntos.add(newCustom);
                }
                //_adapter.notifyDataSetChanged();
            }
        });
*/
        //// ---Ya no esta en uso

//Obtener las preguntas
/*
        preguntas.getPreguntas().observe(getViewLifecycleOwner(), new Observer<List<Custom>>() {
            @Override
            public void onChanged(@Nullable List<Custom> customs) {
                // update ui.
                Log.d("RegisterActiviti", "PReguntas recibidas");



                for (Custom curCustom: customs) {
                    Custom newCustom = new Custom();
                    newCustom.set_id(curCustom.get_id());
                    newCustom.set_name(curCustom.get_name());
                    newCustom.set_desc(curCustom.get_desc());
                    newCustom.set_link(curCustom.getLink());
                    newCustom.set_value1(curCustom.getValue1());
                    _mispreguntas.add(newCustom);
                }
                _adapter.notifyDataSetChanged();
            }
        });

*/





        contactos = new ViewModelProvider(this).get(ContactViewModel.class);

       //Ocultar las encuestas
        //encuestas = new ViewModelProvider(this).get(EncuestasViewModel.class);




        errorServer = true;
        Start();



        contactos.getContacto(user_email).observe(getActivity(), new Observer<User_Intelik>() {
            @Override
            public void onChanged(@Nullable User_Intelik contacto) {
                // update ui.
                swipeRefreshLayout.setRefreshing(false);

                Log.d("RegisterActiviti", "Marcas recibidas");

                progrees_mainview.setVisibility(View.GONE);
                errorServer = false;

                setData(contacto);

            }
        });


        //Ocultar las encuestas temporalmente

        /*
        encuestas.getEncuestas().observe(getViewLifecycleOwner(), new Observer<List<Custom>>() {
            @Override
            public void onChanged(@Nullable List<Custom> customs) {
                // update ui.
                Log.d("RegisterActiviti", "Encuestas recibidas");

                _mispreguntas.addAll(customs);

                _adapter.notifyDataSetChanged();
            }
        });




        //Recycler


        _linearLayoutManager = new LinearLayoutManager( getActivity() , LinearLayoutManager.HORIZONTAL, false);

        _recyclerview.setHasFixedSize( true );
        _recyclerview.setAdapter( _adapter );
        _recyclerview.setLayoutManager( _linearLayoutManager );


        //Recycler1

        */  //Ocultar las encuestas



        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_main, container, false);


        //SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences( MyContext );

        //String name = sharedPref.getString(Common.VAR_USER_NAME, "");

        //mainTitle.setText("!Hola, " + name );


        button_tablapuntos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowHideStackPuntos();
            }
        });

        button_puntosespeciales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowHideStackPuntosEspeciales();
            }
        });

        button_tiendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowHideStackTiendas();
            }
        });


        tienda_adoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://tiendasadoc.com/"));
                startActivity(browserIntent);
            }
        });

        tienda_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://caterpillarca.com/"));
                startActivity(browserIntent);
            }
        });

        tienda_hush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://hushpuppiesca.com/"));
                startActivity(browserIntent);
            }
        });

        tienda_north.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://thenorthfacecentroamerica.com/"));
                startActivity(browserIntent);
            }
        });

        tienda_par2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://tiendaspar2.com/"));
                startActivity(browserIntent);
            }
        });



        return _view;
    }


    //Timer

    public void Start() {
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                Log.d("RegisterActiviti", "Timer finalizado");

                if (errorServer) {
                    progrees_mainview.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                    Common.showWarningDialog(MyContext.getResources().getString(R.string.aviso), MyContext.getResources().getString(R.string.errorservidor), MyContext);
                }

                //handler.postDelayed(this, 30000);
            }
        };


        handler.postDelayed(runnable, 45000);
    }




    @Override
    public void onAttach(Context context) {
        MyContext = context;
        super.onAttach(context);

    }


    private void ShowHideStackPuntos()
    {
        isVisible_stack_puntos = !isVisible_stack_puntos;

        if (isVisible_stack_puntos)
        {
            Drawable img = getContext().getResources().getDrawable(R.drawable.ic_uarrow);
            button_tablapuntos.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
            stack_puntos.setVisibility(View.VISIBLE);
            slide_down(MyContext, stack_puntos);

        }
        else
        {
            Drawable img = getContext().getResources().getDrawable(R.drawable.ic_darrow);
            button_tablapuntos.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
            slide_up(MyContext, stack_puntos);
            //stack_puntos.setVisibility(View.GONE);
        }

    }


    private void ShowHideStackPuntosEspeciales()
    {
        isVisible_stack_puntosespeciales = !isVisible_stack_puntosespeciales;

        if (isVisible_stack_puntosespeciales)
        {
            Drawable img = getContext().getResources().getDrawable(R.drawable.ic_uarrow);
            button_puntosespeciales.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
            stack_puntosespeciale.setVisibility(View.VISIBLE);
            slide_down(MyContext, stack_puntosespeciale);

        }
        else
        {
            Drawable img = getContext().getResources().getDrawable(R.drawable.ic_darrow);
            button_puntosespeciales.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
            slide_up(MyContext, stack_puntosespeciale);
            //stack_puntos.setVisibility(View.GONE);
        }

    }

    private void ShowHideStackTiendas()
    {
        isVisible_tiendas = !isVisible_tiendas;

        if (isVisible_tiendas)
        {
            Drawable img = getContext().getResources().getDrawable(R.drawable.ic_uarrow);
            button_tiendas.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
            stack_tiendas.setVisibility(View.VISIBLE);
            slide_down(MyContext, stack_tiendas);

        }
        else
        {
            Drawable img = getContext().getResources().getDrawable(R.drawable.ic_darrow);
            button_tiendas.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
            slide_up(MyContext, stack_tiendas);
            //stack_puntos.setVisibility(View.GONE);
        }

    }


    public static void slide_down(Context ctx, View v){

        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_down);
        if(a != null){
            a.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }

    public static void slide_up(Context ctx, View v){
        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_up);
        if(a != null){
            a.reset();
            if(v != null){
                v.clearAnimation();

                a.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        v.setVisibility(View.GONE);
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                v.startAnimation(a);
            }
        }
    }
////
private  void setData(User_Intelik contacto)
{


    datos.first_name = contacto.first_name;
    datos.last_name = contacto.last_name;
    datos.email1 = contacto.email1;
    //datos.tipo_documento_identidad_c = contacto.tipo_documento_identidad_c;
    datos.assigned_user_id = contacto.id;
    datos.primary_address_country = contacto.primary_address_country;
    datos.marcas_favoritas_c = contacto.marcas_favoritas_c;
    datos.doc_identidad_c = contacto.doc_identidad_c;
    datos.no_documento_c = contacto.no_documento_c;
    datos.birthdate = contacto.birthdate;
    datos.gender_c = contacto.gender_c;
    datos.nit = contacto.nit_c;


    //datos.doc_identidad_c = contacto.do

    Common.saveUserValue(MyContext);


    mainTitle.setText(" " + contacto.first_name + "!");

    String Puntos_acumulados = contacto.puntos_acumulados_c;

    int totalPuntos = 0;
    int sumaPuntos = 0;
    int totpuntos_parasubir = 0;
    int totpuntos_redimidos = 0;
    int avance_nivel = 0;

    int puntos_disponibles_promo = 0;
    int puntos_acumulados_promo = 0;
    int has_redimido = 0;
    String vigencia_puntos = "Julio";


    totalPuntos = Common.isNumeric(contacto.puntos_disponibles_c) ? (int) Double.parseDouble(contacto.puntos_disponibles_c) : 0;


    totpuntos_parasubir = Common.isNumeric(contacto.puntos_subir_nivel_c) ? (int) Double.parseDouble(contacto.puntos_subir_nivel_c) : 0;
    totpuntos_redimidos = Common.isNumeric(contacto.puntos_redimidos_c) ? (int) Double.parseDouble(contacto.puntos_redimidos_c) : 0;
    avance_nivel = Common.isNumeric(contacto.avance_nivel_c)  ? (int) Double.parseDouble(contacto.avance_nivel_c) : 0;

    puntos_disponibles_promo = Common.isNumeric(contacto.puntos_disponibles_promo_c) ? (int) Double.parseDouble(contacto.puntos_disponibles_promo_c): 0;
    puntos_acumulados_promo = Common.isNumeric(contacto.puntos_acumulados_promo_c) ? (int) Double.parseDouble(contacto.puntos_acumulados_promo_c): 0;
    has_redimido = Common.isNumeric(contacto.puntos_redimidos_promo_c) ? (int) Double.parseDouble(contacto.puntos_redimidos_promo_c): 0;

    sumaPuntos =  totalPuntos + puntos_disponibles_promo;


    main_currentpoints.setText(String.valueOf(sumaPuntos) + " pts");

    String curNivel = contacto.nivel_del_cliente_c.toLowerCase();
    main_currentlevel.setText(contacto.nivel_del_cliente_c);

    puntos_parasubir.setText(String.valueOf(totpuntos_parasubir) + " pts");
    puntos_redimidos.setText(String.valueOf(totpuntos_redimidos));
    puntos_tienes.setText(String.valueOf(avance_nivel) + " %");
    puntos_disponibles_act.setText(String.valueOf(totalPuntos));



    puntos_esp_parasubir.setText(String.valueOf(puntos_disponibles_promo));
    puntos_esp_redimidos.setText(String.valueOf(has_redimido));
    puntos_esp_tienes.setText(String.valueOf(puntos_acumulados_promo));


    if (curNivel.equals("plata"))
    {
        image_meter.setImageResource(R.drawable.ic_meter_plata);
    }

    else if (curNivel.equals("oro"))
    {
        image_meter.setImageResource(R.drawable.ic_meter_oro);
    }
    else if (curNivel.equals("diamante"))
    {
        image_meter.setImageResource(R.drawable.ic_meter_diamante);
    }


}


////

}