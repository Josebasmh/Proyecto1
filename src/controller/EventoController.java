package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class EventoController {

    @FXML
    private ChoiceBox<?> cbBusqueda;

    @FXML
    private Menu mAyuda;

    @FXML
    private Menu mAñadir;

    @FXML
    private TableColumn<?, ?> tcDeporte;

    @FXML
    private TableColumn<?, ?> tcNombre;

    @FXML
    private TableColumn<?, ?> tcOlimpiada;

    @FXML
    private TextField tfBusqueda;

    @FXML
    private TableView<?> tvTabla;

    @FXML
    void aniadirDeporte(ActionEvent event) {

    }

    @FXML
    void aniadirEvento(ActionEvent event) {

    }

    @FXML
    void aniadirOlimpiada(ActionEvent event) {

    }

    @FXML
    void filtrar(KeyEvent event) {

    }

    @FXML
    void ventanaAyuda(ActionEvent event) {

    }

}
