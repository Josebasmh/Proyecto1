package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexion.ConexionBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Deportista;
import model.Participacion;

public class OlimpiadasDao {

	private ConexionBD conexion;
	
	// ESTAS CONSULTAS ESTÁ SIN ; AL FINAL PARA UTILIZARLA EN DISTINTOS PROCESOS AÑADIENDOLES MÁS FILTROS \\
	private String consultaPrincipal = "SELECT Deportista.id_deportista,Evento.id_evento,Equipo.id_equipo,Deportista.nombre,Evento.nombre,Olimpiada.nombre,Deporte.nombre,Equipo.nombre,iniciales,edad,medalla "
			+ "FROM Participacion,Deportista,Evento,Olimpiada,Deporte,Equipo "
			+ "WHERE Deportista.id_deportista = Participacion.id_deportista "
			+ "AND Equipo.id_equipo = Participacion.id_equipo "
			+ "AND Evento.id_evento = Participacion.id_evento "
			+ "AND Olimpiada.id_olimpiada = Evento.id_olimpiada "
			+ "AND Deporte.id_deporte = Evento.id_deporte";
	
	private String consultaDeportista = "SELECT id_deportista,nombre,sexo,peso,altura "
			+ "FROM Deportista";
	
	/**
	 * Carga todos los registros de la tabla Participacion.
	 * @return ObservableList con Participacion
	 */
	public ObservableList<Participacion> cargarParticipacion() {
		
		ObservableList<Participacion>listaParticipacion = FXCollections.observableArrayList();	
		String consultaModificada =consultaPrincipal + ";";
		listaParticipacion=crearListaParticipaciones(consultaModificada);	
		return listaParticipacion;
}
	
	/**
	 * Filtra los datos de participacion dependiendo del campo seleccionado en el ChoiceBox y del valor del TextField.
	 * @param campo Columna de la tabla seleccionada.
	 * @param valor El valor con el que quieres que coincidan los registros. 
	 * @return ObservableList con Participacion
	 */
	public ObservableList<Participacion> filtrarParticipaciones(String campo,String valor){
		
		ObservableList<Participacion>listaParticipacion= FXCollections.observableArrayList();
		if (!valor.equals("")) {
			String consultaModificada=consultaPrincipal;

			// DEPENDIENDO DEL CAMPO SELECCIONADO TIENE UNA SINTAXIS
			switch (campo){
			case "Edad","Medalla":
				consultaModificada += " AND Participacion." + campo + " LIKE '%" + valor + "%';";
				break;
			
			case "Abreviatura":
				consultaModificada += " AND Equipo.iniciales LIKE '%" + valor + "%';";
				break;
				
			case "Deportista","Evento","Olimpiada","Deporte","Equipo":
				consultaModificada += " AND " + campo + ".nombre LIKE '%" + valor + "%';";
				break;
			}
			listaParticipacion=crearListaParticipaciones(consultaModificada);			
		}else {
			listaParticipacion = cargarParticipacion();
		}
		return listaParticipacion;
	}
	
	/**
	 * Crea una lista de participaciones con la consulta pasada como parametro.
	 * @param consulta
	 * @return {@link ObservableList} Participacion
	 */
	private ObservableList<Participacion> crearListaParticipaciones (String consulta) {
		ObservableList<Participacion>listaParticipacion= FXCollections.observableArrayList();
		try {
			conexion = new ConexionBD();
			PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int nIdDeportista = rs.getInt("id_deportista");
				int nIdEvento = rs.getInt("id_evento");
				int nIdEquipo = rs.getInt("id_equipo");
				String sNomDeportista = rs.getString("Deportista.nombre");
				String sNomEvento = rs.getString("Evento.nombre");
				String sNomOlimpiada = rs.getString("Olimpiada.nombre");
				String sNomDeporte = rs.getString("Deporte.nombre");
				String sNomEquipo = rs.getString("Equipo.nombre");
				String sIniciales = rs.getString("iniciales");
				Integer nEdad = rs.getInt("edad");
				String sMedalla = rs.getString("medalla");
				Participacion p = new Participacion(nIdDeportista, nIdEvento, nIdEquipo, sNomDeportista, sNomEvento, sNomOlimpiada, sNomDeporte, sNomEquipo, sIniciales, nEdad, sMedalla);
				listaParticipacion.add(p);
			}
			conexion.CloseConexion();
		}catch(SQLException e) {
			
		}		
		return listaParticipacion;
	}
	
	/**
	 * Carga todos los registros de la tabla Deportista.
	 * @return {@link ObservableList} Deportista
	 */
	public ObservableList<Deportista> cargarDeportista(){
		ObservableList<Deportista> listaDeportista = FXCollections.observableArrayList();
		String consultaModificada =consultaDeportista + ";";
		listaDeportista = crearListaDeportista(consultaModificada);
		return listaDeportista;
	}

	/**
	 * Crea una lista de deportistas con la consulta pasada como parametro.
	 * @param consulta
	 * @return {@link ObservableList} Deportista
	 */
	private ObservableList<Deportista> crearListaDeportista(String consulta) {
		ObservableList<Deportista> listaDeportista = FXCollections.observableArrayList();
		try {
			conexion = new ConexionBD();
			PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int nId = rs.getInt("id_deportista");
				String sNombre = rs.getString("nombre");
				Character cSexo = rs.getCharacterStream("sexo").toString().toCharArray()[0];
				Integer nPeso = rs.getInt("peso");
				Integer nAltura = rs.getInt("altura");
				Deportista d = new Deportista(nId, sNombre, cSexo, nPeso, nAltura);
				listaDeportista.add(d);
			}
			conexion.CloseConexion();
		}catch(SQLException e) {e.printStackTrace();}		
		return listaDeportista;
	}
}
