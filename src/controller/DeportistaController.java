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

public class DeportistaController implements Initializable{

    @FXML
    private ChoiceBox<String> cbBusqueda;

    @FXML
    private Menu mAñadir;

    @FXML
    private TableColumn<Deportista, Integer> tcAltura;

    @FXML
    private TableColumn<Deportista, ?> tcFoto;

    @FXML
    private TableColumn<Deportista, String> tcNombre;

    @FXML
    private TableColumn<Deportista, Integer> tcPeso;

    @FXML
    private TableColumn<Deportista, String> tcSexo;

    @FXML
    private TextField tfBusqueda;

    @FXML
    private TableView<Deportista> tvTabla;
    
    // VARIABLES DE CLASE INSERTADAS MANUALMENTE \\
    private OlimpiadasDao oDao = new OlimpiadasDao();
    private String[]campos = {"Nombre","Sexo","Peso","Altura"};

    @FXML
    void aniadirDeportista(ActionEvent event) {

    }

    @FXML
    void filtrar(KeyEvent event) {

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		cbBusqueda.getItems().addAll(campos);
		ObservableList<Deportista> listaDeportista = oDao.cargarDeportista();
		cargarTabla(listaDeportista);
	}

	private void cargarTabla(ObservableList<Deportista> listaDeportista) {
		tcAltura.setCellValueFactory(new PropertyValueFactory<Deportista, Integer>("altura"));
		//tcFoto.setCellValueFactory(new PropertyValueFactory<Deportista, T>("foto"));
		tcNombre.setCellValueFactory(new PropertyValueFactory<Deportista, String>("nombre"));
		tcPeso.setCellValueFactory(new PropertyValueFactory<Deportista, Integer>("peso"));
		//tcSexo.setCellValueFactory(new PropertyValueFactory<Deportista, String>("sexo"));
		
		tvTabla.setItems(listaDeportista);
	}

}