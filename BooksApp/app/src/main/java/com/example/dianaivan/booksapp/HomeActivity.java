package com.example.dianaivan.booksapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dianaivan.booksapp.Listeners.OnClickListenerEmail;
import com.example.dianaivan.booksapp.database.TableControllerBook;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class HomeActivity extends AppCompatActivity {

    boolean isAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isAdmin = getIntent().getBooleanExtra("isAdmin",false);

        Button buttonEmail = (Button) findViewById(R.id.buttonEmail);
        buttonEmail.setOnClickListener(new OnClickListenerEmail());

        Button buttonLogout=(Button) findViewById(R.id.buttonLogout);

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity.this,MainActivity.class));
                finish();
            }
        });


    }

    /**Called when the user taps the 'Manage Books' button*/
    public void manageBooks(View view)
    {
        //params: context, class of the app comp to which sys should deliver the Intent
        Intent intent=new Intent(this,ManageBooksActivity.class);
        intent.putExtra("isAdmin",isAdmin);
        startActivity(intent);
    }


}
