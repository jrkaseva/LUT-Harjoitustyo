package com.example.lutemongame.Game.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
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

public class GymFragment extends Fragment {
    private final TrainingArea STORAGE = TrainingArea.getInstance();
    private RecyclerView rv;
    private RadioGroup rg;


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

        ImageView lutemonImageAttacker = dialog.findViewById(R.id.imageViewAttackerTraining);
        ImageView lutemonImageDefender = dialog.findViewById(R.id.imageViewDefenderTraining);

        ArrayList<Integer> id_list = getCheckedLutemons();

        if (id_list.size() != 2) return;

        Lutemon attacker = STORAGE.getLutemons().get(id_list.get(0));
        Lutemon defender = STORAGE.getLutemons().get(id_list.get(1));
        lutemonImageAttacker.setImageResource(attacker != null ? attacker.getImage() : 0);
        //lutemonImageDefender.setImageResource(defender != null ? defender.getImage() : 0);

        TextView info = dialog.findViewById(R.id.textViewTraining);
        info.setText("Täällä taistellaa!");

        LutemonAnimation animation = new LutemonAnimation(dialog.getContext());
        LutemonAnimation animation2 = new LutemonAnimation(dialog.getContext());

        lutemonImageAttacker.startAnimation(animation.getSeqAnimation());
        info.setText("Nyt hyökätään!");
        lutemonImageDefender.startAnimation(animation2.getSeqAnimation());
        info.setText("Täällä puolustetaan!");


        //STORAGE.train(attacker, defender,false);

        Button btnExitTraining = dialog.findViewById(R.id.btnExitTraining);

        btnExitTraining.setOnClickListener(v -> {
            dialog.dismiss();
            //rv.setAdapter(new ShowLutemonAdapter(getActivity(), STORAGE.getLutemons()));
        });
        dialog.show();
    }
}