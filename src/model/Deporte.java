package model;

public class Deporte {

	private int idDeporte;
	private String nombre;
	
	public Deporte(int idDep, String nom) {
		idDeporte = idDep;
		nombre = nom;
	}

	// METODOS GETTERS Y SETTERS
	
	public int getIdDeporte() {
		return idDeporte;
	}

	public void setIdDeporte(int idDeporte) {
		this.idDeporte = idDeporte;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
