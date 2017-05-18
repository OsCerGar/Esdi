package com.example.usuario.webesdi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.usuario.webesdi.empresas.Empresas;
import com.example.usuario.webesdi.tutoriales.Tutoriales;
// Para no tener que crear varios menús, tenemos que añadir un control del tipo de cuenta logeada
//  (Lo podemos hacer con un extra del intent) y esconder o mostrar los botones que toquen.

// como idea, podriamos substituir el mapa que tenemos ahora por noticias. Para que sea mas visual.

public class MenuPrincipal extends BaseActivity {
    ImageView btnpaginaweb;
    ImageView btnContacto;
    ImageView btnMensajes;
    ImageView btndispoAulas;
    ImageView btnIncidencias;
    ImageView btnEmpresas;
    ImageView btnTutoriales;
    ImageView btnVacio;
    String email;
    String nombre;
    TextView txtEmail;
    TextView txtTitulo;
    ImageView btnCancelar;
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

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
        //btnVacio = (Button) findViewById(R.id.btnVacio);
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

        /*btnCancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                lanzarEmpresas();
            }
        });*/

        //solo un administrador puede entrar a incidencias
        if (b.getString("rol").equalsIgnoreCase("administrador")){
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
        Intent intent = new Intent(MenuPrincipal.this,Tutoriales.class);
        intent.putExtras(b);
        startActivity(intent);
    }

}
