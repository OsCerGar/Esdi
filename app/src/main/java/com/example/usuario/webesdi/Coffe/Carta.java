package com.example.usuario.webesdi.Coffe;

/**
 * Created by Becario2 on 16/06/2017.
 */

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usuario.webesdi.R;

public class Carta extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_carta, container, false);
        return rootView;
    }
}
