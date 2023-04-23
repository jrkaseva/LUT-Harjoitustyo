package com.example.lutemongame;

import android.app.Dialog;
import android.os.Bundle;
import android.view.animation.Animation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.lutemongame.Game.LutemonAnimation;

public class FightDialog extends DialogFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LutemonAnimation animation = new LutemonAnimation(getContext());

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        return super.onCreateDialog(savedInstanceState);

    }

}
