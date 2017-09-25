package com.example.rodarte.biblioteca;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rodarte.biblioteca.dao.PessoaDao;
import com.example.rodarte.biblioteca.firebaselogin.Perfil;
import com.example.rodarte.biblioteca.modelo.Pessoa;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listVisivel;
    Button btnNovoCadastro, btnPerfil;
    Pessoa pessoa;
    PessoaDao pessoaDao;
    ArrayList<Pessoa> arrayListPessoa;
    ArrayAdapter<Pessoa> arrayAdapterPessoa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main );
        listVisivel = (ListView) findViewById(R.id.listPessoas);
        registerForContextMenu(listVisivel);

        btnPerfil = (Button) findViewById(R.id.btnPerfil);
        btnNovoCadastro = (Button) findViewById(R.id.btnNovoCadastro);

        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, Perfil.class);
                startActivity(it);
            }
        });

        btnNovoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), FormBiblio.class);
                startActivity(i);
            }
        });

        listVisivel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Pessoa pessoaEnviada = (Pessoa) arrayAdapterPessoa.getItem(position);

                Intent i = new Intent(MainActivity.this,FormBiblio.class);
                i.putExtra("pessoa-enviada", pessoaEnviada);
                startActivity(i);
            }
        });

        listVisivel.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                pessoa = arrayAdapterPessoa.getItem(position);
                return false;
            }
        });
    }

    public void popularLista() {
        pessoaDao = new PessoaDao(MainActivity.this);

        arrayListPessoa = pessoaDao.selectAllPessoa();
        pessoaDao.close();

        if (listVisivel != null) {
            arrayAdapterPessoa = new ArrayAdapter<Pessoa>(MainActivity.this, android.R.layout.simple_list_item_1, arrayListPessoa);
            listVisivel.setAdapter(arrayAdapterPessoa);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        popularLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem mDelete = menu.add("Delete Registro");
        mDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                long retornoDB;
                pessoaDao = new PessoaDao(MainActivity.this);
                retornoDB = pessoaDao.excluirPessoa(pessoa);
                pessoaDao.close();

                if(retornoDB == -1) {
                    alert("Erro de Exclusão");
                }else{
                    alert("Ecluido com Sucesso");
                }
                popularLista();
                return false;
            }
        });
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    private void alert (String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}

