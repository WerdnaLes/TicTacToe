package com.example.tictactoe;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SetDifficultyFragment extends Fragment {

    PlayTypeFragment ptFragment;
    private GameTableFragment gtFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.fragment_set_difficulty, container, false);
        layout.findViewById(R.id.btn_easy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ptFragment = new PlayTypeFragment();
                gtFragment = new GameTableFragment();
                FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fl_frame, ptFragment);
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        });
        layout.findViewById(R.id.btn_hard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ptFragment = new PlayTypeFragment();
                gtFragment = new GameTableFragment();
                FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fl_frame, ptFragment);
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                gtFragment.setGameHard(true);
                ft.commit();
            }
        });
        return layout;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        gtFragment = new GameTableFragment();
        gtFragment.setPlayerVsComputer(false);
    }
}