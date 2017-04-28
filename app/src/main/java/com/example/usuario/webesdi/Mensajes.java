package com.example.usuario.webesdi;

import android.content.Intent;
import android.os.Bundle;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mensajes extends BaseActivity {


    // String consulta;
    String correo;
    String rolMaster = "administrador"; //nombre del rol con control total
    TextView txtEmail;
    static TextView txtChat;
    EditText inChat;
    public static String texto;
    Spinner spCorreo;

    TextView txtEmail2;
    EditText inChat2;
    TextView lblEquipo;
    TextView lblFecha;
    TextView lblDescripcion;
    private RecyclerView lstIncidencias;
    FirebaseRecyclerAdapter mAdapter;


    TextView txtEmail3;
    static TextView txtChat3;
    EditText inChat3;


    Bundle b;
    ImageButton enviar;
    ImageButton enviar2;
    ImageButton enviar3;

    List<String> datosCorreos;
    //  public static List<String> datosMensajes;

    int request_code = 1;

    private static final String TAGLOG = "firebase-db";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.configuracion:
                Bundle extras = getIntent().getExtras();
                String nombreActivity = this.getClass().getCanonicalName();
                Intent intent = new Intent(Mensajes.this,Settings.class);
                intent.putExtra("callingActivity", nombreActivity );
                intent.putExtras(extras);
                startActivity(intent);
                return true;
            case R.id.help:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
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
        enviar = (ImageButton) findViewById(R.id.imageButton);

        lblEquipo = (TextView) findViewById(R.id.lblEquipo);
        lblFecha = (TextView) findViewById(R.id.lblFecha);
        lblDescripcion = (TextView) findViewById(R.id.lbldescripcion);
        inChat2 = (EditText) findViewById(R.id.inTitulo);
        txtEmail2 = (TextView) findViewById(R.id.txtEmail2);
        enviar2 = (ImageButton) findViewById(R.id.imageButton2);

        inChat3 = (EditText) findViewById(R.id.inChat3);
        txtEmail3 = (TextView) findViewById(R.id.txtEmail3);
        txtChat3 = (TextView) findViewById(R.id.txtChat3);
        enviar3 = (ImageButton) findViewById(R.id.imageButton3);
        spCorreo = (Spinner) findViewById(R.id.spCorreo);

        correo = b.getString("email"); //inicialmente el correo es el del usuario logueado


        //bloque para dar valor a las pestañas


        Resources res = getResources();

        TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("Sugerencia");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Sugerencias");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("Incidencia");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Incidencias");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("Consulta");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Consultas");
        tabs.addTab(spec);

        tabs.setCurrentTab(0);
        //se carga inicialmente el contenido de la primera pestaña
        cargaTexto("Sugerencia");

        tabs.setOnTabChangedListener(new OnTabChangeListener() {
            public void onTabChanged(final String tabId) {
//cada vez que se selecciona una pestaña se llama al proceso para que muestre su contenido
                cargaTexto(tabId);
            }
        });
        //fin de pestañas

    }

    //metodo que recoge la pestaña seleccionada y muestra el arrayTexto segun toque
    public void cargaTexto(final String tabId) {
        DBmensajes conn = new DBmensajes(tabId);
        //switch con el resultado de la posicion seleccionadan,
        switch (tabId) {
            case "Sugerencia":

                //llama a muestratexto y dependiendo del rol muestra los mensajes
                //si es master oculta el boton enviar y muestra todos los mensajes
                if (b.getString("rol").equals(rolMaster)) {
                    enviar.setVisibility(View.GONE);
                    //  muestraMensajes("todos", tabId);
                    conn.listaMensajes("todos", tabId);

                } else {
                    //si no es master permite enviar y muestra solo sus mensajes
                    // muestraMensajes(b.getString("email").toString(), tabId);
                    conn.listaMensajes(b.getString("email").toString(), tabId);
                }
                break;
            case "Incidencia":

                //// TODO: de momento esta hace lo mismo que sugerencia, hay que cambiarlo  para
                /// incidencias y añadirle un QR

                Intent intent = new Intent(this, QRscanner.class);

                //  startActivityForResult(intent, request_code);


                break;
            case "Consulta":
                //si es master llama a creaspinnercorreo para seleccionar la conversacion

                if ((b.getString("rol")).equals(rolMaster)) {
                    creaSpinnerCorreo(tabId);
                    spCorreo.setVisibility(View.VISIBLE);
                    //si no es master oculta el spiner de seleccion de conversacion, permite enviar
                    //mensajes de consulta y muestra los mensajes propios
                } else {
                    spCorreo.setVisibility(View.GONE);
                    enviar3.setVisibility(View.VISIBLE);
                    // muestraMensajes(b.getString("email").toString(), tabId);
                    conn.listaMensajes(b.getString("email").toString(), tabId);
                }

                break;
            default:
                txtEmail.setText("inválido ");
                break;
        }


        enviar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                entraTexto(tabId);
            }
        });
        enviar2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                entraTexto(tabId);

            }
        });
        enviar3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                entraTexto(tabId);

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // TODO Auto-generated method stub
        if ((requestCode == request_code) && (resultCode == RESULT_OK)) {

            Toast.makeText(this, intent.getStringExtra("resultado"), Toast.LENGTH_LONG).show();
        }
    }


    public void creaSpinnerCorreo(final String miTab) {
        //definición del spinner de conversacion
        //crea una lista dinamica y se carga con los elementos recibidos de la BD firebase


        /*
        datos2 = new ArrayList<String>();
        //el primer dato del spiner sera siempre "todos"
        datos2.add("todos");

        //instancia la base de datos de firebase
        DatabaseReference dbMensajes = FirebaseDatabase.getInstance().getReference().child(miTab);
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

*/

        DBmensajes conn = new DBmensajes(miTab);
        datosCorreos = conn.listaCorreos();

        //creacion del adaptador del spinner de seleccion de conversaciones de consulta
        ArrayAdapter<String> adaptador2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datosCorreos);
        adaptador2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCorreo.setAdapter(adaptador2);
        Log.d(TAGLOG, "----------------------- pasando6---------------" + datosCorreos.size());
        spCorreo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //metodo del spinner cuando se selecciona algo
            public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {
                Log.d(TAGLOG, "----------------------- pasando7---------------" + datosCorreos.size());
                correo = parent.getItemAtPosition(position).toString();
                txtEmail3.setText(miTab + " de " + correo);
                Log.d(TAGLOG, "----------------------- pasando8---------------" + datosCorreos.size());
                switch (spCorreo.getSelectedItemPosition()) {
                    case 0:
                        //si se selecciona todos, oculta el boton enviar
                        enviar3.setVisibility(View.GONE);
                        //   Toast.makeText(getApplicationContext(), "todos seleccionados "+spCorreo.getSelectedItemPosition(), Toast.LENGTH_LONG).show();
                        break;
                    default:
                        //por defecto muestra el boton enviar
                        enviar3.setVisibility(View.VISIBLE);
                        //   Toast.makeText(getApplicationContext(), "otros seleccionados "+spCorreo.getSelectedItemPosition(), Toast.LENGTH_LONG).show();
                        break;
                }
                Log.d(TAGLOG, "----------------------- pasando9---------------" + datosCorreos.size());
                //llama a muestra mensaje con el correo seleccionado a mostrar o con "todos" si no se ha seleccionado ninguno
                //muestraMensajes(correo, miTab);
                DBmensajes conn = new DBmensajes(miTab);
                conn.listaMensajes(correo, miTab);

            }

            //metodo del spinner cuando no se selecciona nada
            public void onNothingSelected(AdapterView<?> parent) {
                //  Toast.makeText(getApplicationContext(), "nada en el spinner", Toast.LENGTH_LONG).show();
            }

        });
    }


    public static void muestraMensajes(final ArrayList<String> datosMensajes, final String miTab) {
/*
        //instancia la base de datos de firebase
        DatabaseReference dbMensajes = FirebaseDatabase.getInstance().getReference().child(miTab);

        //hace una consulta para mostrar los mensajes ordenados por fecha
        Query dbQuery = dbMensajes.orderByKey();

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot nodoUsuario) {

                arrayTexto = "";
                for (DataSnapshot childDataSnapshot : nodoUsuario.getChildren()) {
                    //  Log.d(TAGLOG, "----------------------- pasndo---------------");

                    //si el usuario logueado es master y selecciona "todos" en el spinner, muestra todos los mensajes, si no muestra solo
                    //los que coincide su correo (que le pasamos al llamar al procedimiento) con el del correo del mensaje de la DB
                    if (correo.equals("todos")) {
                        arrayTexto = (arrayTexto + childDataSnapshot.child("Nombre").getValue() + ": "
                                + childDataSnapshot.child("Mensaje").getValue() + "\n");

                    } else if ((childDataSnapshot.child("Correo").getValue()).equals(correo)) {
                        arrayTexto = (arrayTexto + childDataSnapshot.child("Nombre").getValue() + ": "
                                + childDataSnapshot.child("Mensaje").getValue() + "\n");
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAGLOG, "Error!", databaseError.toException());
            }
        };


        dbQuery.addValueEventListener(eventListener);
        */

        //  DBmensajes conn = new DBmensajes(miTab);
        //datosMensajes = conn.listaMensajes(correo);

        //  Log.d(TAGLOG, "----------------------- pasando3---------------" + datos2.get(0));
        //  texto=conn.texto;
        String texto = "";


        switch (miTab) {
            case "Sugerencia":
                for (String elemento : datosMensajes) {
                    texto = (texto + elemento);
                    //            Log.d(TAGLOG, "----------------------- pasando4---------------" + datos2.get(0));
                }
                txtChat.setText(texto);
                txtChat.setMovementMethod(new ScrollingMovementMethod());
                break;
            case "Incidencia":

                break;
            case "Consulta":
                for (String elemento : datosMensajes) {
                    texto = (texto + elemento);
                    //        Log.d(TAGLOG, "----------------------- pasando5---------------" + datos2.get(0));
                }
                txtChat3.setText(texto);
                txtChat3.setMovementMethod(new ScrollingMovementMethod());
                break;
            default:
                break;
        }
    }


    //metodo que se ejecuta al clickar enviar
    public void entraTexto(String miTab) {
        //instancia la base de datos de firebase
        DatabaseReference dbEnviar = FirebaseDatabase.getInstance().getReference().child(miTab);
        //crea una lista con datos del usuario y el mensaje introducido
        Map<String, String> envio = new HashMap<>();
        envio.put("Correo", correo);
        envio.put("Nombre", b.getString("nombre"));

        //selecciona una de las 3 cajas de entrada (una por pestaña) segun la pestaña y pone el valor
        //en el array
        switch (miTab) {
            case "Sugerencia":
                envio.put("Mensaje", inChat.getText().toString());
                //vacia la caja de entrada de arrayTexto
                inChat.setText("");
                break;
            case "Incidencia":
                envio.put("Mensaje", inChat2.getText().toString());
                //vacia la caja de entrada de arrayTexto
                inChat2.setText("");
                break;
            case "Consulta":
                envio.put("Mensaje", inChat3.getText().toString());
                //vacia la caja de entrada de arrayTexto
                inChat3.setText("");
                break;
            default:
                break;
        }


        //sube el arrayTexto entrado a firebase, al nodo del usuario
        //  dbMensajes.child(txtEmail.getText().toString()).push().setValue(inChat.getText().toString());
        dbEnviar.push().setValue(envio);

    }



}
