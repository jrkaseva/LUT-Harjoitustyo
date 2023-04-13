package com.example.lutemongame.Game.Creatures;

import com.example.lutemongame.R;

public class Black extends Lutemon{

    public Black(String name) {
        super(name);
        atk = 9;
        def = 0;
        health = 16; maxHealth = 16;
        color = "Black";
        image = R.mipmap.ic_lutemon_black_foreground;
    }
    
}
