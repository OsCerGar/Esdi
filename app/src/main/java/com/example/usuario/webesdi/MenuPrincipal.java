package com.example.usuario.webesdi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
 // Para no tener que crear varios menús, tenemos que añadir un control del tipo de cuenta logeada
//  (Lo podemos hacer con un extra del intent) y esconder o mostrar los botones que toquen.

// como idea, podriamos substituir el mapa que tenemos ahora por noticias. Para que sea mas visual.

public class MenuPrincipal extends AppCompatActivity {
    Button btnpaginaweb;
    Button btnContacto;
    Button btnMensajes;
    Button btndispoAulas;
    String email;
    String nombre;
    TextView txtEmail;
    Usuario usuario;
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        Intent Mainact = getIntent();

        b = Mainact.getExtras();

        email = b.getString("email");
        nombre = b.getString("nombre");

      //  email = Mainact.getStringExtra("email");

        btnpaginaweb = (Button) findViewById(R.id.brnpaginaweb);
        btnContacto = (Button) findViewById(R.id.btnContacto);
        btnMensajes = (Button) findViewById(R.id.btnMensajes);
        btndispoAulas = (Button) findViewById(R.id.btndispoAulas);
        txtEmail = (TextView)findViewById(R.id.txtEmail);
        txtEmail.setText(email + " - " + nombre);

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
        btnContacto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                lanzarDispo();

            }
        });

    }
    private void lanzarPaginaWeb(){
        Intent intent = new Intent(MenuPrincipal.this,PaginaWeb.class);
        startActivity(intent);
    }
    private void lanzarContacto(){
        Intent intent = new Intent(MenuPrincipal.this,Contacto.class);

        intent.putExtras(b);
       // intent.putExtra("email",email );
        startActivity(intent);
    }

    private void lanzarMensajes3(){
        Intent intent = new Intent(MenuPrincipal.this,Mensajes.class);
        startActivity(intent);
    }
    private void lanzarDispo(){
        Intent intent = new Intent(MenuPrincipal.this,DispoAulas.class);
        startActivity(intent);
    }


}
