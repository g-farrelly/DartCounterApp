package com.example.dartcounterapp;

/**
 * Represents a player in a darts game.
 * Tracks score, legs, sets, turn state, and performance averages.
 */
public class Player {

    private final String name;
    private int currentScore;
    private int turnStartScore;
    private int lastTurnScored;
    private int legsWon;
    private int setsWon;

    private int totalDartsThrown = 0;
    private int totalPointsScored = 0;

    /**
     * Constructs a player with a name and starting score.
     *
     * @param name the player's name
     * @param startScore the initial score at the start of a leg
     */
    public Player(String name, int startScore) {
        this.name = name;
        this.currentScore = startScore;
        this.turnStartScore = startScore;
        this.lastTurnScored = 0;
    }

    /** @return the player's name */
    public String getName() { return name; }

    /** @return the current remaining score */
    public int getCurrentScore() { return currentScore; }

    /** @return the score at the start of the current turn */
    public int getTurnStartScore() { return turnStartScore; }

    /** @return the score achieved in the last turn */
    public int getLastTurnScored() { return lastTurnScored; }

    /** @return number of legs won */
    public int getLegsWon() { return legsWon; }

    /** @return number of sets won */
    public int getSetsWon() { return setsWon; }

    /** Sets the current score to the given value. */
    public void setCurrentScore(int score) { this.currentScore = score; }

    /** Resets the leg count to zero (used when starting a new set). */
    public void resetLegs() { this.legsWon = 0; }

    /** Marks the start of a player's turn by recording their current score. */
    public void startTurn() {
        this.turnStartScore = this.currentScore;
    }

    /**
     * Ends a player's turn and updates scoring statistics.
     *
     * @param dartsThrownThisTurn number of darts thrown this turn
     * @param bust whether the turn ended in a bust
     */
    public void endTurn(int dartsThrownThisTurn, boolean bust) {
        int scored = 0;

        if (!bust) {
            scored = turnStartScore - currentScore;
            lastTurnScored = scored;
        } else {
            lastTurnScored = 0;
        }

        totalDartsThrown += 3; // always count 3 darts for averaging consistency
        totalPointsScored += scored;
    }

    /**
     * Resets the player’s state for a new leg.
     *
     * @param targetScore the score to reset to (e.g., 501)
     */
    public void resetForNewLeg(int targetScore) {
        this.currentScore = targetScore;
        this.turnStartScore = targetScore;
        this.lastTurnScored = 0;
    }

    /** Increments the number of legs won by one. */
    public void addLeg() { legsWon++; }

    /** Increments the number of sets won by one. */
    public void addSet() { setsWon++; }

    /**
     * Calculates the player's three-dart average.
     *
     * @return average score per three darts
     */
    public double getThreeDartAverage() {
        if (totalDartsThrown == 0) return 0.0;
        double avgPerDart = (double) totalPointsScored / totalDartsThrown;
        return avgPerDart * 3.0;
    }
}