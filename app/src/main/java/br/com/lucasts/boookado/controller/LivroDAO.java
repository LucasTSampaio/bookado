package br.com.lucasts.boookado.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.lucasts.boookado.banco.Conexao;
import br.com.lucasts.boookado.model.Livro;

public class LivroDAO {

    private Conexao conexao;
    private SQLiteDatabase banco;

    public LivroDAO(Context context) {
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public long inserir(Livro livro) {
        ContentValues values = new ContentValues();
        values.put("titulo", livro.getTitulo());
        values.put("autor", livro.getAutor());
        values.put("nota", livro.getNota());
        return banco.insert("livro", null, values);

    }


    public List<Livro> obterTodos() {
        List<Livro> livros = new ArrayList<>();
        Cursor cursor = banco.query("livro", new String[]{"id", "titulo", "autor", "nota"},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            Livro livro = new Livro();
            livro.setId(cursor.getInt(0));
            livro.setTitulo(cursor.getString(1));
            livro.setAutor(cursor.getString(2));
            livro.setNota(cursor.getString(3));
            livros.add(livro);

        }

        return livros;
    }

    public void excluir(Livro livro) {
        banco.delete("livro", "id = ?", new String[]{livro.getId().toString()});
    }

    public void atualizar(Livro livro) {

        ContentValues values = new ContentValues();
        values.put("titulo", livro.getTitulo());
        values.put("autor", livro.getAutor());
        values.put("nota", livro.getNota());
        banco.update("livro", values, "id = ?", new String[]{livro.getId().toString()});


    }


}
