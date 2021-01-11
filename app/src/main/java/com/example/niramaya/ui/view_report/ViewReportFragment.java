package com.example.niramaya.ui.view_report;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.location.LocationManager;
import android.widget.Toast;

import com.example.niramaya.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ServerTimestamp;

import org.joda.time.DateTimeComparator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.github.mikephil.charting.utils.ColorTemplate.PASTEL_COLORS;
import static java.lang.System.currentTimeMillis;

public class ViewReportFragment extends Fragment {


    private BarChart mbarchart;
    private LineChart mlinechart;
    private LocationManager mLocationManager;
    public String area, disease, timePeriod;
    public @ServerTimestamp Date date, pdate;
    private ProgressDialog progressDialog;
    private AutoCompleteTextView actv1;
    private Button viewGraph;
    private ScrollView choices;
    private RelativeLayout graph;
    private FirebaseFirestore db;
    private int[] sev, onemonthcount, threemontcount;
    public  Date[] devdate, onemonthdate, threemonthdate;
    SimpleDateFormat sdf;
   public @ServerTimestamp ArrayList<Date> resultDate;



    final String[] auto_area = new String[]{"Surat","Adajan Patiya","Adajan Gam","Rander","Ramnagar","Krushnakunj","Jahangirpura","Gorat","Palgam","Palanpore","Variav","Tadwadi",
            "Nanpura","Makkaipul","Rustampura","Sagarampura","Ruderpura","Navapura","Salabatpura","Begunpura","Moti Tokies","Haripura","Mahidharpura",
            "Saiydpura","Laldarwaja","Gopipura","Wadi Faliya","Chowk Bazar","Nanavat","Shapore","Puna A","Puna","Navagam-A","Navagam-B","Karanj-A","Karanj-B",
            "Lambe Hanuman","Ashwanikumar","Kapodra","Bhagyoday","Puna Gam","Puna B","Nana Varachha","Simada","Mota Varachha","Sarthana",
            "Umarwada A","Umarwada B","Anjana","Magob","Mithikhadi","Limbayat","Udhna Yard","Navagam/Dindoli","Magob-Parvat","Godadara","Dindoli","Parvat-Magob",
            "Karimabad","Vesu","Magdalla","Dumas","Khajod","Athwa","Piplod","City light","Althan Bhatar","Khatodara Pumping","Panas Ward",
            "Khatodara","Udhna Gam","Vijyanagar","Pandesara -Bhedwad","Bhestan","Pandesara Housing","Pandesara Bamroli","Udhna Sandh","Un-Gabheni","Vadod-Dipli","Jiyav-Sonari","New Bamroli",
            "Katargam","Paras","Gotalawadi","Fulpada","Amroli","Chhaparabhatha","Akhandanand","Ved Dabholi","Nani Bahucharaji","Kosad Awas","Kosad","Utran"};


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_view_report, container, false);

        db = FirebaseFirestore.getInstance();
        Calendar calendar = Calendar.getInstance();

        choices = root.findViewById(R.id.choices);
        Spinner sp = root.findViewById(R.id.spinner);
        Spinner sp1 = root.findViewById(R.id.spinner2);
        viewGraph = root.findViewById(R.id.viewG);
        graph = root.findViewById(R.id.graph);
        mbarchart=root.findViewById(R.id.barchart);
        mlinechart=root.findViewById(R.id.linechart);
        progressDialog = new ProgressDialog(getContext());

        mbarchart.getDescription().setEnabled(false);


        //All lists
        resultDate = new ArrayList<>();
        //Lists for Last Seven Days
        devdate = new Date[7];
        sev = new int[7];
        //Lists for Last One Month
        onemonthdate = new Date[31];
        onemonthcount = new int[31];

        //Lists for Last 3 Month
        threemonthdate = new Date[3];
        threemontcount = new int[3];


         sdf = new SimpleDateFormat("dd/MM/yyyy");
        //Spinner For Disease
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.disease, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).toString().equals("----Disease List----")) {
                } else {
                    disease = adapterView.getItemAtPosition(i).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        //Spinner For TimePeriod
        ArrayAdapter<CharSequence> ad = ArrayAdapter.createFromResource(getActivity(), R.array.timeslot, android.R.layout.simple_spinner_item);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(ad);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).toString().equals("----Time Period----")) {
                } else {
                    timePeriod = adapterView.getItemAtPosition(i).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        //Selector For Area
        actv1 = root.findViewById(R.id.autoArea);
        ImageView img = root.findViewById(R.id.image);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, auto_area);
        actv1.setAdapter(adapter1);
        actv1.setThreshold(1);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actv1.showDropDown();
            }
        });

        final SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        viewGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                area = actv1.getText().toString();

                if (area == null || disease == null || timePeriod == null) {
                    Toast.makeText(getActivity(), "Please Select the Empty Field", Toast.LENGTH_SHORT).show();
                } else {
                    choices.setVisibility(View.INVISIBLE);
                    graph.setVisibility(View.VISIBLE);
                    progressDialog.setMessage("Getting Records .......");
                    progressDialog.show();

                    if(area.equals("Surat")){
                        db.collection("Report")
                                .whereEqualTo("Disease", disease)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Timestamp temp = (Timestamp) document.get("date");
                                                pdate = temp.toDate();
                                                resultDate.add(pdate);
                                            }
                                            viewReport();
                                        } else {
                                            Toast.makeText(getContext(), "Couldn't Load Data Try Again .....", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }else{
                        db.collection("Report")
                                .whereEqualTo("ward", area)
                                .whereEqualTo("Disease", disease)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {

                                                Timestamp temp = (Timestamp) document.get("date");
                                                pdate = temp.toDate();
                                                resultDate.add(pdate);
                                            }

                                            viewReport();
                                        } else {
                                            Toast.makeText(getContext(), "Couldn't Load Data Try Again .....", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }



                }
            }
        });


        return root;
    }


    private void viewReport() {


        switch (timePeriod) {
            case "Last 7 Days":
                sevenday();
                break;

            case "Last Month":
                lastMonth();
                break;

            case "Last 3 Months":
                lastThreeMonth();
                break;
        }

    }


    private void sevenday() {

        final ArrayList<String> days = new ArrayList<>();
        SimpleDateFormat format=new SimpleDateFormat("EE");
        DateTimeComparator dateTimeComparator = DateTimeComparator.getDateOnlyInstance();
        for (int i = 0; i < 7; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -(i + 1));
            devdate[i] = calendar.getTime();
            days.add(format.format(devdate[i]));
        }

       //Toast.makeText(getActivity(), ""+days[0]+"\n"+days[1]+"\n"+days[2]+"\n"+days[3]+"\n"+days[4]+"\n"+days[5], Toast.LENGTH_SHORT).show();


        //Toast.makeText(getContext(), "" + resultDate.size(), Toast.LENGTH_SHORT).show();
        if (resultDate.size() == 0) {
            Toast.makeText(getContext(), "No Cases Are Registerd for "+disease+" disease.", Toast.LENGTH_SHORT).show();
        } else{
            for (int k = 0; k <=resultDate.size()-1; k++) {
            for (int j = 0; j < devdate.length; j++) {

                if (dateTimeComparator.compare(resultDate.get(k), devdate[j]) == 0) {
                    sev[j] += 1;
                }
            }
        }
        }



       // Toast.makeText(getContext(), "day 1 " + sev[0] + "\nday 2 " + sev[1] + "\nday 3 " + sev[2] + "\nday 4 " + sev[3] + "\nday 5 " + sev[4] + "\nday 6 " + sev[5] + "\nday 7 " + sev[6], Toast.LENGTH_SHORT).show();


        List<BarEntry> barEntries= new ArrayList<>();

        for(int i=0 ; i<sev.length;i++){
            barEntries.add(new BarEntry(i+1,sev[i]));

        }

        BarDataSet barDataSet =new BarDataSet(barEntries,"Last 7 Days Report ");
       // mbarchart.setBackgroundColor(Color.rgb(255,253,215 ));

        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData barData = new BarData(barDataSet);
        progressDialog.dismiss();
        mbarchart.setVisibility(View.VISIBLE);
        mbarchart.animateY(2000);
        mbarchart.setData(barData);
        mbarchart.setDragEnabled(false);
        XAxis xAxis=mbarchart.getXAxis();
        xAxis.setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(days) );
        xAxis.setGranularity(1f);
        xAxis.setTextSize(10);
        xAxis.setCenterAxisLabels(true);
        xAxis.setAvoidFirstLastClipping(true);

        xAxis.setEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.TOP);

        mbarchart.getXAxis().setDrawGridLines(false);
        mbarchart.getAxisLeft().setDrawGridLines(false);
        mbarchart.getAxisRight().setEnabled(false);
        mbarchart.setFitBars(true);

    }


    private void lastMonth() {
        DateTimeComparator dateTimeComparator1 = DateTimeComparator.getDateOnlyInstance();
        for (int i = 0; i < 31; i++) {
            Calendar calendar1 = Calendar.getInstance();
            calendar1.add(Calendar.DATE, -(i + 1));
            onemonthdate[i] = calendar1.getTime();
        }

        if (resultDate.size() == 0) {
            Toast.makeText(getContext(), "No Cases Are Registerd for "+disease+" disease.", Toast.LENGTH_SHORT).show();
        } else {
            for (int k = 0; k <= resultDate.size()-1; k++) {
                for (int j = 0; j < onemonthdate.length; j++) {
                    if (dateTimeComparator1.compare(resultDate.get(k), onemonthdate[j]) == 0) {
                        onemonthcount[j] += 1;
                    }
                }
            }
        }


        List<Entry> yValues=new ArrayList<>();
        for(int i=0 ; i<=onemonthcount.length-1;i++){
            yValues.add(new Entry(i+1,onemonthcount[i]));
        }


        LineDataSet set1=new LineDataSet(yValues,"Last 28 Days");
        set1.setFillAlpha(110);
        set1.setColor(Color.RED);
        set1.setLineWidth(2f);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);
        progressDialog.dismiss();
        mlinechart.setVisibility(View.VISIBLE);
