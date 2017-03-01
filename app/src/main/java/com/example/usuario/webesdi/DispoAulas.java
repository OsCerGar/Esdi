package com.example.usuario.webesdi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.util.Calendar;

public class DispoAulas extends AppCompatActivity {
    ImageView ivMapa;
    // con el calendar puedo mirar datos del tiempo actual.
    Calendar c = Calendar.getInstance();
    //Todos los tiempo que nos interesan
    int seconds = c.get(Calendar.SECOND);
    int minutos = c.get(Calendar.MINUTE);
    int horas = c.get(Calendar.HOUR_OF_DAY);
    int diasemana = c.get(Calendar.DAY_OF_WEEK);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispo_aulas);
        ivMapa = (ImageView)findViewById(R.id.ivMapa);

        //este metodo comprovará la hora y cambiará el Recurso del ImageView por el que nos interese
        if (comprovarhora()){
            cambiarImagen();
        }



    }



        public void cambiarImagen(){
            switch(diasemana) {
                case 1:

                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;

            }
        }



    private boolean comprovarhora() {
        if (horas>8 && horas<20){
            return true;
        }else{
            return false;
        }

    }
}

