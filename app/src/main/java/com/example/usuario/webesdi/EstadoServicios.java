package com.example.usuario.webesdi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
    ImageView btngoogleapps;
    ImageView btncantina;
    boolean dispo;
    int diasemana,horas,minutos;
    Calendar cal = Calendar.getInstance();
    String dia = "Finde";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_servicios);
        btnimpresoras  = (ImageView) findViewById(R.id.btnimpresoras);
        btnwifi = (ImageView) findViewById(R.id.btnwifi);
        btngoogleapps = (ImageView) findViewById(R.id.btnGoogleApps);
        btncantina = (ImageView) findViewById(R.id.btncantina);

        new disponibilitatwifi().execute();
        new disponibilitatgooleapps().execute();
        new disponibilitatimpresora().execute();
        new disponibilitatcantina().execute();


        btnimpresoras.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        btnwifi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });

        btngoogleapps.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });

        btncantina.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });
    }

    private class disponibilitatwifi extends AsyncTask<Void, Void, Boolean>{
        Boolean a;
        @Override

        protected Boolean doInBackground(Void... params) {
            try {
                Inet4Address address;
                address = (Inet4Address) InetAddress.getByName("173.194.69.105");

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            a = true;
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
    private class disponibilitatgooleapps extends AsyncTask<Void, Void, Boolean> {
        Boolean a;

        @Override

        protected Boolean doInBackground(Void... params) {
           if(isReachable("192.160.50.222",80,1000)== true){
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
                btngoogleapps.setImageResource(R.drawable.googleapps_verd);
            } else {
                btngoogleapps.setImageResource(R.drawable.googleapps_vermell);
            }

        }
    }
            private class disponibilitatimpresora extends AsyncTask<Void, Void, Boolean>{
            Boolean a;
            @Override

            protected Boolean doInBackground(Void... params) {
                try {
                    if (InetAddress.getByName("173.194.69.105").isReachable(1000) == true) {
                        a = true;
                    }else{
                        a = false;
                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            protected void onPostExecute(Boolean color) {
                if ( color == true) {
                    btnimpresoras.setImageResource(R.drawable.impresora_verda);
                } else {
                    btnimpresoras.setImageResource(R.drawable.impresora_vermell);
                }

            }
        }
        private class disponibilitatcantina extends AsyncTask<Void, Void, Boolean>{
            Boolean a;
            @Override

            protected Boolean doInBackground(Void... params) {
                diasemana = cal.get(Calendar.DAY_OF_WEEK);
                horas = cal.get(Calendar.HOUR_OF_DAY);
                minutos = cal.get(Calendar.MINUTE);
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
                } else {
                    btncantina.setImageResource(R.drawable.cantina_vermell);
                }

            }
        }
    private static boolean isReachable(String addr, int openPort, int timeOutMillis) {
        // Any Open port on other machine
        // openPort =  22 - ssh, 80 or 443 - webserver, 25 - mailserver etc.
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



