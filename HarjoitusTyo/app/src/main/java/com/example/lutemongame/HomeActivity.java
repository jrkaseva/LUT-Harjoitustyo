package com.example.lutemongame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.example.lutemongame.Game.Areas.Home;
import com.example.lutemongame.Game.Creatures.Lutemon;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private Home storage;
    private View decorView;
    private RecyclerView rv;
    private RadioGroup rg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        storage = Home.getInstance();
        rv = findViewById(R.id.idRVHome);
        rg = findViewById(R.id.rgSendFromHome);

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
    }

    public void switchToArena(View view){
        Intent intent = new Intent(this, ArenaActivity.class);
        startActivity(intent);
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