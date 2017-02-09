package com.example.usuario.webesdi;

/**
 * Created by JR on 09/02/2017.
 */

public class Prediccion {

    private String cielo;
    private long temperatura;
    private double humedad;
    private String fecha;


    public Prediccion() {
    }

    public Prediccion(String cielo, long temperatura, double humedad, String fecha) {
        this.cielo = cielo;
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.fecha = fecha;
    }

    public String getCielo() {
        return cielo;
    }

    public void setCielo(String cielo) {
        this.cielo = cielo;
    }

    public long getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(long temperatura) {
        this.temperatura = temperatura;
    }

    public double getHumedad() {
        return humedad;
    }

    public void setHumedad(double humedad) {
        this.humedad = humedad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Prediccion{" +
                "cielo='" + cielo + '\'' +
                ", temperatura=" + temperatura +
                ", humedad=" + humedad +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}
