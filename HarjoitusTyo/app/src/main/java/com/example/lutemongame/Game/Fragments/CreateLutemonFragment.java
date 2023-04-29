package com.example.lutemongame.Game.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lutemongame.Game.Areas.BattleField;
import com.example.lutemongame.Game.Areas.Home;
import com.example.lutemongame.Game.Areas.TrainingArea;
import com.example.lutemongame.Game.Creatures.Black;
import com.example.lutemongame.Game.Creatures.Green;
import com.example.lutemongame.Game.Creatures.Lutemon;
import com.example.lutemongame.Game.Creatures.Orange;
import com.example.lutemongame.Game.Creatures.Pink;
import com.example.lutemongame.Game.Creatures.White;
import com.example.lutemongame.MainActivity;
import com.example.lutemongame.R;

public class CreateLutemonFragment extends Fragment {
    private final int MAX_LUTEMONS = 10;
    private TextView lutemon_name;
    private RadioGroup rg;
    private TextView lutemon_count;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_lutemon, container, false);
        lutemon_name = view.findViewById(R.id.idEditLutemonName);
        rg = view.findViewById(R.id.rgColor);
        lutemon_count = view.findViewById(R.id.tvAmountOfLutemons);
        lutemon_count.setText(amountOfLutemons() + "/" + MAX_LUTEMONS);
        Button btnAdd = view.findViewById(R.id.btnAddNewLutemon);
        btnAdd.setOnClickListener(v -> createLutemon());
        Button btnCancel = view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
        return view;
    }

    @SuppressLint("NonConstantResourceId")
    public void createLutemon(){
        if (amountOfLutemons() >= MAX_LUTEMONS){
            Toast toast = Toast.makeText(getContext(), "Max amount of Lutemons created", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        Lutemon lutemon;
        String name = lutemon_name.getText().toString();
        switch (rg.getCheckedRadioButtonId()) {
            case R.id.rbWhite:
                lutemon = new White(name);
                break;
            case R.id.rbBlack:
                lutemon = new Black(name);
                break;
            case R.id.rbGreen:
                lutemon = new Green(name);
                break;
            case R.id.rbOrange:
                lutemon = new Orange(name);
                break;
            case R.id.rbPink:
                lutemon = new Pink(name);
                break;
            default:
                System.out.println("No color selected");
                lutemon = new Lutemon("Error");
        }

        Home.getInstance().createLutemon(lutemon);

        ((MainActivity)getActivity()).saveData();

        lutemon_name.setText("");
        Toast toast = Toast.makeText(getContext(), "Lutemon created", Toast.LENGTH_SHORT);
        toast.show();
        lutemon_count.setText(amountOfLutemons() + "/" + MAX_LUTEMONS);
    }
    public int amountOfLutemons() {
        return Home.getInstance().getLutemons().size() + TrainingArea.getInstance().getLutemons().size() + BattleField.getInstance().getLutemons().size();
    }
}