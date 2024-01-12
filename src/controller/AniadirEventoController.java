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
import model.Evento;
import model.Olimpiada;

public class AniadirEventoController implements Initializable{

    @FXML
    private ChoiceBox<Deporte> cbDeporte;

    @FXML
    private ChoiceBox<Olimpiada> cbOlimpiada;

    @FXML
    private TextField tfNombre;
    
    OlimpiadasDao oDao = new OlimpiadasDao();
    
    
    /**
     * Genera un Evento y lo añade a la BBDD.
     * @param event
     */
    @FXML
    void Aceptar(ActionEvent event) {

    	int nId = oDao.generarId("Evento");
    	String sNombre = tfNombre.getText();
    	Olimpiada o = cbOlimpiada.getSelectionModel().getSelectedItem();
    	Deporte d = cbDeporte.getSelectionModel().getSelectedItem();
    	Evento e = new Evento(nId, o.getIdOlimpiada(), d.getIdDeporte(), sNombre, o.getNombre(), d.getNombre());
    	boolean resultado = oDao.aniadirEvento(e);
    	if (resultado) {
    		TablaGeneralController.ventanaAlerta("I", "Deportista añadido con éxito");
    		Cancelar(event);
    	}else {
    		TablaGeneralController.ventanaAlerta("E", "Error al añadir Deportista");
    	}
    }

    /**
     * Cierra la ventana.
     * @param event
     */
    @FXML
    void Cancelar(ActionEvent event) {
    	Node node = (Node)event.getSource();
    	Stage stage = (Stage) node.getScene().getWindow();
    	stage.close();
    }

    /**
     * al iniciar la ventana carga las olimpiadas y deportes para añadir a los ChoiceBox.
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<Olimpiada> listaOlimpiada= oDao.cargarOlimpiada();
		Iterator<Olimpiada> itO = listaOlimpiada.iterator();
		while (itO.hasNext()) {
			Olimpiada o = itO.next();
			cbOlimpiada.getItems().add(o);
		}
		ObservableList<Deporte> listaDeporte = oDao.cargarDeporte();
		Iterator<Deporte> itD = listaDeporte.iterator();
		while (itD.hasNext()) {
			Deporte d = itD.next();
			cbDeporte.getItems().add(d);
		}
	}

}