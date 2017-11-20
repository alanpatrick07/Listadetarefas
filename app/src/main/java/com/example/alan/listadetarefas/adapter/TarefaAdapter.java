package com.example.alan.listadetarefas.adapter;



import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.alan.listadetarefas.R;
import com.example.alan.listadetarefas.model.Tarefa;

import java.util.List;

public class TarefaAdapter extends BaseAdapter {
    private Context context;
    private List<Tarefa> lista;

    public TarefaAdapter(Context ctx, List<Tarefa> tarefas) {
        this.context = ctx;
        this.lista = tarefas;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Tarefa tarefa = lista.get(position);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.tarefas, null);
        }
        TextView txtarefa = (TextView) view.findViewById(R.id.tarefa_lista_nome);
        txtarefa.setText(tarefa.getTarefa());

        return view;
    }
}
