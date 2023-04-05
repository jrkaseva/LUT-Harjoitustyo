package com.example.lutemongame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        holder.lutemonName.setText(lutemons.get(id_list.get(position)).getName());
        holder.lutemonColor.setText("(" + lutemons.get(id_list.get(position)).getColor() + ")");
        holder.lutemonWins.setText("WINS: " + lutemons.get(id_list.get(position)).getWins());
        holder.lutemonLosses.setText("LOSSES: " + lutemons.get(id_list.get(position)).getLosses());
        holder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                if(holder.cb.isChecked()) lutemons.get(id_list.get(pos)).select(true);
                else lutemons.get(id_list.get(pos)).select(false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lutemons.size();
    }
}
