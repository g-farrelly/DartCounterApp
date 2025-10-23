package com.example.dartcounterapp;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a darts game containing two players.
 * Handles scoring, turn logic, leg/set transitions, and checkout suggestions.
 */
public class Game {

    private final Player player1;
    private final Player player2;
    private Player currentPlayer;
    private Player setStarter;
    private Player startingPlayer;

    private final int targetScore;
    private final int legsPerSet;
    private final int setsToWin;

    private int dartsThrown = 0;

    private static final int[] IMPOSSIBLE_THREE_DART_SCORES = {
            179, 178, 176, 175, 173, 172, 169, 168, 166, 165, 163, 162
    };

    private static final Map<Integer, String> CHECKOUTS = new HashMap<>();

    static {
        CHECKOUTS.put(170, "T20 T20 Bull");
        CHECKOUTS.put(167, "T20 T19 Bull");
        CHECKOUTS.put(164, "T20 T18 Bull");
        CHECKOUTS.put(161, "T20 T17 Bull");
        CHECKOUTS.put(160, "T20 T20 D20");
        CHECKOUTS.put(158, "T20 T20 D19");
        CHECKOUTS.put(157, "T20 T19 D20");
        CHECKOUTS.put(156, "T20 T20 D18");
        CHECKOUTS.put(155, "T20 T19 D19");
        CHECKOUTS.put(154, "T20 T18 D20");
        CHECKOUTS.put(153, "T20 T19 D18");
        CHECKOUTS.put(152, "T20 T20 D16");
        CHECKOUTS.put(151, "T20 T17 D20");
        CHECKOUTS.put(150, "T20 T18 D18");
        CHECKOUTS.put(149, "T20 T19 D16");
        CHECKOUTS.put(148, "T20 T16 D20");
        CHECKOUTS.put(147, "T20 T17 D18");
        CHECKOUTS.put(146, "T20 T18 D16");
        CHECKOUTS.put(145, "T20 T15 D20");
        CHECKOUTS.put(144, "T20 T20 D12");
        CHECKOUTS.put(143, "T20 T17 D16");
        CHECKOUTS.put(142, "T20 T14 D20");
        CHECKOUTS.put(141, "T20 T19 D12");
        CHECKOUTS.put(140, "T20 T16 D16");
        CHECKOUTS.put(139, "T20 T13 D20");
        CHECKOUTS.put(138, "T20 T18 D12");
        CHECKOUTS.put(137, "T19 T16 D16");
        CHECKOUTS.put(136, "T20 T20 D8");
        CHECKOUTS.put(135, "Bull T15 D20");
        CHECKOUTS.put(134, "T20 T14 D16");
        CHECKOUTS.put(133, "T20 T19 D8");
        CHECKOUTS.put(132, "Bull Bull D16");
        CHECKOUTS.put(131, "T20 T13 D16");
        CHECKOUTS.put(130, "T20 T18 D8");
        CHECKOUTS.put(129, "T19 T16 D12");
        CHECKOUTS.put(128, "T18 T14 D16");
        CHECKOUTS.put(127, "T20 T17 D8");
        CHECKOUTS.put(126, "T19 T19 D6");
        CHECKOUTS.put(125, "Bull T15 D20");
        CHECKOUTS.put(124, "T20 T16 D8");
        CHECKOUTS.put(123, "T19 T10 D18");
        CHECKOUTS.put(122, "T18 T18 D7");
        CHECKOUTS.put(121, "T20 T11 D14");
        CHECKOUTS.put(120, "T20 20 D20");
        CHECKOUTS.put(119, "T19 10 D16");
        CHECKOUTS.put(118, "T20 18 D20");
        CHECKOUTS.put(117, "T20 17 D20");
        CHECKOUTS.put(116, "T20 16 D20");
        CHECKOUTS.put(115, "T20 15 D20");
        CHECKOUTS.put(114, "T20 14 D20");
        CHECKOUTS.put(113, "T20 13 D20");
        CHECKOUTS.put(112, "T20 12 D20");
        CHECKOUTS.put(111, "T20 11 D20");
        CHECKOUTS.put(110, "T20 10 D20");
        CHECKOUTS.put(109, "T20 9 D20");
        CHECKOUTS.put(108, "T20 8 D20");
        CHECKOUTS.put(107, "T19 10 D20");
        CHECKOUTS.put(106, "T20 6 D20");
        CHECKOUTS.put(105, "T20 13 D16");
        CHECKOUTS.put(104, "T18 18 D16");
        CHECKOUTS.put(103, "T20 3 D20");
        CHECKOUTS.put(102, "T20 10 D16");
        CHECKOUTS.put(101, "T17 10 D20");
        CHECKOUTS.put(100, "T20 D20");
        CHECKOUTS.put(99, "T19 10 D16");
        CHECKOUTS.put(98, "T20 D19");
        CHECKOUTS.put(97, "T19 D20");
        CHECKOUTS.put(96, "T20 D18");
        CHECKOUTS.put(95, "T19 D19");
        CHECKOUTS.put(94, "T18 D20");
        CHECKOUTS.put(93, "T19 D18");
        CHECKOUTS.put(92, "T20 D16");
        CHECKOUTS.put(91, "T17 D20");
        CHECKOUTS.put(90, "T18 D18");
        CHECKOUTS.put(89, "T19 D16");
        CHECKOUTS.put(88, "T20 D14");
        CHECKOUTS.put(87, "T17 D18");
        CHECKOUTS.put(86, "T18 D16");
        CHECKOUTS.put(85, "T15 D20");
        CHECKOUTS.put(84, "T20 D12");
        CHECKOUTS.put(83, "T17 D16");
        CHECKOUTS.put(82, "Bull D16");
        CHECKOUTS.put(81, "T19 D12");
        CHECKOUTS.put(80, "T20 D10");
        CHECKOUTS.put(79, "T13 D20");
        CHECKOUTS.put(78, "T18 D12");
        CHECKOUTS.put(77, "T19 D10");
        CHECKOUTS.put(76, "T20 D8");
        CHECKOUTS.put(75, "T17 D12");
        CHECKOUTS.put(74, "T14 D16");
        CHECKOUTS.put(73, "T19 D8");
        CHECKOUTS.put(72, "T16 D12");
        CHECKOUTS.put(71, "T13 D16");
        CHECKOUTS.put(70, "T18 D8");
        CHECKOUTS.put(69, "T19 D6");
        CHECKOUTS.put(68, "T20 D4");
        CHECKOUTS.put(67, "T17 D8");
        CHECKOUTS.put(66, "T10 D18");
        CHECKOUTS.put(65, "T19 D4");
        CHECKOUTS.put(64, "T16 D8");
        CHECKOUTS.put(63, "T13 D12");
        CHECKOUTS.put(62, "T10 D16");
        CHECKOUTS.put(61, "T15 D8");
        CHECKOUTS.put(60, "20 D20");
        CHECKOUTS.put(59, "19 D20");
        CHECKOUTS.put(58, "18 D20");
        CHECKOUTS.put(57, "17 D20");
        CHECKOUTS.put(56, "16 D20");
        CHECKOUTS.put(55, "15 D20");
        CHECKOUTS.put(54, "14 D20");
        CHECKOUTS.put(53, "13 D20");
        CHECKOUTS.put(52, "12 D20");
        CHECKOUTS.put(51, "11 D20");
        CHECKOUTS.put(50, "D25");
        CHECKOUTS.put(49, "9 D20");
        CHECKOUTS.put(48, "16 D16");
        CHECKOUTS.put(47, "15 D16");
        CHECKOUTS.put(46, "6 D20");
        CHECKOUTS.put(45, "13 D16");
        CHECKOUTS.put(44, "12 D16");
        CHECKOUTS.put(43, "3 D20");
        CHECKOUTS.put(42, "10 D16");
        CHECKOUTS.put(41, "9 D16");
        CHECKOUTS.put(40, "D20");
        CHECKOUTS.put(39, "7 D16");
        CHECKOUTS.put(38, "D19");
        CHECKOUTS.put(37, "5 D16");
        CHECKOUTS.put(36, "D18");
        CHECKOUTS.put(35, "3 D16");
        CHECKOUTS.put(34, "D17");
        CHECKOUTS.put(33, "1 D16");
        CHECKOUTS.put(32, "D16");
        CHECKOUTS.put(31, "15 D8");
        CHECKOUTS.put(30, "D15");
        CHECKOUTS.put(29, "13 D8");
        CHECKOUTS.put(28, "D14");
        CHECKOUTS.put(27, "11 D8");
        CHECKOUTS.put(26, "D13");
        CHECKOUTS.put(25, "9 D8");
        CHECKOUTS.put(24, "D12");
        CHECKOUTS.put(23, "7 D8");
        CHECKOUTS.put(22, "D11");
        CHECKOUTS.put(21, "5 D8");
        CHECKOUTS.put(20, "D10");
        CHECKOUTS.put(19, "3 D8");
        CHECKOUTS.put(18, "D9");
        CHECKOUTS.put(17, "1 D8");
        CHECKOUTS.put(16, "D8");
        CHECKOUTS.put(15, "7 D4");
        CHECKOUTS.put(14, "D7");
        CHECKOUTS.put(13, "5 D4");
        CHECKOUTS.put(12, "D6");
        CHECKOUTS.put(11, "3 D4");
        CHECKOUTS.put(10, "D5");
        CHECKOUTS.put(9, "1 D4");
        CHECKOUTS.put(8, "D4");
        CHECKOUTS.put(7, "3 D2");
        CHECKOUTS.put(6, "D3");
        CHECKOUTS.put(5, "1 D2");
        CHECKOUTS.put(4, "D2");
        CHECKOUTS.put(3, "1 D1");
        CHECKOUTS.put(2, "D1");
    }

