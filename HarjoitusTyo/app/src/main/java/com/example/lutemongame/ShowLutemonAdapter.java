package com.example.lutemongame;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lutemongame.Game.Creatures.Lutemon;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowLutemonAdapter extends RecyclerView.Adapter<LutemonViewHolder> {
    private Context context;
    private HashMap<Integer, Lutemon> lutemons;
    private ArrayList<Integer> id_list;

    public ShowLutemonAdapter(Context context, @NonNull HashMap<Integer, Lutemon> lutemons) {
        this.context = context;
        this.lutemons = lutemons;
        id_list = new ArrayList<>();
        id_list.addAll(lutemons.keySet());
    }

    @NonNull
    @Override
    public LutemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LutemonViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_lutemon, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LutemonViewHolder holder, int position) {
        if(id_list.size() == 0) return;
        holder.lutemonImage.setImageResource(lutemons.get(id_list.get(position)).getImage());
        holder.lutemonName.setText(lutemons.get(id_list.get(position)).getName());
        holder.lutemonColor.setText("(" + lutemons.get(id_list.get(position)).getColor() + ")");
        holder.lutemonWins.setText("WINS: " + lutemons.get(id_list.get(position)).getWins());
        holder.lutemonLosses.setText("LOSSES: " + lutemons.get(id_list.get(position)).getLosses());
        int pos = holder.getAdapterPosition();
        holder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.cb.isChecked()) lutemons.get(id_list.get(pos)).select(true);
                else lutemons.get(id_list.get(pos)).select(false);
            }
        });
        // DOESN'T WORK
        holder.lutemonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLutemonInfo(pos, v);
            }
        });
    }

    public void showLutemonInfo(int pos, View v){
        Dialog dialog = new Dialog(v.getRootView().getContext());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_lutemon);

        ImageView lutemonImage = dialog.findViewById(R.id.dialogImageView);
        TextView idNameColor = dialog.findViewById(R.id.dialogTVIdNameColor);
        TextView atk, def, exp, hp, wins, losses;
        atk = dialog.findViewById(R.id.dialogTVAtk);
        def = dialog.findViewById(R.id.dialogTVDef);
        exp = dialog.findViewById(R.id.dialogTVExp);
        hp = dialog.findViewById(R.id.dialogTVHp);
        wins = dialog.findViewById(R.id.dialogTVWins);
        losses = dialog.findViewById(R.id.dialogTVLosses);

        Lutemon temp = lutemons.get(id_list.get(pos));
        lutemonImage.setImageResource(temp.getImage());
        idNameColor.setText(temp.getIdNameColor());
        atk.setText("ATK: " + temp.getAtk());
        def.setText("DEF: " + temp.getDef());
        exp.setText("EXP: " + temp.getExperience());
        hp.setText("HP: " + temp.getHealth() + "/" + temp.getMaxHealth());
        losses.setText("LOSSES: " + temp.getLosses());
        wins.setText("WINS: " + temp.getWins());

        dialog.show();
    }

    @Override
    public int getItemCount() {
        return lutemons.size();
    }
}
