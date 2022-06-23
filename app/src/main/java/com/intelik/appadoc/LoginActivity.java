package com.intelik.appadoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {

    androidx.appcompat.widget.AppCompatButton buttonRegistrar;
    androidx.appcompat.widget.AppCompatButton buttonIngresar;
    androidx.appcompat.widget.AppCompatButton buttonOlvidar;
    private EditText _email;
    private EditText _password;

    Context context;


    private FirebaseAuth mAuth;

    private String TAG ="LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        context = this;



        buttonRegistrar = (androidx.appcompat.widget.AppCompatButton) findViewById(R.id.RegistrarButton_aviso);
        buttonIngresar = (androidx.appcompat.widget.AppCompatButton) findViewById(R.id.IngresarButton);
        buttonOlvidar = (androidx.appcompat.widget.AppCompatButton) findViewById(R.id.OlvidoButton);


        _email = (EditText) findViewById(R.id.input_email_ingresar);
        _password = (EditText) findViewById(R.id.input_password_ingresar);


        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setClass(context, RegisterActivity.class);
                //finish();
                startActivity(intent);


            }
        });

        buttonIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginUserAccount();


            }
        });


        buttonOlvidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setClass(context, ForgetPassword.class);
                //finish();
                startActivity(intent);


            }
        });


    }

    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
           // reload();
            Intent intent
                    = new Intent(LoginActivity.this,
                    MainActivity.class);
            startActivity(intent);
            finish();

        }



    }

    ////////////////
    private void loginUserAccount()
    {

        // show the visibility of progress bar to show loading

        ProgressDialog _progressDialog;

        // Take the value of two edit texts in Strings
        String email, password;
        email = _email.getText().toString();
        password = _password.getText().toString();

        // validations for input email and password
        if (TextUtils.isEmpty(email)) {
            //Toast.makeText(getApplicationContext(), "Please enter email!!", Toast.LENGTH_LONG).show();
            Common.showWarningDialog(context.getResources().getString(R.string.aviso),context.getResources().getString(R.string.tuemail), context);

            return;
        }


        if (!Common.isValidMail(email))
        {
            Common.showWarningDialog(context.getResources().getString(R.string.aviso),context.getResources().getString(R.string.tuemailinvalido), context);
            return;
        }


        if (TextUtils.isEmpty(password)) {
            //Toast.makeText(getApplicationContext(),"Please enter password!!", Toast.LENGTH_LONG).show();
            Common.showWarningDialog(context.getResources().getString(R.string.aviso),context.getResources().getString(R.string.tupass), context);
            return;
        }

        _progressDialog = ProgressDialog.show( context, "Espera un momento..", "Ingreso de usuarios..", true );

        // signin existing user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(
                                    @NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Login exitoso!", Toast.LENGTH_LONG).show();

                                    // hide the progress bar
                                    _progressDialog.dismiss();

                                    // if sign-in is successful
                                    // intent to home activity
                                    Intent intent
                                            = new Intent(LoginActivity.this,
                                            MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                                else {

                                    // sign-in failed
                                    // hide the progress bar
                                    _progressDialog.dismiss();

                                    //The password is invalid or the user does not have a password.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    //Toast.makeText(getApplicationContext(), "Login failed!!", Toast.LENGTH_LONG).show();
                                    Common.showWarningDialog(context.getResources().getString(R.string.aviso),context.getResources().getString(R.string.invalidlogin), context);



                                }
                            }
                        });
    }

    //////////////
}