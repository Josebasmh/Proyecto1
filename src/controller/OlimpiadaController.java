package controller;

import java.net.URL;
import java.util.ResourceBundle;

import dao.OlimpiadasDao;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import model.Deportista;
import model.Olimpiada;

public class OlimpiadaController implements Initializable{

    @FXML
    private ChoiceBox<String> cbBusqueda;

    @FXML
    private TableColumn<Olimpiada, Integer> tcAnio;

    @FXML
    private TableColumn<Olimpiada,String> tcCiudad;

    @FXML
    private TableColumn<Olimpiada, String> tcNombre;

    @FXML
    private TableColumn<Olimpiada, String> tcTemporada;

    @FXML
    private TextField tfBusqueda;

    @FXML
    private TableView<Olimpiada> tvTabla;
    
    // VARIABLES DE CLASE INSERTADAS MANUALMENTE \\    
    private OlimpiadasDao oDao = new OlimpiadasDao();
    private String[]campos = {"Nombre","Año","Ciudad","Temporada"};

    @FXML
    void aniadirOlimpiada(ActionEvent event) {

    }

    @FXML
    void filtrar(KeyEvent event) {

    	String campoSeleccionado = cbBusqueda.getSelectionModel().getSelectedItem();
    	String txFiltro = tfBusqueda.getText().toString();    	
    	ObservableList<Olimpiada>listaFiltrada = oDao.filtrarOlimpiada(campoSeleccionado, txFiltro);
    	cargarTabla(listaFiltrada);
    }

	@FXML
    void ventanaAyuda(ActionEvent event) {

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		cbBusqueda.getItems().addAll(campos);
		ObservableList<Olimpiada> listaOlimpiada = oDao.cargarOlimpiada();
		cargarTabla(listaOlimpiada);
	}
	
	private void cargarTabla(ObservableList<Olimpiada> listaOlimpiada) {
		tcAnio.setCellValueFactory(new PropertyValueFactory<Olimpiada, Integer>("anio"));
		tcCiudad.setCellValueFactory(new PropertyValueFactory<Olimpiada, String>("ciudad"));
		tcNombre.setCellValueFactory(new PropertyValueFactory<Olimpiada, String>("nombre"));
		tcTemporada.setCellValueFactory(new PropertyValueFactory<Olimpiada, String>("temporada"));
		
		tvTabla.setItems(listaOlimpiada);
		
	}

}
