package com.example.niramaya;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Register extends AppCompatActivity implements View.OnClickListener  {

    LinearLayout verifyNumber;
    ScrollView regist;
    EditText editTextPhone, editTextCode,editEmail,editPass;
    FirebaseAuth mAuth;
    String codeSent;
    String phone;
    PhoneAuthCredential credential=null;
    private LocationManager locationManager;
    public GeoPoint home;
    PlacesClient placesClient;
    String api="AIzaSyBQjwrVDfFp06T0lHLt2a4ibbMQksq9awE";
    private TextInputLayout editTextEmail;
    private TextInputLayout editTextPassword;
    private TextInputLayout editTextName;
    private TextView mDate,answer;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Button register,sendcode,signin,resend;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    public int year;
    public int month;
    public int day;
    private String dob;
    public int ag;
    private String userID,gender,age , address , token,add_token;
    private FirebaseFirestore firebaseFirestore;
    private AuthCredential credential1;

    public Double Rlat, Rlo;
    public AutoCompleteTextView actv1;
    final String[] auto_area=new String[]{"Adajan Patiya","Adajan Gam","Rander","Ramnagar","Krushnakunj","Jahangirpura","Gorat","Palgam","Palanpore","Variav","Tadwadi",
            "Nanpura","Makkaipul","Rustampura","Sagarampura","Ruderpura","Navapura","Salabatpura","Begunpura","Moti Tokies","Haripura","Mahidharpura",
            "Saiydpura","Laldarwaja","Gopipura","Wadi Faliya","Chowk Bazar","Nanavat","Shapore","Puna A","Puna","Navagam-A","Navagam-B","Karanj-A","Karanj-B",
            "Lambe Hanuman","Ashwanikumar","Kapodra","Bhagyoday","Puna Gam","Puna B","Nana Varachha","Simada","Mota Varachha","Sarthana",
            "Umarwada A","Umarwada B","Anjana","Magob","Mithikhadi","Limbayat","Udhna Yard","Navagam/Dindoli","Magob-Parvat","Godadara","Dindoli","Parvat-Magob",
            "Karimabad","Vesu","Magdalla","Dumas","Khajod","Athwa","Piplod","City light","Althan Bhatar","Khatodara Pumping","Panas Ward",
            "Khatodara","Udhna Gam","Vijyanagar","Pandesara -Bhedwad","Bhestan","Pandesara Housing","Pandesara Bamroli","Udhna Sandh","Un-Gabheni","Vadod-Dipli","Jiyav-Sonari","New Bamroli",
            "Katargam","Paras","Gotalawadi","Fulpada","Amroli","Chhaparabhatha","Akhandanand","Ved Dabholi","Nani Bahucharaji","Kosad Awas","Kosad","Utran"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        verifyNumber=findViewById(R.id.NumberVerify);
        regist=findViewById(R.id.regist);
        verifyNumber.setVisibility(View.VISIBLE);
        regist.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();

        editTextCode = findViewById(R.id.editTextCode);
        editTextPhone = findViewById(R.id.editTextPhone);
        sendcode=findViewById(R.id.buttonGetVerificationCode);
        signin= findViewById(R.id.buttonSignIn);
        resend=findViewById(R.id.buttonGetResendCode);
        signin.setEnabled(false);
        resend.setEnabled(false);
        sendcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationCode();
            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationCode();
            }
        });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifySignInCode();
            }
        });


        editTextEmail=(TextInputLayout) findViewById(R.id.ema);
        editTextPassword=(TextInputLayout) findViewById(R.id.pass);
        editTextName=(TextInputLayout) findViewById(R.id.p_name);
        register = (Button) findViewById(R.id.reg);
        progressDialog = new ProgressDialog(this);
        radioGroup = findViewById(R.id.gender);



        requestPermissions(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
        }, 10);

        //For Date
        mDate = (TextView) findViewById(R.id.tvDate) ;
        answer=findViewById(R.id.ans);

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                year = cal.get(Calendar.YEAR);
                month= cal.get(Calendar.MONTH )  ;
                day= cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Register.this , android.R.style.Theme_Holo_Light_Dialog_MinWidth ,mDateSetListener , year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });


        if (!Places.isInitialized()){
            Places.initialize(getApplicationContext(),api);
        }
            placesClient=Places.createClient(this);


        final AutocompleteSupportFragment autocompleteSupportFragment= (AutocompleteSupportFragment) getSupportFragmentManager()
                                        .findFragmentById(R.id.add_auto);
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID,Place.Field.LAT_LNG,Place.Field.ADDRESS,Place.Field.NAME));
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Rlo =place.getLatLng().longitude;
                Rlat = place.getLatLng().latitude;
                home = new GeoPoint(Rlat,Rlo);
                 address=place.getName()+", "+place.getAddress();
                 add_token = Rlat+"-"+Rlo;
                Toast.makeText(Register.this, ""+add_token, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(@NonNull Status status) {

            }
        });







        actv1 = findViewById(R.id.autoArea);
        ImageView img =findViewById(R.id.image);

        ArrayAdapter<String> adapter1= new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,auto_area);
        actv1.setAdapter(adapter1);
        actv1.setThreshold(1);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actv1.showDropDown();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Calendar cal = Calendar.getInstance();

                month = month+1;
                dob=day+"/"+month+"/"+year;
                ag = cal.get(Calendar.YEAR) - year;
                age= String.valueOf(ag);
                answer.setPaintFlags(answer.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                answer.setText(dob);

            }
        };





        firebaseAuth= FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        register.setOnClickListener(this);


    }

    public void checkButton(View v){
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        gender=radioButton.getText().toString();
    }





    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override

    public void onClick(View v) {
        if( v == register){



            mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        FirebaseInstanceId.getInstance().getInstanceId()
                                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                        token=task.getResult().getToken();
                                        registerUser();

                                    }
                                });

                    }else
                    {
                        Toast.makeText(Register.this, "Error in Registering .... Please Try Again..", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),Register.class));
                        finish();

                    }

                }
            });

        }

    }

    private void verifySignInCode() {
        String code = editTextCode.getText().toString();

         credential = PhoneAuthProvider.getCredential(codeSent, code);

         if(credential!=null){
             Toast.makeText(getApplicationContext(),
                     "Verification Successfull", Toast.LENGTH_LONG).show();
             verifyNumber.setVisibility(View.INVISIBLE);
             regist.setVisibility(View.VISIBLE);

         }else
         {
             Toast.makeText(getApplicationContext(),
                     "Verification UnSuccessfull", Toast.LENGTH_LONG).show();

         }

    }


    private void sendVerificationCode() {
        String temp;
         temp = "+91 "+editTextPhone.getText().toString().trim();
        phone=temp;

        if (phone.isEmpty()) {
            editTextPhone.setError("Phone number is required");
            editTextPhone.requestFocus();
            return;
        }

        if (phone.length() < 10) {
            editTextPhone.setError("Please enter a valid phone");
            editTextPhone.requestFocus();
            return;
        }


        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {


        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            resend.setEnabled(true);
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Toast.makeText(Register.this, "OTP has been Send to your Phone number", Toast.LENGTH_SHORT).show();
            codeSent = s;
            resend.setEnabled(false);
            signin.setEnabled(true);

        }



    };



    @RequiresApi(api = Build.VERSION_CODES.M)

    private void registerUser(){
        final String email=editTextEmail.getEditText().getText().toString().trim();
        String password=editTextPassword.getEditText().getText().toString().trim();
        final String name = editTextName.getEditText().getText().toString().trim();
        final String p_area = actv1.getText().toString();




        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please Enter The Details...", Toast.LENGTH_SHORT).show();
            return;
        }else{



            firebaseAuth= FirebaseAuth.getInstance();

            //ADDING CREDENTIAL
            credential1 = EmailAuthProvider.getCredential(email, password);
            firebaseAuth.getCurrentUser().linkWithCredential(credential1);
            progressDialog.setMessage("Registering User .......");
            progressDialog.show();

            //Add user Info to The Database
            userID =firebaseAuth.getCurrentUser().getUid();
            firebaseAuth.getCurrentUser();

            //Add User Info
            DocumentReference documentReference =firebaseFirestore.collection("user_data").document(userID);
            Map<String,Object> user = new HashMap<>();
            user.put("fname",name);
            user.put("email",email);
            user.put("dob",dob);
            user.put("age",age);
            user.put("Gender",gender);
            user.put("area",p_area);
            user.put("home_location",home);
            user.put("home_address",address);
            user.put("add_token",add_token);






                documentReference.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){


                            Toast.makeText(Register.this, "User Registerd Successfull", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                            startActivity(new Intent(getApplicationContext(), DashBoard.class));
                        }else{
                            Toast.makeText(Register.this, "User Registeration UnSuccessfull", Toast.LENGTH_SHORT).show();
                            firebaseAuth.getCurrentUser().delete();
                            startActivity(new Intent(getApplicationContext(),Register.class));
                            finish();
                        }


                    }
                });





        }
    }

}