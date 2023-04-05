package com.example.lutemongame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.lutemongame.Game.Areas.Home;
import com.example.lutemongame.Game.Areas.TrainingArea;

public class GymActivity extends AppCompatActivity {
    private final TrainingArea STORAGE = TrainingArea.getInstance();
    private View decorView;
    private RecyclerView rv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym);

        rv = findViewById(R.id.idRVGym);

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0){
                    decorView.setSystemUiVisibility(hideSystemBars());
                }
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new ShowLutemonAdapter(getApplicationContext(), TrainingArea.getInstance().getLutemons()));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            decorView.setSystemUiVisibility(hideSystemBars());
        }
        rv.setAdapter(new ShowLutemonAdapter(getApplicationContext(), TrainingArea.getInstance().getLutemons()));
    }

    public void switchToCreateLutemon(View view){
        Intent intent = new Intent(this, CreateLutemonActivity.class);
        startActivity(intent);
    }

    private int hideSystemBars(){
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }

    public void switchToHome(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void switchToArena(View view){
        Intent intent = new Intent(this, ArenaActivity.class);
        startActivity(intent);
    }
}