package com.example.usuario.webesdi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Mensajes extends AppCompatActivity {

   // String email;
    TextView txtEmail;
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensajes);

        Intent Mainact = getIntent();
        b = Mainact.getExtras();

        String email = b.getString("email");
        String nombre = b.getString("nombre");

        txtEmail = (TextView)findViewById(R.id.txtEmail);
        txtEmail.setText(email + " - " + nombre);
    }
}
