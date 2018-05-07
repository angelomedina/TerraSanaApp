package com.example.katty.terrasana.objetos;

public class Producto {

    String nombre  ="";
    String icono   ="";
    String imagen1 ="";
    String imagen2 ="";
    String imagen3 ="";
    int    precio  =0;
    String unodad  ="";

    public Producto(String nombre, String icono, String imagen1, String imagen2, String imagen3, int precio, String unodad) {
        this.nombre = nombre;
        this.icono = icono;
        this.imagen1 = imagen1;
        this.imagen2 = imagen2;
        this.imagen3 = imagen3;
        this.precio = precio;
        this.unodad = unodad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public String getImagen1() {
        return imagen1;
    }

    public void setImagen1(String imagen1) {
        this.imagen1 = imagen1;
    }

    public String getImagen2() {
        return imagen2;
    }

    public void setImagen2(String imagen2) {
        this.imagen2 = imagen2;
    }

    public String getImagen3() {
        return imagen3;
    }

    public void setImagen3(String imagen3) {
        this.imagen3 = imagen3;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getUnodad() {
        return unodad;
    }

    public void setUnodad(String unodad) {
        this.unodad = unodad;
    }
}