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
import model.Deporte;

public class AniadirDeporteController implements Initializable{

    @FXML
    private TextField tfNombre;
    
    OlimpiadasDao oDao;

    @FXML
    void Aceptar(ActionEvent event) {
    	int nId = oDao.generarId("Deportista");
    	String sNombre = tfNombre.getText();
    	Deporte d = new Deporte(nId, sNombre);
    	boolean resultado = oDao.aniadirDeporte(d);
    	if (resultado) {
    		TablaGeneralController.ventanaAlerta("I", "Deporte añadido con éxito");
    		Cancelar(event);
    	}else {
    		TablaGeneralController.ventanaAlerta("E", "Error al añadir Deporte");
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
		oDao = new OlimpiadasDao();
		
	}

}
