package com.example.usuario.webesdi;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    ImageView numero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

        //recibe los datos de usuario a traves del bundle del intent
        Intent Mainact = getIntent();
        b = Mainact.getExtras();

        String nombre = b.getString("nombre");
        String email = b.getString("email");
        numero = (ImageView) findViewById(R.id.numero);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtEmail.setText(email + " - " + nombre);


        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        numero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri num = Uri.parse("tel:625341986");
                Intent i = new Intent(Intent.ACTION_CALL, num);
                if (ActivityCompat.checkSelfPermission(Contacto.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(i);
            }
        });
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
