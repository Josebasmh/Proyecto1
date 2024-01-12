package controller;

import java.net.URL;
import java.util.ResourceBundle;

import dao.OlimpiadasDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Deportista;
import model.Equipo;

public class AniadirEquipoController implements Initializable{

    @FXML
    private TextField tfIniciales;

    @FXML
    private TextField tfNombre;
    
    OlimpiadasDao oDao;

    /**
     * Al pulsar el botón genera un Equipo y lo añade a la BBDD.
     * @param event
     */
    @FXML
    void Aceptar(ActionEvent event) {
    	int nId = oDao.generarId("Equipo");
    	String sNombre = tfNombre.getText();
    	String sIniciales = tfIniciales.getText();
    	
    	Equipo e = new Equipo(nId, sNombre, sIniciales);
    	boolean resultado = oDao.aniadirEquipo(e);
    	if (resultado) {
    		TablaGeneralController.ventanaAlerta("I", "Equipo añadido con éxito");
    		Cancelar(event);
    	}else {
    		TablaGeneralController.ventanaAlerta("E", "Error al añadir Equipo");
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		oDao = new OlimpiadasDao();
		
	}

}
