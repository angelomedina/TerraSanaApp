package com.example.katty.terrasana.objetos;

public class Historial {
    String fecha;
    String ubicacion;
    String monto;

    public Historial(String fecha, String ubicacion, String monto) {
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.monto = monto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }
}
