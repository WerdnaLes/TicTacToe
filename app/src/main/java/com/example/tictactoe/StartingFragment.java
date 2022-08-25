package com.example.tictactoe;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class StartingFragment extends Fragment {
    private GameTableFragment gtFragment;
    private SetDifficultyFragment stFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ConstraintLayout layout = (ConstraintLayout)
                inflater.inflate(R.layout.fragment_starting, container, false);
        layout.findViewById(R.id.bv_ai_choice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gtFragment = new GameTableFragment();
                stFragment = new SetDifficultyFragment();
                FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fl_frame, stFragment);
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                gtFragment.setPlayerVsComputer(true);
                ft.commit();
            }
        });
        layout.findViewById(R.id.bv_player_choice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gtFragment = new GameTableFragment();
                FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fl_frame, gtFragment);
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                gtFragment.setPlayerVsPlayer(true);
                ft.commit();
            }
        });
        return layout;
    }
}