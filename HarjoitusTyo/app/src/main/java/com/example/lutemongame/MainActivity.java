package com.example.lutemongame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lutemongame.Game.Areas.BattleField;
import com.example.lutemongame.Game.Areas.Home;
import com.example.lutemongame.Game.Areas.TrainingArea;
import com.example.lutemongame.Game.Creatures.Black;
import com.example.lutemongame.Game.Creatures.Green;
import com.example.lutemongame.Game.Creatures.Orange;
import com.example.lutemongame.Game.Creatures.Pink;
import com.example.lutemongame.Game.Creatures.White;
import com.example.lutemongame.Game.Fragments.ArenaFragment;
import com.example.lutemongame.Game.Fragments.GymFragment;
import com.example.lutemongame.Game.Fragments.HomeFragment;

public class MainActivity extends AppCompatActivity {
    private View decorView;
    private boolean doubleBackToExitPressedOnce = false;
    private LinearLayout layoutButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layoutButtons = findViewById(R.id.linearLayoutMain);
        // Load Data
        Home.getInstance().loadLutemon(this, "home.data");
        int id = Home.getInstance().getLutemons().size();
        System.out.println("Count of Lutemons: "+ id);

        TrainingArea.getInstance().loadLutemon(this, "gym.data");
        BattleField.getInstance().loadLutemon(this, "arena.data");

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
        //createTestLutemons();
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
            TrainingArea.getInstance().saveLutemon(this,"gym.data");
            BattleField.getInstance().saveLutemon(this, "arena.data");
            Home.getInstance().saveLutemon(this, "home.data");
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
        // Creating default Lutemons for testing
        Home Storage = Home.getInstance();
        Storage.createLutemon(new Green("Goblin"));
        Storage.createLutemon(new Orange("Orangutan"));
        Storage.createLutemon(new White("Walter"));
        Storage.createLutemon(new Pink("Panther"));
        Storage.createLutemon(new Black("Betty"));
        Storage.createLutemon(new Black("Panther"));
        Storage.createLutemon(new Pink("Flamingo"));
        Storage.createLutemon(new White("Winter"));
        Storage.sendToBattleField(1);
        Storage.sendToBattleField(2);
        Storage.sendToBattleField(3);
        Storage.sendToBattleField(4);
        Storage.sendToTrain(5);
        Storage.sendToTrain(6);
    }

    @Override
    protected void onDestroy() {
        TrainingArea.getInstance().saveLutemon(this,"gym.data");
        BattleField.getInstance().saveLutemon(this, "arena.data");
        Home.getInstance().saveLutemon(this, "home.data");
        super.onDestroy();
    }
}