package com.example.usuario.webesdi;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.Calendar;
import java.util.List;

public class EstadoServicios extends AppCompatActivity {
    ImageView btnimpresoras;
    ImageView btnwifi;
    ImageView btngoogledocs;
    ImageView btngooglegmail;
    ImageView btngooglecalendar;
    ImageView btncantina;
    int diasemana,horas;
    Calendar cal = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_servicios);

        setTitle(getResources().getText(R.string.titServicios));
        btnimpresoras  = (ImageView) findViewById(R.id.btnimpresoras);
        btnwifi = (ImageView) findViewById(R.id.btnwifi);
        btngoogledocs = (ImageView) findViewById(R.id.btnGoogleDocs);
        btncantina = (ImageView) findViewById(R.id.btncantina);
        btngooglegmail = (ImageView) findViewById(R.id.btnGoogleGmail);
        btngooglecalendar = (ImageView) findViewById(R.id.btnGoogleCalendar);

        new disponibilitatwifi().execute();
        new disponibilitatdocs().execute();
        new disponibilitatgmail().execute();
       // new disponibilitatimpresora().execute();
        new disponibilitatcantina().execute();
        new disponibilitatcalendar().execute();




    }



    private class disponibilitatwifi extends AsyncTask<Void, Void, Boolean>{
        Boolean a;
        @Override

        protected Boolean doInBackground(Void... params) {
            WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            final List<ScanResult> results = wifi.getScanResults();
            if (results != null) {
                for (int i = 0; i < results.size(); i++) {
                    String ssid = results.get(i).SSID;
                    if (ssid.startsWith("ESDiWIFI")) {
                        a = true;}
                    else {
                        a = false;
                    }
                }
            }
            return a;
        }


        @Override
        protected void onPostExecute(Boolean color) {
            if ( color == true) {
                btnwifi.setImageResource(R.drawable.wifi_verd);
                btnwifi.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v)  {
                        Toast.makeText(getBaseContext(), "La Wifi esta disponible on estas situat" , Toast.LENGTH_SHORT ).show();
                    }
                });

            } else {
                btnwifi.setImageResource(R.drawable.wifi_vermell);
                btnwifi.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v)  {
                        Toast.makeText(getBaseContext(), "La Wifi no esta disponible on estas situat" , Toast.LENGTH_SHORT ).show();
                    }
                });
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
          /*  private class disponibilitatimpresora extends AsyncTask<Void, Void, Boolean>{
            Boolean a;
                String funciona1 = "", funciona2="",funciona3="";
            @Override

            protected Boolean doInBackground(Void... params) {

                URL url= null;
                try {
                    url = new URL("http://67.222.58.123/ping.php");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                InputStream in = null;
                try {
                    in = url.openStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder result = new StringBuilder();
                String line;
                try {
                    while((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(result.toString());




                if (isReachable("192.168.0.101", 80, 1000) == true && isReachable("192.168.0.102", 80, 1000) == true && isReachable("192.168.0.103", 80, 1000) == true ) {
                        a = true;
                    } else {
                        a = false;
                    }

                if (isReachable("1.177.96.147", 80, 1000) == true) {
                    funciona1 = result.toString();
                } else {
                    funciona1 = result.toString();
                }

                if (isReachable("192.160.50.221", 80, 1000) == true) {
                    funciona2 = "funciona";
                } else {
                    funciona2 = "no funciona";
                }

                if (isReachable("192.160.50.222", 80, 1000) == true) {
                    funciona3 = "funciona";
                } else {
                    funciona3 = "no funciona";
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
                }
                    btnimpresoras.setImageResource(R.drawable.impresora_vermell);

                    btnimpresoras.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v)  {
                            Toast.makeText(getBaseContext(), "La impresora de la primera clase:"+ funciona1 +"\n"+"La impresora de la segona clase:"+ funciona2 +"\n"+"La impresora de la tercera clase:"+ funciona3, Toast.LENGTH_SHORT ).show();
                        }
                    });
                }

            }
*/
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



