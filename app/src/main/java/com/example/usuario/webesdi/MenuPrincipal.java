package com.example.usuario.webesdi;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.usuario.webesdi.empresas.Empresas;


public class MenuPrincipal extends BaseActivity {
    ImageView btnpaginaweb;
    ImageView btnContacto;
    ImageView btnMensajes;
    ImageView btndispoAulas;
    ImageView btnIncidencias;
    ImageView btnEmpresas;
    ImageView btnTutoriales;
    ImageView btnVacio;
    ImageView btnServicios;
    String email;
    String nombre;
    TextView txtEmail;
    TextView txtTitulo;
    ImageView btnCancelar;
    Bundle b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //pruebas de tablet




        //fin pruebas tablet
        setContentView(R.layout.activity_menu_principal);


        if(getResources().getBoolean(R.bool.isTab)) {

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            Log.i("tag","pis");
        }else{
            Log.i("tag","caca");
        }

        Intent Mainact = getIntent();

        b = Mainact.getExtras();

        email = b.getString("email");
        nombre = b.getString("nombre");

        email = Mainact.getStringExtra("email");

        btnpaginaweb = (ImageView) findViewById(R.id.btnpaginaweb);
        btnContacto = (ImageView) findViewById(R.id.btnContacto);
        btnMensajes = (ImageView) findViewById(R.id.btnMensajes);
        btndispoAulas = (ImageView) findViewById(R.id.btndispoAulas);
        btnIncidencias = (ImageView) findViewById(R.id.btnIncidencias);
        btnEmpresas = (ImageView) findViewById(R.id.btnEmpresas);
        btnTutoriales = (ImageView) findViewById(R.id.btnTutoriales);
        btnServicios = (ImageView) findViewById(R.id.btnServicios);
        //btnCancelar = (ImageView) findViewById(R.id.algo);

        txtEmail = (TextView)findViewById(R.id.txtEmail);
        txtTitulo = (TextView)findViewById(R.id.txtTitulo);
        txtEmail.setText(email + " - " + nombre);

        txtTitulo.setText(b.getString("rol"));

        btnpaginaweb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                lanzarPaginaWeb();

                }
        });
        btnContacto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                lanzarContacto();

            }
        });
        btnMensajes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                lanzarMensajes3();

            }
        });
        btndispoAulas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                lanzarDispo();

            }
        });
        btnIncidencias.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                lanzarIncidencias();

            }
        });
        btnTutoriales.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                lanzarTutoriales();

            }
        });
        btnEmpresas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                lanzarEmpresas();
            }
        });
        btnServicios.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                lanzarServicios();
            }
        });

        /*btnCancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                lanzarEmpresas();
            }
        });*/

        //solo un administrador puede entrar a incidencias
        if (b.getString("rol").equalsIgnoreCase("Administrador")){
            btnIncidencias.setVisibility(View.VISIBLE);
        }else{
//            btnIncidencias.setVisibility(View.GONE);
        }
        //btnVacio.setVisibility(View.GONE);



    }
    private void lanzarPaginaWeb(){
        Intent intent = new Intent(MenuPrincipal.this,PaginaWeb.class);
        intent.putExtras(b);
        startActivity(intent);
    }
    private void lanzarContacto(){
        Intent intent = new Intent(MenuPrincipal.this,Contacto.class);
        intent.putExtras(b);
        startActivity(intent);
    }

    private void lanzarMensajes3(){
       // Intent intent = new Intent(MenuPrincipal.this,Mensajes.class);
        Intent intent = new Intent(MenuPrincipal.this,Mensajes.class);
        intent.putExtras(b);
        startActivity(intent);
    }
    private void lanzarDispo(){
        Intent intent = new Intent(MenuPrincipal.this,DispoAulas.class);
        intent.putExtras(b);
        startActivity(intent);
    }

    private void lanzarIncidencias(){
        Intent intent = new Intent(MenuPrincipal.this,GLPI.class);
        intent.putExtras(b);
        startActivity(intent);
    }

    private void lanzarEmpresas(){
        Intent intent = new Intent(MenuPrincipal.this,Empresas.class);
        intent.putExtras(b);
        startActivity(intent);
    }

    private void lanzarTutoriales(){
        Intent intent = new Intent(MenuPrincipal.this,tutoriaV2.class);
        intent.putExtras(b);
        startActivity(intent);
    }
    private void lanzarServicios(){
        Intent intent = new Intent(MenuPrincipal.this,EstadoServicios.class);
        intent.putExtras(b);
        startActivity(intent);
    }

}
