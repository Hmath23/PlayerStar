package com.example.playerstar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

public class Users extends AppCompatActivity {

    private String apiPath = "http://10.0.2.2/playerstar/jogadores/desconectar/";
    private JSONArray restulJsonArray;
    public int logado = 0;
    public String mensagem = "", titulo="";
    public TextView txtnome, txtemail;
    Button btnVis1, btnVis2, btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        btnVoltar = findViewById(R.id.btnVoltar);
        btnVis1 = findViewById(R.id.btnVis);
        btnVis2 = findViewById(R.id.btnVis2);

        txtnome = findViewById(R.id.textviewNome);
        txtemail= findViewById(R.id.textViewEmail);

        Intent login = getIntent();
        txtnome.setText(String.valueOf(login.getStringExtra("nome")));
        txtemail.setText(String.valueOf(login.getStringExtra("email")));

        btnVis1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jog1 = new Intent(getApplicationContext(), com.example.playerstar.Jog1.class);
                startActivity(jog1);
                finish();
            }
        });

        btnVis2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jog2 = new Intent(getApplicationContext(), com.example.playerstar.Jog2.class);
                startActivity(jog2);
                finish();
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titulo = "Aviso";
                mensagem = "Deseja realmente sair?";
                AlertDialog.Builder builder = new AlertDialog.Builder(Users.this)
                        .setTitle(titulo)
                        .setMessage(mensagem)
                        .setNegativeButton("Não", null)
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                logoutApi();
                            }
                        });
                builder.create().show();
            }
        });
    }
    protected void logoutApi(){
        AndroidNetworking.post(apiPath)
                .addBodyParameter("HTTP_ACCEPT", "application/json")
                .addBodyParameter("txtNome", txtnome.getText().toString())
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject != null) {
                                restulJsonArray = jsonObject.getJSONArray("RetornoDados");
                                JSONObject jsonObj = null;
                                jsonObj = restulJsonArray.getJSONObject(0);
                                logado = jsonObj.getInt("plogado");
                                if  (logado == 1){
                                    Intent login = new Intent(getApplicationContext(),
                                            Login.class);
                                    startActivity(login);
                                    finish();
                                }

                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        try {
                            if (anError.getErrorCode() == 0){
                                mensagem = "Problemas com conexão!! \nTente novamente.";
                            }
                            AlertDialog.Builder builder = new AlertDialog.Builder(Users.this)
                                    .setTitle("Aviso")
                                    .setMessage(mensagem).
                                    setPositiveButton("ok", null);
                            builder.create().show();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        Log.d("BridgeUpdateService", "error" + anError.getErrorCode() + anError.getErrorDetail());

                    }
                });
    }



}