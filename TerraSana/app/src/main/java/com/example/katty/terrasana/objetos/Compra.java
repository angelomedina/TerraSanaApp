package com.example.katty.terrasana.objetos;

import java.util.List;

public class Compra {

    List<Pedido> lista;
    String  monto;
    String  ubicacion;
    String  email;
    String  fecha;

    public Compra(List<Pedido> lista, String monto, String ubicacion, String email, String fecha) {
        this.lista = lista;
        this.monto = monto;
        this.ubicacion = ubicacion;
        this.email = email;
        this.fecha = fecha;
    }

    public List<Pedido> getLista() {
        return lista;
    }

    public void setLista(List<Pedido> lista) {
        this.lista = lista;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
