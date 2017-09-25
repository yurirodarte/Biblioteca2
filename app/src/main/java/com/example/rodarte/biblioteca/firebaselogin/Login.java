package com.example.rodarte.biblioteca.firebaselogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodarte.biblioteca.FormBiblio;
import com.example.rodarte.biblioteca.MainActivity;
import com.example.rodarte.biblioteca.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Rodarte on 24/09/2017.
 */

public class Login extends AppCompatActivity {
    private EditText editEmail, editSenha;
    private Button btnLogar,btnNovo;
    private TextView txtResetSenha;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            inicializaComponentes();
            eventoClicks();
    }

    private void eventoClicks(){
        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), Cadastro.class);
                startActivity(it);
            }
        });

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmail.getText().toString().trim();
                String senha = editSenha.getText().toString().trim();
                login(email,senha);
            }
        });

        txtResetSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (Login.this,ResetSenha.class);
                startActivity(i);
            }
        });
    }

    private void login(String email, String senha){
        auth.signInWithEmailAndPassword(email,senha)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent i = new Intent(Login.this,MainActivity.class);
                            startActivity(i);
                        }else{
                            alert("E-mail ou Senha Errados");
                        }

                    }
                });
    }

    private void alert(String s) {
        Toast.makeText(Login.this,s,Toast.LENGTH_SHORT).show();
    }

    private void inicializaComponentes(){
        editEmail = (EditText) findViewById(R.id.editLoginEmail);
        editSenha = (EditText) findViewById(R.id.editLoginSenha);
        btnLogar = (Button) findViewById(R.id.btnLoginLogar);
        btnNovo = (Button) findViewById(R.id.btnLoginNovo);
        txtResetSenha = (TextView) findViewById(R.id.txtResetSenha);
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }
}
