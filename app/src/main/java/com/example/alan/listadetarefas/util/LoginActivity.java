package com.example.alan.listadetarefas.util;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.alan.listadetarefas.R;
import com.example.alan.listadetarefas.dao.UsuarioDAO;



public class LoginActivity extends AppCompatActivity {
    private EditText edtUsuario, edtSenha;
    UsuarioDAO helper;
    private CheckBox ckbConectado;
    private static final String MANTER_CONECTADO = "manter conectado";
    private static final String PREFERENCE_NAME = "LoginActivityPreferences";

    public String login_nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1A237E")));

        edtUsuario = (EditText) findViewById(R.id.login_edtUsuario);
        edtSenha = (EditText) findViewById(R.id.login_edtSenha);
        ckbConectado = (CheckBox) findViewById(R.id.login_ckbConectado);
        helper = new UsuarioDAO(this);

        SharedPreferences preferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        boolean conectado = preferences.getBoolean(MANTER_CONECTADO, false);
        if (conectado) {
            chamarMainActivity();
        }
    }

    public void logar(View view) {
        String usuario = edtUsuario.getText().toString();
        String senha = edtSenha.getText().toString();
        Boolean validacao = true;
        if (usuario == null || usuario.equals("")) {
            validacao = false;
            edtUsuario.setError(getString(R.string.Login_valUsuario));
        }
        if (senha == null || usuario.equals("")) {
            validacao = false;
            edtSenha.setError(getString(R.string.Login_valSenha));
        }
        if (validacao) {

            //logar
            if (helper.logar(usuario, senha)) {
                //manter conectado
                if ((ckbConectado.isChecked())) {
                    SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(MANTER_CONECTADO, true);
                    editor.commit();
                }
                ///  login_nome = helper.buscarUsuarioPorNome(usuario);
                ///    Toast.makeText(this, login_nome.toString(), Toast.LENGTH_LONG).show();
                chamarMainActivity();
            } else {
                //erro
                Mensagem.Msg(this, getString(R.string.Login_valErro));
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Sair");
            alert.setMessage("Voce deseja realmente Sair?");
            alert.setPositiveButton("sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert.setNegativeButton("não", null);
            alert.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void chamarMainActivity() {

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
