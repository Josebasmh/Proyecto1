package controller;

import java.net.URL;
import java.util.ResourceBundle;

import dao.OlimpiadasDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Olimpiada;

public class AniadirOlimpiadaController implements Initializable{

    @FXML
    private TextField tfAnio;

    @FXML
    private TextField tfCiudad;
    
    @FXML
    private ChoiceBox<String> cbTemporada;
    
    OlimpiadasDao oDao = new OlimpiadasDao();
    String[] campos = {"Summer","Winter"};

    @FXML
    void Aceptar(ActionEvent event) {

    	int nId = oDao.generarId("Olimpiada");
    	Integer nAnio = Integer.parseInt(tfAnio.getText());
    	String sTemporada = cbTemporada.getSelectionModel().getSelectedItem();
    	String sCiudad = tfCiudad.getText();
    	String sNombre = nAnio + " " + sCiudad;
    	Olimpiada o = new Olimpiada(nId, sNombre, nAnio, sTemporada, sCiudad);
    	boolean resultado = oDao.aniadirOlimpiada(o);
    	if (resultado) {
    		TablaGeneralController.ventanaAlerta("I", "Olimpiada añadido con éxito");
    		Cancelar(event);
    	}else {
    		TablaGeneralController.ventanaAlerta("E", "Error al añadir Olimpiada");
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
		cbTemporada.getItems().addAll(campos);
		
	}

}
