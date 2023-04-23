package com.example.lutemongame;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lutemongame.Game.Creatures.Lutemon;
import com.example.lutemongame.Game.LutemonAnimation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ShowLutemonAdapter extends RecyclerView.Adapter<LutemonViewHolder> {
    private final Context context;
    private final HashMap<Integer, Lutemon> lutemons;
    private final ArrayList<Integer> id_list;

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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LutemonViewHolder holder, int position) {
        if(id_list.size() == 0) return;
        holder.lutemonImage.setImageResource(Objects.requireNonNull(lutemons.get(id_list.get(position))).getImage());
        holder.lutemonName.setText(Objects.requireNonNull(lutemons.get(id_list.get(position))).getName());
        holder.lutemonColor.setText("(" + Objects.requireNonNull(lutemons.get(id_list.get(position))).getColor() + ")");
        holder.lutemonWins.setText("WINS: " + Objects.requireNonNull(lutemons.get(id_list.get(position))).getWins());
        holder.lutemonLosses.setText("LOSSES: " + Objects.requireNonNull(lutemons.get(id_list.get(position))).getLosses());
        int pos = holder.getAdapterPosition();
        holder.cb.setOnClickListener(v -> Objects.requireNonNull(lutemons.get(id_list.get(pos))).select(holder.cb.isChecked()));
        holder.lutemonInfo.setOnClickListener(v -> showLutemonInfo(pos, v));
    }

    @SuppressLint("SetTextI18n")
    public void showLutemonInfo(int pos, View v){
        Dialog dialog = new Dialog(v.getRootView().getContext());
        // the character's favorite move
        LutemonAnimation animation = new LutemonAnimation(v.getContext());

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
        animation.setFavoriteAnimation(temp,v.getContext());

        lutemonImage.setImageResource(Objects.requireNonNull(temp).getImage());
        lutemonImage.startAnimation(animation.getFavoriteAnimation());
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
