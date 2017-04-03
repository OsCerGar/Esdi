package com.example.usuario.webesdi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class GLPI extends AppCompatActivity {

    private String URL;
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glpi);

        Intent Mainact = getIntent();
        b = Mainact.getExtras();
        URL = b.getString(URL);
        //trabaja con el webView del xml, simplemente le hace cargar una URL dentro de la app.
        WebView myWebView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl(URL+"/glpi/front/helpdesk.public.php?create_ticket=1");
    }
}
