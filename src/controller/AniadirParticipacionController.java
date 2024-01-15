package controller;

import java.net.URL;
import java.util.Iterator;
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
		ObservableList<Equipo> listaEquipos = oDao.cargarEquipo();
		ObservableList<Evento> listaEventos = oDao.cargarEvento();
		
		cbDeportista.getItems().addAll(listaDeportistas);
		cbEquipo.getItems().addAll(listaEquipos);
		cbEvento.getItems().addAll(listaEventos);
		cbMedalla.getItems().addAll(medallas);
		
		Participacion p = TablaGeneralController.pModificar;
		if (p.getIdDeportista()!=0) {
			ObservableList<Deportista>listaDeportista = oDao.filtrarDeportista("id_deportista", p.getIdDeportista()+"");
			ObservableList<Evento>listaEvento = oDao.filtrarEvento("id_evento", p.getIdEvento()+"");
			ObservableList<Equipo>listaEquipo = oDao.filtrarEquipo("id_equipo", p.getIdEquipo()+"");
			System.out.println(listaEquipo.get(0).getIdEquipo());
						
			Deportista d = listaDeportista.get(0);
			Evento ev = listaEvento.get(0);
			Equipo eq= listaEquipo.get(0);
			
			cbDeportista.getSelectionModel().select(d);
			cbEvento.getSelectionModel().select(ev);
			cbEquipo.getSelectionModel().select(eq);
			tfEdad.setText(p.getEdad().toString());
			cbMedalla.getSelectionModel().select(p.getMedalla());
		}
	}
}