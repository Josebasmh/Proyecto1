package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class DeportistaController {

    @FXML
    private ChoiceBox<?> cbBusqueda;

    @FXML
    private Menu mAñadir;

    @FXML
    private TableColumn<?, ?> tcAltura;

    @FXML
    private TableColumn<?, ?> tcFoto;

    @FXML
    private TableColumn<?, ?> tcNombre;

    @FXML
    private TableColumn<?, ?> tcPeso;

    @FXML
    private TableColumn<?, ?> tcSexo;

    @FXML
    private TextField tfBusqueda;

    @FXML
    private TableView<?> tvTabla;

    @FXML
    void aniadirDeportista(ActionEvent event) {

    }

    @FXML
    void filtrar(KeyEvent event) {

    }

}