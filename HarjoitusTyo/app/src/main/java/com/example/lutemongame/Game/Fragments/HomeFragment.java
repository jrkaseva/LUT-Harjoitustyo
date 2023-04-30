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
import com.example.lutemongame.MainActivity;
import com.example.lutemongame.R;
import com.example.lutemongame.ShowLutemonAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment {
    private final Home STORAGE = Home.getInstance();
    private RecyclerView rv;
    private RadioGroup rg;


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
        Button transfer = view.findViewById(R.id.btnHomeTransferLutemons);
        transfer.setOnClickListener(v -> ((MainActivity) requireActivity()).sendTo(rg, STORAGE, rv, getCheckedLutemons()));
        Button createLutemon = view.findViewById(R.id.btnTrainLutemon);
        createLutemon.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameMain, new CreateLutemonFragment())
                .addToBackStack(null)
                .commit());
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
}