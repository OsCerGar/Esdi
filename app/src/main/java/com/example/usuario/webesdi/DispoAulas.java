package com.example.usuario.webesdi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.util.Calendar;

public class DispoAulas extends AppCompatActivity {
    ImageView ivMapa,h1,h2,h3,h4,h5;

    boolean[] horarioh2;
    boolean[] horarioh3;
    boolean[] horarioh4;
    boolean[] horarioh5;
    // con el calendar puedo mirar datos del tiempo actual.
    Calendar c = Calendar.getInstance();
    //Todos los tiempo que nos interesan

    int segundos,minutos ,horas ,diasemana ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispo_aulas);

        h1 = (ImageView)findViewById(R.id.h1);
        h2 = (ImageView)findViewById(R.id.h2);
        h3 = (ImageView)findViewById(R.id.h3);
        h4 = (ImageView)findViewById(R.id.h4);
        h5 = (ImageView)findViewById(R.id.h5);

        //informacion del momento actual usando la libreria Calendar
        segundos = c.get(Calendar.SECOND);
        minutos = c.get(Calendar.MINUTE);
        horas = c.get(Calendar.HOUR_OF_DAY);
        diasemana = c.get(Calendar.DAY_OF_WEEK);


        ivMapa = (ImageView)findViewById(R.id.ivMapa);

        //este metodo comprovará la hora y cambiará el Recurso del ImageView por el que nos interese
        // comprobarhora mira que no sea por la noche, o mañana (fuera de horarios)
        if (comprobarhora()){
            cambiarImagen();
        }



    }



        public void cambiarImagen(){
            //instanciar Arrays crea los arrays de los horarios dependiendo del dia (true ocupado, false libre)
            switch(diasemana) {
                case 1:
                    cambiarh1();
                    cambiarh2(1);
                    cambiarh3(1);
                    cambiarh4(1);
                    cambiarh5(1);
                    break;
                case 2:
                    cambiarh1();
                    cambiarh2(2);
                    cambiarh3(2);
                    cambiarh4(2);
                    cambiarh5(2);
                    break;
                case 3:
                    cambiarh1();
                    cambiarh2(3);
                    cambiarh3(3);
                    cambiarh4(3);
                    cambiarh5(3);
                    break;
                case 4:
                    cambiarh1();
                    cambiarh2(4);
                    cambiarh3(4);
                    cambiarh4(4);
                    cambiarh5(4);
                    break;
                case 5:
                    cambiarh1();
                    cambiarh2(5);
                    cambiarh3(5);
                    cambiarh4(5);
                    cambiarh5(5);
                    break;
                case 6:
                    break;
                case 7:
                    break;

            }
        }


    // hace una comprovación rápida, para asegurar que las aulas estan abiertas.
    private boolean comprobarhora() {
        if (horas==8 ){
            if (minutos>30){
                return true;
            }
        }else if (horas > 8 && horas<20) {
            return true;
        }
            return false;


    }
    //este metodo se encarga de comparar el momento actual con el horario, y mostrar la imagen que corresponde
    private void cambiarh1(){
        h1.setImageResource(R.drawable.edifici_esdi_h1verde);

    }
    private void cambiarh2(int dia){

    }
    private void cambiarh3(int dia){

    }
    private void cambiarh4(int dia){

    }
    private void cambiarh5(int dia){

    }


}

