package com.example.usuario.webesdi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Mensajes extends AppCompatActivity {


    TextView txtEmail;
    TextView txtChat;
    EditText inChat;
    String texto;
    Spinner inDestino;
    Bundle b;
    String ID;
    private DatabaseReference dbMensajes;
    private static final String TAGLOG = "firebase-db";
    private ValueEventListener eventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensajes);

        //recibe los datos de usuario a traves del bundle del intent
        Intent Mainact = getIntent();
        b = Mainact.getExtras();

        String nombre = b.getString("nombre");
        String email = b.getString("email");
        //  Log.d(TAGLOG, String.valueOf("=========================> "+b.getString("rol")+" <==============="));


        inChat = (EditText) findViewById(R.id.inChat);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtChat = (TextView) findViewById(R.id.txtChat);
        inDestino = (Spinner) findViewById(R.id.inDestino);


        //definición del spinner
        //lista de elementos del spinner (modificar aqui las opciones del desplegable)
        final String[] datos = new String[]{"Sugerencia", "Incidencia", "Consulta"};

        //creacion del adaptador
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inDestino.setAdapter(adaptador);
        inDestino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //metodo del spinner cuando se selecciona algo
            public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {

                txtEmail.setText(parent.getItemAtPosition(position).toString());
                muestraMensajes();

                //switch con el resultado de la posicion seleccionadan,
                switch (inDestino.getSelectedItemPosition()) {
                    case 0:
                        //llama a muestratexto y dependiendo del rol muestra los mensajes
                        //propios o todos ---hecho---
                       // txtEmail.setText("Seleccionado: 0 " + parent.getItemAtPosition(position));
                        break;
                    case 1:
                       // txtEmail.setText("Seleccionado: 1 " + parent.getItemAtPosition(position));
                        break;
                    case 2:
                        //if master llama a muestratexto y a creaspinner, que genera un spinner
                        //con todos los correos de las consultas
                        //al seleccionar uno de los correos se llama a otro muestratexto
                        //que muestra solo los mensajes del correo seleccionado y guarda los enviados
                        //al correo seleccionado como si fueran suyos

                      //  txtEmail.setText("Seleccionado: 2 " + parent.getItemAtPosition(position));
                        break;
                    default:
                        txtEmail.setText("invalido " + parent.getItemAtPosition(position));
                        break;
                }

            }

            //metodo del spinner cuando no se selecciona nada
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "nada en el spinner", Toast.LENGTH_LONG).show();
            }

        });

    }


    public void muestraMensajes() {

        //poner como parametro recibido el correo a mostrar, asi se reutiliza
        //para los mensajes

        //instancia la base de datos de firebase
        dbMensajes = FirebaseDatabase.getInstance().getReference().child(txtEmail.getText().toString());

        //hace una consulta para mostrar los mensajes ordenados por fecha
        Query dbQuery = dbMensajes.orderByKey();
        txtChat.setText("");

        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot nodoUsuario) {
                texto = "";
                int cont = 0;
                // for (DataSnapshot childDataSnapshot : nodoUsuario.child(txtEmail.getText().toString()).getChildren()) {
                for (DataSnapshot childDataSnapshot : nodoUsuario.getChildren()) {
                    cont++;
                    //  Log.d(TAGLOG, "" + childDataSnapshot.getKey() + ": " + childDataSnapshot.getValue());
                    //añade al texto que ya hay, el nombre de usuario mas el texto nuevo
                    //  texto = (texto + nodoUsuario.getKey() + ": " + childDataSnapshot.getValue() + "\n");
                    //  Log.d(TAGLOG, "==++===" + childDataSnapshot.child("Correo").getValue() + ": " + b.getString("email"));

                    //si el usuario logueado es master muestra todos los mensajes, si no muestra solo
                    //los que coincide su correo con el del correo del mensaje de la DB
                    if ((b.getString("rol")).equals("master")) {
                        texto = (texto + childDataSnapshot.child("Nombre").getValue() + ": " + childDataSnapshot.child("Mensaje").getValue() + "\n");
                        //carga el nuevo texto al textview
                        txtChat.setText(texto);

                    } else if ((childDataSnapshot.child("Correo").getValue()).equals(b.getString("email"))) {
                        texto = (texto + childDataSnapshot.child("Nombre").getValue() + ": " + childDataSnapshot.child("Mensaje").getValue() + "\n");
                        //carga el nuevo texto al textview
                        txtChat.setText(texto);
                    }
                }

                //permite el scroll de la ventana del textview
                txtChat.setMovementMethod(new ScrollingMovementMethod());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAGLOG, "Error!", databaseError.toException());
            }
        };


        dbQuery.addValueEventListener(eventListener);

    }




    //metodo que se ejecuta al clickar enviar
    public void entraTexto(View v) {
        //instancia la base de datos de firebase
        DatabaseReference dbEnviar = FirebaseDatabase.getInstance().getReference().child(txtEmail.getText().toString());
        //crea una lista con datos del usuario y el mensaje introducido
        Map<String, String> envio = new HashMap<>();
        envio.put("Correo", b.getString("email"));
        envio.put("Nombre", b.getString("nombre"));
        envio.put("Mensaje", inChat.getText().toString());

        //sube el texto entrado a firebase, al nodo del usuario
        //  dbMensajes.child(txtEmail.getText().toString()).push().setValue(inChat.getText().toString());
        dbEnviar.push().setValue(envio);

        //vacia la caja de entrada de texto
        inChat.setText("");

    }
}
