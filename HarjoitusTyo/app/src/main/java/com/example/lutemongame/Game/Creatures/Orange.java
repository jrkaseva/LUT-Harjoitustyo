package com.example.lutemongame.Game.Creatures;

import com.example.lutemongame.R;

public class Orange extends Lutemon{

    public Orange(String name) {
        super(name);
        atk = 8;
        def = 1;
        health = 17; maxHealth = 17;
        color = "Orange";
        image = R.mipmap.ic_lutemon_orange_foreground;
    }
    
}
