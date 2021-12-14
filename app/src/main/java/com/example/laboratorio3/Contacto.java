package com.example.laboratorio3;

public class Contacto 
{
    private int id;
	private String nombre;
	private String apellidos;
	private String telefono;
	private int sexo;  // 0 masculino, 1 femenino, 2 no binario
	
	public Contacto() {	}

	public Contacto(int id, String nombre, String apellidos, String telefono, int sexo) {
        this.setId(id);
        this.setNombre(nombre);
		this.setApellidos(apellidos);
		this.setTelefono(telefono);
		this.setSexo(sexo);
	}

    public int getId() { return id; }
    public void setId(int id) { this.id = id;   }
	public String toString() { return nombre; }
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
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
}
