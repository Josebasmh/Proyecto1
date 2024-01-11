package controller;

import java.net.URL;
import java.util.ResourceBundle;

import dao.OlimpiadasDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Deportista;

public class AniadirDeportistaController implements Initializable{

    @FXML
    private ImageView imgImagen;

    @FXML
    private RadioButton rbM;

    @FXML
    private RadioButton rdF;

    @FXML
    private TextField tfAltura;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfPeso;

    @FXML
    private ToggleGroup tgSexo;
    
    OlimpiadasDao oDao;

    @FXML
    void Aceptar(ActionEvent event) throws Throwable {

    	int nId = oDao.generarId("Deportista");
    	String sNombre = tfNombre.getText();
    	Character cSexo;
    	if (rbM.isSelected()) {
    		cSexo = 'M';
    	}else {
    		cSexo = 'F';
    	}
    	Integer nPeso = Integer.parseInt(tfPeso.getText());
    	Integer nAltura = Integer.parseInt(tfAltura.getText());
    	Deportista d = new Deportista(nId, sNombre, cSexo, nPeso, nAltura);
    	boolean resultado = oDao.aniadirDeportista(d);
    	if (resultado) {
    		TablaGeneralController.ventanaAlerta("I", "Deportista añadido con éxito");
    		Cancelar(event);
    	}else {
    		TablaGeneralController.ventanaAlerta("E", "Error al añadir Deportista");
    	}
    }

    @FXML
    void Cancelar(ActionEvent event) throws Throwable {
    	Node node = (Node)event.getSource();
    	Stage stage = (Stage) node.getScene().getWindow();
    	stage.close();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		oDao = new OlimpiadasDao();
		
	}

}

