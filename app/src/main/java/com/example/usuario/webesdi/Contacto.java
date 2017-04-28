package com.example.usuario.webesdi;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Contacto extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap mapa;

    TextView txtEmail;
    Bundle b;
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
                Intent intent = new Intent(Contacto.this,Settings.class);
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
        setContentView(R.layout.activity_contacto);

        //recibe los datos de usuario a traves del bundle del intent
        Intent Mainact = getIntent();
        b = Mainact.getExtras();

        String nombre = b.getString("nombre");
        String email = b.getString("email");

        txtEmail = (TextView)findViewById(R.id.txtEmail);
        txtEmail.setText(email + " - " + nombre);


        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap map) {
        mapa = map;
        moverCentro();
        insertarMarcador();
    }

    private void moverCentro()
    {
        // actualiza la camara para que se mueva a la posicion de Esdi, con un zoom de 15
        CameraUpdate camUpd1 =
                CameraUpdateFactory
                        .newLatLngZoom(new LatLng(41.5438081, 2.1168294000000287), 15);

        mapa.moveCamera(camUpd1);
    }
    private void insertarMarcador() {
        //pone un marcador sobre el centro, permite hacerle click, y abrir la ruta para llegar.
        mapa.addMarker(new MarkerOptions()
                .position(new LatLng(41.5438081, 2.1168294000000287))
                .title("ESDI: Sabadell"));
    }

    public void lanzarMensajes(View V){
        Intent intent = new Intent(Contacto.this,Mensajes.class);
        intent.putExtras(b);
        startActivity(intent);
    }

}
