package com.example.lutemongame.Game.OutOfUse;

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
import com.example.lutemongame.Game.Areas.TrainingArea;
import com.example.lutemongame.R;

import java.util.ArrayList;

/**
 * Class not in use anymore. Delete for final version
 */
public class GymActivity extends AppCompatActivity {
    private final TrainingArea STORAGE = TrainingArea.getInstance();
    private RecyclerView rv;
    private RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rv = findViewById(R.id.idRVGym);
        rg = findViewById(R.id.rgSendFromGym);

    }

    public void sendTo(View view){
        switch (rg.getCheckedRadioButtonId()) {
            case R.id.rbSendArena:
                for (int i : getCheckedLutemons()) {
                    STORAGE.sendToBattleField(i);
                }
                break;
            case R.id.rbSendHome:
                for (int i : getCheckedLutemons()) {
                    STORAGE.sendToHome(i);
                }
                break;
            default:
                System.out.println("No destination selected");
        }
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
}