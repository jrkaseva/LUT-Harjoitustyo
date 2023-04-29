package com.example.lutemongame.Game.Areas;

import java.io.File;
import java.io.Serializable;
import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import com.example.lutemongame.Game.Creatures.Lutemon;

public abstract class Storage implements Serializable {
    private static final long serialVersionUID = 1234567;
    protected String name;
    protected HashMap<Integer, Lutemon> lutemons = new HashMap<>();

    /**
     * @return Hashmap of Lutemons with their ID and themselves (<int ID, Lutemon lutemon>)
     */
    public HashMap<Integer, Lutemon> getLutemons(){
        return lutemons;
    }

    /**
     * Lists all the Lutemons in the hashmap
     */
    public void listLutemons(){
        System.out.println("Lutemons in [" + name + "]:");
        for (int i : lutemons.keySet()){
            System.out.println(lutemons.get(i));
        }
    }

    /**
     * @param id of target Lutemon
     * @return Lutemon with the ID of id
     */
    public Lutemon getLutemon(int id) throws BadIdException{
        if(checkIdExists(id)) return lutemons.get(id);
        throw new BadIdException("No Lutemon with ID: " + id);
    }

    /**
     * @param id lutemon to be sent to Home
     */
    public void sendToHome(int id){
        try {
            Lutemon l = getLutemon(id); l.heal();
            sendTo(Home.getInstance(), l);
        } catch (BadIdException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param id lutemon to be sent to TrainingArea
     */
    public void sendToTrain(int id){
        try {
            Lutemon l = getLutemon(id);
            sendTo(TrainingArea.getInstance(), l);
        } catch (BadIdException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param id lutemon to be sent to BattleField
     */
    public void sendToBattleField(int id){
        try {
            Lutemon l = getLutemon(id);
            sendTo(BattleField.getInstance(), l);
        } catch (BadIdException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @return name of Lutemon
     */
    public String getName() {
        return name;
    }

    /**
     * @param lutemon to add to hashmap
     */
    private void addLutemon(Lutemon lutemon){
        if (lutemon == null){
            System.out.println("Error: No lutemon to add");
            return;
        }
        lutemons.put(lutemon.getId(), lutemon);
    }

    /**
     * @param place to be sent to
     * @param lutemon to be sent
     */
    private void sendTo(Storage place, Lutemon lutemon) {
        if (lutemon == null || place == null){
            System.out.println("Error: No lutemon or destination");
            return;
        }
        place.addLutemon(lutemon);
        this.removeLutemon(lutemon);
        System.out.println("---------------------------------------\n" + lutemon.getName() + " sent to the " + place.getName());
        lutemon.select(false);
    }

    /**
     * @param lutemon to be removed from hashmap
     */
    private void removeLutemon(Lutemon lutemon) {
        lutemons.remove(lutemon.getId());
    }

    /**
     * Checks if HashMap contains key with "id"
     * @param id to be found
     * @return true if found
     */
    protected boolean checkIdExists(int id){
        return lutemons.containsKey(id);
    }

    public void saveLutemon(Context context, String filename){
        try {
            ObjectOutputStream lutemonWriter = new ObjectOutputStream(context.openFileOutput(filename, Context.MODE_PRIVATE));
            lutemonWriter.writeObject(lutemons);
            lutemonWriter.close();
            System.out.println("Data saved!");
        } catch (IOException e) {
            System.out.println("Error saving data");
            e.printStackTrace();
        }
    }

    public void loadLutemon(Context context, String filename){
        try {
            ObjectInputStream lutemonReader = new ObjectInputStream(context.openFileInput(filename));
            lutemons = (HashMap<Integer, Lutemon>) lutemonReader.readObject();
            System.out.println("Data Read!");
            lutemonReader.close();
        } catch (Exception e) {
            System.out.println("Error: Couldn't read data");
            e.printStackTrace();
        }
    }

    public int getHighestID(){
        int max = -1;
        for(int i : lutemons.keySet()){
            if (i > max) max = i;
        }
        return max;
    }

}
