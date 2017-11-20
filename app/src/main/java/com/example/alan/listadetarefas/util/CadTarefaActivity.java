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
import com.example.alan.listadetarefas.dao.TarefaDAO;
import com.example.alan.listadetarefas.model.Tarefa;

import java.util.Calendar;


public class CadTarefaActivity extends AppCompatActivity {
    // private EditText edtnome, edtlogin, edtsenha;
    public EditText edttarefa, edtdtcria, edtdtcomp;
    private TarefaDAO tarefaoDAO;
    public Tarefa tarefa;
    private int idTarefa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_tarefa);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1A237E")));

        final Calendar c = Calendar.getInstance();
        tarefaoDAO = new TarefaDAO(this);

        edttarefa = (EditText) findViewById(R.id.tarefa_edtTarefa);
        edtdtcria = (EditText) findViewById(R.id.tarefa_edtDTcriacao);
        edtdtcomp = (EditText) findViewById(R.id.tarefa_edtDTcompletado);

        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        edtdtcria.setText(day + "/" + (month + 1) + "/" + year);

        //edição
        idTarefa = getIntent().getIntExtra("TAREFA_ID", 0);
        if (idTarefa > 0) {
            Tarefa model = tarefaoDAO.buscarTarefaPorID(idTarefa);
            edttarefa.setText(model.getTarefa());
            edtdtcria.setText(model.getDt_criacao());
            edtdtcomp.setText(model.getDt_completado());
            setTitle("Atualizar Tarefa");
        }

    }

    public void onDestroy() {
        tarefaoDAO.fechar();
        super.onDestroy();
    }

    public void cadastrar() {
        boolean validacao = true;
        String ntarefa = edttarefa.getText().toString();
        String dt_cria = edtdtcria.getText().toString();
        String dt_comp = edtdtcomp.getText().toString();

        if (ntarefa == null || ntarefa.equals("")) {
            validacao = false;
            edttarefa.setError("Campo Obrigatório!");
        }

        if (dt_cria == null || dt_cria.equals("")) {
            validacao = false;
            edtdtcria.setError("Campo Obrigatório!");
        }


        if (validacao) {
            tarefa = new Tarefa();
            tarefa.setTarefa(ntarefa);
            tarefa.setDt_criacao(dt_cria);
            tarefa.setDt_completado(dt_comp);

            //atualizacao
            if (idTarefa > 0) {
                tarefa.set_id(idTarefa);
            }
            long resultado = tarefaoDAO.SalvarTarefa(tarefa);
            if (resultado != -1) {
                if (idTarefa > 0) {
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
        getMenuInflater().inflate(R.menu.cadastrostarefa, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_Menu_SalvarTarefa:
                this.cadastrar();
                break;
            case R.id.action_Menu_SairTarefa:
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;


        }

        return super.onOptionsItemSelected(item);
    }
}
