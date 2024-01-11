package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Olimpiada;

public class OlimpiadaController {

    @FXML
    private ChoiceBox<String> cbBusqueda;

    @FXML
    private Menu mAyuda;

    @FXML
    private Menu mAñadir;

    @FXML
    private TableColumn<Olimpiada, Integer> tcAnio;

    @FXML
    private TableColumn<Olimpiada, String> tcCiudad;

    @FXML
    private TableColumn<Olimpiada, String> tcNombre;

    @FXML
    private TableColumn<Olimpiada, String> tcTemporada;

    @FXML
    private TextField tfBusqueda;

    @FXML
    private TableView<Olimpiada> tvTabla;

    @FXML
    void aniadirOlimpiada(ActionEvent event) {
    	
    }

    @FXML
    void filtrar(KeyEvent event) {

    }

    @FXML
    void ventanaAyuda(ActionEvent event) {

    }

    /**
	 * Abrir ventana auxiliar.
	 * @param f fxml
	 * @param t titulo
	 * @param altura
	 * @param anchura
	 */
	private void ventanaSecundaria(String f, String t,Integer altura,Integer anchura) {
		Stage stage = new Stage();
		try {
			FlowPane root = (FlowPane)FXMLLoader.load(getClass().getResource("/fxml/"+f+".fxml"));
			stage.setTitle(t);
			Scene scene = new Scene(root,altura,anchura);
			stage.setScene(scene);
			stage.setMinWidth(altura);
			stage.setMinHeight(anchura);
			stage.setMaxWidth(altura);
			stage.setMaxHeight(anchura);
			stage.getIcons().add(new Image(getClass().getResource("/img/imgOlimpiadas.jpg").toString()));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();	
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