    public Game(Player p1, Player p2, int targetScore, int legsPerSet, int setsToWin) {
        this.player1 = p1;
        this.player2 = p2;
        this.currentPlayer = p1;
        this.startingPlayer = p1;
        this.setStarter = p1;
        this.targetScore = targetScore;
        this.legsPerSet = legsPerSet;
        this.setsToWin = setsToWin;
        this.player1.startTurn();
    }

    public Player getPlayer1() { return player1; }
    public Player getPlayer2() { return player2; }
    public Player getCurrentPlayer() { return currentPlayer; }
    public int getLegsPerSet() { return legsPerSet; }
    public int getSetsToWin() { return setsToWin; }

    /** Returns checkout suggestion text for the given remaining score. */
    public String getCheckoutSuggestion(int score) {
        if (score > 170 || score < 2) return "";
        if (score == 169 || score == 168 || score == 166 || score == 165 ||
                score == 163 || score == 162 || score == 159) return "";
        return CHECKOUTS.getOrDefault(score, "");
    }

    /** Parses a dart input (e.g., "s20", "t19", "50") into its numeric value. */
    public int parseDart(String dartInput) {
        if (dartInput == null || dartInput.isEmpty()) return -1;
        dartInput = dartInput.trim().toLowerCase();

        if (dartInput.equals("25")) return 25;
        if (dartInput.equals("50")) return 50;
        if (dartInput.length() < 2) return -1;

        char multiplier = dartInput.charAt(0);
        int value;
        try {
            value = Integer.parseInt(dartInput.substring(1));
        } catch (NumberFormatException e) {
            return -1;
        }
        if (value < 1 || value > 20) return -1;

        return switch (multiplier) {
            case 's' -> value;
            case 'd' -> value * 2;
            case 't' -> value * 3;
            default -> -1;
        };
    }

