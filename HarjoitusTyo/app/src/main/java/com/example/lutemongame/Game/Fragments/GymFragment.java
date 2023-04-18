package com.example.lutemongame.Game.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lutemongame.Game.Areas.BattleField;
import com.example.lutemongame.Game.Areas.TrainingArea;
import com.example.lutemongame.R;
import com.example.lutemongame.ShowLutemonAdapter;

import java.util.ArrayList;

public class GymFragment extends Fragment {
    private final TrainingArea STORAGE = TrainingArea.getInstance();
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
        View view = inflater.inflate(R.layout.fragment_gym, container, false);
        rv = view.findViewById(R.id.idRVGym);
        rg = view.findViewById(R.id.rgSendFromGym);
        transfer = view.findViewById(R.id.btnGymTransferLutemons);
        transfer.setOnClickListener(v -> {
            sendTo();
        });
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new ShowLutemonAdapter(getActivity(), STORAGE.getLutemons()));
        return view;

    }

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
            if(STORAGE.getLutemons().get(id).isSelected()){
                id_list.add(id);
            }
        }
        return id_list;
    }
}