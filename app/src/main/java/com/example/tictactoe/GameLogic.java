package com.example.tictactoe;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class GameLogic {

    private TicTacToeBoardView.TicTacToeModel[] gameBoard;
    private TicTacToeBoardView.TicTacToeModel player;
    private TicTacToeBoardView.TicTacToeModel computer;
    private Button playAgainBTN;
    private Button homeBTN;
    private TextView playerTurn;
    private final GameTableFragment table = new GameTableFragment();
    // 1st element = row, 2nd = col, 3rd = line type
    private int[] winType = {-1, -1, -1};
    private boolean gameWon = false;


    GameLogic() {
        setPlayer(TicTacToeBoardView.TicTacToeModel.O);
        gameBoard = new TicTacToeBoardView.TicTacToeModel[9];
        for (int i = 0; i < 9; i++) {
            gameBoard[i] = TicTacToeBoardView.TicTacToeModel.SPACE;
        }
        if (table.isPlayerVsComputer()) {
            setPlayers();
        }
    }

    public boolean updateGameBoard(int pos) {
        if (!winnerCheck()) {
            if (gameBoard[pos] == TicTacToeBoardView.TicTacToeModel.SPACE) {
                if (table.isPlayerVsPlayer()) {
                    playerVsPlayer();
                }
                gameBoard[pos] = player;
                if (winnerCheck()) {
                    return false;
                }

                if (table.isPlayerVsComputer()) {
                    int aiMove = playerVsComputer();
                    gameBoard[aiMove] = computer;
                    if (winnerCheck()) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean winCondition(TicTacToeBoardView.TicTacToeModel move) {
        if (gameBoard[0] == gameBoard[1] && gameBoard[0] == gameBoard[2] && gameBoard[0] == move) {
            winType = new int[]{0, 0, 1};
            return true;
        } else if (gameBoard[3] == gameBoard[4] && gameBoard[3] == gameBoard[5] && gameBoard[3] == move) {
            winType = new int[]{1, 0, 1};
            return true;
        } else if (gameBoard[6] == gameBoard[7] && gameBoard[6] == gameBoard[8] && gameBoard[6] == move) {
            winType = new int[]{2, 0, 1};
            return true;
        } else if (gameBoard[0] == gameBoard[3] && gameBoard[0] == gameBoard[6] && gameBoard[0] == move) {
            winType = new int[]{0, 0, 2};
            return true;
        } else if (gameBoard[1] == gameBoard[4] && gameBoard[1] == gameBoard[7] && gameBoard[1] == move) {
            winType = new int[]{0, 1, 2};
            return true;
        } else if (gameBoard[2] == gameBoard[5] && gameBoard[2] == gameBoard[8] && gameBoard[2] == move) {
            winType = new int[]{0, 2, 2};
            return true;
        } else if (gameBoard[0] == gameBoard[4] && gameBoard[0] == gameBoard[8] && gameBoard[0] == move) {
            winType = new int[]{0, 2, 3};
            return true;
        } else if (gameBoard[2] == gameBoard[4] && gameBoard[2] == gameBoard[6] && gameBoard[2] == move) {
            winType = new int[]{2, 2, 4};
            return true;
        } else return false;
    }

    public boolean winnerCheck() {
        if (winCondition(player)) {
            playAgainBTN.setVisibility(View.VISIBLE);
            homeBTN.setVisibility(View.VISIBLE);
            String pWon = player.name() + " перемогли!";
            playerTurn.setText(pWon);
            gameWon = true;
            return true;
        }
        if (table.isPlayerVsComputer()) {
            if (winCondition(computer)) {
                playAgainBTN.setVisibility(View.VISIBLE);
                homeBTN.setVisibility(View.VISIBLE);
                String cWon = computer.name() + " перемогли!";
                playerTurn.setText(cWon);
                gameWon = true;
                return true;
            }
        }
        if (tie()) {
            playAgainBTN.setVisibility(View.VISIBLE);
            homeBTN.setVisibility(View.VISIBLE);
            playerTurn.setText("Нічия!");
            return true;
        } else {
            return false;
        }
    }


    public void resetGame() {
        setPlayer(TicTacToeBoardView.TicTacToeModel.O);
        gameWon = false;
        gameBoard = new TicTacToeBoardView.TicTacToeModel[9];
        for (int i = 0; i < 9; i++) {
            gameBoard[i] = TicTacToeBoardView.TicTacToeModel.SPACE;
        }
        if (table.isPlayerVsComputer()) {
            setPlayers();
        }
        playerTurn.setText("");
        playAgainBTN.setVisibility(View.GONE);
        homeBTN.setVisibility(View.GONE);
    }

    public void setPlayAgainBTN(Button playAgainBTN) {
        this.playAgainBTN = playAgainBTN;
    }

    public void setHomeBTN(Button homeBTN) {
        this.homeBTN = homeBTN;
    }

    public void setPlayerTurn(TextView playerTurn) {
        this.playerTurn = playerTurn;
    }

    public TicTacToeBoardView.TicTacToeModel[] getGameBoard() {
        return gameBoard;
    }

    public TicTacToeBoardView.TicTacToeModel getComputer() {
        return computer;
    }

    public void setComputer(TicTacToeBoardView.TicTacToeModel computer) {
        this.computer = computer;
    }

    public void setPlayer(TicTacToeBoardView.TicTacToeModel player) {
        this.player = player;
    }

    public int[] getWinType() {
        return winType;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    private void setPlayers() {
        if (table.isXChoice()) {
            setPlayer(TicTacToeBoardView.TicTacToeModel.X);
            setComputer(TicTacToeBoardView.TicTacToeModel.O);
        }
        if (table.isOChoice()) {
            setPlayer(TicTacToeBoardView.TicTacToeModel.O);
            setComputer(TicTacToeBoardView.TicTacToeModel.X);
            int aiMove = playerVsComputer();
            gameBoard[aiMove] = computer;
        }
    }

    private void playerVsPlayer() {
        if (player == TicTacToeBoardView.TicTacToeModel.X) {
            setPlayer(TicTacToeBoardView.TicTacToeModel.O);
        } else {
            setPlayer(TicTacToeBoardView.TicTacToeModel.X);
        }
    }

    private int playerVsComputer() {

        if (table.isGameHard()) {
            return setAIHardMove();
            // Easy AI
        } else {
            return setAIEasyMove();
        }
    }

    private int setAIEasyMove() {
        while (!tie()) {
            int random = new Random().nextInt(9);
            if (getGameBoard()[random] == TicTacToeBoardView.TicTacToeModel.SPACE) {
                return random;
            }
        }
        return 0;
    }


    private int setAIHardMove() {
        int bestMove = 4;
        int bestScore = 2147483647;

        for (int i = 0; i < getGameBoard().length; i++) {
            if (gameBoard[i] == TicTacToeBoardView.TicTacToeModel.SPACE) {
                gameBoard[i] = computer;
                int score = minimax(false);
                gameBoard[i] = TicTacToeBoardView.TicTacToeModel.SPACE;
                if (score < bestScore) {
                    bestScore = score;
                    bestMove = i;
                }
                if (score == -1) {
                    break;
                }
            }
        }
        return bestMove;
    }

    private int minimax(boolean isMinimizing) {
        if (winCondition(computer)) {
            return -1;
        } else if (winCondition(player)) {
            return 1;
        } else if (tie()) {
            return 0;
        } else {
            int bestScore;
            if (isMinimizing) {
                bestScore = 2147483647;

                for (int i = 0; i < gameBoard.length; i++) {
                    if (gameBoard[i] == TicTacToeBoardView.TicTacToeModel.SPACE) {
                        gameBoard[i] = computer;
                        int score = this.minimax(false);
                        gameBoard[i] = TicTacToeBoardView.TicTacToeModel.SPACE;
                        if (score == -1) {
                            return score;
                        }
                        bestScore = Math.min(score, bestScore);
                    }
                }

                return bestScore;
            } else {
                bestScore = -2147483648;

                for (int i = 0; i < getGameBoard().length; i++) {
                    if (gameBoard[i] == TicTacToeBoardView.TicTacToeModel.SPACE) {
                        gameBoard[i] = player;
                        int score = this.minimax(true);
                        gameBoard[i] = TicTacToeBoardView.TicTacToeModel.SPACE;
                        if (score == 1) {
                            return score;
                        }
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    private boolean tie() { // нічия
        boolean tieTrue = true;
        for (TicTacToeBoardView.TicTacToeModel ticTacToeModels : getGameBoard()) {
            if (ticTacToeModels != TicTacToeBoardView.TicTacToeModel.SPACE) {
                tieTrue = false;
            } else {
                return false;
            }
        }
        return !tieTrue;
    }
}