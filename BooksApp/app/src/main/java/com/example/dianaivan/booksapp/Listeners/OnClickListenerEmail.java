package com.example.dianaivan.booksapp.Listeners;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dianaivan.booksapp.R;

/**
 * Created by Diana Ivan on 11/5/2017.
 */

public class OnClickListenerEmail implements View.OnClickListener {
    @Override
    public void onClick(View view)
    {
        //get the application context that is needed to inflate the XML layout file
        final Context context=view.getRootView().getContext();
        //make ui elements/widgets accessible using code
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView=inflater.inflate(R.layout.email_form,null,false);

        //list form widgets inside mail_form as final vars for using them in an AlertDialog
        final EditText editTextEmail=(EditText) formElementsView.findViewById(R.id.editTextEmail);
        final EditText editTextEmailContent=(EditText) formElementsView.findViewById(R.id.editTextEmailContent);

        //create an alert dialog with the inflated book_input_form.xml

        new AlertDialog.Builder(context).setView(formElementsView)
                .setTitle("Send E-mail")
                .setPositiveButton("Send",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog,int id){
                                String email=editTextEmail.getText().toString();
                                String emailContent=editTextEmailContent.getText().toString();

                                sendEmail(context,email,emailContent);
                                dialog.cancel();
                            }
                        }).show();

    }

    public void sendEmail(Context context,String email, String emailContent){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{email});
        i.putExtra(Intent.EXTRA_SUBJECT, "Email from BooksApp");
        i.putExtra(Intent.EXTRA_TEXT   , emailContent);
        try {
            context.startActivity(Intent.createChooser(i, "Send email"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "There are no e-mail clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

}
