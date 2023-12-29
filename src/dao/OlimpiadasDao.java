package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexion.ConexionBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Participacion;

public class OlimpiadasDao {

	private ConexionBD conexion;
	
	public ObservableList<Participacion> cargarParticipacion() {
		ObservableList<Participacion>listaParticipacion= FXCollections.observableArrayList();
		try {
			conexion = new ConexionBD();
			String consulta = "SELECT Deportista.id_deportista,Evento.id_evento,Equipo.id_equipo,Deportista.nombre,Evento.nombre,Olimpiada.nombre,Deporte.nombre,Equipo.nombre,iniciales,edad,medalla "
					+ "FROM Participacion,Deportista,Evento,Olimpiada,Deporte,Equipo "
					+ "WHERE Deportista.id_deportista = Participacion.id_deportista "
					+ "AND Equipo.id_equipo = Participacion.id_equipo "
					+ "AND Evento.id_evento = Participacion.id_evento "
					+ "AND Olimpiada.id_olimpiada = Evento.id_olimpiada "
					+ "AND Deporte.id_deporte = Evento.id_deporte;";
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaParticipacion;
	}
}
