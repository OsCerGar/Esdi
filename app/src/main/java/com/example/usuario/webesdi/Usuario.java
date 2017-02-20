package com.example.usuario.webesdi;

/**
 * Created by JR on 09/02/2017.
 */

public class Usuario {

    private String nombre;
    private String correo;
    private String rol;
    private String ID;

    public Usuario() {
    }

    public Usuario(String correo, String nombre, String rol) {
        this.correo = correo;
        this.nombre = nombre;
        this.rol = rol;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = correo.replace('.', '_');
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", rol='" + rol + '\'' +
                ", ID='" + ID + '\'' +
                '}';
    }
}
