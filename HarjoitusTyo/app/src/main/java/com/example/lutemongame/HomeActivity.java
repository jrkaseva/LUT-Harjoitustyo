package com.example.lutemongame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.lutemongame.Game.Areas.Home;
import com.example.lutemongame.Game.Creatures.Black;
import com.example.lutemongame.Game.Creatures.Green;
import com.example.lutemongame.Game.Creatures.Lutemon;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private Home storage;
    private View decorView;
    private RecyclerView rv;
    private RadioGroup rg;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        storage = Home.getInstance();
        rv = findViewById(R.id.idRVHome);
        rg = findViewById(R.id.rgSendFromHome);

        boolean first = true;
        if (first) {
            storage.createLutemon(new Black("Kissa"));
            storage.createLutemon(new Green("Koira"));
            first = false;
        }
        storage.sendToBattleField(2);
        storage.sendToBattleField(1);

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(visibility -> {
            if (visibility == 0){
                decorView.setSystemUiVisibility(hideSystemBars());
            }
        });

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new ShowLutemonAdapter(getApplicationContext(), storage.getLutemons()));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            decorView.setSystemUiVisibility(hideSystemBars());
        }
        rv.setAdapter(new ShowLutemonAdapter(getApplicationContext(), storage.getLutemons()));
    }
    /**
     * https://stackoverflow.com/questions/8430805/clicking-the-back-button-twice-to-exit-an-activity
     */
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    public void switchToCreateLutemon(View view){
        Intent intent = new Intent(this, CreateLutemonActivity.class);
        startActivity(intent);
        rv.setAdapter(new ShowLutemonAdapter(getApplicationContext(), storage.getLutemons()));
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
        Intent intent = new Intent(this, GymActivity.class);
        startActivity(intent);
        finish();
    }

    public void switchToArena(View view){
        Intent intent = new Intent(this, ArenaActivity.class);
        startActivity(intent);
        finish();
    }

    public void sendTo(View view){
        switch (rg.getCheckedRadioButtonId()) {
            case R.id.rbSendArena:
                for(int i : getCheckedLutemons()){
                    storage.sendToBattleField(i);
                }
                break;
            case R.id.rbSendGym:
                for(int i : getCheckedLutemons()){
                    storage.sendToTrain(i);
                }
                break;
            default:
                System.out.println("No destination selected");
                return;
        }
        rv.setAdapter(new ShowLutemonAdapter(getApplicationContext(), storage.getLutemons()));
    }

    public ArrayList<Integer> getCheckedLutemons(){
        ArrayList<Integer> id_list = new ArrayList<>();
        for(int id : storage.getLutemons().keySet()){
            if(storage.getLutemons().get(id).isSelected()){
                id_list.add(id);
            }
        }
        return id_list;
    }
}