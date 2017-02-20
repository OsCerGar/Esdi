package com.example.usuario.webesdi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MenuPrincipal extends AppCompatActivity {
    Button btnpaginaweb;
    Button btnContacto;
    String nombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        Intent Mainact = getIntent();
        String nombre = Mainact.getStringExtra("email");

        btnpaginaweb = (Button) findViewById(R.id.brnpaginaweb);
        btnContacto = (Button) findViewById(R.id.btnContacto);

        btnpaginaweb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                lanzarActivity();

                }
        });
        btnContacto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                lanzarActivity2();

            }
        });

    }
    private void lanzarActivity(){
        Intent intent = new Intent(MenuPrincipal.this,PaginaWeb.class);
        startActivity(intent);
    }
    private void lanzarActivity2(){
        Intent intent = new Intent(MenuPrincipal.this,Contacto.class);
        intent.putExtra("nombre",nombre );
        startActivity(intent);
    }


}
