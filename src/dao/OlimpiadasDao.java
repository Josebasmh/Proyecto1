package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexion.ConexionBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Deporte;
import model.Deportista;
import model.Equipo;
import model.Evento;
import model.Olimpiada;
import model.Participacion;

public class OlimpiadasDao {

	private ConexionBD conexion;
	
	// ESTAS CONSULTAS ESTÁN SIN ; AL FINAL PARA UTILIZARLAS EN DISTINTOS PROCESOS AÑADIENDOLES MÁS FILTROS \\
	private String consultaPrincipal = "SELECT Deportista.id_deportista,Evento.id_evento,Equipo.id_equipo,Deportista.nombre,Evento.nombre,Olimpiada.nombre,Deporte.nombre,Equipo.nombre,iniciales,edad,medalla "
			+ "FROM Participacion,Deportista,Evento,Olimpiada,Deporte,Equipo "
			+ "WHERE Deportista.id_deportista = Participacion.id_deportista "
			+ "AND Equipo.id_equipo = Participacion.id_equipo "
			+ "AND Evento.id_evento = Participacion.id_evento "
			+ "AND Olimpiada.id_olimpiada = Evento.id_olimpiada "
			+ "AND Deporte.id_deporte = Evento.id_deporte";
	
	private String consultaDeportista = "SELECT id_deportista,nombre,sexo,peso,altura "
			+ "FROM Deportista";
	
	private String consultaEvento = "SELECT e.id_evento,e.id_olimpiada,e.id_deporte,e.nombre as nomevento,o.nombre as nomolimpiada,d.nombre as nomdeporte "
			+ "FROM Evento e "
			+ "LEFT JOIN Olimpiada o on o.id_olimpiada = e.id_olimpiada "
			+ "LEFT JOIN Deporte d on d.id_deporte = e.id_deporte";
	
	private String consultaOlimpiada = "SELECT id_olimpiada,nombre,anio,temporada,ciudad "
			+ "FROM Olimpiada";
	
	private String consultaDeporte = "SELECT id_deporte,nombre "
			+ "FROM Deporte";
	
	private String consultaEquipo = "SELECT id_equipo,nombre,iniciales "
			+ "FROM Equipo";
	
	// GENÉRICOS \\
	/**
	 * Genera un ID nuevo contando los registros de la tabla que le pasamos como parametro e incrementa la cantidad 1.
	 * @param tabla
	 * @return ID generado.
	 */
	public Integer generarId(String tabla) {
		Integer nId = 0;
		String consulta = "SELECT COUNT(*) FROM " + tabla + ";";
		try {
			conexion = new ConexionBD();
			PreparedStatement ps = conexion.getConexion().prepareStatement(consulta);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				nId = rs.getInt(0);
			}
			conexion.CloseConexion();
		} catch (SQLException e) {}
		
