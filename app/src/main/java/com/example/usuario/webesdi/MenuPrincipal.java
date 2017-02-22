package com.example.usuario.webesdi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuPrincipal extends AppCompatActivity {
    Button btnpaginaweb;
    Button btnContacto;
    String email;
    TextView txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        Intent Mainact = getIntent();
        email = Mainact.getStringExtra("email");

        btnpaginaweb = (Button) findViewById(R.id.brnpaginaweb);
        btnContacto = (Button) findViewById(R.id.btnContacto);
        txtEmail = (TextView)findViewById(R.id.txtEmail);
        txtEmail.setText(email);

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
        intent.putExtra("email",email );
        startActivity(intent);
    }


}
