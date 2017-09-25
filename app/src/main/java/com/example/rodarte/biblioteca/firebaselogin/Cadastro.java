package com.example.rodarte.biblioteca.firebaselogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rodarte.biblioteca.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Rodarte on 24/09/2017.
 */

public class Cadastro extends AppCompatActivity{

    private EditText editEmail, editSenha;
    private Button btnRegistrar, btnVoltar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);
        inicializarComponentes();
        eventoClicks();
    }

    private void eventoClicks(){
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmail.getText().toString().trim();
                String senha = editSenha.getText().toString().trim();
                criarUser(email, senha);
            }
        });
    }

    private void criarUser(String email, String senha) {
        auth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(Cadastro.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            alert("Usuário Cadastrado com Sucesso");
                            Intent i  = new Intent(Cadastro.this,Perfil.class);
                            startActivity(i);
                            finish();
                        }else{
                            alert("Erro ao Cadastrar o Usuário");
                        }
                    }
                });
    }

    private void alert(String msg){
        Toast.makeText(Cadastro.this,msg,Toast.LENGTH_SHORT).show();
    }

    private void inicializarComponentes(){
        editEmail = (EditText) findViewById(R.id.editEmail);
        editSenha = (EditText) findViewById(R.id.editSenha);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        btnVoltar = (Button) findViewById(R.id.btnVoltar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }
}
