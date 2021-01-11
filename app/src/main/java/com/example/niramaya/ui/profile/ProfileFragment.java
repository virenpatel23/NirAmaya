package com.example.niramaya.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.niramaya.R;
import com.example.niramaya.Updateprofile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import java.util.Objects;
import javax.annotation.Nullable;

public class ProfileFragment extends Fragment {

    private TextView name;
    private TextView dob ;
    private TextView age;
    private TextView gender ;
    private TextView email;
    private TextView area ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        name=root.findViewById(R.id.pname);
        dob =root.findViewById(R.id.prdob);
        age = root.findViewById(R.id.prage);
        gender = root.findViewById(R.id.prgender);
        email = root.findViewById(R.id.premail);
        area = root.findViewById(R.id.prarea);
        Button b = root.findViewById(R.id.update);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), Updateprofile.class);
                startActivity(intent);
            }
        }
        );
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        String userId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        DocumentReference documentReference = firebaseFirestore.collection("user_data").document(userId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                String pname= Objects.requireNonNull(documentSnapshot).getString("fname");
                String pdob=documentSnapshot.getString("dob");
                String pAge=documentSnapshot.getString("age");
                String pgender =documentSnapshot.getString("Gender");
                String pemail= documentSnapshot.getString("email");
                String parea = documentSnapshot.getString("area");
                name.setText(pname);
                dob.setText(pdob);
                age.setText(pAge);
                gender.setText(pgender);
                email.setText(pemail);
                area.setText(parea);

            }
        });

    }


}