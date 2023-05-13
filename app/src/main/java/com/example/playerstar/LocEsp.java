package com.example.playerstar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LocEsp extends AppCompatActivity {

    Button btnFinConv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loc_esp);

        btnFinConv = findViewById(R.id.btnFnConv);

        btnFinConv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent conv = new Intent(getApplicationContext(), ConvEnviado.class);
                startActivity(conv);
                finish();
            }
        });
    }
}