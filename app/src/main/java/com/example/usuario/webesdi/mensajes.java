package com.example.usuario.webesdi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Spinner;

public class Mensajes extends AppCompatActivity {


    TextView txtEmail;
    TextView txtChat;
    EditText inChat;
    String texto ="";
    Spinner inDestino;
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensajes);

        //recibe los datos de usuario a traves del bundle del intent
        Intent Mainact = getIntent();
        b = Mainact.getExtras();
        String email = b.getString("email");
        String nombre = b.getString("nombre");

        inChat = (EditText) findViewById(R.id.inChat);
        txtEmail = (TextView)findViewById(R.id.txtEmail);
        txtChat = (TextView)findViewById(R.id.txtChat);

        inDestino = (Spinner)findViewById(R.id.inDestino);

        txtEmail.setText(email + " - " + nombre);


        final String[] datos =
                new String[]{"sugerencia","Incidencia","Consulta"};

        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, datos);

        adaptador.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        inDestino.setAdapter(adaptador);

    }

    public void entraTexto (View v){

        texto = (texto + b.getString("nombre") + ": " + inChat.getText().toString()+ "\n");
        txtChat.setText(texto);
        inChat.setText("");
        txtChat.setMovementMethod(new ScrollingMovementMethod());

    }
}
