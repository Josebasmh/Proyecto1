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
import javafx.scene.control.Alert;
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
import model.Deportista;
import model.Equipo;
import model.Evento;
import model.Participacion;

public class TablaGeneralController implements Initializable{

    @FXML
    private ChoiceBox<String> cbBusqueda;
    
    @FXML
    private ContextMenu cmTabla;
    
    @FXML
    private Menu mAyuda;

    @FXML
    private Menu mAñadir;

    @FXML
    private Menu mTablas;
    
    @FXML
    private MenuItem miEliminar;
    
    @FXML
    private MenuItem miModificar;
    
    @FXML
    private MenuItem miModificarEquipo;

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
    static Participacion pModificar;
    static Equipo gEquipoModificar;

    /**
     * Método para iniciar la tabla Deportista
     * @param event
     */
    @FXML
    void abrirDeportista(ActionEvent event) {
    	ventanaSecundaria("VentanaDeportista", "DEPORTISTAS",600,800);
    }

    /**
     *Método para iniciar la tabla Evento
     * @param event
     */
    @FXML
    void abrirEvento(ActionEvent event) {
    	ventanaSecundaria("VentanaEvento", "EVENTOS",600,800);
    }

    /**
     * Método para iniciar la tabla Olimpiada
     * @param event
     */
    @FXML
    void abrirOlimpiada(ActionEvent event) {
    	ventanaSecundaria("VentanaOlimpiada", "OLIMPIADAS",600,800);
    }
    
    /**
     * Inicia la ventana AñadirDeporte.
     * @param event
     */
    @FXML
    void aniadirDeporte(ActionEvent event) {
    	ventanaSecundaria("VentanaAñadirDeporte", "AÑADIR DEPORTE", 200, 450);
    }

    /**
     * Inicia la ventana Añadir Deportista.
     * @param event
     */
    @FXML
    void aniadirDeportista(ActionEvent event) {
    	ventanaSecundaria("VentanaAñadirDeportista", "AÑADIR DEPORTISTA", 450, 500);
    }

    /**
     * Inicia la ventana Añadir Equipo.
     * @param event
     */
    @FXML
    void aniadirEquipo(ActionEvent event) {
    	ventanaSecundaria("VentanaAñadirEquipo", "AÑADIR EQUIPO", 275, 475);
    }

    /**
     * Inicia la ventana Añadir Evento.
     * @param event
     */
    @FXML
    void aniadirEvento(ActionEvent event) {
    	ventanaSecundaria("VentanaAñadirEvento", "AÑADIR EVENTO", 310, 450);
    }

    /**
     * Inicia la ventana Añadir Olimpiada.
     * @param event
     */
    @FXML
    void aniadirOlimpiada(ActionEvent event) {
    	ventanaSecundaria("VentanaAñadirOlimpiada", "AÑADIR OLIMPIADA", 300, 450);
    }

    @FXML
    void aniadirParticipacion(ActionEvent event) {
    	ventanaSecundaria("VentanaAñadirParticipacion", "AÑADIR PARTICIPACION", 500, 450);
    	ObservableList<Participacion>participaciones = oDao.cargarParticipacion();
    	cargarTabla(participaciones);
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

    /**
     * Modifica la participación seleccionada en la tabla. 
     * @param event
     */
    @FXML
    void modificar(ActionEvent event) {    		
    		pModificar = tvTabla.getSelectionModel().getSelectedItem();    		
    		ventanaSecundaria("VentanaAñadirParticipacion", "MODIFICAR PARTICIPACION", 500, 450);
    		pModificar=null;
    		ObservableList<Participacion>participaciones = oDao.cargarParticipacion();
    		tvTabla.setItems(participaciones);		
    }
    
    @FXML
    void modificarEquipo(ActionEvent event) {
    	gEquipoModificar = oDao.filtrarEquipo("nombre", tvTabla.getSelectionModel().getSelectedItem().getNomEquipo()).get(0);    	
		ventanaSecundaria("VentanaAñadirEquipo", "MODIFICAR EQUIPO", 380, 460);
		gEquipoModificar =null;  
    }
    
    /**
     * Elimina la participación y los hijos/nietos sin vinculación.
     * @param event
     */
    @FXML
    void eliminar(ActionEvent event) {
    	Participacion p = tvTabla.getSelectionModel().getSelectedItem();
    	boolean resultado = oDao.eliminarParticipacion(p);
    	if (resultado) {
    		TablaGeneralController.ventanaAlerta("I", "Participación eliminada con éxito");
    		Integer contD = oDao.buscarRegistros("Participacion","id_deportista",p.getIdDeportista());
    		Integer contEq = oDao.buscarRegistros("Participacion","id_equipo",p.getIdEquipo());
    		Integer contEv = oDao.buscarRegistros("Participacion","id_equipo",p.getIdEvento());
    		
    		if (contEq==0) {
    			oDao.eliminar("Equipo", "id_equipo", p.getIdEquipo());
    			TablaGeneralController.ventanaAlerta("I", "El equipo se eliminó al eliminar el último registro vinculado");
    		}
    		if(contD==0) {
    			oDao.eliminar("Deportista", "id_deportista", p.getIdDeportista());
    			TablaGeneralController.ventanaAlerta("I", "El deportista se eliminó al eliminar el último registro vinculado");
    		}
    		if(contEv==0) {
    			Evento ev = oDao.filtrarEvento("id_evento", p.getIdEvento()+"").get(0);
    			Integer contDe= oDao.buscarRegistros("Evento", "id_deporte", ev.getIdDeporte());
    			Integer contOl= oDao.buscarRegistros("Evento", "id_olimpiada", ev.getIdOlimpiada());
    			
    			oDao.eliminar("Evento", "id_evento", p.getIdEvento());
    			TablaGeneralController.ventanaAlerta("I", "El evento se eliminó al eliminar el último registro vinculado");
    			
    			if(contDe==0) {
    				oDao.eliminar("Deporte", "id_deporte", ev.getIdDeporte());
        			TablaGeneralController.ventanaAlerta("I", "El deporte se eliminó al eliminar el último registro vinculado");
    			}
    			if(contOl==0) {
    				oDao.eliminar("Evento", "id_evento", ev.getIdOlimpiada());
        			TablaGeneralController.ventanaAlerta("I", "La olimpiada se eliminó al eliminar el último registro vinculado");
    			}
    		}    		
    		
    	}else {
    		TablaGeneralController.ventanaAlerta("E", "ELIMINE TODOS LOS REGISTROS ASOCIADOS");
    	}
    	ObservableList<Participacion>participaciones = oDao.cargarParticipacion();
    	tvTabla.setItems(participaciones);
    	gEquipoModificar =null;
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
			stage.setMinWidth(anchura);
			stage.setMinHeight(altura);
			stage.setMaxWidth(anchura);
			stage.setMaxHeight(altura);
			stage.getIcons().add(new Image(getClass().getResource("/img/imgOlimpiadas.jpg").toString()));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Crea una alerta en pantalla que puede ser de tipo error (E) o de tipo información(I)
	 * @param tipoAlerta 'E','I'
	 * @param mensaje mensaje que se insertará en la alerta
	 */
	static void ventanaAlerta(String tipoAlerta, String mensaje) {
		Alert alert = null;
		switch (tipoAlerta) {
			case ("E"):
				alert = new Alert(Alert.AlertType.ERROR);
				break;
			case ("I"):
				alert = new Alert(Alert.AlertType.INFORMATION);
		}
        alert.setContentText(mensaje);
        alert.showAndWait();
	}
}
