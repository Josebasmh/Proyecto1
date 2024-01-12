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
import model.Deporte;
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

    @FXML
    void Aceptar(ActionEvent event) {
    	int nId = oDao.generarId("Participacion");
    	Deportista d = cbDeportista.getSelectionModel().getSelectedItem();
    	Equipo eq = cbEquipo.getSelectionModel().getSelectedItem();
    	Evento ev = cbEvento.getSelectionModel().getSelectedItem();
    	Integer nEdad = Integer.parseInt(tfEdad.getText());
    	String sMedalla = cbMedalla.getSelectionModel().getSelectedItem();
    	
    	Participacion p = new Participacion(d, ev, eq, nEdad, sMedalla);
    	boolean resultado = oDao.aniadirParticipacion(p);
    	if (resultado) {
    		TablaGeneralController.ventanaAlerta("I", "Participación añadida con éxito");
    		Cancelar(event);
    	}else {
    		TablaGeneralController.ventanaAlerta("E", "Error al añadir participación");
    	}
    }

    @FXML
    void Cancelar(ActionEvent event) {
    	Node node = (Node)event.getSource();
    	Stage stage = (Stage) node.getScene().getWindow();
    	stage.close();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<Deportista> listaDeportistas = oDao.cargarDeportista();
		ObservableList<Equipo> listaEquipo = oDao.cargarEquipo();
		ObservableList<Evento> listaEvento = oDao.cargarEvento();
		
		cbDeportista.getItems().addAll(listaDeportistas);
		cbEquipo.getItems().addAll(listaEquipo);
		cbEvento.getItems().addAll(listaEvento);
		cbMedalla.getItems().addAll(medallas);
	}
}