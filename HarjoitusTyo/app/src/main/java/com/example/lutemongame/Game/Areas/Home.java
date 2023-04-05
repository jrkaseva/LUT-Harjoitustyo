package com.example.lutemongame.Game.Areas;

import com.example.lutemongame.Game.Creatures.Lutemon;

public class Home extends Storage{
    private static Home storage = null;

    /**
     * @return static instance of Home
     */
    public static Home getInstance() {
        if (storage == null){
            storage = new Home();
        }
        return storage;
    }

    /**
     * Constructor to assign name
     */
    private Home(){
        name = "Resting area";
    }
    
    /**
     * Creates a new Lutemon
     * @param lutemon to create
     */
    public void createLutemon(Lutemon lutemon){
        getInstance().getLutemons().put(lutemon.getId(), lutemon);
        System.out.println("NEW LUTEMON CREATED (ID: [" + lutemon.getId() + "])");
    }
}
