package br.com.lucasts.boookado.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.lucasts.boookado.R;
import br.com.lucasts.boookado.controller.LivroAdapter;
import br.com.lucasts.boookado.controller.LivroDAO;
import br.com.lucasts.boookado.model.Livro;

public class ListarBooksActivity extends AppCompatActivity {

    private ListView listView;
    private LivroDAO dao;
    private List<Livro> livros;
    private List<Livro> livrosFiltrados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_books);

        listView = findViewById(R.id.books_lista);
        dao = new LivroDAO(this);
        livros = dao.obterTodos();
        livrosFiltrados.addAll(livros);

        LivroAdapter adaptador = new LivroAdapter(this, livrosFiltrados);
        listView.setAdapter(adaptador);


        registerForContextMenu(listView);


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.menu_principal, menu);

        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                procuraLivro(s);
                return false;
            }
        });

        return true;
    }

    public void procuraLivro(String titulo) {
        livrosFiltrados.clear();
        for (Livro l : livros) {
            if (l.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                livrosFiltrados.add(l);
            }
        }
        listView.invalidateViews();
    }



    public void onCreateContextMenu(android.view.ContextMenu menu, android.view.View v, android.view.ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto, menu);
    }

    public void goCadastrar(View view) {
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
    }

    public void excluir(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Livro livroExcluir = livrosFiltrados.get(menuInfo.position);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção!")
                .setMessage("Deseja excluir o Book?")
                .setNegativeButton("Não", null)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        livrosFiltrados.remove(livroExcluir);
                        livros.remove(livroExcluir);
                        dao.excluir(livroExcluir);
                        listView.invalidateViews();

                    }
                }).create();
        dialog.show();

    }

    public void atualizar(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Livro livroAtualizar = livrosFiltrados.get(menuInfo.position);

        Intent it = new Intent(this, CadastroActivity.class);
        it.putExtra("livro", livroAtualizar);
        startActivity(it);

    }

    @Override
    public void onResume() {
        super.onResume();
        livros = dao.obterTodos();
        livrosFiltrados.clear();
        livrosFiltrados.addAll(livros);
        listView.invalidateViews();
    }



}
