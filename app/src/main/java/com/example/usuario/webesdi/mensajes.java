package com.example.usuario.webesdi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Mensajes extends AppCompatActivity {


    TextView txtEmail;
    TextView txtChat;
    EditText inChat;
    String texto ="";
    Spinner inDestino;
    Bundle b;
    private DatabaseReference dbMensajes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensajes);

        dbMensajes = FirebaseDatabase.getInstance().getReference().child("usuario");
      //  dbMensajes = FirebaseDatabase.getInstance().getReference().child("prediccion-hoy");

        //recibe los datos de usuario a traves del bundle del intent
        Intent Mainact = getIntent();
        b = Mainact.getExtras();
        String email = b.getString("email");
        String nombre = b.getString("nombre");

        inChat = (EditText) findViewById(R.id.inChat);
        txtEmail = (TextView)findViewById(R.id.txtEmail);
        txtChat = (TextView)findViewById(R.id.txtChat);
        inDestino = (Spinner)findViewById(R.id.inDestino);

       // txtEmail.setText(email + " - " + nombre);


        //definición del spinner
        final String[] datos = new String[]{"sugerencia","Incidencia","Consulta"};

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, datos);

        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        inDestino.setAdapter(adaptador);


        inDestino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //metodo del spinner cuando se selecciona algo
                    public void onItemSelected(AdapterView<?> parent,android.view.View v, int position, long id) {
                        //switch con el resultado de la posicion seleccionadan, devuelve un int
                        switch (inDestino.getSelectedItemPosition()){
                            case 0: txtEmail.setText("Seleccionado: 0 " + parent.getItemAtPosition(position));
                                break;
                            case 1: txtEmail.setText("Seleccionado: 1 " + parent.getItemAtPosition(position));
                                break;
                            case 2: txtEmail.setText("Seleccionado: 2 " + parent.getItemAtPosition(position));
                                break;
                            default: txtEmail.setText("invalido " + parent.getItemAtPosition(position));
                                break;
                        }
                    }
            //metodo del spinner cuando no se selecciona nada
                    public void onNothingSelected(AdapterView<?> parent) {
                        Toast.makeText(getApplicationContext(), "nada en el spinner", Toast.LENGTH_LONG).show();
                    }
                });


    }

    public void entraTexto (View v){

        texto = (texto + b.getString("nombre") + ": " + inChat.getText().toString()+ "\n");
        txtChat.setText(texto);
        dbMensajes.push().setValue(inChat.getText().toString());
        inChat.setText("");
        txtChat.setMovementMethod(new ScrollingMovementMethod());

    }
}
