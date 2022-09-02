package com.example.tictactoe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class PlayTypeFragment extends Fragment {

    private GameTableFragment gtFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_play_type, container, false);
        layout.findViewById(R.id.bv_x_choice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fl_frame, new GameTableFragment());
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                GameTableFragment.setXChoice(true);
                ft.commit();
            }
        });
        layout.findViewById(R.id.bv_o_choice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fl_frame, new GameTableFragment());
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                GameTableFragment.setOChoice(true);
                ft.commit();
            }
        });
        return layout;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GameTableFragment.setGameHard(false);
    }
}