    /** Handles scoring when a player wins a leg, set, or match. */
    private String handleLegOrSetWin(Player winner) {
        if (winner.getLegsWon() + 1 == legsPerSet) {
            winner.addSet();
            player1.resetLegs();
            player2.resetLegs();

            if (winner.getSetsWon() == setsToWin) {
                player1.resetForNewLeg(targetScore);
                player2.resetForNewLeg(targetScore);
                dartsThrown = 0;
                return winner.getName() + " wins the MATCH!";
            }

            setStarter = (setStarter == player1) ? player2 : player1;
            startingPlayer = setStarter;
            currentPlayer = startingPlayer;

            player1.resetForNewLeg(targetScore);
            player2.resetForNewLeg(targetScore);
            currentPlayer.startTurn();

            dartsThrown = 0;
            return winner.getName() + " wins the SET!";
        }

        winner.addLeg();
        player1.resetForNewLeg(targetScore);
        player2.resetForNewLeg(targetScore);

        startingPlayer = (startingPlayer == player1) ? player2 : player1;
        currentPlayer = startingPlayer;
        currentPlayer.startTurn();

        dartsThrown = 0;
        return winner.getName() + " wins the LEG!";
    }

    /** Processes a single dart throw input. */
    public String throwDart(String dartInput) {
        int dartScore = parseDart(dartInput);
        if (dartScore < 0) return "Invalid dart entry!";

        int newScore = currentPlayer.getCurrentScore() - dartScore;

        if (newScore < 2 && newScore != 0) {
            currentPlayer.setCurrentScore(currentPlayer.getTurnStartScore());
            currentPlayer.endTurn(3, true);
            switchPlayer();
            dartsThrown = 0;
            return "Bust!";
        }

        currentPlayer.setCurrentScore(newScore);
        dartsThrown++;

        if (newScore == 0) {
            currentPlayer.endTurn(dartsThrown, false);
            return handleLegOrSetWin(currentPlayer);
        }

        if (dartsThrown == 3) {
            currentPlayer.endTurn(3, false);
            switchPlayer();
            dartsThrown = 0;
            return "Switch";
        }
        return "";
    }

    /** Processes a total score entry for a turn (e.g., 100, 140, 180). */
    public String throwTotal(int totalScore) {
        if (totalScore < 0) return "Invalid score: cannot be negative!";
        if (totalScore > 180) return "Invalid score: cannot exceed 180!";
        if (isImpossibleThreeDartScore(totalScore)) return "Invalid score: not possible in 3 darts!";

        int newScore = currentPlayer.getCurrentScore() - totalScore;

        if (newScore < 2 && newScore != 0) {
            currentPlayer.setCurrentScore(currentPlayer.getTurnStartScore());
            currentPlayer.endTurn(3, true);
            switchPlayer();
            return "Bust!";
        }

        currentPlayer.setCurrentScore(newScore);

        if (newScore == 0) {
            currentPlayer.endTurn(3, false);
            return handleLegOrSetWin(currentPlayer);
        }

        currentPlayer.endTurn(3, false);
        switchPlayer();
        return "Switch";
    }

    /** Switches the active player at the end of a turn. */
    private void switchPlayer() {
        dartsThrown = 0;
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
        currentPlayer.startTurn();
    }

    private boolean isImpossibleThreeDartScore(int score) {
        for (int s : IMPOSSIBLE_THREE_DART_SCORES) {
            if (s == score) return true;
        }
        return false;
    }
}