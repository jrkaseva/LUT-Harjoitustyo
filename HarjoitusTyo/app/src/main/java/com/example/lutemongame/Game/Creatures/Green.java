package com.example.lutemongame.Game.Creatures;

import com.example.lutemongame.R;

public class Green extends Lutemon{

    public Green(String name) {
        super(name);
        atk = 6;
        def = 3;
        health = 19; maxHealth = 19;
        color = "Green";
        image = R.mipmap.ic_lutemon_green_foreground;
    }
    
}
