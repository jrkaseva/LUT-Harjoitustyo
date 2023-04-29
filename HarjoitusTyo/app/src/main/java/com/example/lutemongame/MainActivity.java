package com.example.lutemongame;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lutemongame.Game.Areas.BadIdException;
import com.example.lutemongame.Game.Areas.BattleField;
import com.example.lutemongame.Game.Areas.Home;
import com.example.lutemongame.Game.Areas.Storage;
import com.example.lutemongame.Game.Areas.TrainingArea;
import com.example.lutemongame.Game.Creatures.Black;
import com.example.lutemongame.Game.Creatures.Green;
import com.example.lutemongame.Game.Creatures.Lutemon;
import com.example.lutemongame.Game.Creatures.Orange;
import com.example.lutemongame.Game.Creatures.Pink;
import com.example.lutemongame.Game.Creatures.White;
import com.example.lutemongame.Game.Fragments.ArenaFragment;
import com.example.lutemongame.Game.Fragments.GymFragment;
import com.example.lutemongame.Game.Fragments.HomeFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private View decorView;
    private boolean doubleBackToExitPressedOnce = false;
    private LinearLayout layoutButtons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layoutButtons = findViewById(R.id.linearLayoutMain);

        loadData();
        deselectAll();

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(visibility -> {
            if (visibility == 0){
                decorView.setSystemUiVisibility(hideSystemBars());
            }
        });

        // Setting Home as default place
        layoutButtons.setBackgroundResource(R.color.home);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameMain, new HomeFragment())
                .commit();
        // createTestLutemons();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            decorView.setSystemUiVisibility(hideSystemBars());
        }
    }

    /**
     * <a href="https://stackoverflow.com/questions/8430805/clicking-the-back-button-twice-to-exit-an-activity">...</a>
     */
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(() -> doubleBackToExitPressedOnce=false, 2000);
    }

    private int hideSystemBars(){
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }

    public void switchToGym(View view){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameMain, new GymFragment())
                .commit();
        layoutButtons.setBackgroundResource(R.color.gym);
    }

    public void switchToArena(View view){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameMain, new ArenaFragment())
                .commit();
        layoutButtons.setBackgroundResource(R.color.arena);
    }

    public void switchToHome(View view){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameMain, new HomeFragment())
                .commit();
        layoutButtons.setBackgroundResource(R.color.home);
    }

    public void createTestLutemons(){
        Home Storage = Home.getInstance();
        Storage.createLutemon(new Green("Goblin"));
        Storage.createLutemon(new Orange("Orangutan"));
        Storage.createLutemon(new White("Walter"));
        Storage.createLutemon(new Pink("Panther"));
        Storage.createLutemon(new Black("Betty"));
        Storage.createLutemon(new Black("Panther"));
        Storage.createLutemon(new Pink("Flamingo"));
        Storage.createLutemon(new White("Winter"));
    }

    private void loadData(){
        Home.getInstance().loadLutemon(this, "home.data");
        int count = Home.getInstance().getLutemons().size();
        System.out.println("Count of Lutemons: " + count);
        Home.getInstance().listLutemons();
        if (getHighestID() != -1) Lutemon.setIdCounter(getHighestID() + 1);

        TrainingArea.getInstance().loadLutemon(this, "gym.data");
        BattleField.getInstance().loadLutemon(this, "arena.data");
    }

    public void saveData(){
        TrainingArea.getInstance().saveLutemon(this,"gym.data");
        BattleField.getInstance().saveLutemon(this, "arena.data");
        Home.getInstance().saveLutemon(this, "home.data");
    }

    @SuppressLint("NonConstantResourceId")
    public void sendTo(RadioGroup rg, Storage storage, RecyclerView rv, ArrayList<Integer> arr){
        switch (rg.getCheckedRadioButtonId()) {
            case R.id.rbSendHome:
                for(int i : arr){
                    storage.sendToHome(i);
                }
                break;
            case R.id.rbSendGym:
                for(int i : arr){
                    storage.sendToTrain(i);
                }
                break;
            case R.id.rbSendArena:
                for(int i : arr){
                    storage.sendToBattleField(i);
                }
                break;
            default:
                System.out.println("No destination selected");
        }
        rv.setAdapter(new ShowLutemonAdapter(this, storage.getLutemons()));
        saveData();
    }

    private int getHighestID() {
        int max = Home.getInstance().getHighestID();
        if (BattleField.getInstance().getHighestID() > max)
            max = BattleField.getInstance().getHighestID();
        if (TrainingArea.getInstance().getHighestID() > max)
            max = TrainingArea.getInstance().getHighestID();
        return max;
    }

    public void removeLutemon(int id) {
        try {
            BattleField.getInstance().removeLutemon(BattleField.getInstance().getLutemon(id));
        } catch (BadIdException e) {
            System.out.println(e.getMessage() + "[BATTLEFIELD]");
        }
        try {
            TrainingArea.getInstance().removeLutemon(TrainingArea.getInstance().getLutemon(id));
        } catch (BadIdException e) {
            System.out.println(e.getMessage() + "[TRAININGAREA]");
        }
        try {
            Home.getInstance().removeLutemon(Home.getInstance().getLutemon(id));
        } catch (BadIdException e) {
            System.out.println(e.getMessage() + "[HOME]");
        }
    }

    public void deselectAll(){
        Home.getInstance().deselectAll();
        BattleField.getInstance().deselectAll();
        TrainingArea.getInstance().deselectAll();
    }
}