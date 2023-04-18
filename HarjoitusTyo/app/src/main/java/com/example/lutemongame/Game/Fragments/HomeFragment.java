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

import com.example.lutemongame.Game.Areas.Home;
import com.example.lutemongame.R;
import com.example.lutemongame.ShowLutemonAdapter;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private final Home STORAGE = Home.getInstance();
    private RecyclerView rv;
    private RadioGroup rg;
    private Button transfer;
    private Button createLutemon;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rv = view.findViewById(R.id.idRVHome);
        rg = view.findViewById(R.id.rgSendFromHome);
        transfer = view.findViewById(R.id.btnHomeTransferLutemons);
        transfer.setOnClickListener(v -> {
            sendTo();
        });
        createLutemon = view.findViewById(R.id.btnCreateLutemon);
        createLutemon.setOnClickListener(v ->{
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameMain, new CreateLutemonFragment())
                    .addToBackStack(null)
                    .commit();
        });
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new ShowLutemonAdapter(getActivity(), STORAGE.getLutemons()));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void sendTo(){
        switch (rg.getCheckedRadioButtonId()) {
            case R.id.rbSendArena:
                for(int i : getCheckedLutemons()){
                    STORAGE.sendToBattleField(i);
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
}