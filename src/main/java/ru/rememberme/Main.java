package ru.rememberme;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ru.rememberme.storage.StorageSQL;
import ru.rememberme.views.RootPaneView;

import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws SQLException {
        Stage rootStage = new Stage();
        rootStage.setTitle("Remember Me");
        rootStage.setResizable(false);
        rootStage.setOnCloseRequest(event -> {
            try {
                StorageSQL.getInstance().closeConnection();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        String style = "styles/style.css";
        Scene scene = new Scene(new RootPaneView(rootStage, style), 1000, 500, Color.WHITE);
        scene.getStylesheets().add(style);
        rootStage.setScene(scene);
        rootStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
