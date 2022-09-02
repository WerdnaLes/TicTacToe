package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class GameTableFragment extends Fragment {

    private TicTacToeBoardView ticTacToeBoard;
    private GameLogic gameLogic;
    private static boolean playerVsPlayer = false;
    private static boolean playerVsComputer = false;
    private static boolean gameHard = false;
    private static boolean XChoice = false;
    private static boolean OChoice = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_table, container, false);
        gameLogic = new GameLogic();
        ticTacToeBoard = layout.findViewById(R.id.ticTacToeBoard);
        ticTacToeBoard.setCallback(gameLogic);
        ticTacToeBoard.setGame(gameLogic);

        Button replayBtn = layout.findViewById(R.id.btn_play_again);
        Button homeBtn = layout.findViewById(R.id.btn_home);
        TextView playerWon = layout.findViewById(R.id.tv_player_won);

        replayBtn.setVisibility(View.GONE);
        homeBtn.setVisibility(View.GONE);

        gameLogic.setUpGame(replayBtn, homeBtn, playerWon);
        replayBtn.setOnClickListener(this::onButtonsClicked);
        homeBtn.setOnClickListener(this::onButtonsClicked);
        return layout;
    }

    @SuppressLint("NonConstantResourceId")
    public void onButtonsClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_play_again:
                gameLogic.resetGame();
                ticTacToeBoard.invalidate();
                break;
            case R.id.btn_home:
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isPlayerVsPlayer()) {
            setPlayerVsPlayer(false);
        }
        if (isPlayerVsComputer()){
            setOChoice(false);
            setXChoice(false);
        }

    }

    public static void setPlayerVsPlayer(boolean playerVsPlayer) {
        GameTableFragment.playerVsPlayer = playerVsPlayer;
    }

    public static void setPlayerVsComputer(boolean playerVsComputer) {
        GameTableFragment.playerVsComputer = playerVsComputer;
    }

    public static void setGameHard(boolean gameHard) {
        GameTableFragment.gameHard = gameHard;
    }

    public static void setXChoice(boolean XChoice) {
        GameTableFragment.XChoice = XChoice;
    }

    public static void setOChoice(boolean OChoice) {
        GameTableFragment.OChoice = OChoice;
    }

    public static boolean isPlayerVsPlayer() {
        return playerVsPlayer;
    }

    public static boolean isPlayerVsComputer() {
        return playerVsComputer;
    }

    public static boolean isGameHard() {
        return gameHard;
    }

    public static boolean isXChoice() {
        return XChoice;
    }

    public static boolean isOChoice() {
        return OChoice;
    }
}