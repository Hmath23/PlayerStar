package com.example.playerstar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Jog2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jog2);

        Button btnConv, btnVoltar;

        btnVoltar = findViewById(R.id.btnVoltar);
        btnConv = findViewById(R.id.btnConv);

        btnConv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent conv = new Intent(getApplicationContext(), LocEsp.class);
                startActivity(conv);
                finish();
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent user = new Intent(getApplicationContext(), Users.class);
                startActivity(user);
                finish();
            }
        });
    }
}