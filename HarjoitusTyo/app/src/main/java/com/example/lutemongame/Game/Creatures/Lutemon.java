package com.example.lutemongame.Game.Creatures;

public class Lutemon {
    private static int idCounter = 0;
    /**
     * @return latest ID created
     */
    public static int getIdCounter() {
        return idCounter;
    }

    /**
     * @return amount of Lutemons created, AKA highest ID
     */
    public static int getNumberOfCreatedLutemons(){
        return idCounter;
    }
    public static void main(String[] args){
        Lutemon snow = new White("Snow");
        Lutemon coal = new Black("Coal");
        System.out.println(snow);
        System.out.println(coal);
    }
    protected int atk;
    protected int def;
    protected int experience = 0;

    protected int health;
    protected int maxHealth;

    protected int id;

    protected int wins = 0;
    protected int losses = 0;

    protected String name;

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
     * @return true if Lutemon is alive (health > 0)
     */
    public boolean isAlive(){
        if(health < 1) return false;
        return true;
    }

    /**
     * @param skip_battle
     * @return total power of Lutemon's attack
     */
    public int attack(boolean skip_battle){
        int randomness = (int)(((Math.random() * 100) % 5) - ((Math.random() * 100) % 5));
        if(!skip_battle){
            if (randomness < -1) System.out.println(getName() + " isn't focusing");
            else if (-1 <= randomness && randomness <= 1) System.out.println(getName() + " is focused in battle");
            else if (randomness > 1 && randomness < 4) System.out.println(getName() + " is finding weak spots from the opponent");
            else if (randomness >= 4) System.out.println(getName() + " has been possessed by a warrior! Great attack incoming");
        }
        return atk + experience + randomness;
    }

    /**
     * @return name of Lutemon
     */
    public String getName(){
        return name;
    }

    /**
     * @param attacker which attacks this Lutemon
     * @param skip_battle
     */
    public void defense(Lutemon attacker, boolean skip_battle){
        int damage = attacker.attack(skip_battle);
        if (!skip_battle) System.out.println(String.format("%s(%s) attacks %s(%s) [POWER: %d]",
         attacker.getColor(), attacker.getName(), this.getColor(), this.getName(), damage));
        if(damage > def) health -= (damage - def);
    }

    /**
     * @param i amount to set experience to
     */
    public void setExp(int i){
        experience = i;
    }

    /**
     * @param i added to existing experience points
     */
    public void gainExp(int i){
        experience += i;
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

    public void addWin(){
        wins++;
    }

    public void addLoss(){
        losses++;
    }

    public void resetLutemon(){
        wins = 0;
        losses = 0;
        experience = 0;
    }
    public boolean isSelected(){
        return selected;
    }

    public void select(boolean b){
        selected = b;
        System.out.println("Selected Lutemon");
    }

    /**
     * @return Color of Lutemon
     */
    public String getColor() {
        return color;
    }

    public String getColorName(){
        return color + "(" + name + ")";
    }

    public String getIdName(){
        return "[" + id + "] " + name;
    }

    public String getIdColorName(){
        return "[" + id + "] " + color + "(" + name + ")";
    }
}
