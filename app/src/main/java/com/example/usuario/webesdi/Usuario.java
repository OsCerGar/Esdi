package com.example.usuario.webesdi;

/**
 * Created by JR on 09/02/2017.
 */

public class Usuario {

    private String nombre;
    private String correo;

    public Usuario() {
    }

    public Usuario(String nombre, String correo) {
        this.nombre = nombre;
        this.correo = correo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                '}';
    }
}
