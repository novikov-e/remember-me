package ru.rememberme.views;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ru.rememberme.model.Exercise;
import ru.rememberme.stages.DeleteExerciseStage;
import ru.rememberme.stages.NewExerciseStage;
import ru.rememberme.stages.RenameExerciseStage;
import ru.rememberme.storage.StorageSQL;
import ru.rememberme.ui.*;

import java.sql.SQLException;

public class RootPaneView extends Pane {

    public final ListView<Exercise> listExercise;
    private final CustomTabPane tabPane = new CustomTabPane(750, 500);

    public RootPaneView(Stage rootStage, String style) throws SQLException {
        EventHandler<ActionEvent> newExerciseEvent =
                event -> new NewExerciseStage(rootStage, this, style);
        CustomButton addNewExerciseBtn = new CustomButton(
                "Создать список", 230, 30, newExerciseEvent);

        listExercise = new ListView<>(StorageSQL.getInstance().getListExercises());
        listExercise.setLayoutX(10);
        listExercise.setLayoutY(10);
        listExercise.setMaxSize(230, 435);
        listExercise.setMinSize(230, 435);
        listExercise.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                try {
                    if (listExercise.getSelectionModel().getSelectedItem() != null) {
                        Exercise exercise = StorageSQL.getInstance()
                                .getTranslates(listExercise.getSelectionModel().getSelectedItem());
                        boolean opened = false;
                        if (!tabPane.getTabs().isEmpty()) {
                            for (int i = 0; i < tabPane.getTabs().size(); i++) {
                                CustomTab tab = (CustomTab) tabPane.getTabs().get(i);
                                if (tab.exercise.getId() == exercise.getId()) {
                                    opened = true;
                                    break;
                                }
                            }
                        }
                        if (!opened) {
                            tabPane.getTabs().add(new CustomTab(exercise));
                        }
                    }
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
            }
        });

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.getChildren().addAll(addNewExerciseBtn, listExercise);

        ContextMenu contextMenu = new ContextMenu();
        MenuItem editMenuItem = new MenuItem("Переименовать");
        editMenuItem.setOnAction(event -> new RenameExerciseStage(
                rootStage, this,
                style, listExercise.getSelectionModel().getSelectedItem()));
        MenuItem deleteMenuItem = new MenuItem("Удалить");
        deleteMenuItem.setOnAction(e -> new DeleteExerciseStage(
                rootStage, this,
                style, listExercise.getSelectionModel().getSelectedItem().toString()));
        contextMenu.getItems().addAll(editMenuItem, deleteMenuItem);
        listExercise.setContextMenu(contextMenu);

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(vBox, tabPane);
        this.getChildren().add(hBox);
    }

    public void newExercise(String name) {
        boolean exist = false;
        if (!listExercise.getItems().isEmpty()) {
            for (Exercise exercise : listExercise.getItems()) {
                if (exercise.getName().equals(name)) {
                    exist = true;
                    break;
                }
            }
        }
        if (!exist) {
            try {
                Exercise exercise = StorageSQL.getInstance().newExercises(name);
                listExercise.getItems().add(exercise);
                tabPane.getTabs().add(new CustomTab(exercise));
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public void renameExercise(Exercise exercise) throws SQLException {
        StorageSQL.getInstance().updateExercise(exercise);
        listExercise.refresh();
    }

    public void deleteExercise() {
        Exercise exercise = listExercise.getSelectionModel().getSelectedItem();
        try {
            StorageSQL.getInstance().deleteExercise(exercise.getId());
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        for (int i = 0; i < tabPane.getTabs().size(); i++) {
            CustomTab tab = (CustomTab) tabPane.getTabs().get(i);
            if (tab.exercise.getId() == exercise.getId()) {
                tabPane.getTabs().remove(i);
            }
        }
        listExercise.getItems().remove(exercise);
    }
}
