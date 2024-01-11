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
import model.Deportista;
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
    	ventanaSecundaria("VentanaAñadirEvento", "AÑADIR EVENTO", 380, 460);
    }

    @FXML
    void aniadirOlimpiada(ActionEvent event) {

    }

    @FXML
    void filtrar(KeyEvent event) {

    	String campoSeleccionado = cbBusqueda.getSelectionModel().getSelectedItem();
    	String txFiltro = tfBusqueda.getText().toString();    	
    	ObservableList<Evento>listaFiltrada = oDao.filtrarEvento(campoSeleccionado, txFiltro);
    	cargarTabla(listaFiltrada);
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
	
	/**
	 * 
	 * @param f fxml
	 * @param t titulo
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
