package com.example.niramaya.ui.contacts;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.net.Uri;
import android.view.View;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.niramaya.R;
import com.google.firebase.auth.FirebaseAuth;


public class ContactsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_contacts, container, false);

        final TextView mail=root.findViewById(R.id.t3);
        TextView civil=root.findViewById(R.id.t5);
        TextView mahavir=root.findViewById(R.id.t6);
        TextView apple=root.findViewById(R.id.t7);
        TextView smimer=root.findViewById(R.id.t8);
        Button b1=root.findViewById(R.id.button);
        Button b2=root.findViewById(R.id.button1);
        Button b3=root.findViewById(R.id.button2);
        Button b4=root.findViewById(R.id.button3);


        // mail.setMovementMethod(LinkMovementMethod.getInstance());
        mail.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("IntentReset")
            @Override
            public void onClick(View view) {
                final String email=mail.getText().toString().trim();
                Intent i=new Intent(Intent.ACTION_SEND);
                i.setData(Uri.parse("mailto:"));
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_EMAIL,new String[]{email});
                startActivity(Intent.createChooser(i,"Choose an Email Client"));
            }
        });
        civil.setMovementMethod(LinkMovementMethod.getInstance());
        mahavir.setMovementMethod(LinkMovementMethod.getInstance());
        apple.setMovementMethod(LinkMovementMethod.getInstance());
        smimer.setMovementMethod(LinkMovementMethod.getInstance());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri n1 = Uri.parse("tel:02612244457");
                Intent i=new Intent(Intent.ACTION_DIAL,n1);
                startActivity(i);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri n2 = Uri.parse("tel:02612290000");
                Intent j=new Intent(Intent.ACTION_DIAL,n2);
                startActivity(j);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri n3 = Uri.parse("tel:02616696000");
                Intent k=new Intent(Intent.ACTION_DIAL,n3);
                startActivity(k);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri n4 = Uri.parse("tel:02612368040");
                Intent l=new Intent(Intent.ACTION_DIAL,n4);
                startActivity(l);
            }
        });
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        fAuth.signOut();
        return root;
    }
}