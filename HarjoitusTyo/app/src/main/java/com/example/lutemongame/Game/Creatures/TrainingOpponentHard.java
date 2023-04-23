package com.example.lutemongame.Game.Creatures;

import com.example.lutemongame.R;

public class TrainingOpponentHard extends Lutemon{

    public TrainingOpponentHard(String name) {
        super(name);
        atk = 10;
        def = 3;
        health = 20; maxHealth = 20;
        color = "Black";
        image = R.mipmap.ic_lutemon_black_foreground;
    }
}
