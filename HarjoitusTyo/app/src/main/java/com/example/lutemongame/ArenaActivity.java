package com.example.lutemongame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lutemongame.Game.Areas.BattleField;
import com.example.lutemongame.Game.Areas.Home;
import com.example.lutemongame.Game.Creatures.Lutemon;
import com.example.lutemongame.Game.LutemonAnimation;

import java.util.ArrayList;
import java.util.HashMap;

public class ArenaActivity extends AppCompatActivity {
    private final BattleField STORAGE = BattleField.getInstance();
    private View decorView;
    private RecyclerView rv;
    private RadioGroup rg;
    private boolean doubleBackToExitPressedOnce = false;
    private HashMap<Integer, Lutemon> lutemons;
    private ArrayList<Integer> id_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arena);

        rv = findViewById(R.id.idRVArena);
        rg = findViewById(R.id.rgSendFromArena);

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
        rv.setAdapter(new ShowLutemonAdapter(this, BattleField.getInstance().getLutemons()));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            decorView.setSystemUiVisibility(hideSystemBars());
        }
        rv.setAdapter(new ShowLutemonAdapter(getApplicationContext(), BattleField.getInstance().getLutemons()));
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

    public void switchToHome(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void sendTo(View view){
        switch (rg.getCheckedRadioButtonId()) {
            case R.id.rbSendHome:
                for(int i : getCheckedLutemons()){
                    STORAGE.sendToHome(i);
                }
                break;
            case R.id.rbSendGym:
                for(int i : getCheckedLutemons()){
                    STORAGE.sendToTrain(i);
                }
                break;
            default:
                System.out.println("No destination selected");
                return;
        }
        rv.setAdapter(new ShowLutemonAdapter(getApplicationContext(), STORAGE.getLutemons()));
    }

    public ArrayList<Integer> getCheckedLutemons(){
        ArrayList<Integer> id_list = new ArrayList<>();
        for(int id : STORAGE.getLutemons().keySet()){
            if(STORAGE.getLutemons().get(id).isSelected()){
                id_list.add(id);
            }
        }
        return id_list;
    }

    @SuppressLint("SetTextI18n")
    public void showLutemonFight(View v){
        Dialog dialog = new Dialog(v.getRootView().getContext());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_fight);

        ImageView lutemonImageAttacker = dialog.findViewById(R.id.imageViewAttack);
        ImageView lutemonImageDefencer = dialog.findViewById(R.id.imageViewDefender);

       // TextView idNameColor = dialog.findViewById(R.id.dialogTVIdNameColor);
       /* TextView atk, def, exp, hp, wins, losses;
        atk = dialog.findViewById(R.id.dialogTVAtk);
        def = dialog.findViewById(R.id.dialogTVDef);
        exp = dialog.findViewById(R.id.dialogTVExp);
        hp = dialog.findViewById(R.id.dialogTVHp);
        wins = dialog.findViewById(R.id.dialogTVWins);
        losses = dialog.findViewById(R.id.dialogTVLosses);
*/
        id_list = getCheckedLutemons();

        if (id_list.size() != 2) return;

        Lutemon temp = STORAGE.getLutemons().get(id_list.get(0));
        Lutemon temp2 = STORAGE.getLutemons().get(id_list.get(1));
        lutemonImageAttacker.setImageResource(temp.getImage());
        lutemonImageDefencer.setImageResource(temp2.getImage());

        TextView info = dialog.findViewById(R.id.textViewFight);
        info.setText("Täällä taistellaa!");

        LutemonAnimation animation = new LutemonAnimation(dialog.getContext());
        LutemonAnimation animation2 = new LutemonAnimation(dialog.getContext());

        lutemonImageAttacker.startAnimation(animation.getSeqAnimation());
        info.setText("Nyt hyökätään!");
        lutemonImageDefencer.startAnimation(animation2.getSeqAnimation());
        info.setText("Täällä puolustetaan!");
        Button btnExit = dialog.findViewById(R.id.btnExit);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        //idNameColor.setText(temp.getIdNameColor());

        /*      atk.setText("ATK: " + temp.getAtk());
        def.setText("DEF: " + temp.getDef());
        exp.setText("EXP: " + temp.getExperience());
        hp.setText("HP: " + temp.getHealth() + "/" + temp.getMaxHealth());
        losses.setText("LOSSES: " + temp.getLosses());
        wins.setText("WINS: " + temp.getWins());
*/
        dialog.show();

    }
}