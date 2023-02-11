package Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User_Intelik {

    public String id;
    public String name;

    public String date_entered;
    public String date_modified;
    public String modified_user_id;
    public String modified_by_name;

    public String description;
    public String first_name;
    public String last_name;
    public String full_name;
    public String phone_home;
    public String phone_mobile;
    public String phone_work;
    public String email1;
    public String birthdate;


    public String primary_address_country;
    public String tipo_documento_identidad_c;
    public String doc_identidad_c;
    public String no_documento_c;

    public String nivel_del_cliente_c;
    public String nivel_anterior_c;
    public List<String> marcas_favoritas_c;



    public String puntos_disponibles_c;
    public String puntos_redimidos_c;
    public String puntos_acumulados_c;

    public String puntos_disponibles_promo_c;
    public String puntos_acumulados_promo_c;
    public String puntos_redimidos_promo_c;


    public String avance_nivel_c;
    public String puntos_mantener_nivel_c;
    public String porcentaje_subir_c;
    public String puntos_subir_nivel_c;
    public String gender_c;

    public String nit_c;

public User_Intelik()
{
    this.id= "";
    this.name= "";
    this.date_entered= "";
    this.date_modified= "";
    this.modified_user_id= "";
    this.modified_by_name= "";
    this.description= "";
    this.first_name= "";
    this.last_name= "";
    this.full_name= "";
    this.phone_home= "";
    this.phone_mobile= "";
    this.phone_work= "";
    this.email1= "";
    this.nivel_anterior_c= "";
    this.puntos_disponibles_c= "";
    this.puntos_redimidos_c= "";
    this.puntos_acumulados_c= "";

    this.primary_address_country= "";
    this.tipo_documento_identidad_c= "";
    this.doc_identidad_c= "";
    this.nivel_del_cliente_c = "";

    this.puntos_disponibles_promo_c = "";
    this.puntos_acumulados_promo_c = "";
    this.puntos_redimidos_promo_c = "";

    this.primary_address_country  = "";
    this.tipo_documento_identidad_c  = "";
    this.doc_identidad_c  = "";
    this.no_documento_c  = "";
    this.gender_c = "";
    this.nit_c = "";


    this.marcas_favoritas_c = new ArrayList<String>();


}


}
