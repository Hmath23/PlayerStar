package com.example.playerstar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

public class Cadastro extends AppCompatActivity {

    private JSONArray restulJsonArray;
    private String apiPath = "http://10.0.2.2/playerstar/jogadores/incluir/";

    private int logado = 0, resultado;
    private String mensagem = "";
    EditText edtNome, edtIdade, edtEmail, edtEsp, edtEstilo, edtCidade, edtBairro, edtUsuario, edtSenha, edtConfSenha;
    Button btnLogin, btnCadastro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtNome = findViewById(R.id.editTextNome);
        edtIdade = findViewById(R.id.editTextIdade);
        edtEmail = findViewById(R.id.editTextEmail);
        edtEsp = findViewById(R.id.editTextEsportes);
        edtEstilo = findViewById(R.id.editTextEstilo);
        edtCidade = findViewById(R.id.editTextCidade);
        edtBairro = findViewById(R.id.editTextBairro);
        edtUsuario = findViewById(R.id.editTextUser);
        edtSenha = findViewById(R.id.editTextSenha);
        edtConfSenha = findViewById(R.id.editTextSenhaConf);

        btnLogin = findViewById(R.id.btnLogin);
        btnCadastro = findViewById(R.id.btnCad);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Login = new Intent(getApplicationContext(), com.example.playerstar.Login.class);
                startActivity(Login);
                finish();
            }
        });

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String  senha, confSenha;

                senha = edtSenha.getText().toString();
                confSenha = edtConfSenha.getText().toString();

                if (confSenha.equals(senha)) {
                    cadastroApi();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Cadastro.this)
                            .setTitle("Erro")
                            .setMessage("As senhas não são iguais")
                            .setPositiveButton("OK", null);
                    builder.create().show();
                }
            }
            });
    }
        protected void cadastroApi() {
            AndroidNetworking.post(apiPath)
                    .addBodyParameter("HTTP_ACCEPT", "application/json")
                    .addBodyParameter("txtNome", edtNome.getText().toString())
                    .addBodyParameter("txtIdade", edtIdade.getText().toString())
                    .addBodyParameter("txtEmail", edtEmail.getText().toString())
                    .addBodyParameter("txtEsportes", edtEsp.getText().toString())
                    .addBodyParameter("txtEstilo", edtEstilo.getText().toString())
                    .addBodyParameter("txtCidade",edtCidade.getText().toString())
                    .addBodyParameter("txtBairro", edtBairro.getText().toString())
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
                                    if (jsonObject.getJSONObject("RetornoDados").getInt("sucesso") == 0) {
                                        mensagem = "Não foi possivel realizar o cadastro";
                                    } else if (jsonObject.getJSONObject("RetornoDados").getInt("sucesso") == 1) {
                                        mensagem = "Dados cadastrados com sucesso";

                                    }
                                }
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Cadastro.this);
                                    builder

                                            .setTitle("Aviso")
                                            .setMessage(mensagem)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent Login = new Intent(getApplicationContext(), com.example.playerstar.Login.class);
                                                    startActivity(Login);
                                                    finish();
                                                }
                                            });

                                    AlertDialog alert = builder.create();
                                    alert.show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(ANError anError) {
                            try {
                                if (anError.getErrorCode() == 0) {
                                    mensagem = "Problemas com a conexão!! \nTente Novamente.";
                                } else {
                                    JSONObject jsonObject = new JSONObject(anError.getErrorBody());

                                }
                                AlertDialog.Builder builder = new AlertDialog.Builder(Cadastro.this)
                                        .setTitle("Aviso")
                                        .setMessage(mensagem)
                                        .setPositiveButton("OK", null);
                                AlertDialog alert = builder.create();

                                alert.show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Log.d("BridgeUpdateService", "error" + anError.getErrorCode() + anError.getErrorDetail());
                        }


                    });
        }
}