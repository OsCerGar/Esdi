package com.example.usuario.webesdi;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by Silvan on 09/02/2017.
 */

public class Settings extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private String callingActivity = "";
    private SharedPreferences SP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SP = PreferenceManager.getDefaultSharedPreferences(this);
        SP.registerOnSharedPreferenceChangeListener(this);
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
        if (callingActivity.isEmpty()){
            Intent i = getIntent();
            callingActivity =  i.getStringExtra("callingActivity");
        }
    }

    public static class MyPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key){
            case "temaAplicacion":
                ponTema(SP);
                break;
            case "lenguaAplicacion":
                ponLengua(sharedPreferences, key);
                break;
        }
        finish();
        startActivity(getIntent());
    }
    private void ponTema(SharedPreferences SP){
        int version = android.os.Build.VERSION.SDK_INT;
        SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean appTheme = SP.getBoolean("applicationTheme", true);
        if (version >20) {
            if (appTheme) {
                this.setTheme(R.style.LightTheme);
            } else {
                this.setTheme(R.style.BlackTheme);
            }
        }
        else{
            if (appTheme) {
                this.setTheme(R.style.OldLightTheme);
            } else {
                this.setTheme(R.style.OldBlackTheme);
            }
        }
    }

    private void ponLengua(SharedPreferences sharedPreferences, String key){
        String valor = SP.getString(key, "1");
        String lengua = "en";
        switch (valor){
            case "1":
                lengua = "en";
                break;
            case "2":
                lengua = "ca";
                break;
            case "3":
                lengua = "es";
                break;
        }
        setLocale(lengua);
    }
    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }



    @Override
    public void onBackPressed() {
        Class<?> c = null;
        if (callingActivity != null) {
            try {
                c = Class.forName(callingActivity);
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Intent intent = new Intent(Settings.this, c);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        ponTema(SP);
        super.onStart();
        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        ponTema(SP);
        super.onRestart();
        Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        ponTema(SP);
        super.onResume();
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
    }

}
