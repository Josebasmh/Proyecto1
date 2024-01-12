package model;

public class Evento {

	private int idEvento;
	private String nomEvento,nomOlimpiada,nomDeporte;
	private Integer idOlimpiada, idDeporte;
	
	/**
	 * Clase para crear un evento.
	 * @param idEve ID del evento
	 * @param idOli ID de la olimpiada asociada
	 * @param idDep ID del deporte asociado
	 * @param nomEve nombre del evento
	 * @param nomOli nombre de la olimpiada
	 * @param nomDep noombre del deporte
	 */
	public Evento(int idEve, Integer idOli, Integer idDep, String nomEve, String nomOli, String nomDep) {
		idEvento = idEve;
		idOlimpiada = idOli;
		idDeporte = idDep;
		nomEvento = nomEve;
		nomOlimpiada = nomOli;
		nomDeporte = nomDep;		
	}

	// METODOS GETTERS Y SETTERS
	
	public int getIdEvento() {
		return idEvento;
	}

	public String getNomEvento() {
		return nomEvento;
	}

	public void setNomEvento(String nomEvento) {
		this.nomEvento = nomEvento;
	}

	public String getNomOlimpiada() {
		return nomOlimpiada;
	}

	public void setNomOlimpiada(String nomOlimpiada) {
		this.nomOlimpiada = nomOlimpiada;
	}

	public String getNomDeporte() {
		return nomDeporte;
	}

	public void setNomDeporte(String nomDeporte) {
		this.nomDeporte = nomDeporte;
	}

	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
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

	@Override
	public String toString() {
		return nomEvento;
	}
	
}
