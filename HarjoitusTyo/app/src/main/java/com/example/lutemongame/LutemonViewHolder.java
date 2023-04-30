package com.example.lutemongame;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LutemonViewHolder extends RecyclerView.ViewHolder {
    final ImageView lutemonImage;
    final ImageView lutemonInfo;
    final TextView lutemonName;
    final TextView lutemonColor;
    final TextView lutemonWins;
    final TextView lutemonLosses;
    final CheckBox cb;

    public LutemonViewHolder(@NonNull View itemView) {
        super(itemView);
        lutemonImage = itemView.findViewById(R.id.idIVLutemonPicture);
        lutemonColor = itemView.findViewById(R.id.idTVLutemonColor);
        lutemonName = itemView.findViewById(R.id.idTVLutemonName);
        lutemonWins = itemView.findViewById(R.id.idTVLutemonWins);
        lutemonLosses = itemView.findViewById(R.id.idTVLutemonLosses);
        cb = itemView.findViewById(R.id.idCBLutemon);
        lutemonInfo = itemView.findViewById(R.id.lutemonInfo);
    }
}
