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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lutemongame.Game.Areas.TrainingArea;
import com.example.lutemongame.Game.Creatures.Lutemon;
import com.example.lutemongame.Game.LutemonAnimation;
import com.example.lutemongame.MainActivity;
import com.example.lutemongame.R;
import com.example.lutemongame.ShowLutemonAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class GymFragment extends Fragment {
    private final TrainingArea STORAGE = TrainingArea.getInstance();
    private RecyclerView rv;
    private RadioGroup rg;
    private boolean swap = false;
    private final Lutemon easy = new Lutemon("Trainer easy", true);
    private final Lutemon hard = new Lutemon("Trainer hard", false);

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
        transfer.setOnClickListener(v -> ((MainActivity) requireActivity()).sendTo(rg, STORAGE, rv, getCheckedLutemons()));
        Button train = view.findViewById(R.id.btnTrainLutemon);
        train.setOnClickListener(v -> showChooseDifficulty());
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

    public void showChooseDifficulty(){
        ArrayList<Integer> id_list = getCheckedLutemons();
        if (id_list.size() != 1) {
            Toast toast = Toast.makeText(getContext(), "Choose 1 Lutemon", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        Dialog dialog = new Dialog(getActivity());
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_choose_difficulty);
        dialog.show();
        Button train = dialog.findViewById(R.id.btnConfirmDifficulty);
        train.setOnClickListener(v -> {
            int difficulty = getDifficulty(dialog);
            if (difficulty != -1) {
                showTrainingArea(difficulty);
                dialog.dismiss();
            }
        });
    }
    @SuppressLint("NonConstantResourceId")
    public int getDifficulty(Dialog dialog){
        RadioGroup rgDifficult = dialog.findViewById(R.id.rgChooseDifficulty);
        int difficulty = -1;
        switch (rgDifficult.getCheckedRadioButtonId()) {
            case R.id.rbEasy:
                difficulty = 0;
                break;
            case R.id.rbHard:
                difficulty = 1;
                break;
            default:
                Toast toast = Toast.makeText(getContext(), "Choose a difficulty", Toast.LENGTH_SHORT);
                toast.show();
        }
        return difficulty;
    }
    public void showTrainingArea(int difficulty){
        Dialog dialog = new Dialog(getActivity());
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_train);
        ArrayList<Integer> id_list = getCheckedLutemons();

        Lutemon attacker = STORAGE.getLutemons().get(id_list.get(0));
        boolean trainEasy = difficulty != 1;

        Button btnExitTraining = dialog.findViewById(R.id.btnExitTraining);
        dialog.show();

        btnExitTraining.setOnClickListener(v -> {
            dialog.dismiss();
            rv.setAdapter(new ShowLutemonAdapter(getActivity(), STORAGE.getLutemons()));
        });
        Objects.requireNonNull(attacker).select(false);
        if(trainEasy) fightTraining(attacker, easy, dialog, difficulty);
        else fightTraining(attacker, hard, dialog, difficulty);
    }

    public void fightTraining(Lutemon attacker, Lutemon defender, Dialog dialog, int difficulty){
        ImageView left = dialog.findViewById(R.id.imageViewAttackerTraining);
        ImageView right = dialog.findViewById(R.id.imageViewDefenderTraining);
        left.setImageResource(attacker != null ? attacker.getImage() : 0);
        right.setImageResource(defender != null ? defender.getImage() : 0);

        LutemonAnimation animation = new LutemonAnimation(dialog.getContext());
        left.startAnimation(animation.getSlideAnimation());
        right.startAnimation(animation.getSlideAnimation());

        TextView info = dialog.findViewById(R.id.textViewFightTraining);
        Button round = dialog.findViewById(R.id.btnRoundTraining);

        round.setOnClickListener(v -> {
            if(roundOfFightTraining(dialog, attacker, defender, left, right, info, difficulty)) round.setVisibility(View.GONE);
        });

    }

    public void endFightTraining(Lutemon defender, Lutemon attacker, int difficulty) {
        attacker.heal();
        defender.heal();
        if(attacker.equals(easy) || attacker.equals(hard)) return;
        switch (difficulty){
            case 0:
                attacker.gainExp(1);
                break;
            case 1:
                attacker.gainExp(2);
                break;
        }
        ((MainActivity) requireActivity()).saveData();
    }

    public boolean roundOfFightTraining(Dialog dialog, Lutemon attacker, Lutemon defender, ImageView left, ImageView right, TextView info, int difficulty){

        LutemonAnimation animation = new LutemonAnimation(dialog.getContext());
        Lutemon leftLutemon = attacker;
        Handler handler = new Handler(Looper.getMainLooper());

        if (swap){
            Lutemon temp = attacker;
            attacker = defender;
            defender = temp;
        }

        if (attacker.equals(leftLutemon)) {
            left.startAnimation(animation.getHitLeftAnimation());
            right.startAnimation(animation.getRotateAnimation());

/*            TimerTask attack = new TimerTask() {
                @Override
                public void run() {
                    right.startAnimation(animation.getRotateAnimation());
                }
            };
            timer.schedule(attack, 450);*/
        } else {
            right.startAnimation(animation.getHitRightAnimation());
            left.startAnimation(animation.getRotateAnimation());
           /* TimerTask attack = new TimerTask() {
                @Override
                public void run() {
                    left.startAnimation(animation.getRotateAnimation());
                }
            };
            timer.schedule(attack, 450);*/
        }

        String addToText = defender.defense(attacker);
        @SuppressLint("SetTextI18n") Runnable addText = () -> info.setText(info.getText() + "\n" + addToText);
        handler.postDelayed(addText, 1400);

        if (defender.isAlive()) {
            swap = !swap;
        } else {
            TextView winner = dialog.findViewById(R.id.textViewWinnerTraining);
            Lutemon attacker1 = attacker;
            endFightTraining(defender, attacker, difficulty);
            @SuppressLint("SetTextI18n") Runnable end = () -> {
                winner.setText(attacker1.getName() + " is the winner!");
                dialog.findViewById(R.id.btnExitTraining).setVisibility(View.VISIBLE);
            };
            handler.postDelayed(end, 1400);
            if (attacker.equals(leftLutemon)) {
             //   left.startAnimation(animation.getBounceAnimation());
             //   right.startAnimation(animation.getZoomAnimation());
                Runnable end3 = () -> {
                        left.startAnimation(animation.getBounceAnimation());
                        right.startAnimation(animation.getZoomAnimation());
                    };
                handler.postDelayed(end3, 1400);
            } else {
            Runnable end2 = () -> {
                right.startAnimation(animation.getBounceAnimation());
                left.startAnimation(animation.getZoomAnimation());
            };
            handler.postDelayed(end2, 1400);

               /* TimerTask attack = new TimerTask() {
                    @Override
                    public void run() {
                        right.startAnimation(animation.getBounceAnimation());
                        left.startAnimation(animation.getZoomAnimation());
                    }
                };
                timer.schedule(attack, 1400);*/
            }
            return true;
        }
        return false;
    }
}