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

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.BreakIterator;

public class Login extends AppCompatActivity {

    private String apiPath = "http://10.0.2.2/playerstar/jogadores/conectar/";
    private JSONArray restulJsonArray;
    public int logado = 0, resultado, stridade;
    public String mensagem = "", strnome = "", stremail = "", strestilo = "", stresporte = "", strbairro ="", strcidade="";
    EditText edtUsuario, edtSenha;
    Button btnLogin, btnCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        btnLogin = findViewById(R.id.btnLogin);
        btnCadastro = findViewById(R.id.btnCad);
        edtUsuario = findViewById(R.id.editTextUsuario);
        edtSenha = findViewById(R.id.editTextSenha);

        AndroidNetworking.initialize(getApplicationContext());

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Cadastro = new Intent(getApplicationContext(), com.example.playerstar.Cadastro.class);
                startActivity(Cadastro);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user,senha;

                user = edtUsuario.getText().toString();
                senha = edtSenha.getText().toString();

                if (user.isEmpty() || senha.isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Login.this)
                            .setTitle("Erro")
                            .setMessage("Favor preencher os campos")
                            .setPositiveButton("OK",null);
                    builder.create().show();
                }
                else {
                    sendApi();
                }
            }
        });
    }
    protected void sendApi() {
        AndroidNetworking.post(apiPath)
                .addBodyParameter("HTTP_ACCEPT", "application/json")
                .addBodyParameter("txtUsuario", edtUsuario.getText().toString())
                .addBodyParameter("txtSenha", edtSenha.getText().toString())
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
                                resultado = jsonObj.getInt("resultado");

                                if (resultado == 1) {
                                    for (int i = 0; i < restulJsonArray.length(); i++) {
                                        jsonObj = restulJsonArray.getJSONObject(i);
                                        logado = jsonObj.getInt("plogado");
                                        strnome = jsonObj.getString("pnome");
                                        stridade = jsonObj.getInt("pidade");
                                        stremail = jsonObj.getString("pemail");
                                        strestilo = jsonObj.getString("pestilo");
                                        stresporte = jsonObj.getString("pesportes");
                                        strbairro = jsonObj.getString("pbairro");
                                        strcidade = jsonObj.getString("pcidade");
                                    }
                                    switch (logado) {
                                        case 1:
                                            mensagem = "Bem vindo ao Sistema";
                                            break;
                                        case 2:
                                            mensagem = "Usuário já esta conectado";
                                            break;
                                    }
                                }
                                else{
                                    mensagem = "Usuário ou senha invalidos";
                                }

                                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this)
                                        .setTitle("Aviso")
                                        .setMessage(mensagem).
                                        setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                if (logado == 1) {
                                                    Intent user = new Intent(getApplicationContext(), Users.class);
                                                    user.putExtra("nome", strnome.toString());
                                                    user.putExtra("email", stremail.toString());
                                                    startActivity(user);
                                                    finish();
                                                }
                                            }
                                        });

                                builder.create().show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        try {
                            if (anError.getErrorCode() == 0) {
                                mensagem = "Problemas com conexão!! \nTente novamente.";
                            } else {
                                JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                                if (jsonObject.getJSONObject("RetornoDados").getInt("sucesso") == 0) {
                                    mensagem = "Usuário ou senha inválidos";
                                }
                            }
                            AlertDialog.Builder builder = new AlertDialog.Builder(Login.this)
                                    .setTitle("Aviso")
                                    .setMessage(mensagem).
                                    setPositiveButton("ok", null);
                            builder.create().show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.d("BridgeUpdateService", "error" + anError.getErrorCode() + anError.getErrorDetail());

                    }
                });
    }
}
