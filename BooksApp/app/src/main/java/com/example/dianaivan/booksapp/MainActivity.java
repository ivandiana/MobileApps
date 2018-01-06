package com.example.dianaivan.booksapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView authFailed;
    private Button loginButton,registerButton;
    private EditText emailEdit,passwordEdit;

    private static final String TAG="LogInActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        mAuth=FirebaseAuth.getInstance();
        authFailed=(TextView)findViewById(R.id.authFailed);
        loginButton=(Button) findViewById(R.id.loginButton);
        registerButton=(Button)findViewById(R.id.registerButton);
        emailEdit=(EditText)findViewById(R.id.email);
        passwordEdit=(EditText)findViewById(R.id.password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn(emailEdit.getText().toString(),passwordEdit.getText().toString());
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register(emailEdit.getText().toString(),passwordEdit.getText().toString());
            }
        });
    }

    public void Register(String email, String password)
    {
        Log.d(TAG,"Creating account for... "+email);
        if(!validateInputs())
        {
            Log.d(TAG,"Input validation failed for... "+email);
            return;
        }
        Log.d(TAG,"Input validation is ok for... "+email);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {

                            Log.d(TAG,"Successfully created account.");
                            FirebaseUser user=mAuth.getCurrentUser();
                            //account created at register are clients
                            signedIn(false);
                        }
                        else
                        {
                            Log.d(TAG,"Registering failed. ");
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            authFailed.setText("Authentication failed.");
                        }
                    }
                });

    }
    public void SignIn(final String email, String password)
    {
        Log.d(TAG,"SignIn attempt from:"+email);
        if(!validateInputs())
        {
            return;
        }


        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Log.d(TAG,"Successfully logged in: "+email);
                            FirebaseUser user=mAuth.getCurrentUser();
                            DatabaseReference dbRef= FirebaseDatabase.getInstance().getReference();
                            dbRef.child("admins").child("email").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String email=dataSnapshot.getValue(String.class);
                                    if(email!=null && email.equals(mAuth.getCurrentUser().getEmail().toString()))
                                    {
                                        //is admin
                                        signedIn(true);
                                        Log.d(TAG,"User is admin.");
                                    }
                                    else
                                    {
                                        //is normal user
                                        Log.d(TAG,"User is client.");
                                        signedIn(false);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.d(TAG,"User role checking failed.");
                                }
                            });
                        }
                        else
                        {
                            Log.d(TAG,"Login failed: "+email);
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            authFailed.setText("Authentication failed");
                        }
                    }
                });
    }

    private boolean validateInputs()
    {
        boolean res=true;
        emailEdit.setError(null);
        passwordEdit.setError(null);
        if(emailEdit.getText().toString().isEmpty())
        {
            emailEdit.setError("Required field.");
            res=false;
        }
        if(passwordEdit.getText().toString().isEmpty())
        {
            passwordEdit.setError("Required field.");
            res=false;
        }
        return res;
    }

    private void signedIn(boolean isAdmin) {
        Intent intent;
        authFailed.setText("");
        if (isAdmin)
        {
            //redirect to admin page
            intent=new Intent(MainActivity.this,HomeActivity.class);

        }
        else
        {
            //redirect to normal activity
            //TODO : redirect to specific page here
            intent=new Intent(MainActivity.this,HomeActivity.class);
        }
        intent.putExtra("isAdmin",isAdmin);
        startActivity(intent);
    }
}
