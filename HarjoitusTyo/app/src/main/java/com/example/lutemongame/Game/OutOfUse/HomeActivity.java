package com.example.lutemongame.Game.OutOfUse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.example.lutemongame.Game.Areas.Home;
import com.example.lutemongame.Game.Creatures.Black;
import com.example.lutemongame.Game.Creatures.Green;
import com.example.lutemongame.R;
import com.example.lutemongame.ShowLutemonAdapter;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private Home storage;
    private RecyclerView rv;
    private RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        storage = Home.getInstance();
        rv = findViewById(R.id.idRVHome);
        rg = findViewById(R.id.rgSendFromHome);

        storage.createLutemon(new Black("Kissa"));
        storage.createLutemon(new Green("Koira"));

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new ShowLutemonAdapter(getApplicationContext(), storage.getLutemons()));
    }

    public void switchToCreateLutemon(View view){
        Intent intent = new Intent(this, CreateLutemonActivity.class);
        startActivity(intent);
        rv.setAdapter(new ShowLutemonAdapter(getApplicationContext(), storage.getLutemons()));
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