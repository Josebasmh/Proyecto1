package model;

public class Deportista {

	private int idDeportista;
	private String nombre;
	private Character sexo;
	private Integer peso,altura;
	
	/**
	 * Clase para crear un deportista.
	 * @param idDep ID del deportista
	 * @param nom nombre
	 * @param sex sexo
	 * @param pes peso
	 * @param alt altura
	 */
	public Deportista(int idDep, String nom, Character sex, Integer pes, Integer alt) {
		idDeportista = idDep;
		nombre = nom;
		sexo = sex;
		peso = pes;
		altura = alt;
	}

	// METODOS GETTER Y SETTER
	
	public int getIdDeportista() {
		return idDeportista;
	}

	public void setIdDeportista(int idDeportista) {
		this.idDeportista = idDeportista;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Character getSexo() {
		return sexo;
	}

	public void setSexo(Character sexo) {
		this.sexo = sexo;
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}

	public Integer getAltura() {
		return altura;
	}

	public void setAltura(Integer altura) {
		this.altura = altura;
	}
	
	@Override
	public String toString() {
		return nombre;
	}
}
