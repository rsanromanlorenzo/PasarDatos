package com.example.robert.pasardatos;

import java.io.Serializable;

/**
 * Created by ROBERT on 06/12/2014.
 */
public class Persona implements Serializable {
    String nombre;
    int numero;

    public Persona() {
    }

    public Persona(String nombre, int numero) {
        this.nombre = nombre;
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
}
