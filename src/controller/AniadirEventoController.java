package controller;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

import dao.OlimpiadasDao;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import model.Deporte;
import model.Olimpiada;

public class AniadirEventoController implements Initializable{

    @FXML
    private ChoiceBox<String> cbDeporte;

    @FXML
    private ChoiceBox<String> cbOlimpiada;

    @FXML
    private TextField tfNombre;
    
    OlimpiadasDao oDao = new OlimpiadasDao();
    

    @FXML
    void Aceptar(ActionEvent event) {

    }

    @FXML
    void Cancelar(ActionEvent event) {

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<Olimpiada> listaOlimpiada= oDao.cargarOlimpiada();
		Iterator<Olimpiada> itO = listaOlimpiada.iterator();
		while (itO.hasNext()) {
			Olimpiada o = itO.next();
			cbOlimpiada.getItems().add(o.getNombre());
		}
		ObservableList<Deporte> listaDeporte = oDao.cargarDeporte();
		Iterator<Deporte> itD = listaDeporte.iterator();
		while (itD.hasNext()) {
			Deporte d = itD.next();
			cbDeporte.getItems().add(d.getNombre());
		}
	}

}