package com.example.lutemongame.Game.Creatures;

import com.example.lutemongame.R;

public class TrainingOpponentEasy extends Lutemon{

    public TrainingOpponentEasy(String name) {
        super(name);
        atk = 1;
        def = 3;
        health = 12; maxHealth = 12;
        color = "Black";
        image = R.mipmap.ic_lutemon_black_foreground;
    }
}
