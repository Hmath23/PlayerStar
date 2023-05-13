package com.example.playerstar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ConvEnviado extends AppCompatActivity {

    Button btnVolt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conv_enviado);

        btnVolt = findViewById(R.id.btnVoltUser);

        btnVolt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Intent user = new Intent(getApplicationContext(), Users.class);
                        startActivity(user);
                        finish();
            }
        });
    }
}