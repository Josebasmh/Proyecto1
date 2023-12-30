package controller;

import java.net.URL;
import java.util.Iterator;
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
import model.Participacion;

public class TablaGeneralController implements Initializable{

    @FXML
    private ChoiceBox<String> cbBusqueda;

    @FXML
    private Menu mAyuda;

    @FXML
    private Menu mAñadir;

    @FXML
    private Menu mTablas;

    @FXML
    private TableColumn<Participacion, String> tcAbreviatura;

    @FXML
    private TableColumn<Participacion, String> tcDeporte;

    @FXML
    private TableColumn<Participacion, String> tcDeportista;

    @FXML
    private TableColumn<Participacion, Integer> tcEdad;

    @FXML
    private TableColumn<Participacion, String> tcEquipo;

    @FXML
    private TableColumn<Participacion, String> tcEvento;

    @FXML
    private TableColumn<Participacion, String> tcMedalla;

    @FXML
    private TableColumn<Participacion, String> tcOlimpiada;

    @FXML
    private TextField tfBusqueda;

    @FXML
    private TableView<Participacion> tvTabla;
    
    // VARIABLES DE CLASE INSERTADAS MANUALMENTE 
    private OlimpiadasDao oDao = new OlimpiadasDao();
    private String[]campos = {"Deportista","Evento","Olimpiada","Deporte","Equipo","Abreviatura","Edad","Medalla"};

    @FXML
    void abrirTabla(ActionEvent event) {

    }

    @FXML
    void aniadirDeporte(ActionEvent event) {

    }

    @FXML
    void aniadirDeportista(ActionEvent event) {

    }

    @FXML
    void aniadirEquipo(ActionEvent event) {

    }

    @FXML
    void aniadirEvento(ActionEvent event) {

    }

    @FXML
    void aniadirOlimpiada(ActionEvent event) {

    }

    @FXML
    void aniadirParticipacion(ActionEvent event) {

    }

    /**
     * Filtra los registros de la tabla dependiendo del ChoiceBox y el TextField
     * @param event
     */
    @FXML
    void filtrar(KeyEvent event) {

    	String campoSeleccionado = cbBusqueda.getSelectionModel().getSelectedItem();
    	String txFiltro = tfBusqueda.getText().toString();
    	System.out.println(txFiltro);
    	ObservableList<Participacion>listaFiltrada = oDao.filtrarParticipaciones(campoSeleccionado, txFiltro);
    	cargarTabla(listaFiltrada);
    }

    @FXML
    void ventanaAyuda(ActionEvent event) {

    	
    }

    /**
     * Cuando se inicia la ventana, añade todos los registros de la tabla Participacion de la bbdd en la tabla de la ventana y 
     * rellena el choicebox para seleccionar el campo que queremos filtrar.
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		cbBusqueda.getItems().addAll(campos);
		ObservableList<Participacion>participaciones = oDao.cargarParticipacion();
		cargarTabla(participaciones);
		cargarTabla(participaciones);
	}
	
	/**
	 * Método para cargar los datos de la bbdd en la tabla.
	 */
	private void cargarTabla(ObservableList<Participacion>participaciones) {

		tcAbreviatura.setCellValueFactory(new PropertyValueFactory<Participacion, String>("abreviaturaEquipo"));
		tcDeporte.setCellValueFactory(new PropertyValueFactory<Participacion, String>("nomDeporte"));
		tcDeportista.setCellValueFactory(new PropertyValueFactory<Participacion, String>("nomDeportista"));
		tcEdad.setCellValueFactory(new PropertyValueFactory<Participacion, Integer>("edad"));
		tcEquipo.setCellValueFactory(new PropertyValueFactory<Participacion, String>("nomEquipo"));
		tcEvento.setCellValueFactory(new PropertyValueFactory<Participacion, String>("nomEvento"));
		tcMedalla.setCellValueFactory(new PropertyValueFactory<Participacion, String>("medalla"));
		tcOlimpiada.setCellValueFactory(new PropertyValueFactory<Participacion, String>("nomOlimpiada"));
		
		tvTabla.setItems(participaciones);
	}
}
