package com.example.usuario.webesdi.empresas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.usuario.webesdi.BaseActivity;
import com.example.usuario.webesdi.R;

import java.util.ArrayList;
import java.util.List;

public class Empresas extends BaseActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Empresa> listaEmpreas;
    private EmpresaAdapter adaptadorEmpresas;

    String[] names = {"Universitat", "ESDi", "Barcelona", "Sabadell", "Exemple", "Exemple",
            "Exemple", "Exemple", "Exemple", "Exemple", "Exemple", "Exemple", "Exemple", "Exemple", "Exemple"};

    String[] singers = {"Estudiar", "Sabadell", "Exemple", "Exemple", "Exemple", "Exemple",
            "Exemple", "Exemple", "Exemple", "Exemple", "Exemple",
            "Exemple", "Exemple", "Exemple", "Exemple"};

    int[] pics = {
            R.drawable.esdi,
            R.drawable.harvard,
            R.drawable.android,
            R.drawable.cs,
            R.drawable.aboutus,
            R.drawable.focus,
            R.drawable.javaa,
            R.drawable.uab,
            R.drawable.tel,
            R.drawable.unnamed,
            R.drawable.upc,
            R.drawable.aboutus,
            R.drawable.android,
            R.drawable.android,
            R.drawable.android};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresas);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        //Use this setting to improve performance if you know that changes in
        //the content do not change the layout size of the RecyclerView
        if (mRecyclerView != null) {
            mRecyclerView.setHasFixedSize(true);
        }

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //intializing an arraylist called songlist
        listaEmpreas = new ArrayList<>();

        //adding data from arrays to songlist
        for (int i = 0; i < names.length; i++) {
            Empresa empresa = new Empresa(names[i], singers[i], i + 1, pics[i]);
            listaEmpreas.add(empresa);
        }
        //initializing adapter
        adaptadorEmpresas = new EmpresaAdapter(listaEmpreas);

        //specifying an adapter to access data, create views and replace the content
        mRecyclerView.setAdapter(adaptadorEmpresas);
        adaptadorEmpresas.notifyDataSetChanged();

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(Empresas.this, "Card at " + position + " is clicked", Toast.LENGTH_SHORT).show();
            }
        }));
    }
}
