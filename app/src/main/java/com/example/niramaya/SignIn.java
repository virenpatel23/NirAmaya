package com.example.niramaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {

  private TextInputLayout Email;
  private TextInputLayout Password;
  private Button Sign_In_button;
  private TextView Or;
  private TextView Tag1;
  private TextView Reglink;
  private ProgressDialog progressDialog;
  private FirebaseAuth firebaseAuth;

  String mytoast ="Invalid Email and Password";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_in);

    try{
      this.getSupportActionBar().hide();
    }catch (Exception e){
      Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_SHORT).show();
    }


    Email=(TextInputLayout) findViewById(R.id.ema);
    Password=(TextInputLayout) findViewById(R.id.pass);
    Sign_In_button=(Button) findViewById(R.id.button);
    Or=(TextView) findViewById(R.id.or);
    Tag1=(TextView) findViewById(R.id.tag1);
    Reglink=(TextView) findViewById(R.id.reglink);
    progressDialog = new ProgressDialog(this);

    firebaseAuth= FirebaseAuth.getInstance();

    if(firebaseAuth.getCurrentUser()!=null){
      finish();
      startActivity(new Intent(getApplicationContext(), DashBoard.class));
    }


    Sign_In_button.setOnClickListener(new View.OnClickListener() {
      public void onClick(View view) {
        sign_In();
      }
    });
    Reglink.setOnClickListener(new View.OnClickListener() {
                                 public void onClick(View view) {
                                   Intent intent=new Intent(SignIn.this,Register.class);
                                   startActivity(intent);
                                 }
                               }
    );


  }


  private void sign_In(){

    String email=Email.getEditText().getText().toString().trim();
    String password=Password.getEditText().getText().toString().trim();

    if(TextUtils.isEmpty(email)){
      Toast.makeText(this, "Please Enter Email...", Toast.LENGTH_SHORT).show();
      return;
    }

    if(TextUtils.isEmpty(password)){
      Toast.makeText(this, "Please Enter Passowrd.......", Toast.LENGTH_SHORT).show();
      return;
    }

    progressDialog.setMessage("Signing In .......");
    progressDialog.show();



    firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                  startActivity(new Intent(getApplicationContext(), DashBoard.class));
                  finish();
                }else{
                  progressDialog.dismiss();
                  Password.setError(null);
                  Toast.makeText(SignIn.this, "Sign_In Failed...", Toast.LENGTH_SHORT).show();
                }

              }
            });

  }

}
