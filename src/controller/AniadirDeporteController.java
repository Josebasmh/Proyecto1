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
    
    private OlimpiadasDao oDao;
    private Deporte d;
    private boolean modificar;

    @FXML
    void Aceptar(ActionEvent event) {
    	
    	int nId = oDao.generarId("Deportista");
    	String sNombre = tfNombre.getText();
    	
    	if (modificar) {
    		Deporte dMod = new Deporte(d.getIdDeporte(), sNombre);
    		boolean resultado = oDao.modificarDeporte(dMod);
        	if (resultado) {
        		TablaGeneralController.ventanaAlerta("I", "Deporte modificado con éxito");
        		Cancelar(event);
        	}else {
        		TablaGeneralController.ventanaAlerta("E", "Error al modificar Deporte");
        	}
    	}else {
    		Deporte d = new Deporte(nId, sNombre);
        	boolean resultado = oDao.aniadirDeporte(d);
        	if (resultado) {
        		TablaGeneralController.ventanaAlerta("I", "Deporte añadido con éxito");
        		Cancelar(event);
        	}else {
        		TablaGeneralController.ventanaAlerta("E", "Error al añadir Deporte");
        	}	
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
		
		try {
			d = EventoController.gDepModificar;
			modificar = true;
			mostrarDatosModificar();
		}catch(Exception e) {}
		
	}

	private void mostrarDatosModificar() {
		tfNombre.setText(d.getNombre());
	}

}
