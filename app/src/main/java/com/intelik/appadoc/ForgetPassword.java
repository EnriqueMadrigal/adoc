package com.intelik.appadoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgetPassword extends AppCompatActivity {

    private androidx.appcompat.widget.AppCompatButton _olvidoButton;
    private EditText _email;
    private FirebaseAuth mAuth;
    Context context;


    private String TAG = "ForgetPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        mAuth = FirebaseAuth.getInstance();
        context = this;

        _olvidoButton = (androidx.appcompat.widget.AppCompatButton) findViewById(R.id.ButtonEnviarCorreo);
        _email = (EditText) findViewById(R.id.input_email_olivdo);

        _olvidoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendEmail();


            }
        });

    }

    private void sendEmail()
    {

        String email = _email.getText().toString();


        if(email.length() == 0)
        {
            Common.showWarningDialog(context.getResources().getString(R.string.aviso),context.getResources().getString(R.string.tuemail), context);
            return;
        }


        if (!Common.isValidMail(email))
        {
            Common.showWarningDialog(context.getResources().getString(R.string.aviso),context.getResources().getString(R.string.tuemailinvalido), context);
            return;
        }



        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            Toast.makeText(getApplicationContext(), context.getResources().getText(R.string.seenviocorreo), Toast.LENGTH_LONG).show();
                            onBackPressed();

                        }
                    }
                });



    }

}