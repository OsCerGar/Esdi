package com.example.usuario.webesdi;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
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
    String consulta;
    String correo;
    EditText inChat;
    String texto;
    Spinner spDestino;
    Spinner spCorreo;
    Bundle b;
    ImageButton enviar;
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

        //  Log.d(TAGLOG, String.valueOf("=========================> "+b.getString("rol")+" <==============="));


        inChat = (EditText) findViewById(R.id.inChat);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtChat = (TextView) findViewById(R.id.txtChat);
        spDestino = (Spinner) findViewById(R.id.spDestino);
        spCorreo = (Spinner) findViewById(R.id.spCorreo);
        enviar = (ImageButton) findViewById(R.id.imageButton);

        correo = b.getString("email"); //inicialmente el correo es el del usuario logueado

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

                consulta = parent.getItemAtPosition(position).toString();
                txtEmail.setText(consulta);


                //switch con el resultado de la posicion seleccionadan,
                switch (spDestino.getSelectedItemPosition()) {
                    case 0:
                        //oculta el spinner de seleccion de usuario de consulta
                        spCorreo.setVisibility(View.GONE);

                        //llama a muestratexto y dependiendo del rol muestra los mensajes
                        //si es master oculta el boton enviar y muestra todos los mensajes

                        if (b.getString("rol").equals("master")){
                            enviar.setVisibility(View.GONE);
                            muestraMensajes("todos");
                        }
                        //si no es master permite enviar y muestra solo sus mensajes
                        muestraMensajes(b.getString("email").toString());

                        break;
                    case 1:
                        //// TODO: de momeneto esta hace lo mismo que sugerencia, hay que cambiarlo  para incidencias

                        //oculta el spinner de seleccion de usuario de consulta
                        spCorreo.setVisibility(View.GONE);

                        //llama a muestratexto y dependiendo del rol muestra los mensajes
                        //si es master oculta el boton enviar y muestra todos los mensajes

                        if (b.getString("rol").equals("master")){
                            enviar.setVisibility(View.GONE);
                            muestraMensajes("todos");
                        }
                        //si no es master puede enviar y muestra solo sus mensajes
                        muestraMensajes(b.getString("email").toString());
                        break;
                    case 2:
                        //si es master llama a creaspinnercorreo para seleccionar la conversacion
                        if ((b.getString("rol")).equals("master")) {
                            creaSpinnerCorreo();
                            spCorreo.setVisibility(View.VISIBLE);
                            //si no es master oculta el spiner de seleccion de conversacion, permite enviar
                            //mensajes de consulta y muestra los mensajes propios
                        } else {
                            spCorreo.setVisibility(View.GONE);
                            enviar.setVisibility(View.VISIBLE);
                            muestraMensajes(b.getString("email").toString());
                        }

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
        datos2.add("todos");

        //instancia la base de datos de firebase
        DatabaseReference dbMensajes = FirebaseDatabase.getInstance().getReference().child(consulta);
        Query dbQuery = dbMensajes.orderByKey();

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot nodoUsuario) {
                //en cada bucle carga un nuevo elemento a la lista
                for (DataSnapshot childDataSnapshot : nodoUsuario.getChildren()) {
                    if (datos2.contains(childDataSnapshot.child("Correo").getValue().toString())) {
                        //   Log.d(TAGLOG, "======= paso por aqui ==========   " + childDataSnapshot.child("Correo").getValue().toString());
                        //si el correo ya se ha añadido antes al arraylist, no hace nada, así no se duplica

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

                correo = parent.getItemAtPosition(position).toString();
                txtEmail.setText(consulta + " de " + correo);

                switch (spCorreo.getSelectedItemPosition()) {
                    case 0:
                        //si se selecciona todos, oculta el boton enviar
                        enviar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "todos seleccionados "+spCorreo.getSelectedItemPosition(), Toast.LENGTH_LONG).show();
                        break;
                    default:
                        //por defecto muestra el boton enviar
                        enviar.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "otros seleccionados "+spCorreo.getSelectedItemPosition(), Toast.LENGTH_LONG).show();
                        break;
                }

                //llama a muestra mensaje con el correo a mostrar o con "todos" si no se ha seleccionado ninguno
                muestraMensajes(correo);
            }

            //metodo del spinner cuando no se selecciona nada
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "nada en el spinner", Toast.LENGTH_LONG).show();
            }

        });
    }


    public void muestraMensajes(final String correo) {

        //instancia la base de datos de firebase
        DatabaseReference dbMensajes = FirebaseDatabase.getInstance().getReference().child(consulta);

        //hace una consulta para mostrar los mensajes ordenados por fecha
        Query dbQuery = dbMensajes.orderByKey();
        txtChat.setText("");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot nodoUsuario) {
                texto = "";
                for (DataSnapshot childDataSnapshot : nodoUsuario.getChildren()) {

                    //si el usuario logueado es master y selecciona "todos" en el spinner, muestra todos los mensajes, si no muestra solo
                    //los que coincide su correo (que le pasamos al llamar al procedimiento) con el del correo del mensaje de la DB
                    if (correo.equals("todos")) {
                        texto = (texto + childDataSnapshot.child("Nombre").getValue() + ": " + childDataSnapshot.child("Mensaje").getValue() + "\n");

                    } else if ((childDataSnapshot.child("Correo").getValue()).equals(correo)) {
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
        DatabaseReference dbEnviar = FirebaseDatabase.getInstance().getReference().child(consulta);
        //crea una lista con datos del usuario y el mensaje introducido
        Map<String, String> envio = new HashMap<>();
        envio.put("Correo", correo);
        envio.put("Nombre", b.getString("nombre"));
        envio.put("Mensaje", inChat.getText().toString());

        //sube el texto entrado a firebase, al nodo del usuario
        //  dbMensajes.child(txtEmail.getText().toString()).push().setValue(inChat.getText().toString());
        dbEnviar.push().setValue(envio);

        //vacia la caja de entrada de texto
        inChat.setText("");

    }
}
