package com.example.usuario.webesdi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mensajes extends AppCompatActivity {


    TextView txtEmail;
    TextView txtChat;
    EditText inChat;
    String texto;
    Spinner spDestino;
    Spinner spCorreo;
    Bundle b;
    List<String> datos2;
    //private DatabaseReference dbMensajes;
    //private ValueEventListener eventListener;
    private static final String TAGLOG = "firebase-db";


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
        spDestino = (Spinner) findViewById(R.id.spDestino);
        spCorreo = (Spinner) findViewById(R.id.spCorreo);


        //definición del spinner de conversacion
        //lista de elementos del spinner (modificar aqui las opciones del desplegable)
        final String[] datos = new String[]{"Sugerencia", "Incidencia", "Consulta"};

        //creacion del adaptador del spinner
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDestino.setAdapter(adaptador);
        spDestino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //metodo del spinner cuando se selecciona algo
            public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {

                txtEmail.setText(parent.getItemAtPosition(position).toString());


                //switch con el resultado de la posicion seleccionadan,
                switch (spDestino.getSelectedItemPosition()) {
                    case 0:
                        //llama a muestratexto y dependiendo del rol muestra los mensajes
                        //propios o todos ---hecho---
                        spCorreo.setVisibility(View.GONE);
                        muestraMensajes(b.getString("correo"));
                        // txtEmail.setText("Seleccionado: 0 " + parent.getItemAtPosition(position));
                        break;
                    case 1:
                        spCorreo.setVisibility(View.GONE);
                        muestraMensajes(b.getString("correo"));
                        // txtEmail.setText("Seleccionado: 1 " + parent.getItemAtPosition(position));
                        break;
                    case 2:
                        //if master llama a muestratexto y a creaspinner, que genera un spinner
                        //con todos los correos de las consultas
                        if ((b.getString("rol")).equals("master")) {
                            creaSpinnerCorreo();
                            spCorreo.setVisibility(View.VISIBLE);
                        }

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


    public void creaSpinnerCorreo() {
        //definición del spinner de conversacion
        //crea una lista dinamica y se carga con los elementos recibidos de la BD

        datos2 = new ArrayList<String>();
        datos2.add("primer dato");

        //instancia la base de datos de firebase
        DatabaseReference dbMensajes = FirebaseDatabase.getInstance().getReference().child(txtEmail.getText().toString());
        Query dbQuery = dbMensajes.orderByKey();

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot nodoUsuario) {
                //en cada bucle carga un nuevo elemento a la lista
                for (DataSnapshot childDataSnapshot : nodoUsuario.getChildren()) {
                    if (datos2.contains(childDataSnapshot.child("Correo").getValue().toString())) {
                        //   Log.d(TAGLOG, "======= paso por aqui ==========   " + childDataSnapshot.child("Correo").getValue().toString());

                    } else {
                        datos2.add(childDataSnapshot.child("Correo").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAGLOG, "Error!", databaseError.toException());
            }
        };


        dbQuery.addValueEventListener(eventListener);


        //creacion del adaptador del spinner
        ArrayAdapter<String> adaptador2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos2);
        adaptador2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCorreo.setAdapter(adaptador2);
        spCorreo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //metodo del spinner cuando se selecciona algo
            public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {


                txtEmail.setText(parent.getItemAtPosition(position).toString());

            }

            //metodo del spinner cuando no se selecciona nada
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "nada en el spinner", Toast.LENGTH_LONG).show();
            }

        });
    }


    public void muestraMensajes(String correo) {
        //poner como parametro recibido el correo a mostrar, asi se reutiliza
        //para los mensajes

        //instancia la base de datos de firebase
        DatabaseReference dbMensajes = FirebaseDatabase.getInstance().getReference().child(txtEmail.getText().toString());

        //hace una consulta para mostrar los mensajes ordenados por fecha
        Query dbQuery = dbMensajes.orderByKey();
        txtChat.setText("");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot nodoUsuario) {
                texto = "";
                // for (DataSnapshot childDataSnapshot : nodoUsuario.child(txtEmail.getText().toString()).getChildren()) {
                for (DataSnapshot childDataSnapshot : nodoUsuario.getChildren()) {

                    //si el usuario logueado es master muestra todos los mensajes, si no muestra solo
                    //los que coincide su correo con el del correo del mensaje de la DB
                    if ((b.getString("rol")).equals("master")) {
                        texto = (texto + childDataSnapshot.child("Nombre").getValue() + ": " + childDataSnapshot.child("Mensaje").getValue() + "\n");

                    } else if ((childDataSnapshot.child("Correo").getValue()).equals(b.getString("email"))) {
                        texto = (texto + childDataSnapshot.child("Nombre").getValue() + ": " + childDataSnapshot.child("Mensaje").getValue() + "\n");
                    }
                    //carga el nuevo texto al textview
                    txtChat.setText(texto);
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
