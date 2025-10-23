package com.example.dartcounterapp;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Controller for the scoreboard view.
 * Handles user input, updates the game state, and refreshes the UI.
 */
public class ScoreboardController {

    @FXML private Label player1NameLabel, player2NameLabel;
    @FXML private Label player1ScoreLabel, player2ScoreLabel;
    @FXML private Label player1PrevScoreLabel, player2PrevScoreLabel;
    @FXML private Label player1AvgLabel, player2AvgLabel;
    @FXML private Label player1LegsLabel, player2LegsLabel;
    @FXML private Label player1SetsLabel, player2SetsLabel;
    @FXML private Label player1CheckoutLabel, player2CheckoutLabel;
    @FXML private Label matchFormatLabel;
    @FXML private Text currentPlayerLabel, dartsThrownLabel, messageLabel;
    @FXML private TextField dartInputField;
    @FXML private Button throwButton;
    @FXML private ToggleButton modeToggle;

    private Game game;
    private final StringBuilder currentTurnDarts = new StringBuilder();
    private boolean perDartMode = false;
    private int dartsThisTurn = 0;

    /**
     * Injects the Game instance and initializes the match format display.
     */
    public void setGame(Game game) {
        this.game = game;
        matchFormatLabel.setText(
                String.format("First to %d Legs, %d Sets", game.getLegsPerSet(), game.getSetsToWin())
        );
        updateUI();
    }

    /**
     * Called automatically by JavaFX after FXML loading.
     * Sets up event listeners for throw and toggle buttons.
     */
    @FXML
    public void initialize() {
        throwButton.setOnAction(e -> handleThrow());
        dartInputField.setOnAction(e -> handleThrow());

        modeToggle.setOnAction(e -> {
            if (dartsThisTurn == 0) {
                toggleInputMode();
            } else {
                messageLabel.setText("Can't switch mode mid-turn!");
                messageLabel.setFill(Color.ORANGE);
                modeToggle.setSelected(perDartMode);
            }
        });
    }

    /** Toggles between per-dart and total-score input modes. */
    private void toggleInputMode() {
        perDartMode = !perDartMode;
        modeToggle.setPrefWidth(100);
        modeToggle.setPrefHeight(52);

        if (perDartMode) {
            modeToggle.setText("🎯");
            modeToggle.setStyle("-fx-font-size: 22px; -fx-background-radius: 10; "
                    + "-fx-background-color: #444; -fx-text-fill: white; -fx-font-weight: bold;");
            messageLabel.setText("Per-Dart Mode");
        } else {
            modeToggle.setText("🎯🎯🎯");
            modeToggle.setStyle("-fx-font-size: 22px; -fx-background-radius: 10; "
                    + "-fx-background-color: #2a6aff; -fx-text-fill: white; -fx-font-weight: bold;");
            messageLabel.setText("Total Mode");
        }
        messageLabel.setFill(Color.LIGHTBLUE);
    }

    /** Handles when the user presses the "Throw" button or hits Enter. */
    private void handleThrow() {
        if (game == null) return;

        String input = dartInputField.getText().trim();
        if (input.isEmpty()) return;

        dartsThisTurn++;
        String result = perDartMode ? handlePerDartInput(input) : handleTotalInput(input);

        dartInputField.clear();
        processResult(result);
        updateUI();
    }

    /** Handles individual dart input mode (e.g. "T20", "D10"). */
    private String handlePerDartInput(String input) {
        int parsed = game.parseDart(input);
        if (parsed < 0) {
            messageLabel.setText("Invalid dart entry!");
            messageLabel.setFill(Color.RED);
            return "";
        }
        currentTurnDarts.append(input.toUpperCase()).append("  ");
        dartsThrownLabel.setText("Darts this turn: " + currentTurnDarts);
        return game.throwDart(input);
    }

