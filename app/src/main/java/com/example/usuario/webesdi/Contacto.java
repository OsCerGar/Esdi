package com.example.usuario.webesdi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

public class Contacto extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mapa;
    Button btnChat;
    String email;
    TextView txtEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

        Intent Mainact = getIntent();
        String email = Mainact.getStringExtra("email");
        txtEmail = (TextView)findViewById(R.id.txtEmail);
        txtEmail.setText("hola holA");


        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        btnChat = (Button) findViewById(R.id.btnChat);

        btnChat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                lanzarActivity();

            }
        });

    }

    @Override
    public void onMapReady(GoogleMap map) {
        mapa = map;
    }


    private void lanzarActivity(){
        Intent intent = new Intent(Contacto.this,Mensajes.class);
        startActivity(intent);
    }
}
