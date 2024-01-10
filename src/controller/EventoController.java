package controller;

import java.net.URL;
import java.util.ResourceBundle;

import dao.OlimpiadasDao;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import model.Evento;
import model.Participacion;

public class EventoController implements Initializable{

    @FXML
    private ChoiceBox<String> cbBusqueda;

    @FXML
    private Menu mAyuda;

    @FXML
    private Menu mAñadir;

    @FXML
    private TableColumn<Evento, String> tcDeporte;

    @FXML
    private TableColumn<Evento, String> tcNombre;

    @FXML
    private TableColumn<Evento, String> tcOlimpiada;

    @FXML
    private TextField tfBusqueda;

    @FXML
    private TableView<Evento> tvTabla;
    
    // VARIABLES DE CLASE INSERTADAS MANUALMENTE \\
    OlimpiadasDao oDao = new OlimpiadasDao();
    private String[]campos = {"Evento","Olimpiada","Deporte"};

    @FXML
    void aniadirDeporte(ActionEvent event) {

    }

    @FXML
    void aniadirEvento(ActionEvent event) {

    }

    @FXML
    void aniadirOlimpiada(ActionEvent event) {

    }

    @FXML
    void filtrar(KeyEvent event) {

    }

    @FXML
    void ventanaAyuda(ActionEvent event) {

    }

    /**
     * Carga los registros de la base de datos mediante el evento cargarTabla() y añade valores al ChoiceBox de busqueda.
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cbBusqueda.getItems().addAll(campos);
		ObservableList<Evento>eventos = oDao.cargarEvento();
		cargarTabla(eventos);		
	}

	/**
	 * Vincula los campos de la tabla y cambia los registros de la tabla con la lista que se pone de parámetro. 
	 * @param eventos lista de eventos.
	 */
	private void cargarTabla(ObservableList<Evento>eventos) {
		tcNombre.setCellValueFactory(new PropertyValueFactory<Evento, String>("nomEvento"));
		tcOlimpiada.setCellValueFactory(new PropertyValueFactory<Evento, String>("nomOlimpiada"));
		tcDeporte.setCellValueFactory(new PropertyValueFactory<Evento, String>("nomDeporte"));		
		
		tvTabla.setItems(eventos);
	}
}
