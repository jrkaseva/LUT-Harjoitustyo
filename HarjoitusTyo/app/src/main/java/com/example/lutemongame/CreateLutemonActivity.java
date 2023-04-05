package com.example.lutemongame;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lutemongame.Game.Areas.Home;
import com.example.lutemongame.Game.Creatures.*;

public class CreateLutemonActivity extends AppCompatActivity {
    Context context;
    private TextView lutemon_name;
    private RadioGroup rg;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lutemon);

        lutemon_name = findViewById(R.id.idEditLutemonName);
        rg = findViewById(R.id.rgColor);
        context = getApplicationContext();
    }

    public void createLutemon(View view){
        Lutemon lutemon = null;
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
        lutemon_name.setText("");
        Toast toast = Toast.makeText(context, "Lutemon created", Toast.LENGTH_LONG);
        toast.show();
    }
}
