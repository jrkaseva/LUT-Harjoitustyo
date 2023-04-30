package com.example.lutemongame.Game.Creatures;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;

import com.example.lutemongame.R;

import java.io.Serializable;

public class Lutemon implements Serializable {
    private static int idCounter = 0;

    protected int image;
    protected int atk;
    protected int def;
    protected int experience = 0;

    protected int health;
    protected int maxHealth;

    protected int id;

    protected int wins = 0;
    protected int losses = 0;

    protected final String name;

    protected String color;
    protected boolean selected = false;

    /**
     * Constructor
     * @param name of Lutemon
     */
    public Lutemon(String name){
        this.name = name;
        id = ++idCounter;
    }


    /**
     * Trainer constructor
     * @param name of trainer
     * @param easy if easy trainer, else hard trainer
     */
    public Lutemon(String name, boolean easy){
        this.name = name;
        if(easy){
            atk = 3;
            def = 3;
            health = 12; maxHealth = 12;
        }
        else {
            atk = 10;
            def = 3;
            health = 20; maxHealth = 20;
        }
        color = "Black";
        image = R.mipmap.ic_lutemon_brown_foreground;
    }

    /**
     * @return true if Lutemon is alive (health > 0)
     */
    public boolean isAlive(){
        return health >= 1;
    }

    /**
     * @return total power of Lutemon's attack
     */
    public int attack(){
        int randomness = (int)(((Math.random() * 100) % 5) - ((Math.random() * 100) % 5));
        if (randomness < -1) System.out.println(getName() + " isn't focusing");
        else if (randomness <= 1) System.out.println(getName() + " is focused in battle");
        else if (randomness < 4) System.out.println(getName() + " is finding weak spots from the opponent");
        else System.out.println(getName() + " has been possessed by a warrior! Great attack incoming");
        return atk + randomness;
    }

    /**
     * @return name of Lutemon
     */
    public String getName(){
        return name;
    }

    /**
     * @param attacker which attacks this Lutemon
     */
    @SuppressLint("DefaultLocale")
    public String defense(Lutemon attacker){
        int damage = attacker.attack();
        System.out.printf("%s(%s) attacks %s(%s) [POWER: %d]%n",
                attacker.getColor(), attacker.getName(), this.getColor(), this.getName(), damage);
        if(damage > def) health -= (damage - def);
        return String.format("%s (%d) | %s (%d/%d)", attacker.name, damage, name, health, maxHealth);
    }

    /**
     * @param i amount to set experience to
     */
    public void setExp(int i){
        experience = i;
    }
    public void resetAtk(){
        atk = atk - experience;
    }
    public void resetDef(){
        def = def - experience;
    }
    /**
     * @param i added to existing experience points
     */
    public void gainExp(int i){
        experience += i;
        atk += i;
        def += i;
    }
    
    /**
     * @return ID of Lutemon
     */
    public int getId(){
        return id;
    }

    /**
     * Heals the Lutemon to full health
     */
    public void heal(){
        health = maxHealth;
    }

    /**
     * Get Lutemon as a string with most data
     * @return [id] color(name) atk: x; def: x; exp: x; health: x/x
     */
    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public String toString(){
        return String.format("[%d] %s(%s) atk: %d; def: %d; exp: %d; health: %d/%d",
        id, color, name, atk, def, experience, health, maxHealth);
    }

    /**
     * @return Atk-stat of Lutemon
     */
    public int getAtk() {
        return atk;
    }

    /**
     * @return Def-stat of Lutemon
     */
    public int getDef() {
        return def;
    }

    /**
     * @return Exp-stat of Lutemon
     */
    public int getExperience() {
        return experience;
    }

    /**
     * @return health of Lutemon
     */
    public int getHealth() {
        return health;
    }

    /**
     * @return Max health of Lutemon
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * @return win amount of Lutemon
     */
    public int getWins() {
        return wins;
    }

    /**
     * @return loss amount of Lutemon
     */
    public int getLosses() {
        return losses;
    }

    /**
     * Adds one win to Lutemon's wins
     */
    public void addWin(){
        wins++;
    }

    /**
     * Adds one loss to Lutemon's losses
     */
    public void addLoss(){
        losses++;
    }

    /**
     * true if Lutemon has been selected in a RecyclerView
     * @return true if selected
     */
    public boolean isSelected(){
        return selected;
    }

    /**
     * Selects/deselects a Lutemon in a RecyclerView
     * @param b true if to be selected, false if to be deselected
     */
    public void select(boolean b){
        selected = b;
        if (selected) System.out.println("Selected " + name);
    }

    /**
     * Getter for color
     * @return color of Lutemon
     */
    public String getColor() {
        return color;
    }

    /**
     * Getter for id, color and name
     * @return [id] color(name)
     */
    public String getIdNameColor(){
        return "[" + id + "] " + name + " (" + color + ")";
    }

    public int getImage() {
        return image;
    }

    public static void setIdCounter(int i){
        idCounter = i;
    }
}
