/**
 * @author Joseba
 *
 */
module Proyecto1 {
	requires javafx.controls;
	requires java.desktop;
	requires javafx.web;
	requires javafx.fxml;
	requires javafx.swing;
	requires javafx.media;
	requires javafx.graphics;
	requires javafx.base;
	requires java.sql;
	
	opens model to javafx.graphics, javafx.fxml, javafx.base;
	opens application to javafx.graphics, javafx.fxml, javafx.base;
	opens controller to javafx.graphics, javafx.fxml, javafx.base;
	opens dao to javafx.base,java.sql;
	opens conexion to java.sql;
}