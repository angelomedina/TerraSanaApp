package com.example.katty.terrasana;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.katty.terrasana.objetos.FirebaseReferences;
import com.example.katty.terrasana.objetos.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonRegister;
    EditText editTextEmail, editTextPass;

    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        buttonRegister = (Button) findViewById(R.id.btn_registrar);
        editTextEmail  = (EditText) findViewById(R.id.correo_registro);
        editTextPass  =  (EditText) findViewById(R.id.contraseña_registro);

        buttonRegister.setOnClickListener(this);
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
        String email_Register = editTextEmail.getText().toString();
        String pass_Register  = editTextPass.getText().toString();
        registrar(email_Register,pass_Register);
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

    private void registrar(final String email, final String pass){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference ref      = database.getReference(FirebaseReferences.USER_REFERENCE);

                    Usuario usuario = new Usuario(email,pass);
                    ref.child(FirebaseReferences.USER_REFERENCE).push().setValue(usuario);

                    Toast.makeText(getApplicationContext(), "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();

                    Intent siguienteVentana = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(siguienteVentana);

                }else{
                    Log.i("SESION",task.getException().getMessage()+"");
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    editTextEmail.setText("");
                    editTextPass.setText("");
                }
            }
        });
    }

    /*public void mensaje(String mensaje){

        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

    }*/
}
