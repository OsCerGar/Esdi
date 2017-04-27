package com.example.usuario.webesdi;

/**
 * Created by JR on 27/04/2017.
 */

public class MensajesIncidencias {
    String Equipo;
    String Fecha;
    String titulo;

    public MensajesIncidencias(String equipo, String fecha, String descripcion) {
        Equipo = equipo;
        Fecha = fecha;
        titulo = descripcion;
    }

    public String getEquipo() {
        return Equipo;
    }

    public String getFecha() {
        return Fecha;
    }

    public String getTitulo() {
        return titulo;
    }
}
