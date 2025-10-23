package com.example.dartcounterapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main JavaFX application entry point for the Dart Counter app.
 * Loads the scoreboard view, initializes the game, and launches the UI.
 */
public class DartCounterApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scoreboard-view.fxml"));
        Scene scene = new Scene(loader.load(), 600, 400);

        Player player1 = new Player("Player 1", 501);
        Player player2 = new Player("Player 2", 501);
        Game game = new Game(player1, player2, 501, 3, 3);

        ScoreboardController controller = loader.getController();
        controller.setGame(game);

        stage.setTitle("Dart Counter");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}