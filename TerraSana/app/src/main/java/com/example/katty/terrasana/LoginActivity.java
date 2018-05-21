package com.example.katty.terrasana;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    Button buttonSignIn,buttonVentanaRegistrar;
    EditText editTextEmail, editTextPass;

    public static  String userEmail;

    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonSignIn = (Button) findViewById(R.id.btn_login);
        buttonVentanaRegistrar = (Button) findViewById(R.id.btn_ventana_registrar);
        editTextEmail = (EditText) findViewById(R.id.correo_login);
        editTextPass = (EditText) findViewById(R.id.contraseña_login);

        buttonSignIn.setOnClickListener(this);
        buttonVentanaRegistrar.setOnClickListener(this);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null){
                    //Log.i("SESION","Sesion iniciada con email: "+user.getEmail());
                }else{
                    //Log.i("SESION","Sesion cerrada");
                }
            }
        };


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                String email_login = editTextEmail.getText().toString();
                String pass_Login  = editTextPass.getText().toString();
                iniciarSesion(email_login,pass_Login);
                break;

            case R.id.btn_ventana_registrar:
                Intent siguienteVentana = new Intent(getApplicationContext(), RegistroActivity.class);
                startActivity(siguienteVentana);
                break;
        }

    }

    private void iniciarSesion(String email,String pass){
        userEmail = email;
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //mensaje("Inicio de secion correctamente");
                    Intent siguienteVentana = new Intent(getApplicationContext(), CatalogoActivity.class);
                    startActivity(siguienteVentana);
                }else{
                    mensaje(task.getException().getMessage()+"");
                }
            }
        });
    }



    @Override
    protected  void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    protected  void onStop() {
        super.onStop();
        if(mAuthListener != null){
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }

    }

    public void mensaje(String mensaje){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensaje)
                .setTitle("Mensaje")
                .setCancelable(false)
                .setNeutralButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }




}

