package com.example.rodarte.biblioteca.firebaselogin;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Rodarte on 24/09/2017.
 */

public class Conexao {
    private static FirebaseAuth firebaseAuth;
    private static FirebaseAuth.AuthStateListener authStateListener;
    private static FirebaseUser firebaseUser;

    private Conexao(){

    }

    public static FirebaseAuth getFirebaseAuth(){
        if (firebaseAuth == null){
            inicializarFirebaseAuth();
        }
        return firebaseAuth;
    }

    private static void inicializarFirebaseAuth(){
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged (@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    firebaseUser = user;
                }
            }
        };
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    public static FirebaseUser getFirebaseUser(){
        return firebaseUser;
    }

    public static void logOut(){
        firebaseAuth.signOut();
    }
}
