package com.example.usuario.webesdi;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
// Para no tener que crear varios menús, tenemos que añadir un control del tipo de cuenta logeada
//  (Lo podemos hacer con un extra del intent) y esconder o mostrar los botones que toquen.

// como idea, podriamos substituir el mapa que tenemos ahora por noticias. Para que sea mas visual.

public class MenuPrincipal extends BaseActivity {
    Button btnpaginaweb;
    Button btnContacto;
    Button btnMensajes;
    Button btndispoAulas;
    Button btnIncidencias;
    String email;
    String nombre;
    TextView txtEmail;
    TextView txtTitulo;
    Usuario usuario;
    Bundle b;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.configuracion:
                lanzarSettings();
                return true;
            case R.id.help:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        Intent Mainact = getIntent();

        b = Mainact.getExtras();

        email = b.getString("email");
        nombre = b.getString("nombre");

      //  email = Mainact.getStringExtra("email");

        btnpaginaweb = (Button) findViewById(R.id.btnpaginaweb);
        btnContacto = (Button) findViewById(R.id.btnContacto);
        btnMensajes = (Button) findViewById(R.id.btnMensajes);
        btndispoAulas = (Button) findViewById(R.id.btndispoAulas);
        btnIncidencias = (Button) findViewById(R.id.btnIncidencias);

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

        //solo un administrador puede entrar a incidencias
        if (b.getString("rol").equalsIgnoreCase("administrador")){
            btnIncidencias.setVisibility(View.VISIBLE);
        }else{
            btnIncidencias.setVisibility(View.GONE);
        }

    }
    private void lanzarPaginaWeb(){
        Intent intent = new Intent(MenuPrincipal.this,PaginaWeb.class);
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

    private void lanzarSettings(){
        Bundle extras = getIntent().getExtras();
        String nombreActivity = this.getClass().getCanonicalName();
        Intent intent = new Intent(MenuPrincipal.this,Settings.class);
        intent.putExtra("callingActivity", nombreActivity );
        intent.putExtras(extras);
        startActivity(intent);
    }

}
