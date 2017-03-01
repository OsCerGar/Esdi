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

    int segundos,minutos ,horas ,diasemana ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispo_aulas);


        segundos = c.get(Calendar.SECOND);
        minutos = c.get(Calendar.MINUTE);
        horas = c.get(Calendar.HOUR_OF_DAY);
        diasemana = c.get(Calendar.DAY_OF_WEEK);


        ivMapa = (ImageView)findViewById(R.id.ivMapa);

        //este metodo comprovará la hora y cambiará el Recurso del ImageView por el que nos interese
        if (comprobarhora()){
            cambiarImagen();
        }



    }



        public void cambiarImagen(){
            switch(diasemana) {
                case 1:
                    disponibilidad(1,horas, minutos);
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


    // hace una comprovación rápida, para asegurar que las aulas estan abiertas.
    private boolean comprobarhora() {
        if (horas>8 && horas<20){
            return true;
        }else{
            return false;
        }

    }
    //este metodo se encarga de comparar el momento actual con el horario, y mostrar la imagen que corresponde
    private void disponibilidad(int pdia,int phoras,int pminutos){

    }
}

