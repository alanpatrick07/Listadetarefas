package com.example.alan.listadetarefas.util;

/**
 * Created by Alan on 19/11/2017.
 */

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.alan.listadetarefas.R;
import com.example.alan.listadetarefas.dao.UsuarioDAO;
import com.example.alan.listadetarefas.model.Usuario;




public class CadUsuarioActivity extends AppCompatActivity {
    private EditText edtnome, edtlogin, edtsenha;
    private UsuarioDAO usuarioDAO;
    private Usuario usuario;
    private int idusuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_usuario);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1A237E")));

        usuarioDAO = new UsuarioDAO(this);

        edtnome = (EditText) findViewById(R.id.usuario_edtNome);
        edtlogin = (EditText) findViewById(R.id.usuario_edtLogin);
        edtsenha = (EditText) findViewById(R.id.usuario_edtSenha);

        //edição
        idusuario = getIntent().getIntExtra("USUARIO_ID",0);
        if (idusuario>0){
            Usuario model = usuarioDAO.buscarUsuarioPorID(idusuario);
            edtnome.setText(model.getNome());
            edtlogin.setText(model.getLogin());
            edtsenha.setText(model.getSenha());
            setTitle("Atualizar usuario");
        }

    }

    public void onDestroy() {
        usuarioDAO.fechar();
        super.onDestroy();
    }

    public void cadastrar() {
        boolean validacao = true;
        String nome = edtnome.getText().toString();
        String login = edtlogin.getText().toString();
        String senha = edtsenha.getText().toString();

        if (nome == null || nome.equals("")) {
            validacao = false;
            edtnome.setError("Campo Obrigatório!");
        }

        if (login == null || login.equals("")) {
            validacao = false;
            edtlogin.setError("Campo Obrigatório!");
        }

        if (senha == null || senha.equals("")) {
            validacao = false;
            edtsenha.setError("Campo Obrigatório!");
        }
        if (validacao) {
            usuario = new Usuario();
            usuario.setNome(nome);
            usuario.setLogin(login);
            usuario.setSenha(senha);

            //atualizacao
            if (idusuario >0) {
                usuario.set_id(idusuario);
            }
            long resultado = usuarioDAO.SalvarUsuario(usuario);
            if (resultado != -1) {
                if (idusuario > 0) {
                    Mensagem.Msg(this, "Atualizado com sucesso!");

                } else {
                    Mensagem.Msg(this, "Salvo com sucesso!");
                }
                finish();
                startActivity(new Intent(this, MainActivity.class));
            } else {
                Mensagem.Msg(this, "ERRO ao salvar!");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cadastros, menu);



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_Menu_Salvar:
                this.cadastrar();
                break;
            case R.id.action_Menu_Sair:
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;


        }

        return super.onOptionsItemSelected(item);
    }
}