    /** Handles total-score input mode (e.g. 100, 140). */
    private String handleTotalInput(String input) {
        try {
            int total = Integer.parseInt(input);
            String result = game.throwTotal(total);
            if (result.startsWith("Invalid")) {
                messageLabel.setText(result);
                messageLabel.setFill(Color.RED);
                return "";
            }
            return result;
        } catch (NumberFormatException e) {
            messageLabel.setText("Enter a valid total (number only).");
            messageLabel.setFill(Color.RED);
            return "";
        }
    }

    /**
     * Interprets game results and updates message/status text.
     * Resets turn state when switching or busting.
     */
    private void processResult(String result) {
        if (result.isEmpty()) {
            messageLabel.setText("");
            return;
        }

        messageLabel.setText(result);
        messageLabel.setFill(result.equals("Bust!") ? Color.RED : Color.GREEN);

        if (result.equals("Bust!") || result.equals("Switch") || result.contains("wins the leg")) {
            currentTurnDarts.setLength(0);
            dartsThisTurn = 0;
            dartsThrownLabel.setText("Darts this turn:");
        }
    }

    /** Updates all UI labels to reflect the current game state. */
    private void updateUI() {
        if (game == null) return;

        Player p1 = game.getPlayer1();
        Player p2 = game.getPlayer2();

        player1NameLabel.setText(p1.getName());
        player2NameLabel.setText(p2.getName());
        player1ScoreLabel.setText(String.valueOf(p1.getCurrentScore()));
        player2ScoreLabel.setText(String.valueOf(p2.getCurrentScore()));

        player1PrevScoreLabel.setText("Prev: " + p1.getLastTurnScored());
        player2PrevScoreLabel.setText("Prev: " + p2.getLastTurnScored());
        player1AvgLabel.setText(String.format("3-Dart Avg: %.2f", p1.getThreeDartAverage()));
        player2AvgLabel.setText(String.format("3-Dart Avg: %.2f", p2.getThreeDartAverage()));

        player1LegsLabel.setText(String.valueOf(p1.getLegsWon()));
        player2LegsLabel.setText(String.valueOf(p2.getLegsWon()));
        player1SetsLabel.setText(String.valueOf(p1.getSetsWon()));
        player2SetsLabel.setText(String.valueOf(p2.getSetsWon()));

        updateActivePlayerHighlight(p1, p2);
        updateCheckoutDisplays(p1, p2);

        currentPlayerLabel.setText("Current Turn: " + game.getCurrentPlayer().getName());
    }

    /** Highlights the active player’s name label. */
    private void updateActivePlayerHighlight(Player p1, Player p2) {
        if (game.getCurrentPlayer() == p1) {
            player1NameLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: lime;");
            player2NameLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: normal; -fx-text-fill: white;");
        } else {
            player2NameLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: lime;");
            player1NameLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: normal; -fx-text-fill: white;");
        }
    }

    /** Updates and animates checkout suggestions for both players. */
    private void updateCheckoutDisplays(Player p1, Player p2) {
        String p1Checkout = game.getCheckoutSuggestion(p1.getCurrentScore());
        String p2Checkout = game.getCheckoutSuggestion(p2.getCurrentScore());

        if (!p1Checkout.isEmpty()) {
            player1CheckoutLabel.setText(p1Checkout);
            player1CheckoutLabel.setTextFill(game.getCurrentPlayer() == p1
                    ? Color.LIMEGREEN : Color.web("#00bfff"));
            fadeLabel(player1CheckoutLabel, true);
        } else {
            fadeLabel(player1CheckoutLabel, false);
        }

        if (!p2Checkout.isEmpty()) {
            player2CheckoutLabel.setText(p2Checkout);
            player2CheckoutLabel.setTextFill(game.getCurrentPlayer() == p2
                    ? Color.LIMEGREEN : Color.web("#00bfff"));
            fadeLabel(player2CheckoutLabel, true);
        } else {
            fadeLabel(player2CheckoutLabel, false);
        }
    }

    /** Fades a label in or out smoothly. */
    private void fadeLabel(Label label, boolean visible) {
        FadeTransition ft = new FadeTransition(Duration.seconds(0.5), label);
        ft.setFromValue(visible ? 0 : 1);
        ft.setToValue(visible ? 1 : 0);
        ft.play();
    }
}