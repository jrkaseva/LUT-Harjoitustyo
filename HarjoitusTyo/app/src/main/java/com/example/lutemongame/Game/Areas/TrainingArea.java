package com.example.lutemongame.Game.Areas;

import android.app.Dialog;
import android.widget.TextView;

import com.example.lutemongame.Game.Creatures.Lutemon;
import com.example.lutemongame.R;

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

    /*public void trainingFight(Lutemon lutemon_1, Lutemon lutemon_2){
        if(lutemon_1 == null || lutemon_2 == null){
            System.out.println("Error in starting battle with Lutemons: null Lutemon");
            return;
        }
        else if (lutemon_1 == lutemon_2){
            System.out.println("Error in starting battle with Lutemons: same Lutemon");
            return;
        }
        trainingBattleSequence(lutemon_1, lutemon_2);
    }

    public void trainingBattleSequence(Lutemon lutemon_1, Lutemon lutemon_2){
        Lutemon attacker = lutemon_1;
        Lutemon defender = lutemon_2;
        while(true){

            defender.defense(attacker);
            if (defender.isAlive()){
                System.out.println(defender.getColorName() + " avoided fainting");
                Lutemon temp = defender;
                defender = attacker;
                attacker = temp;
            }
            else {
                System.out.println(("\n" + defender.getColorName() + " passed out. "  + attacker.getColorName() + " won the battle").toUpperCase());
                endTrainingBattle(defender, attacker);
                return;
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void endTrainingBattle(Lutemon defender, Lutemon attacker) {
        attacker.gainExp(1);
        attacker.heal();
        defender.heal();
    }*/
}
