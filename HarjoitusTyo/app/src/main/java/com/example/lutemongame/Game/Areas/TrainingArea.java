package com.example.lutemongame.Game.Areas;

public class TrainingArea extends Storage {
    private static TrainingArea storage = null;

    /**
     * @return static instance of TrainingArea
     */
    public static TrainingArea getInstance() {
        if (storage == null){
            storage = new TrainingArea();
        }
        return storage;
    }

    /**
     * Constructor to assign name
     */
    private TrainingArea(){
        name = "Gym";
    }
}
