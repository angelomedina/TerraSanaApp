package com.example.katty.terrasana.objetos;

public class Perfil {
    String nombre;
    String direccion;
    String telefono;
    String correoE;
    String usuario;

    public Perfil(String nombre, String direccion, String telefono, String correoE, String usuario) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correoE = correoE;
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoE() {
        return correoE;
    }

    public void setCorreoE(String correoE) {
        this.correoE = correoE;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
