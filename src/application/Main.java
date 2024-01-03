package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;



public class Main extends Application{
	
	FlowPane root;

	@Override
	public void start(Stage stage) throws Exception {
		try {
			root = (FlowPane)FXMLLoader.load(getClass().getResource("/fxml/VentanaGeneral.fxml"));
			stage.setTitle("GESTION DE OLIMPIADAS");
			Scene scene = new Scene(root,800,600);
			stage.setScene(scene);
			stage.setMinWidth(800);
			stage.setMinHeight(600);
			stage.setMaxWidth(800);
			stage.setMaxHeight(600);
			stage.getIcons().add(new Image(getClass().getResource("/img/imgOlimpiadas.jpg").toString()));
			stage.show();	
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
	
}
