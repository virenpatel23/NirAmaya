package com.example.niramaya;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.niramaya.ui.profile.ProfileFragment;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Calendar;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class Updateprofile extends AppCompatActivity {

    private TextView mDate;
    private RadioGroup radioGroup;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    public int year;
    public int month;
    public int day;
    private String dob;
    public int ag;

    public AutoCompleteTextView actv1;
    final String[] auto_area=new String[]{"Nanpura","Sagrampura","Salabatpura","Begumpura","Haripura","Mahidharpura","Saiyadpura","Gopipura",
            "Wadifalia","Sonifalia","Nanavat","Shahpor","Athwa","Rander","Adajan","Nanavarachha","Laldarwaja","Katargam Gotalawadi","Ashvanikuma Navagam","Athwa - Umara"," Majura - Khatodara","Anjana",
            " Umarwada","TPS - 9 Majura","Tunki","Singanpor","Dabholi","Ved","Katargam","Fulpada","Kapadra","Nanavarachha","Karanj","Umarwada",
            "Magob","Dumbhal","Anjana","Limbayat","Dindoli","Bhedvad","Bhestan","Pandesara","Udhana","Bamroli","Majura","Bhatar","Althan","Umara","Piplod",
            "Jahangirabad","Jahangirpura","Pisad","Vadod","Pal","Palanpor","Variyav","Chhapara Bhatha","Kosad","Amroli","Utran","Motavarachha",
            "Sarthana","Simada","Puna","Magob","Parvat","Godadara","Dindoli","Unn","Sonari","Gabheni","Budiya","Jiyav","Vadod","Bamroli","Bhimrad","Bharthana ","Sarsana","Khajod","Abhava","Vesu","Rundh","Magdalla","Gaviyar","Vanta","Dumas","Sultanabad","Bhimpor"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_profile);

        TextInputLayout editTextEmail = findViewById(R.id.ema);
        TextInputLayout editTextName = findViewById(R.id.p_name);
        Button submit = findViewById(R.id.submit);
        ProgressDialog progressDialog = new ProgressDialog(this);
        radioGroup = findViewById(R.id.gender);

        requestPermissions(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
        }, 10);

        //For Date
        mDate = findViewById(R.id.tvDate) ;

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                year = cal.get(Calendar.YEAR);
                month= cal.get(Calendar.MONTH )  ;
                day= cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Updateprofile.this , android.R.style.Theme_Holo_Light_Dialog_MinWidth ,mDateSetListener , year,month,day);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        actv1 = findViewById(R.id.autoArea);
        ImageView img =findViewById(R.id.image);

        ArrayAdapter<String> adapter1= new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,auto_area);
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
                String age = String.valueOf(ag);
                mDate.setText(dob);

            }
        };

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(Updateprofile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Updateprofile.this, ProfileFragment.class);
                startActivity(intent);
            }
        });
    }

    public void checkButton(View v){
        int radioId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioId);
        String gender = radioButton.getText().toString();
    }



}
