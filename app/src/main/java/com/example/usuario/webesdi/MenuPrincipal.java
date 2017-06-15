package com.example.usuario.webesdi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.usuario.webesdi.empresas.Empresas;


public class MenuPrincipal extends BaseActivity {
    ImageView btnpaginaweb;
    ImageView btnContacto;
    ImageView btnMensajes;
    ImageView btndispoAulas;
    ImageView btnIncidencias;
    ImageView btnEmpresas;
    ImageView btnTutoriales;
    ImageView btnServicios;
    String email;
    String nombre;
    Bundle b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        setTitle(getResources().getText(R.string.titMenuP));

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
        //btnVacio = (Button) findViewById(R.id.btnVacio);
        //btnCancelar = (ImageView) findViewById(R.id.algo);

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
        }else if(b.getString("rol").equalsIgnoreCase("Invitado")){
            btndispoAulas.setVisibility(View.INVISIBLE);
            btnServicios.setVisibility(View.INVISIBLE);
            btnMensajes.setVisibility(View.INVISIBLE);
            btnMensajes.setVisibility(View.INVISIBLE);
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
