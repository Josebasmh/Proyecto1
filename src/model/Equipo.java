package model;

public class Equipo {

	private int idEquipo;
	private String nombre,iniciales;
	
	/**
	 * Clase para crear un Equipo.
	 * @param idEq ID del Equipo
	 * @param nom Nombre
	 * @param ini Iniciales
	 */
	public Equipo(int idEq,String nom, String ini) {
		idEquipo = idEq;
		nombre = nom;
		iniciales = ini;
	}

	// METODOS GETTER Y SETTER
	
	public int getIdEquipo() {
		return idEquipo;
	}

	public void setIdEquipo(int idEquipo) {
		this.idEquipo = idEquipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIniciales() {
		return iniciales;
	}

	public void setIniciales(String iniciales) {
		this.iniciales = iniciales;
	}

	@Override
	public String toString() {
		return nombre;
	}
	
}
