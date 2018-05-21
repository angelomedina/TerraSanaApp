package com.example.katty.terrasana.objetos;

public class Carrito {

    String  email;
    String  producto;
    String  precio;
    int     catidad;
    Boolean estado;

    public Carrito(String email, String producto, String precio, int catidad, Boolean estado) {
        this.email = email;
        this.producto = producto;
        this.precio = precio;
        this.catidad = catidad;
        this.estado = estado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public int getCatidad() {
        return catidad;
    }

    public void setCatidad(int catidad) {
        this.catidad = catidad;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
