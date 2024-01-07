package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dao.OlimpiadasDao;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    
    // VARIABLES DE CLASE INSERTADAS MANUALMENTE \\
    private OlimpiadasDao oDao = new OlimpiadasDao();
    private String[]campos = {"Deportista","Evento","Olimpiada","Deporte","Equipo","Abreviatura","Edad","Medalla"};

    /**
     * Método para iniciar la tabla Deportista
     * @param event
     */
    @FXML
    void abrirDeportista(ActionEvent event) {
    	ventanaSecundaria("VentanaDeportista", "DEPORTISTAS",800,600);
    }

    /**
     *Método para iniciar la tabla Evento
     * @param event
     */
    @FXML
    void abrirEvento(ActionEvent event) {
    	ventanaSecundaria("VentanaEvento", "EVENTOS",800,600);
    }

    /**
     * Método para iniciar la tabla Olimpiada
     * @param event
     */
    @FXML
    void abrirOlimpiada(ActionEvent event) {
    	ventanaSecundaria("VentanaOlimpiada", "OLIMPIADAS",800,600);
    }
    
    @FXML
    void aniadirDeporte(ActionEvent event) {

    }

    @FXML
    void aniadirDeportista(ActionEvent event) {
    	ventanaSecundaria("VentanaAñadirDeportista", "AÑADIR DEPORTISTA",450,500);
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
	
	/**
	 * Método para crear una ventana y ver una tabla secundaria.
	 * @param f fxml
	 * @param t Título de ventana
	 * @param altura
	 * @param anchura
	 */
	private void ventanaSecundaria(String f, String t,Integer altura,Integer anchura) {
		Stage stage = new Stage();
		try {
			FlowPane root = (FlowPane)FXMLLoader.load(getClass().getResource("/fxml/"+f+".fxml"));
			stage.setTitle(t);
			Scene scene = new Scene(root,altura,anchura);
			stage.setScene(scene);
			stage.setMinWidth(altura);
			stage.setMinHeight(anchura);
			stage.setMaxWidth(altura);
			stage.setMaxHeight(anchura);
			stage.getIcons().add(new Image(getClass().getResource("/img/imgOlimpiadas.jpg").toString()));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();	
		}catch(IOException e) {
			e.printStackTrace();
		}
	} 
}
