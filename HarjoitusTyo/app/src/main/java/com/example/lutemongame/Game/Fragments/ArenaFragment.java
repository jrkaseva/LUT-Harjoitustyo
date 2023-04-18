package com.example.lutemongame.Game.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.lutemongame.Game.Areas.BattleField;
import com.example.lutemongame.Game.Creatures.Lutemon;
import com.example.lutemongame.Game.LutemonAnimation;
import com.example.lutemongame.R;
import com.example.lutemongame.ShowLutemonAdapter;

import java.util.ArrayList;

public class ArenaFragment extends Fragment {
    private final BattleField STORAGE = BattleField.getInstance();
    private RecyclerView rv;
    private RadioGroup rg;
    private Button transfer;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_arena, container, false);
        rv = view.findViewById(R.id.idRVArena);
        rg = view.findViewById(R.id.rgSendFromArena);
        transfer = view.findViewById(R.id.btnArenaTransferLutemons);
        transfer.setOnClickListener(v -> {
            sendTo();
        });
        Button fight = view.findViewById(R.id.btnBattleLutemon);
        fight.setOnClickListener(v -> {
            showLutemonFight();
        });
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new ShowLutemonAdapter(getActivity(), STORAGE.getLutemons()));
        return view;

    }

    public void sendTo(){
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
        }
        rv.setAdapter(new ShowLutemonAdapter(getActivity(), STORAGE.getLutemons()));
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
    public void showLutemonFight(){
        Dialog dialog = new Dialog(getActivity());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_fight);

        ImageView lutemonImageAttacker = dialog.findViewById(R.id.imageViewAttack);
        ImageView lutemonImageDefender = dialog.findViewById(R.id.imageViewDefender);

        ArrayList<Integer> id_list = getCheckedLutemons();

        if (id_list.size() != 2) return;

        Lutemon temp = STORAGE.getLutemons().get(id_list.get(0));
        Lutemon temp2 = STORAGE.getLutemons().get(id_list.get(1));
        lutemonImageAttacker.setImageResource(temp.getImage());
        lutemonImageDefender.setImageResource(temp2.getImage());

        TextView info = dialog.findViewById(R.id.textViewFight);
        info.setText("Täällä taistellaa!");

        LutemonAnimation animation = new LutemonAnimation(dialog.getContext());
        LutemonAnimation animation2 = new LutemonAnimation(dialog.getContext());

        lutemonImageAttacker.startAnimation(animation.getSeqAnimation());
        info.setText("Nyt hyökätään!");
        lutemonImageDefender.startAnimation(animation2.getSeqAnimation());
        info.setText("Täällä puolustetaan!");
        Button btnExit = dialog.findViewById(R.id.btnExit);

        STORAGE.fight(temp, temp2,false);


        btnExit.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
        rv.setAdapter(new ShowLutemonAdapter(getActivity(), STORAGE.getLutemons()));
    }
}