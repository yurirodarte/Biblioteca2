package com.example.rodarte.biblioteca.firebaselogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rodarte.biblioteca.MainActivity;
import com.example.rodarte.biblioteca.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Rodarte on 24/09/2017.
 */

public class Perfil extends AppCompatActivity {

    private TextView txtEmail, txtID;
    private Button btnLogOut;

    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        inicializaComponentes();
        eventoClick();
    }
    private void eventoClick(){
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (Perfil.this,Login.class);
                startActivity(i);
                Conexao.logOut();
                finish();
            }
        });
    }

    private void inicializaComponentes(){
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtID = (TextView) findViewById(R.id.txtID);
        btnLogOut = (Button) findViewById(R.id.btnLogOut);

    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
        user = Conexao.getFirebaseUser();
        verificaUser();
    }

    public void verificaUser(){
        if(user == null){
            finish();
        }else {
            txtEmail.setText("Email:"+user.getEmail());
            txtID.setText("ID:"+user.getUid());
        }

    }
}
