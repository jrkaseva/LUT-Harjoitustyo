package com.example.lutemongame.Game.Areas;


public class BattleField extends Storage{
    private static BattleField storage = null;

    /**
     * @return static instance of BattleField
     */
    public static BattleField getInstance() {
        if (storage == null){
            storage = new BattleField();
        }
        return storage;
    }

    /**
     * Constructor to assign name
     */
    private BattleField(){
        name = "Arena";
    }
}
