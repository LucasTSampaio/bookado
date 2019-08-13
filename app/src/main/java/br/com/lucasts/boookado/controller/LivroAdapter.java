package br.com.lucasts.boookado.controller;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.lucasts.boookado.R;
import br.com.lucasts.boookado.model.Livro;

public class LivroAdapter extends BaseAdapter {

    private List<Livro> livros;
    private Activity activity;

    public LivroAdapter(Activity activity, List<Livro> livros){
        this.activity = activity;
        this.livros = livros;

    }

    @Override
    public int getCount() {
        return livros.size();
    }

    @Override
    public Object getItem(int i) {
        return livros.get(i);
    }

    @Override
    public long getItemId(int i) {
        return livros.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = activity.getLayoutInflater().inflate(R.layout.item, viewGroup, false);

        TextView titulo = v.findViewById(R.id.txt_titulo);
        TextView autor = v.findViewById(R.id.txt_autor);
        TextView nota = v.findViewById(R.id.txt_nota);

        Livro liv = livros.get(i);

        titulo.setText(liv.getTitulo());
        autor.setText(liv.getAutor());
        nota.setText(liv.getNota());

        return v;
    }
}
