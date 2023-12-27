package model;

public class Evento {

	private int idEvento;
	private String nombre;
	private Integer idOlimpiada, idDeporte;
	
	/**
	 * Clase para crear un evento.
	 * @param idEve ID del evento
	 * @param nom nombre
	 * @param idOli ID de la olimpiada asociada
	 * @param idDep ID del deporte asociado
	 */
	public Evento(int idEve, String nom, Integer idOli, Integer idDep) {
		idEvento = idEve;
		nombre = nom;
		idOlimpiada = idOli;
		idDeporte = idDep;
	}

	// METODOS GETTERS Y SETTERS
	
	public int getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getIdOlimpiada() {
		return idOlimpiada;
	}

	public void setIdOlimpiada(Integer idOlimpiada) {
		this.idOlimpiada = idOlimpiada;
	}

	public Integer getIdDeporte() {
		return idDeporte;
	}

	public void setIdDeporte(Integer idDeporte) {
		this.idDeporte = idDeporte;
	}
}
