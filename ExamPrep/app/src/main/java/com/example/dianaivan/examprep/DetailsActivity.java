package com.example.dianaivan.examprep;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DetailsActivity extends AppCompatActivity {

    TextView bookTitle;
    TextView bookDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        bookTitle=(TextView) findViewById(R.id.bookTitle);
        bookDate=(TextView)findViewById(R.id.bookDate);


    }
}
