package ru.rememberme.storage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.rememberme.model.Exercise;
import ru.rememberme.model.Translate;
import java.io.File;
import java.sql.*;

public class StorageSQL {

    private Connection connection;
    private final String url = "jdbc:sqlite:" + System.getProperty("user.dir")
            + File.separator + "database.db";
    private final ObservableList<Exercise> listExercises;
    private static StorageSQL instance;

    private StorageSQL() throws SQLException {
        listExercises = FXCollections.observableArrayList();
        connect();
        createTables();
        getExercises();
    }

    public static StorageSQL getInstance() throws SQLException {
        if (instance == null) {
            instance = new StorageSQL();
        }
        return instance;
    }

    private void connect() {
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    private void createTables() throws SQLException {
        String exercise =
                "CREATE TABLE IF NOT EXISTS exercises (" +
                "id integer PRIMARY KEY, " +
                "exercise text NOT NULL);";
        String translates =
                "CREATE TABLE IF NOT EXISTS english_russian (" +
                "id integer PRIMARY KEY, " +
                "exercise_id integer NOT NULL, " +
                "word text NOT NULL, " +
                "translate text NOT NULL);";
        Statement stmt = connection.createStatement();
        stmt.execute(exercise);
        stmt.execute(translates);
    }

    public Exercise newExercises(String name) throws SQLException {
        String sql = "INSERT INTO exercises(exercise) VALUES(?);";
        PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, name);
        pstmt.executeUpdate();
        ResultSet rs = pstmt.getGeneratedKeys();
        int id = rs.getInt(1);
        Exercise result = new Exercise(id, name);
        listExercises.add(result);
        return result;
    }

    public void updateExercise(Exercise exercise) throws SQLException {
        String sql = "UPDATE exercises SET exercise = ? WHERE id = ?;";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, exercise.getName());
        pstmt.setInt(2, exercise.getId());
        pstmt.executeUpdate();
    }

    public void getExercises() throws SQLException {
        String sql = "SELECT * FROM exercises;";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            listExercises.add(new Exercise(
                    rs.getInt("id"),
                    rs.getString("exercise")));
        }
    }

    public Exercise getTranslates(Exercise exercise) throws SQLException {
        String sql = "SELECT id, word, translate FROM english_russian WHERE exercise_id = ?;";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, exercise.getId());
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            exercise.addTranslate(new Translate(
                    rs.getInt("id"),
                    rs.getString("word"),
                    rs.getString("translate")));
        }
        return exercise;
    }

    public void deleteExercise(int id) throws SQLException {
        String firstQuery = "DELETE FROM english_russian WHERE exercise_id = ?;";
        PreparedStatement pstmt = connection.prepareStatement(firstQuery);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
        String secondQuery = "DELETE FROM exercises WHERE id = ?;";
        pstmt = connection.prepareStatement(secondQuery);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    }

    public Translate newTranslate(int exerciseID, String word, String translate) throws SQLException {
        String sql = "INSERT INTO english_russian(exercise_id,word,translate) VALUES(?,?,?);";
        PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setInt(1, exerciseID);
        pstmt.setString(2, word);
        pstmt.setString(3, translate);
        pstmt.executeUpdate();
        ResultSet rs = pstmt.getGeneratedKeys();
        int id = rs.getInt(1);
        return new Translate(id, word, translate);
    }

    public void updateTranslate(Translate translate) throws SQLException {

        String sql = "UPDATE english_russian SET word = ?, translate = ? WHERE id = ?;";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, translate.getWord());
        pstmt.setString(2, translate.getTranslate());
        pstmt.setInt(3, translate.getId());
        pstmt.executeUpdate();
    }

    public void deleteTranslate(int id) throws SQLException {
        String sql = "DELETE FROM english_russian WHERE id = ?;";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    }

    public ObservableList<Exercise> getListExercises() {
        return FXCollections.observableArrayList(listExercises);
    }
}