//        mlinechart.setBackgroundColor(Color.rgb(255,253,215));

        mlinechart.setTouchEnabled(true);
        mlinechart.setDragEnabled(true);
        mlinechart.setScaleEnabled(true);
        mlinechart.setPinchZoom(true);
        mlinechart.setDrawGridBackground(false);
        mlinechart.setExtraLeftOffset(15);
        mlinechart.setExtraRightOffset(15);
        mlinechart.getDescription().setEnabled(false);
        //to hide background lines
        mlinechart.getXAxis().setDrawGridLines(false);
        mlinechart.getAxisLeft().setDrawGridLines(false);
        mlinechart.getAxisRight().setDrawGridLines(false);

        //to hide right Y and top X border
        YAxis rightYAxis = mlinechart.getAxisRight();
        rightYAxis.setEnabled(false);
        YAxis leftYAxis = mlinechart.getAxisLeft();
        leftYAxis.setGranularity(1);
        leftYAxis.setAxisMaximum(50);

        XAxis topXAxis = mlinechart.getXAxis();
        topXAxis.setEnabled(false);


        XAxis xAxis = mlinechart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        set1.setLineWidth(4f);
        set1.setCircleRadius(3f);
        set1.setDrawValues(false);
       set1.setCircleHoleColor(Color.rgb(102,178,225));
        set1.setCircleColor(Color.rgb(0,0,128));
        mlinechart.setData(data);

    }

    private void lastThreeMonth() {
        DateTimeComparator dateTimeComparator2 = DateTimeComparator.getDateOnlyInstance();
        int year,month,month1,month2;
        String date,date1,date2;


        Calendar calendar1=Calendar.getInstance();
        year=calendar1.get(Calendar.YEAR);
        month=calendar1.get(Calendar.MONTH)+1;
        month1=calendar1.get(Calendar.MONTH);
        month2=calendar1.get(Calendar.MONTH)-1;

        date="01/"+month+"/"+year;
        date1="01/"+month1+"/"+year;
        date2="01/"+month2+"/"+year;

        try {

            threemonthdate[0] = sdf.parse(date);
            threemonthdate[1] = sdf.parse(date1);
            threemonthdate[2] = sdf.parse(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Toast.makeText(getActivity(), ""+threemonthdate[0]+"\n"+threemonthdate[1]+"\n"+threemonthdate[2], Toast.LENGTH_SHORT).show();

        if (resultDate.size() == 0) {
            Toast.makeText(getContext(), "No Cases Are Registerd for "+disease+" disease.", Toast.LENGTH_SHORT).show();
        } else {
            for (int k = 0; k <= resultDate.size()-1; k++) {
                if(dateTimeComparator2.compare(resultDate.get(k), threemonthdate[0])>=0){threemontcount[0]+=1;}
                if(dateTimeComparator2.compare(resultDate.get(k), threemonthdate[0])<0 && dateTimeComparator2.compare(resultDate.get(k), threemonthdate[1])>=0){threemontcount[1]+=1;}
                if(dateTimeComparator2.compare(resultDate.get(k), threemonthdate[1])<0 && dateTimeComparator2.compare(resultDate.get(k), threemonthdate[2])>=0){threemontcount[2]+=1;}

            }
        }


        Toast.makeText(getActivity(), "Current Month : "+ threemontcount[0]+"\nLast 1 Month : "+ threemontcount[1]+"\nLast 3 Month : "+ threemontcount[2], Toast.LENGTH_SHORT).show();

        List<BarEntry> barEntries= new ArrayList<>();

        for(int i=0 ; i<threemontcount.length;i++){
            barEntries.add(new BarEntry(i+1,threemontcount[i]));
        }

        BarDataSet barDataSet =new BarDataSet(barEntries,"Last 3 Months  ");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData barData = new BarData(barDataSet);
        progressDialog.dismiss();
        mbarchart.setVisibility(View.VISIBLE);
       // mbarchart.setBackgroundColor(Color.rgb(255,253,215 ));
        mbarchart.animateY(2000);
        mbarchart.setData(barData);
        mbarchart.getXAxis().setDrawGridLines(false);
        mbarchart.getAxisLeft().setDrawGridLines(false);
        mbarchart.getAxisRight().setEnabled(false);
        mbarchart.setFitBars(true);

        YAxis leftYAxis = mbarchart.getAxisLeft();
        leftYAxis.setGranularity(1);
        leftYAxis.setAxisMaximum(50);

        XAxis xAxis=mbarchart.getXAxis();
        String[] values=new String[] {"This Month","Last Month","2nd Last Month"};
        xAxis.setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(values) );
        xAxis.setGranularity(1f);
        xAxis.setTextSize(10);
        xAxis.setCenterAxisLabels(true);
        xAxis.setAvoidFirstLastClipping(true);

        xAxis.setEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
    }


}