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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

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
                ponTema(sharedPreferences);
                break;
            case "lenguaAplicacion":
                ponIdioma(sharedPreferences, key);
                break;
        }
        finish();
        startActivity(getIntent());
    }
    private void ponTema(SharedPreferences sharedPreferences){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean appTheme = SP.getBoolean("temaAplicacion", true);
        if (appTheme){
            this.setTheme(R.style.AppTheme);
        }
        else {
            this.setTheme(R.style.AppThemeDark);
        }
    }

    private void ponIdioma(SharedPreferences sharedPreferences, String key){
        String valor = SP.getString(key, "1");
        String idioma = "en";
        switch (valor){
            case "1":
                idioma = "en";
                break;
            case "2":
                idioma = "ca";
                break;
            case "3":
                idioma = "es";
                break;
        }
        setLocale(idioma);
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
        Bundle extras = getIntent().getExtras();
        Class<?> c = null;
        if (callingActivity != null) {
            try {
                c = Class.forName(callingActivity);
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Intent intent = new Intent(Settings.this, c);
            intent.putExtras(extras);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        ponTema(SP);
        super.onStart();
    }


}
