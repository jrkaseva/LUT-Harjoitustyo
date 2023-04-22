package com.example.lutemongame.Game.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lutemongame.Game.Areas.TrainingArea;
import com.example.lutemongame.Game.Creatures.Lutemon;
import com.example.lutemongame.Game.LutemonAnimation;
import com.example.lutemongame.R;
import com.example.lutemongame.ShowLutemonAdapter;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class GymFragment extends Fragment {
    private final TrainingArea STORAGE = TrainingArea.getInstance();
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
        View view = inflater.inflate(R.layout.fragment_gym, container, false);
        rv = view.findViewById(R.id.idRVGym);
        rg = view.findViewById(R.id.rgSendFromGym);
        Button transfer = view.findViewById(R.id.btnGymTransferLutemons);
        transfer.setOnClickListener(v -> sendTo());
        Button train = view.findViewById(R.id.btnTrainLutemon);
        train.setOnClickListener(v -> showTrainingArea());
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new ShowLutemonAdapter(getActivity(), STORAGE.getLutemons()));
        return view;

    }

    @SuppressLint("NonConstantResourceId")
    public void sendTo(){
        switch (rg.getCheckedRadioButtonId()) {
            case R.id.rbSendArena:
                for(int i : getCheckedLutemons()){
                    STORAGE.sendToBattleField(i);
                }
                break;
            case R.id.rbSendHome:
                for(int i : getCheckedLutemons()){
                    STORAGE.sendToHome(i);
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
            if(Objects.requireNonNull(STORAGE.getLutemons().get(id)).isSelected()){
                id_list.add(id);
            }
        }
        return id_list;
    }

    public void showTrainingArea(){
        Dialog dialog = new Dialog(getActivity());
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_train);
        ArrayList<Integer> id_list = getCheckedLutemons();

        //ImageView lutemonImageAttacker = dialog.findViewById(R.id.imageViewAttackerTraining);
        //ImageView lutemonImageDefender = dialog.findViewById(R.id.imageViewDefenderTraining);

        if (id_list.size() != 2) return;

        Lutemon attacker = STORAGE.getLutemons().get(id_list.get(0));
        Lutemon defender = STORAGE.getLutemons().get(id_list.get(1));
        //lutemonImageAttacker.setImageResource(attacker != null ? attacker.getImage() : 0);
        //lutemonImageDefender.setImageResource(defender != null ? defender.getImage() : 0);

        //TextView info = dialog.findViewById(R.id.textViewFightTraining);
        //info.setText("Taistelu alkakoon!");

        //LutemonAnimation animation = new LutemonAnimation(dialog.getContext());
        //LutemonAnimation animation2 = new LutemonAnimation(dialog.getContext());

        //Timer timer = new Timer();
        /*TimerTask slideIn = new TimerTask() {
            @Override
            public void run() {
                lutemonImageAttacker.startAnimation(animation.getRightAnimation());
                info.setText("Nyt hyökätään!");
                setDelay(700);
                lutemonImageDefender.startAnimation(animation.getRotateAnimation());
                info.setText("Täällä puolustetaan!");
                setDelay(1000);
                info.setText("Taistelu on päättynyt.");
                lutemonImageAttacker.startAnimation(animation.getBounceAnimation());
                lutemonImageDefender.startAnimation(animation.getBounceAnimation());
            }
        };
        timer.schedule(slideIn, 2000);*/

        //lutemonImageAttacker.startAnimation(animation.getSlideAnimation());
        //lutemonImageDefender.startAnimation(animation.getSlideAnimation());
/*
        TimerTask startTrainingBattle = new TimerTask() {
            @Override
            public void run() {
                STORAGE.trainingFight(attacker, defender);
            }
        };
        timer.schedule(startTrainingBattle, 3000);*/
        /*TimerTask startBattle = new TimerTask() {
            @Override
            public void run() {
                int i = 0;
                while(true){
                    if (i % 2 == 0) {
                        defender.defense(attacker);
                    } else {
                        attacker.defense(defender);
                    }
                    if (i % 2 == 0) {
                        if (defender.isAlive()){
                            info.setText(defender.getColorName() + " avoided fainting");
                        } else {
                            info.setText(("\n" + defender.getColorName() + " passed out. "  + attacker.getColorName() + " won the battle").toUpperCase());
                            attacker.gainExp(1);
                            attacker.heal();
                            defender.heal();
                            return;
                        }
                    } else {
                        if (attacker.isAlive()) {
                            info.setText(attacker.getColorName() + " avoided fainting");
                        } else {
                            info.setText(("\n" + attacker.getColorName() + " passed out. " + defender.getColorName() + " won the battle").toUpperCase());
                            defender.gainExp(1);
                            attacker.heal();
                            defender.heal();
                            return;
                        }
                    }
                    i++;
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        timer.schedule(startBattle, 3000);*/

        Button btnExitTraining = dialog.findViewById(R.id.btnExitTraining);
        dialog.show();

        btnExitTraining.setOnClickListener(v -> {
            dialog.dismiss();
            rv.setAdapter(new ShowLutemonAdapter(getActivity(), STORAGE.getLutemons()));
        });
        attacker.select(false);
        defender.select(false);

        fightTraining(attacker, defender, dialog);
    }
    public void setDelay(int delay){
        System.out.println("Viivytetään...");
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
            //Thread.currentThread().interrupt();
        }
    }

    public void fightTraining(Lutemon attacker, Lutemon defender, Dialog dialog){
        ImageView left = dialog.findViewById(R.id.imageViewAttackerTraining);
        ImageView right = dialog.findViewById(R.id.imageViewDefenderTraining);
        left.setImageResource(attacker != null ? attacker.getImage() : 0);
        right.setImageResource(defender != null ? defender.getImage() : 0);

        LutemonAnimation animation = new LutemonAnimation(dialog.getContext());
        left.startAnimation(animation.getSlideAnimation());
        right.startAnimation(animation.getSlideAnimation());

        TextView info = dialog.findViewById(R.id.textViewFightTraining);
        Button round = dialog.findViewById(R.id.btnRoundTraining);

        Lutemon leftLutemon = attacker;
        Lutemon rightLutemon = defender;
        round.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(roundOfFightTraining(dialog, leftLutemon, rightLutemon, left, right, info)) round.setVisibility(View.GONE);
            }
        });

    }

    public void endFightTraining(Lutemon defender, Lutemon attacker) {
        attacker.gainExp(1);
        attacker.heal();
        defender.heal();
    }

    public boolean roundOfFightTraining(Dialog dialog, Lutemon attacker, Lutemon defender, ImageView left, ImageView right, TextView info){

        LutemonAnimation animation = new LutemonAnimation(dialog.getContext());
        Lutemon leftLutemon = attacker;
        Timer timer = new Timer();
        Handler handler = new Handler(Looper.getMainLooper());

        if (swap){
            Lutemon temp = attacker;
            attacker = defender;
            defender = temp;
        }

        if (attacker.equals(leftLutemon)) {
            left.startAnimation(animation.getHitRightAnimation());
            TimerTask attack = new TimerTask() {
                @Override
                public void run() {
                    right.startAnimation(animation.getRotateAnimation());
                }
            };
            timer.schedule(attack, 450);
        } else {
            right.startAnimation(animation.getHitLeftAnimation());
            TimerTask attack = new TimerTask() {
                @Override
                public void run() {
                    left.startAnimation(animation.getRotateAnimation());
                }
            };
            timer.schedule(attack, 450);
        }

        String addToText = defender.defense(attacker);
        Runnable addText = () -> info.setText(info.getText() + "\n" + addToText);
        handler.postDelayed(addText, 1400);

        if (defender.isAlive()) {
            swap = !swap;
        } else {
            TextView winner = dialog.findViewById(R.id.textViewWinnerTraining);
            winner.setText(attacker.getName() + " is the winner!");
            endFightTraining(defender, attacker);
            Runnable end = () -> {
                dialog.findViewById(R.id.btnExitTraining).setVisibility(View.VISIBLE);
            };
            handler.postDelayed(end, 1400);
            if (attacker.equals(leftLutemon)) {
                TimerTask attack = new TimerTask() {
                    @Override
                    public void run() {
                        left.startAnimation(animation.getBounceAnimation());
                        right.startAnimation(animation.getZoomAnimation());
                    }
                };
                timer.schedule(attack, 1400);
            } else {
                TimerTask attack = new TimerTask() {
                    @Override
                    public void run() {
                        right.startAnimation(animation.getBounceAnimation());
                        left.startAnimation(animation.getZoomAnimation());
                    }
                };
                timer.schedule(attack, 1400);
            }
            return true;
        }
        return false;
    }
}