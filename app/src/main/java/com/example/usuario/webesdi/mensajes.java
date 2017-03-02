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
import com.google.firebase.database.ValueEventListener;

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
        //el ID es el email, cambiando el punto por el guion bajo, ya que firebase no acepta puntos en el nombre de nodo
        ID = email.replace(".", "_");


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
                muestraTexto();


                /*
                //switch con el resultado de la posicion seleccionadan,
                switch (inDestino.getSelectedItemPosition()) {
                    case 0:
                        txtEmail.setText("Seleccionado: 0 " + parent.getItemAtPosition(position));
                        break;
                    case 1:
                        txtEmail.setText("Seleccionado: 1 " + parent.getItemAtPosition(position));
                        break;
                    case 2:
                        txtEmail.setText("Seleccionado: 2 " + parent.getItemAtPosition(position));
                        break;
                    default:
                        txtEmail.setText("invalido " + parent.getItemAtPosition(position));
                        break;
                }
                */
            }

            //metodo del spinner cuando no se selecciona nada
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "nada en el spinner", Toast.LENGTH_LONG).show();
            }

        });

    }


    public void muestraTexto() {
        //instancia la base de datos de firebase
        dbMensajes = FirebaseDatabase.getInstance().getReference().child(ID);


        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                texto = "";
                int cont = 0;
                for (DataSnapshot childDataSnapshot : dataSnapshot.child(txtEmail.getText().toString()).getChildren()) {
                    cont++;
                  //  Log.d(TAGLOG, "" + childDataSnapshot.getKey() + ": " + childDataSnapshot.getValue());
                    //añade al texto que ya hay, el nombre de usuario mas el texto nuevo
                    texto = (texto + b.getString("nombre") + ": " + childDataSnapshot.getValue() + "\n");
                    //carga el nuevo texto al textview
                    txtChat.setText(texto);
                    Log.d(TAGLOG, String.valueOf(cont));
                }

                //permite el scroll de la ventana del textview
                txtChat.setMovementMethod(new ScrollingMovementMethod());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAGLOG, "Error!", databaseError.toException());
            }
        };


        dbMensajes.addValueEventListener(eventListener);

    }


    //metedo que se ejecuta al clickar enviar
    public void entraTexto(View v) {
        //añade al texto que ya hay, el nombre de usuario mas el texto nuevo
        //  texto = (texto + b.getString("nombre") + ": " + inChat.getText().toString() + "\n");
        //carga el nuevo texto al textview
        //txtChat.setText(texto);
        //sube el texto entrado a firebase
        dbMensajes.child(txtEmail.getText().toString()).push().setValue(inChat.getText().toString());
        //vacia la caja de entrada de texto
        inChat.setText("");
        //permite el scroll de la ventana del textview
        // txtChat.setMovementMethod(new ScrollingMovementMethod());

    }
}
