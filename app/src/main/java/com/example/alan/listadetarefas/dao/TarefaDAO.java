package com.example.alan.listadetarefas.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.alan.listadetarefas.model.Tarefa;


import java.util.ArrayList;
import java.util.List;


public class TarefaDAO {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public TarefaDAO(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    private SQLiteDatabase getDatabase() {
        if (database == null) {
            database = databaseHelper.getWritableDatabase();
        }
        return database;
    }

    private Tarefa CriarTarefa(Cursor cursor) {
        Tarefa model = new Tarefa(
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Tarefas._ID)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Tarefas.TAREFA)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Tarefas.DT_CRIACAO)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Tarefas.DT_COMPLETADO))
        );
        return model;
    }

    public List<Tarefa> listarTarefas() {
        Cursor cursor = getDatabase().query(DatabaseHelper.Tarefas.TABELA,
                DatabaseHelper.Tarefas.COLUNAS, null, null, null, null, null);
        List<Tarefa> tarefas = new ArrayList<Tarefa>();
        while (cursor.moveToNext()) {
            Tarefa model = CriarTarefa(cursor);
            tarefas.add(model);
        }
        cursor.close();
        return tarefas;
    }

    public long SalvarTarefa(Tarefa tarefa) {
        ContentValues valores = new ContentValues();
        valores.put(DatabaseHelper.Tarefas.TAREFA, tarefa.getTarefa());
        valores.put(DatabaseHelper.Tarefas.DT_CRIACAO, tarefa.getDt_criacao());
        valores.put(DatabaseHelper.Tarefas.DT_COMPLETADO, tarefa.getDt_completado());
        if ((tarefa.get_id() != null)) {
            return database.update(DatabaseHelper.Tarefas.TABELA, valores, "_id = ?", new String[]{tarefa.get_id().toString()});
        } else {
            return getDatabase().insert(DatabaseHelper.Tarefas.TABELA, null, valores);
        }
    }

    public boolean removerTarefas(int id) {
        return getDatabase().delete(DatabaseHelper.Tarefas.TABELA, "_id = ?", new String[]{Integer.toString(id)}) > 0;

    }

    public Tarefa buscarTarefaPorID(int id) {
        Cursor cursor = getDatabase().query(DatabaseHelper.Tarefas.TABELA,
                DatabaseHelper.Tarefas.COLUNAS, "_id = ?", new String[]{Integer.toString(id)}, null, null, null);
        if (cursor.moveToNext()) {
            Tarefa model = CriarTarefa(cursor);
            cursor.close();
            return model;
        }
        return null;
    }

    public void fechar() {
        databaseHelper.close();
        database = null;
    }
}
