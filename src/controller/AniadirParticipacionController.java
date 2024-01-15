package controller;

import java.net.URL;
import java.util.ResourceBundle;

import dao.OlimpiadasDao;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Deportista;
import model.Equipo;
import model.Evento;
import model.Participacion;

public class AniadirParticipacionController implements Initializable{

    @FXML
    private ChoiceBox<Deportista> cbDeportista;

    @FXML
    private ChoiceBox<Equipo> cbEquipo;

    @FXML
    private ChoiceBox<Evento> cbEvento;

    @FXML
    private ChoiceBox<String> cbMedalla;

    @FXML
    private TextField tfEdad;
    
    private OlimpiadasDao oDao = new OlimpiadasDao();
    private String[] medallas = {"Gold","Silver","Bronze","N/A"};
    private boolean modificar=false;

    /**
     * Inserta los datos de la ventana en una participación y añadirá/modificará dependiendo desde donde se haya abierto la ventana. 
     * @param event
     */
    @FXML
    void Aceptar(ActionEvent event) {
    	try {
        	Deportista d = cbDeportista.getSelectionModel().getSelectedItem();
        	Equipo eq = cbEquipo.getSelectionModel().getSelectedItem();
        	Evento ev = cbEvento.getSelectionModel().getSelectedItem();
        	Integer nEdad = Integer.parseInt(tfEdad.getText());
        	String sMedalla = cbMedalla.getSelectionModel().getSelectedItem();
        	
        	Participacion p = new Participacion(d, ev, eq, nEdad, sMedalla);
        	boolean resultado;
        	if (modificar) {
        		resultado = oDao.modificarParticipacion(p);
        		if (resultado) {
            		TablaGeneralController.ventanaAlerta("I", "Participación modificada con éxito");
            		Cancelar(event);
            	}else {
            		TablaGeneralController.ventanaAlerta("E", "Error al modificar participación");
            	}
        	}else {
        		resultado = oDao.aniadirParticipacion(p);
        		if (resultado) {
            		TablaGeneralController.ventanaAlerta("I", "Participación añadida con éxito");
            		Cancelar(event);
            	}else {
            		TablaGeneralController.ventanaAlerta("E", "Error al añadir participación");
            	}
        	}	
    	}catch(NumberFormatException e) {
    		TablaGeneralController.ventanaAlerta("E", "Inserte un valor entero positivo en edad");
    	}	
    }

    /**
     * Cierra la ventana actual.
     * @param event
     */
    @FXML
    void Cancelar(ActionEvent event) {    	
    	Node node = (Node)event.getSource();
    	Stage stage = (Stage) node.getScene().getWindow();
    	stage.close();
    }

    /**
     * Al iniciar, carga los deportistas, eventos, equipos y medallas de la BBDD y los añade a sus respectivos ChoiceBox.
     * También controla si se va a añadir o modificar en la BBDD.
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<Deportista> listaDeportistas = oDao.cargarDeportista();
		ObservableList<Equipo> listaEquipos = oDao.cargarEquipo();
		ObservableList<Evento> listaEventos = oDao.cargarEvento();
		
		cbDeportista.getItems().addAll(listaDeportistas);
		cbEquipo.getItems().addAll(listaEquipos);
		cbEvento.getItems().addAll(listaEventos);
		cbMedalla.getItems().addAll(medallas);
		
		try {
			Participacion p = TablaGeneralController.pModificar;
			modificar=true;
			mostrarDatosModificar(p);
		}catch(Exception e) {}		
	}

	/**
	 * Carga los datos de la participacion pasada como parámetro en la ventana. Desactiva los ChoiceBox de deportista y evento (Clave primaria compuesta). 
	 * @param p
	 */
	private void mostrarDatosModificar(Participacion p) {
		ObservableList<Deportista>listaDeportista = oDao.filtrarDeportista("id_deportista", p.getIdDeportista()+"");
		ObservableList<Evento>listaEvento = oDao.filtrarEvento("id_evento", p.getIdEvento()+"");
		ObservableList<Equipo>listaEquipo = oDao.filtrarEquipo("id_equipo", p.getIdEquipo()+"");
					
		Deportista d = listaDeportista.get(0);
		Evento ev = listaEvento.get(0);
		Equipo eq= listaEquipo.get(0);
		
		cbDeportista.getSelectionModel().select(d);
		cbEvento.getSelectionModel().select(ev);
		cbEquipo.getSelectionModel().select(eq);
		tfEdad.setText(p.getEdad().toString());
		cbMedalla.getSelectionModel().select(p.getMedalla());
		
		cbDeportista.setDisable(true);
		cbEvento.setDisable(true);
	}
}