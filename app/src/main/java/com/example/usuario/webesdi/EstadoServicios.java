package com.example.usuario.webesdi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.net.InetAddress;
import java.util.TimeZone;


/**
 * Created by User on 10/05/2017.
 */

public class EstadoServicios extends AppCompatActivity {
    ImageView btnimpresoras;
    ImageView btnwifi;
    ImageView btngoogledocs;
    ImageView btngooglegmail;
    ImageView btngooglecalendar;
    ImageView btncantina;
    boolean dispo;
    int diasemana,horas;
    Calendar cal = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_servicios);
        btnimpresoras  = (ImageView) findViewById(R.id.btnimpresoras);
        btnwifi = (ImageView) findViewById(R.id.btnwifi);
        btngoogledocs = (ImageView) findViewById(R.id.btnGoogleDocs);
        btncantina = (ImageView) findViewById(R.id.btncantina);
        btngooglegmail = (ImageView) findViewById(R.id.btnGoogleGmail);
        btngooglecalendar = (ImageView) findViewById(R.id.btnGoogleCalendar);

        new disponibilitatwifi().execute();
        new disponibilitatdocs().execute();
        new disponibilitatgmail().execute();
        new disponibilitatimpresora().execute();
        new disponibilitatcantina().execute();
        new disponibilitatcalendar().execute();

    }

    private class disponibilitatwifi extends AsyncTask<Void, Void, Boolean>{
        Boolean a;
        @Override

        protected Boolean doInBackground(Void... params) {
            if(isReachable("192.160.50.222",80,1000)== true){
            a = true;}else{
                a = false;
            }
            return a;
            }

        @Override
        protected void onPostExecute(Boolean color) {
            if ( color == true) {
                btnwifi.setImageResource(R.drawable.wifi_verd);

            } else {
                btnwifi.setImageResource(R.drawable.wifi_vermell);
            }

        }
    }
    private class disponibilitatdocs extends AsyncTask<Void, Void, Boolean> {
        Boolean a;

        @Override

        protected Boolean doInBackground(Void... params) {
           if(isReachable("docs.esdi.edu.es",80,1000)== true){
               a = true;
           }
           else{
               a = false;
               }
               return a;
           }





        @Override
        protected void onPostExecute(Boolean color) {
            if (color == true) {
                btngoogledocs.setImageResource(R.drawable.docs_verd);
                btngoogledocs.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v)  {
                        Toast.makeText(getBaseContext(), "Google docs funciona correctament" , Toast.LENGTH_SHORT ).show();
                    }
                });
            } else {
                btngoogledocs.setImageResource(R.drawable.docs_vermell);
                btngoogledocs.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v)  {
                        Toast.makeText(getBaseContext(), "Google docs no esta operatiu " , Toast.LENGTH_SHORT ).show();
                    }
                });
            }

        }
    }
            private class disponibilitatimpresora extends AsyncTask<Void, Void, Boolean>{
            Boolean a;
            @Override

            protected Boolean doInBackground(Void... params) {

                    if (isReachable("192.160.50.222", 80, 1000) == true) {
                        a = true;
                    } else {
                        a = false;
                    }

                    return false;

            }
            @Override
            protected void onPostExecute(Boolean color) {
                if ( color == true) {
                    btnimpresoras.setImageResource(R.drawable.impresora_verda);
                    btnimpresoras.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v)  {
                            Toast.makeText(getBaseContext(), "Les impresores estan operatives" , Toast.LENGTH_SHORT ).show();
                        }
                    });
                } else {
                    btnimpresoras.setImageResource(R.drawable.impresora_vermell);
                    btnimpresoras.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v)  {
                            Toast.makeText(getBaseContext(), "La impresora no esta operativa" , Toast.LENGTH_SHORT ).show();
                        }
                    });
                }

            }
        }
        private class disponibilitatcantina extends AsyncTask<Void, Void, Boolean>{
            Boolean a;
            @Override

            protected Boolean doInBackground(Void... params) {
                diasemana = cal.get(Calendar.DAY_OF_WEEK);
                horas = cal.get(Calendar.HOUR_OF_DAY);
                if ((diasemana==2 || diasemana==3 || diasemana==4 || diasemana==5 || diasemana==6) && (horas>=8 || horas<19)){
                        a = true;

                    }else{
                    a = false;
                }
                return a;
            }

            @Override
            protected void onPostExecute(Boolean color) {
                if ( color == true) {
                    btncantina.setImageResource(R.drawable.cantina_verd);
                    btncantina.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v)  {
                            Toast.makeText(getBaseContext(), "La cantina esta oberta" , Toast.LENGTH_SHORT ).show();
                        }
                    });
                } else {
                    btncantina.setImageResource(R.drawable.cantina_vermell);
                    btncantina.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v)  {
                            Toast.makeText(getBaseContext(), "La cantina esta tancada" , Toast.LENGTH_SHORT ).show();
                        }
                    });
                }

            }
        }
    private class disponibilitatgmail extends AsyncTask<Void, Void, Boolean>{
        Boolean a;
        @Override

        protected Boolean doInBackground(Void... params) {


            if (isReachable("mail.esdi.edu.es", 80, 1000) == true) {
                a = true;
            } else {
                a = false;
            }
            return a;
        }

        @Override
        protected void onPostExecute(Boolean color) {
            if ( color == true) {
                btngooglegmail.setImageResource(R.drawable.gmail_verd);
                btngooglegmail.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v)  {
                        Toast.makeText(getBaseContext(), "El gmail funciona correctament" , Toast.LENGTH_SHORT ).show();
                    }
                });
            } else {
                btngooglegmail.setImageResource(R.drawable.gmail_vermell);
                btngooglegmail.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v)  {
                        Toast.makeText(getBaseContext(), "El gmail no esta operatiu" , Toast.LENGTH_SHORT ).show();
                    }
                });
            }

        }
    }
    private class disponibilitatcalendar extends AsyncTask<Void, Void, Boolean>{
        Boolean a;
        @Override

        protected Boolean doInBackground(Void... params) {
            if (isReachable("calendar.esdi.edu.es", 80, 1000) == true) {
                a = true;
            } else {
                a = false;
            }
            return a;
        }

        @Override
        protected void onPostExecute(Boolean color) {
            if ( color == true) {
                btngooglecalendar.setImageResource(R.drawable.calendari_verd);
                btngooglecalendar.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v)  {
                        Toast.makeText(getBaseContext(), "El Google Calendar funciona correctament" , Toast.LENGTH_SHORT ).show();
                    }
                });
            } else {
                btngooglecalendar.setImageResource(R.drawable.calendari_vermell);
                btngooglecalendar.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v)  {
                        Toast.makeText(getBaseContext(), "El Google Calendar no esta operatiu" , Toast.LENGTH_SHORT ).show();
                    }
                });
            }

        }
    }

    private static boolean isReachable(String addr, int openPort, int timeOutMillis) {
        try {
            try (Socket soc = new Socket()) {
                soc.connect(new InetSocketAddress(addr, openPort), timeOutMillis);
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }


}



