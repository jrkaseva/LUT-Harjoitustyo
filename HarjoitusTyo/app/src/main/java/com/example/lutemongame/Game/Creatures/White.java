package com.example.lutemongame.Game.Creatures;

import com.example.lutemongame.R;

public class White extends Lutemon{

    public White(String name) {
        super(name);
        atk = 5;
        def = 4;
        health = 20; maxHealth = 20;
        color = "White";
        image = R.mipmap.ic_lutemon_white_foreground;
    }
    
}
