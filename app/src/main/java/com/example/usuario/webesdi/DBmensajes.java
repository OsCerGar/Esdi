package com.example.usuario.webesdi;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by JR on 21/04/2017.
 */

public class DBmensajes {
    DatabaseReference dbMensajes;
    private ArrayList<String> datos;
    String texto;
    Boolean flag = false;

    private static final String TAGLOG = "firebase-db";


    public DBmensajes(String miTab) {
        //instancia la base de datos de firebase
        dbMensajes = FirebaseDatabase.getInstance().getReference().child(miTab);
        datos = null;
        //texto ="";
    }

    public ArrayList<String> listaCorreos() {

        datos = new ArrayList<String>();
        //el primer dato del spiner sera siempre "todos"
        datos.add("todos");

        Query dbQuery = dbMensajes.orderByKey();
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot nodoUsuario) {
                //en cada bucle carga un nuevo elemento a la lista
                for (DataSnapshot childDataSnapshot : nodoUsuario.getChildren()) {
                    if (datos.contains(childDataSnapshot.child("Correo").getValue().toString())) {
                       //    Log.d(TAGLOG, "======= paso por aqui 1 ==========   " );
                        //si el correo ya se ha añadido antes al arraylist, no hace nada, así no se duplica

                    } else {
                        datos.add(childDataSnapshot.child("Correo").getValue().toString());
                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAGLOG, "<------- Error DBmensajes listaCorreos -------->", databaseError.toException());
            }
        };

      //  Log.d(TAGLOG, "======= paso por aqui 2 ==========   " );
        dbQuery.addValueEventListener(eventListener);
        return datos;
    }


    public void listaMensajes(final String correo, final String miTab) {

        //hace una consulta para mostrar los mensajes ordenados por fecha
        Query dbQuery = dbMensajes.orderByKey();
        datos = new ArrayList<String>();

            ValueEventListener eventListener = new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot nodoUsuario) {
                    texto = "";
                    for (DataSnapshot childDataSnapshot : nodoUsuario.getChildren()) {
                        //si el usuario logueado es master y selecciona "todos" en el spinner, muestra todos los mensajes, si no muestra solo
                        //los que coincide su correo (que le pasamos al llamar al procedimiento) con el del correo del mensaje de la DB
                        if (correo.equals("todos")) {
                            datos.add(childDataSnapshot.child("Nombre").getValue() + ": "
                                    + childDataSnapshot.child("Mensaje").getValue() + "\n");

                        } else if ((childDataSnapshot.child("Correo").getValue()).equals(correo)) {
                            datos.add (childDataSnapshot.child("Nombre").getValue() + ": "
                                    + childDataSnapshot.child("Mensaje").getValue() + "\n");
                        }
                    }
                          Log.d(TAGLOG, "----------------------- pasando1---------------" + datos.size());
                    Mensajes.muestraMensajes(datos, miTab);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d(TAGLOG, "<-------- Error DBmensajes listaMensajes ------>", databaseError.toException());
                }

            };

        dbQuery.addValueEventListener(eventListener);

        Log.d(TAGLOG, "----------------------- pasando2---------------" + datos.size());

    }

}


