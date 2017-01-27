package com.example.usuario.webesdi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class MenuPrincipal extends AppCompatActivity {
    Button btnpaginaweb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        btnpaginaweb = (Button) findViewById(R.id.brnpaginaweb);

        btnpaginaweb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                lanzarActivity();

                }
        });


    }
    private void lanzarActivity(){
        Intent intent = new Intent(this,PaginaWeb.class);
        startActivity(intent);
    }


}
