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

    private TicTacToeBoard ticTacToeBoard;
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
        ticTacToeBoard = layout.findViewById(R.id.ticTacToeBoard);
        Button replayBtn = layout.findViewById(R.id.btn_play_again);
        Button homeBtn = layout.findViewById(R.id.btn_home);
        TextView playerWon = layout.findViewById(R.id.tv_player_won);
        replayBtn.setVisibility(View.GONE);
        homeBtn.setVisibility(View.GONE);

        ticTacToeBoard.setUpGame(replayBtn, homeBtn, playerWon);
        replayBtn.setOnClickListener(this::onButtonsClicked);
        homeBtn.setOnClickListener(this::onButtonsClicked);
        return layout;
    }

    @SuppressLint("NonConstantResourceId")
    public void onButtonsClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_play_again:
                ticTacToeBoard.reset();
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
        if (playerVsPlayer) {
            setPlayerVsPlayer(false);
        }
        if (playerVsComputer){
            setOChoice(false);
            setXChoice(false);
        }

    }

    public void setPlayerVsPlayer(boolean playerVsPlayer) {
        this.playerVsPlayer = playerVsPlayer;
    }

    public void setPlayerVsComputer(boolean playerVsComputer) {
        this.playerVsComputer = playerVsComputer;
    }

    public void setGameHard(boolean gameHard) {
        this.gameHard = gameHard;
    }

    public void setXChoice(boolean XChoice) {
        this.XChoice = XChoice;
    }

    public void setOChoice(boolean OChoice) {
        this.OChoice = OChoice;
    }

    public boolean isPlayerVsPlayer() {
        return playerVsPlayer;
    }

    public boolean isPlayerVsComputer() {
        return playerVsComputer;
    }

    public boolean isGameHard() {
        return gameHard;
    }

    public boolean isXChoice() {
        return XChoice;
    }

    public boolean isOChoice() {
        return OChoice;
    }
}