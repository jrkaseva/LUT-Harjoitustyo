package com.example.lutemongame.Game.Creatures;

import com.example.lutemongame.R;

public class Pink extends Lutemon{

    public Pink(String name) {
        super(name);
        atk = 7;
        def = 2;
        health = 18; maxHealth = 18;
        color = "Pink";
        image = R.mipmap.ic_lutemon_pink_foreground;
    }
    
}
