package com.example.lutemongame.Game.Fragments;

import static java.util.concurrent.TimeUnit.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.lutemongame.Game.Areas.BattleField;
import com.example.lutemongame.Game.Creatures.Lutemon;
import com.example.lutemongame.Game.LutemonAnimation;
import com.example.lutemongame.MainActivity;
import com.example.lutemongame.R;
import com.example.lutemongame.ShowLutemonAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class ArenaFragment extends Fragment {
    private final BattleField STORAGE = BattleField.getInstance();
    private RecyclerView rv;
    private RadioGroup rg;
    private boolean swap = false;


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
        Button transfer = view.findViewById(R.id.btnArenaTransferLutemons);
        transfer.setOnClickListener(v -> ((MainActivity)getActivity()).sendTo(rg, STORAGE, rv, getCheckedLutemons()));
        Button fight = view.findViewById(R.id.btnBattleLutemon);
        fight.setOnClickListener(v -> showLutemonFight());
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new ShowLutemonAdapter(getActivity(), STORAGE.getLutemons()));
        return view;
    }

    public ArrayList<Integer> getCheckedLutemons(){
        ArrayList<Integer> id_list = new ArrayList<>();
        for(int id : STORAGE.getLutemons().keySet()){
            if(Objects.requireNonNull(STORAGE.getLutemons().get(id)).isSelected()){
                id_list.add(id);
            }
        }
        return id_list;
    }

    @SuppressLint("SetTextI18n")
    public void showLutemonFight(){
        Dialog dialog = new Dialog(getActivity());
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_fight);
        ArrayList<Integer> id_list = getCheckedLutemons();

        if (id_list.size() != 2) return;

        Lutemon attacker = STORAGE.getLutemons().get(id_list.get(0));
        Lutemon defender = STORAGE.getLutemons().get(id_list.get(1));

        Button btnExit = dialog.findViewById(R.id.btnExit);
        dialog.show();

        btnExit.setOnClickListener(v -> {
            dialog.dismiss();
            rv.setAdapter(new ShowLutemonAdapter(getActivity(), STORAGE.getLutemons()));
        });
        attacker.select(false);
        defender.select(false);

        fight(attacker, defender, dialog);
    }

    public void fight(Lutemon attacker, Lutemon defender, Dialog dialog){
        ImageView left = dialog.findViewById(R.id.imageViewAttack);
        ImageView right = dialog.findViewById(R.id.imageViewDefender);
        left.setImageResource(attacker != null ? attacker.getImage() : 0);
        right.setImageResource(defender != null ? defender.getImage() : 0);

        TextView info = dialog.findViewById(R.id.textViewFight);
        Button round = dialog.findViewById(R.id.btnRound);

        Lutemon leftLutemon = attacker;
        Lutemon rightLutemon = defender;
        round.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(roundOfFight(dialog, leftLutemon, rightLutemon, left, right, info)) round.setVisibility(View.GONE);
            }
        });

    }

    public void endFight(Lutemon defender, Lutemon attacker, boolean send_loser_home) {
        attacker.addWin();
        defender.addLoss();
        if (send_loser_home){
            defender.resetAtk();
            defender.resetDef();
            defender.setExp(0);
            attacker.gainExp(1);
            STORAGE.sendToHome(defender.getId());
        }
        else{
            attacker.heal();
            defender.heal();
        }
        ((MainActivity)getActivity()).saveData();
    }

    public boolean roundOfFight(Dialog dialog, Lutemon attacker, Lutemon defender, ImageView left, ImageView right, TextView info){

        LutemonAnimation animation = new LutemonAnimation(dialog.getContext(),attacker);
        LutemonAnimation animation2 = new LutemonAnimation(dialog.getContext(),defender);
        Lutemon leftLutemon = attacker;

        if (swap){
            Lutemon temp = attacker;
            attacker = defender;
            defender = temp;
        }

        if (attacker.equals(leftLutemon)) {
            // Attacker move towards and defense rotate
            left.startAnimation(animation.getHitLeftAnimation());
            right.startAnimation(animation2.getFavoriteAnimation());
        } else {
            // Attacker move towards and defense rotate
            right.startAnimation(animation2.getHitRightAnimation());
            left.startAnimation(animation.getFavoriteAnimation());
        }

        String addToText = defender.defense(attacker);
        info.setText(info.getText() + "\n" + addToText);
        if (defender.isAlive()) {
            swap = !swap;
        } else {
            TextView winner = dialog.findViewById(R.id.textViewWinner);
            winner.setText(attacker.getName() + " is the winner!");

            if (attacker.equals(leftLutemon)) {
                // Winner Slide in and Looser Zoom out...
                left.startAnimation(animation.getSlideAnimation());
                right.startAnimation(animation2.getZoomAnimation());
            } else {
                // Winner Slide in and Looser Zoom out...
                right.startAnimation(animation2.getSlideAnimation());
                left.startAnimation(animation.getZoomAnimation());
            }

            endFight(defender, attacker, true);
            dialog.findViewById(R.id.btnExit).setVisibility(View.VISIBLE);
            return true;
        }
        return false;
    }
}