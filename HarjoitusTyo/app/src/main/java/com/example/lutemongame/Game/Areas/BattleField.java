package com.example.lutemongame.Game.Areas;

import com.example.lutemongame.Game.Creatures.*;

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

    /**
     * Displays the fight sequence between two lutemons.
     * @param lutemon_1 first lutemon to attack
     * @param lutemon_2 first lutemon to defend
     */
    public void fight(Lutemon lutemon_1, Lutemon lutemon_2, boolean skip_battle){
        if(lutemon_1 == null || lutemon_2 == null){
            System.out.println("Error in starting battle with Lutemons: null Lutemon");
            return;
        }
        else if (lutemon_1 == lutemon_2){
            System.out.println("Error in starting battle with Lutemons: same Lutemon");
            return;
        }
        battleSequence(lutemon_1, lutemon_2, skip_battle);
    }

    public void testFight(Lutemon lutemon_1, Lutemon lutemon_2, boolean skip_battle, int amount){
        int amount_lutemon_1_won = 0;
        int amount_lutemon_2_won = 0;
        int amount_attacker_won = 0;
        int amount_defender_won = 0;
        if(lutemon_1 == null || lutemon_2 == null){
            System.out.println("Error in starting battle with Lutemons: null Lutemon");
        }
        else if (lutemon_1 == lutemon_2){
            System.out.println("Error in starting battle with Lutemons: same Lutemon");
        }
        boolean swap = false;
        while(amount > 0){
            int result = -1;
            if(swap) result = testFightPhase2(lutemon_2, lutemon_1, skip_battle);
            else result = testFightPhase2(lutemon_1, lutemon_2, skip_battle);
            switch (result){
                case 1:
                    if(swap) amount_lutemon_2_won++;
                    else amount_lutemon_1_won++;
                    amount_attacker_won++;
                    break;
                case 2:
                    if (swap) amount_lutemon_1_won++;
                    else amount_lutemon_2_won++;
                    amount_defender_won++;
                    break;
                default:
                    System.out.println("Error in getting result of battle");
            }
            amount--;
            swap = !swap;
        }
        String[] strings = new String[]{"\nATTACKER WON: %d BATTLES\n", "DEFENDER WON: %d BATTLES\n", "%s WON: %d BATTLES\n", "%s WON: %d BATTLES\n"};
        System.out.println(String.format(strings[0] + strings[1] + strings[2] + strings[3], 
        amount_attacker_won, amount_defender_won, lutemon_1.getColorName(), amount_lutemon_1_won, lutemon_2.getColorName(), amount_lutemon_2_won));
    }

    private int testFightPhase2(Lutemon lutemon_1, Lutemon lutemon_2, boolean skip_battle){
        Lutemon winner = testBattleSequence(lutemon_1, lutemon_2, skip_battle);
        if (winner.equals(lutemon_1)){
            System.out.println("THE WINNER IS " + winner.getColorName() + "... as excpected");
            return 1;
        } else {
            System.out.println("THE UNDERDOG HAS WON! CONGRATULATIONS " + winner.getColorName() + "!");
            return 2;
        }
    }

    private Lutemon testBattleSequence(Lutemon lutemon_1, Lutemon lutemon_2, boolean skip_battle) {
        Lutemon attacker = lutemon_1;
        Lutemon defender = lutemon_2;
        while(true){
            if (!skip_battle){
                System.out.println(attacker);
                System.out.println(defender);
            }
            defender.defense(attacker, skip_battle);
            if (defender.isAlive()){
                if(!skip_battle) System.out.println(defender.getColorName() + " avoided fainting");
                Lutemon temp = defender;
                defender = attacker;
                attacker = temp;
            }
            else {
                endBattle(defender, attacker, false);
                return attacker;
            }
        }
    }

    public void battleSequence(Lutemon lutemon_1, Lutemon lutemon_2, boolean skip_battle){
        Lutemon attacker = lutemon_1;
        Lutemon defender = lutemon_2;
        while(true){
            if (!skip_battle){
                System.out.println(attacker);
                System.out.println(defender);
            }
            defender.defense(attacker, skip_battle);
            if (defender.isAlive()){
                if(!skip_battle) System.out.println(defender.getColorName() + " avoided fainting");
                Lutemon temp = defender;
                defender = attacker;
                attacker = temp;
            }
            else {
                System.out.println(("\n" + defender.getColorName() + " passed out. "  + attacker.getColorName() + " won the battle").toUpperCase());
                endBattle(defender, attacker, true);
                return;
            }
        }
    }


    /**
     * Aftermath of battle, defender lost
     * @param defender who died
     * @param attacker who won the battle
     */
    public void endBattle(Lutemon defender, Lutemon attacker, boolean send_loser_home) {
        if (send_loser_home){
            defender.setExp(0);
            attacker.gainExp(1);
            sendHome(defender.getId());
        } 
        else{
            attacker.heal();
            defender.heal();
        }
    }
}
