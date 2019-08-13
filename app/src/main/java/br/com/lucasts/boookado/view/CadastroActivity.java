package br.com.lucasts.boookado.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.lucasts.boookado.R;
import br.com.lucasts.boookado.controller.LivroDAO;
import br.com.lucasts.boookado.model.Livro;

public class CadastroActivity extends AppCompatActivity {

    EditText editTitulo, editAutor, editNota;

    Button btnSalvar;

    private LivroDAO dao;
    private Livro livro = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editTitulo = findViewById(R.id.et_titulo);
        editAutor = findViewById(R.id.et_autor);
        editNota = findViewById(R.id.et_nota);
        btnSalvar = findViewById(R.id.btn_salvar);

        dao = new LivroDAO(this);

        Intent it = getIntent();
        if (it.hasExtra("livro")) {
            livro = (Livro) it.getSerializableExtra("livro");
            editTitulo.setText(livro.getTitulo());
            editAutor.setText(livro.getAutor());
            editNota.setText(livro.getNota());
        }


        //pegando valores spinner
        //String status = spinnerStatus.getSelectedItem().toString();


    }

    public void salvar(View view) {

        if (!(editTitulo.getText().toString().isEmpty())){
            if (livro == null){
                livro = new Livro();

                livro.setTitulo(editTitulo.getText().toString());
                livro.setAutor(editAutor.getText().toString());
                livro.setNota(editNota.getText().toString());

                long id = dao.inserir(livro);

                Toast.makeText(this, "Book inserido com sucesso!", Toast.LENGTH_LONG).show();

            } else {
                livro.setTitulo(editTitulo.getText().toString());
                livro.setAutor(editAutor.getText().toString());
                livro.setNota(editNota.getText().toString());
                dao.atualizar(livro);
                Toast.makeText(this, "Book atualizado.", Toast.LENGTH_LONG).show();
            }

            Intent intent = new Intent(this, ListarBooksActivity.class);
            startActivity(intent);

        } else {
            editTitulo.setError("Informe o título do livro!");
        }


    }


}