		return nId;
	}
	
	// PARTICIPACION \\
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
	 * Añade a la BBDD la participación del parámetro.
	 * @param p Participacion
	 * @return true(añadido con éxito) / false(error al añadir).
	 */
	public boolean aniadirParticipacion(Participacion p) {
		String consulta = "INSERT INTO Participacion VALUES ("+p.getIdDeportista()+",'"+p.getIdEvento()+"','"+p.getIdEquipo()+"',"+p.getEdad()+",'"+p.getMedalla()+"');";
		try {
			conexion = new ConexionBD();
			PreparedStatement ps = conexion.getConexion().prepareStatement(consulta);
			int i = ps.executeUpdate(consulta);
			conexion.CloseConexion();
			return true;
		} catch (SQLException e) {
			return false;
		}
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
	
	// DEPORTISTA \\
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
	 * Método que añade una condición de busqueda SQL(WHERE) a la consulta genérica de Deportista y llama a CrearListaDeportista()
	 * para ejecutarla.
	 * @param campoSeleccionado columna que se ejecutara el filtro.
	 * @param txFiltro el valor que se quiere buscar.
	 * @return lista de deportistas.
	 */
	public ObservableList<Deportista> filtrarDeportista(String campoSeleccionado, String txFiltro) {
		ObservableList<Deportista> listaDeportista = FXCollections.observableArrayList();
		String consultaModificada = consultaDeportista + " WHERE "+campoSeleccionado+" LIKE '%"+txFiltro+"%';";
		listaDeportista = crearListaDeportista(consultaModificada);
		return listaDeportista;
	}

	/**
	 * Añade a la BBDD el deportista del parámetro.
	 * @param d deportista
	 * @return true(añadido con éxito) / false(error al añadir).
	 */
	public boolean aniadirDeportista(Deportista d) {
		String consulta = "INSERT INTO Deportista VALUES ("+d.getIdDeportista()+",'"+d.getNombre()+"','"+d.getSexo()+"',"+d.getPeso()+","+d.getAltura()+");";
		try {
			conexion = new ConexionBD();
			PreparedStatement ps = conexion.getConexion().prepareStatement(consulta);
			int i = ps.executeUpdate(consulta);
			conexion.CloseConexion();
			return true;
		} catch (SQLException e) {
			return false;
		}
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
				Character cSexo = rs.getString("sexo").toCharArray()[0];
				Integer nPeso = rs.getInt("peso");
				Integer nAltura = rs.getInt("altura");
				Deportista d = new Deportista(nId, sNombre, cSexo, nPeso, nAltura);
				listaDeportista.add(d);
			}
			conexion.CloseConexion();
		}catch(SQLException e) {}		
		return listaDeportista;
	}

	// EVENTO \\
	/**
	 * Carga todos los registros de la tabla Evento.
	 * @return lista de eventos.
	 */
	public ObservableList<Evento> cargarEvento() {
		ObservableList<Evento> listaEvento= FXCollections.observableArrayList();
		String consultaModificada =consultaEvento + ";";
		listaEvento = crearListaEvento(consultaModificada);
		return listaEvento;
		
	}
	/**
	 * Filtra los datos de evento dependiendo del campo seleccionado en el ChoiceBox y del valor del TextField.
	 * @param campoSeleccionado
	 * @param txFiltro
	 * @return
	 */
	public ObservableList<Evento> filtrarEvento(String campoSeleccionado, String txFiltro) {
		ObservableList<Evento> listaEvento = FXCollections.observableArrayList();
		String consultaModificada = consultaEvento + " WHERE "+campoSeleccionado+" LIKE '%"+txFiltro+"%';";
		listaEvento = crearListaEvento(consultaModificada);
		return listaEvento;
	}
	
	/**
	 * Añade a la BBDD el evento del parámetro.
	 * @param ev Evento.
	 * @return true(añadido con éxito) / false(error al añadir).
	 */
	public boolean aniadirEvento(Evento ev) {
		String consulta = "INSERT INTO Evento VALUES ("+ev.getIdEvento()+",'"+ev.getNomEvento()+"','"+ev.getIdOlimpiada()+"',"+ev.getIdDeporte()+");";
		try {
			conexion = new ConexionBD();
			PreparedStatement ps = conexion.getConexion().prepareStatement(consulta);
			int i = ps.executeUpdate(consulta);			
			conexion.CloseConexion();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	/**
	 *Crea una lista de eventos con la consulta pasada como parametro. 
	 * @param consulta
	 * @return lista de eventos.
	 */
	private ObservableList<Evento> crearListaEvento(String consulta) {
		ObservableList<Evento> listaEvento= FXCollections.observableArrayList();
		try {
			conexion = new ConexionBD();
			PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int nIdEvento = rs.getInt("id_evento");
				Integer nIdOlimpiada = rs.getInt("id_olimpiada");
				Integer nIdDeporte= rs.getInt("id_deporte");
				String sNomEvento = rs.getString("nomevento");
				String sNomOlimpiada = rs.getString("nomolimpiada");
				String sNomDeporte = rs.getString("nomdeporte");
				Evento e = new Evento(nIdEvento, nIdOlimpiada, nIdDeporte, sNomEvento, sNomOlimpiada, sNomDeporte);
				listaEvento.add(e);
			}
			conexion.CloseConexion();
		}catch(SQLException e) {e.printStackTrace();}		
		return listaEvento;		
	}
	
	// OLIMPIADA \\
	
	/**
	 * Carga todos los registros de la tabla Olimpiada.
	 * @return lista de olimpiadas.
	 */
	public ObservableList<Olimpiada> cargarOlimpiada(){
		ObservableList<Olimpiada> listaOlimpiada= FXCollections.observableArrayList();
		String consultaModificada = consultaOlimpiada + ";";
		listaOlimpiada= crearListaOlimpiada(consultaModificada);
		return listaOlimpiada;
	}

	/**
	 * Filtra los datos de olimpiada dependiendo del campo seleccionado en el ChoiceBox y del valor del TextField.
	 * @param campoSeleccionado
	 * @param txFiltro valor del campo.
	 * @return lista de olimpiadas.
	 */
	public ObservableList<Olimpiada> filtrarOlimpiada(String campoSeleccionado, String txFiltro) {
		ObservableList<Olimpiada> listaOlimpiada = FXCollections.observableArrayList();
		if (campoSeleccionado.equals("Año")) {campoSeleccionado = "anio";}
		String consultaModificada = consultaOlimpiada + " WHERE "+campoSeleccionado+" LIKE '%"+txFiltro+"%';";
		listaOlimpiada = crearListaOlimpiada(consultaModificada);
		return listaOlimpiada;
	}
	
	/**
	 * Añade a la BBDD la olimpiada del parámetro.
	 * @param o
	 * @return true(añadido con éxito) / false(error al añadir).
	 */
	public boolean aniadirOlimpiada(Olimpiada o) {
		String consulta = "INSERT INTO Olimpiada VALUES ("+o.getIdOlimpiada()+",'"+o.getNombre()+"',"+o.getAnio()+",'"+o.getTemporada()+"','"+o.getCiudad()+"');";
		try {
			conexion = new ConexionBD();
			PreparedStatement ps = conexion.getConexion().prepareStatement(consulta);
			int i = ps.executeUpdate(consulta);			
			conexion.CloseConexion();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Crea una lista de Olimpiadas con la consulta pasada como parametro. 
	 * @param consulta
	 * @return lista de olimpiadas.
	 */
	private ObservableList<Olimpiada> crearListaOlimpiada(String consulta) {
		ObservableList<Olimpiada> listaOlimpiada= FXCollections.observableArrayList();
		try {
			conexion = new ConexionBD();
			PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int nIdOlimpiada = rs.getInt("id_olimpiada");
				String sNombre = rs.getString("nombre");
				Integer nAnio = rs.getInt("anio");
				String sTemporada = rs.getString("temporada");
				String sCiudad = rs.getString("ciudad");
				Olimpiada o = new Olimpiada(nIdOlimpiada, sNombre, nAnio, sTemporada, sCiudad);
				listaOlimpiada.add(o);
			}
			conexion.CloseConexion();
		}catch(SQLException e) {e.printStackTrace();}		
		return listaOlimpiada;
	}

	// DEPORTE \\
	
	/**
	 * Carga todos los registros de la tabla deporte.
	 * @return lista de deportes.
	 */
	public ObservableList<Deporte> cargarDeporte() {
		ObservableList<Deporte> listaDeporte= FXCollections.observableArrayList();
		String consultaModificada = consultaDeporte + ";";
		listaDeporte= crearListaDeporte(consultaModificada);
		return listaDeporte;
		
	}

	/**
	 * Crea una lista de deportes con la consulta pasada como parametro. 
	 * @param consulta
	 * @return lista de deportes.
	 */
	private ObservableList<Deporte> crearListaDeporte(String consulta) {
		ObservableList<Deporte> listaDeporte= FXCollections.observableArrayList();
		try {
			conexion = new ConexionBD();
			PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int nIdDeporte = rs.getInt("id_deporte");
				String sNombre = rs.getString("nombre");
				Deporte d = new Deporte(nIdDeporte, sNombre);
				listaDeporte.add(d);
			}
			conexion.CloseConexion();
		}catch(SQLException e) {}		
		return listaDeporte;
	}

	// EQUIPO \\
	
	/**
	 * Añade a la BBDD la olimpiada del parámetro.
	 * @param eq
	 * @return true(añadido con éxito) / false(error al añadir).
	 */
	public boolean aniadirEquipo(Equipo eq) {
		String consulta = "INSERT INTO Equipo VALUES ("+eq.getIdEquipo()+",'"+eq.getNombre()+"','"+eq.getIniciales()+"');";
		try {
			conexion = new ConexionBD();
			PreparedStatement ps = conexion.getConexion().prepareStatement(consulta);
			int i = ps.executeUpdate(consulta);			
			conexion.CloseConexion();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	public ObservableList<Equipo> cargarEquipo() {
		ObservableList<Equipo> listaEquipo= FXCollections.observableArrayList();
		String consultaModificada = consultaEquipo+ ";";
		listaEquipo = crearListaEquipo(consultaModificada);
		return listaEquipo;
	}

	
	private ObservableList<Equipo> crearListaEquipo(String consulta) {
		ObservableList<Equipo> listaDeporte= FXCollections.observableArrayList();
		try {
			conexion = new ConexionBD();
			PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int nIdDeporte = rs.getInt("id_equipo");
				String sNombre = rs.getString("nombre");
				String sIniciales = rs.getString("iniciales");
				Equipo e = new Equipo(nIdDeporte, sNombre, sIniciales);
				listaDeporte.add(e);
			}
			conexion.CloseConexion();
		}catch(SQLException e) {}		
		return listaDeporte;
	}

	// DEPORTE \\

	/**
	 * Añade a la BBDD el deporte del parámetro.
	 * @param d Deporte
	 * @return true(añadido con éxito) / false(error al añadir).
	 */
	public boolean aniadirDeporte(Deporte d) {
		String consulta = "INSERT INTO Deporte VALUES ("+d.getIdDeporte()+",'"+d.getNombre()+"');";
		try {
			conexion = new ConexionBD();
			PreparedStatement ps = conexion.getConexion().prepareStatement(consulta);
			int i = ps.executeUpdate(consulta);			
			conexion.CloseConexion();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
}
