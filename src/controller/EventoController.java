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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Deporte;
import model.Deportista;
import model.Evento;

public class EventoController implements Initializable{

    @FXML
    private ChoiceBox<String> cbBusqueda;
    
    @FXML
    private ContextMenu cmTabla;

    @FXML
    private Menu mAñadir;
    
    @FXML
    private MenuItem miEliminar;

    @FXML
    private MenuItem miModificar;

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
    private String[]campos = {"Nombre","Olimpiada","Deporte"};
    static Evento gEveModificar;
    static Deporte gDepModificar;

    @FXML
    void aniadirDeporte(ActionEvent event) {
    	ventanaSecundaria("VentanaAñadirDeporte", "AÑADIR DEPORTE", 450, 190);
    }

    /**
     * Abre la ventana para añadir un evento.
     * @param event
     */
    @FXML
    void aniadirEvento(ActionEvent event) {
    	ventanaSecundaria("VentanaAñadirEvento", "AÑADIR EVENTO", 380, 460);
    }
    
    /**
     * Abre la ventana para añadir una olimpiada.
     * @param event
     */
    @FXML
    void aniadirOlimpiada(ActionEvent event) {
    	ventanaSecundaria("VentanaAñadirOlimpiada", "AÑADIR OLIMPIADA", 380, 460);
    }

    /**
     * Filtra los registros de la tabla dependiendo del campo  del CoiceBox y el valor insertado 
     * en el TextField. 
     * @param event
     */
    @FXML
    void filtrar(KeyEvent event) {

    	String campoSeleccionado = cbBusqueda.getSelectionModel().getSelectedItem();
    	switch (campoSeleccionado){
    	case "Nombre":
    		campoSeleccionado = "e.nombre";
    		break;
    	case "Olimpiada":
    		campoSeleccionado = "o.nombre";
    		break;
    	case "Deporte":
    		campoSeleccionado = "d.nombre";
    		break;
    	}
    	String txFiltro = tfBusqueda.getText().toString();    	
    	ObservableList<Evento>listaFiltrada = oDao.filtrarEvento(campoSeleccionado, txFiltro);
    	cargarTabla(listaFiltrada);
    }
    
    /**
     * Obtiene el evento de la tabla seleccionado y lo pasa a la ventana hija. Actualiza la tabla y reinicia
     * el evento.
     * @param event
     */
    @FXML
    void modificar(ActionEvent event) {
    	gEveModificar = tvTabla.getSelectionModel().getSelectedItem();
    	ventanaSecundaria("VentanaAñadirEvento", "MODIFICAR EVENTO", 380, 460);
		ObservableList<Evento>listaEventos= oDao.cargarEvento();
		tvTabla.setItems(listaEventos);
		gEveModificar=null;
		ObservableList<Evento>eventos = oDao.cargarEvento();
		cargarTabla(eventos);
    }
    
    @FXML
    void modificarDeporte(ActionEvent event) {
    	gDepModificar = oDao.filtrarDeporte("nombre", tvTabla.getSelectionModel().getSelectedItem().getNomDeporte()).get(0);
    	ventanaSecundaria("VentanaAñadirDeporte", "MODIFICAR DEPORTE", 450, 190);
		gDepModificar=null;
		ObservableList<Evento>eventos = oDao.cargarEvento();
		cargarTabla(eventos);
		
    }
    
    /**
     * Elimina el evento y los hijos sin vinculación.
     * @param event
     */
    @FXML
    void eliminar(ActionEvent event) {
    	Evento ev = tvTabla.getSelectionModel().getSelectedItem();
    	oDao.eliminar("Evento", "id_evento", ev.getIdEvento());
    	
    	Integer contEv= oDao.buscarRegistros("Evento", "id_evento", ev.getIdEvento());
		Integer contDe= oDao.buscarRegistros("Evento", "id_deporte", ev.getIdDeporte());
		Integer contOl= oDao.buscarRegistros("Evento", "id_olimpiada", ev.getIdOlimpiada());
		
		if (contEv==0) {
			TablaGeneralController.ventanaAlerta("I", "Evento eliminado con éxito");
			if(contDe==0) {
				oDao.eliminar("Deporte", "id_deporte", ev.getIdDeporte());
				TablaGeneralController.ventanaAlerta("I", "El deporte se eliminó al eliminar el último registro vinculado");
			}
			if(contOl==0) {
				oDao.eliminar("Evento", "id_evento", ev.getIdOlimpiada());
				TablaGeneralController.ventanaAlerta("I", "La olimpiada se eliminó al eliminar el último registro vinculado");
			}
		}else {
			TablaGeneralController.ventanaAlerta("E", "No se pudo eliminar el evento");
		}	
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
	 * Abrir ventana auxiliar.
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
