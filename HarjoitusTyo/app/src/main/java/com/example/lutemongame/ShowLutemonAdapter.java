package com.example.lutemongame;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
                showLutemonInfo(pos);
            }
        });
    }

    public void showLutemonInfo(int pos){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_lutemon);

        // ImageView lutemonImage = dialog.findViewById(R.id.dialogImageView);
        TextView idNameColor = dialog.findViewById(R.id.dialogTVIdNameColor);
        TextView atkDef = dialog.findViewById(R.id.dialogTVAtkDef);
        TextView expHp = dialog.findViewById(R.id.dialogTVExpHp);
        TextView winsLosses = dialog.findViewById(R.id.dialogTVWinsLosses);

        Lutemon temp = lutemons.get(id_list.get(pos));
        // lutemonImage.setImageResource(temp.getImage());
        idNameColor.setText(temp.getIdNameColor());
        atkDef.setText(temp.atkDefToString());
        expHp.setText(temp.expHpToString());
        winsLosses.setText(temp.winsLossesToString());

        dialog.show();

        Intent i = new Intent(context, ArenaActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("key", pos);
        context.startActivity(i);
        notifyItemChanged(pos);
    }

    @Override
    public int getItemCount() {
        return lutemons.size();
    }
}
