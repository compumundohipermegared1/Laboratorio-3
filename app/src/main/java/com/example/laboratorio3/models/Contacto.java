package com.example.laboratorio3.models;

import java.io.Serializable;

public class Contacto implements Serializable {

    Long id;
    String nombre;
    String paterno;
    String materno;
    String telefono;
    int sexo;  // 1 masculino, 0 femenino

    public Contacto() {	}

    public Contacto(long id, String nombre, String apellido_p, String apellido_m, String telefono, int sexo) {
        this.setId(id);
        this.setNombre(nombre);
        this.setPaterno(paterno);
        this.setMaterno(materno);
        this.setTelefono(telefono);
        this.setSexo(sexo);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getSexo() {
        return sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return "Contacto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", paterno='" + paterno + '\'' +
                ", materno='" + materno + '\'' +
                ", telefono='" + telefono + '\'' +
                ", sexo=" + sexo +
                '}';
    }
}
