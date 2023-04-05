package com.example.lutemongame.Game;

import com.example.lutemongame.Game.Areas.*;
import com.example.lutemongame.Game.Creatures.*;

/**
 * Example application. Testing area for different classes and methods
 */
public class AppTest {
    static BattleField arena = BattleField.getInstance();
    static TrainingArea gym = TrainingArea.getInstance();
    static Home home = Home.getInstance();

    static int count = 0;
    public static void main(String[] args){
        initialize();
        addLutemon(10);
        listAllLutemons();
        home.sendToTrain(10);
        home.sendToTrain(11);
        home.sendToTrain(12);
        home.sendToTrain(13);

        listAllLutemons();
        System.out.println(Lutemon.getNumberOfCreatedLutemons());

        simulateBattle(1, 2);

        simulateError();

        fastSimulateBattle(3, 4, 30);
    }

    private static void simulateError() {
        // No Lutemon with ID Error
        System.out.println(-1);

        // No Lutemon in BattleField
        for(int i : arena.getLutemons().keySet()){
            // Emptying BattleField
            arena.sendHome(i);
        }
        simulateBattle(1, 2);

        // Trying to fight Lutemon with itself
        home.sendToBattleField(1);
        home.sendToBattleField(2);
        simulateBattle(1, 1);
    }

    public static void initialize(){
        home.createLutemon(new White("Valkohai"));
        home.createLutemon(new Black("Musta ritari"));
        home.createLutemon(new Pink("Panther"));
        home.createLutemon(new Green("Gnome"));
        home.createLutemon(new Orange("Orangutan"));

        listAllLutemons();

        home.sendToBattleField(1);
        home.sendToBattleField(2);

        listAllLutemons();
    }

    public static void listAllLutemons(){
        System.out.println("---------------------------------------");
        home.listLutemons();
        arena.listLutemons();
        gym.listLutemons();
    }

    public static void addLutemon(){
        System.out.println("---------------------------------------");
        home.createLutemon(new Lutemon("Lutemon " + count));
        count++;
    }

    public static void addLutemon(int i){
        System.out.println("---------------------------------------");
        while(i > 0){
            home.createLutemon(new Lutemon("Lutemon " + count));
            count++;
            i--;
        }
    }

    public static void simulateBattle(int fighter_1_ID, int fighter_2_ID){
        System.out.println("---------------------------------------\nGoing to battle");
        try {
            arena.fight(arena.getLutemon(fighter_1_ID), arena.getLutemon(fighter_2_ID), false);
        } catch (BadIdException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void fastSimulateBattle(int fighter_1_ID, int fighter_2_ID, int amount){
        home.sendToBattleField(fighter_1_ID);
        home.sendToBattleField(fighter_2_ID);
        System.out.println("---------------------------------------\nSIMULATING BATTLE " + amount + " TIMES");
        try {
            arena.testFight(arena.getLutemon(fighter_1_ID), arena.getLutemon(fighter_2_ID), true, amount);
        } catch (BadIdException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        arena.sendHome(fighter_1_ID);
        arena.sendHome(fighter_2_ID);
    }

}
