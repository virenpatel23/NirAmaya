package com.example.niramaya.ui.home;

import  com.example.niramaya.Disease;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.niramaya.DiseaseListAdapter;
import com.example.niramaya.Precautions;
import com.example.niramaya.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment implements OnMapReadyCallback , LocationListener  {

    private HomeViewModel homeViewModel;
    private GoogleMap mapAPI;
    private MapView mapView;
    TileProvider mProvider;
    TileOverlay mOverlay;
    List<LatLng> list ;
    List<GeoPoint> data=new ArrayList<>();
    public Double curLatitude=null,curLongitude=null;
    private TextView tx;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
     public String area,temp,UID;
    ArrayList<String> Tem;
    ListView curr_disease;
    String option_disease = "all";

    Disease disease;
    ArrayList<Disease> diseaseArrayList;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        tx=(TextView) root.findViewById(R.id.tx1);
        curr_disease=(ListView) root.findViewById(R.id.curr_dis);


        firebaseAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        UID=firebaseAuth.getCurrentUser().getUid();

        db.collection("user_data").document(UID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();

                if(task.isSuccessful()){
                    area= (String) document.get("area");

                    tx.setText(area);
                    getReport();

                }

            }
        });

        mapView=root.findViewById(R.id.mapAPI);
        if(mapView!=null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.animate();
            mapView.getMapAsync(this);

        }




        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.dashboard_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.item1:
                option_disease="Malaria";
                addHeatMap();
                Toast.makeText(getContext(), "Heat Map for Malaria", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item2:
                option_disease="Dengue";
                addHeatMap();
                Toast.makeText(getContext(), "Heat Map for Dengue", Toast.LENGTH_SHORT).show();
                break;

            case R.id.item3:
                option_disease="Typhoid";
                addHeatMap();
                Toast.makeText(getContext(), "Heat Map for Typhoid ", Toast.LENGTH_SHORT).show();
                break;

            case R.id.item4:
                option_disease="Gastro_Enteritis";
                addHeatMap();
                Toast.makeText(getContext(), "Heat Map for Gastro_Enteritis", Toast.LENGTH_SHORT).show();
                break;

            case R.id.item5:
                option_disease="Pulmonary TB";
                addHeatMap();
                Toast.makeText(getContext(), "Heat Map for Pulmonary TB", Toast.LENGTH_SHORT).show();
                break;

            case R.id.item6:
                option_disease="Pneumonia";
                addHeatMap();
                Toast.makeText(getContext(), "Heat Map for Pneumonia", Toast.LENGTH_SHORT).show();
                break;

            case R.id.item7:
                option_disease="Acute Respiratory Infections";
                addHeatMap();
                Toast.makeText(getContext(), "Heat Map for Acute Respiratory Infections", Toast.LENGTH_SHORT).show();
                break;

            case R.id.item8:
                option_disease="Hepatitis";
                addHeatMap();
                Toast.makeText(getContext(), "Heat Map for Hepatitis", Toast.LENGTH_SHORT).show();
                break;

            case R.id.item9:
                option_disease="Cholera";
                addHeatMap();
                Toast.makeText(getContext(), "Heat Map for Cholera", Toast.LENGTH_SHORT).show();

                break;
            case R.id.item10:
                option_disease="Chikungunya";
                addHeatMap();
                Toast.makeText(getContext(), "Heat Map for Chikungunya", Toast.LENGTH_SHORT).show();
                break;

            case R.id.item11:
                option_disease="Filaria";
                addHeatMap();
                Toast.makeText(getContext(), "Heat Map for Filaria", Toast.LENGTH_SHORT).show();
                break;
            case R.id.all:
                option_disease="all";
                addHeatMap();
                Toast.makeText(getContext(), "Heat Map for All Diseases", Toast.LENGTH_SHORT).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }





    private void getReport() {

        Tem=new ArrayList<>();
        db.collection("Report")
                .whereEqualTo("ward", area)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                temp = (String) document.get("Disease");
                                Tem.add(temp);
                            }

                            setList();

                        } else {
                            Toast.makeText(getContext(), "Couldn't Load Data Try Again .....", Toast.LENGTH_SHORT).show();
                        }
                    }


                });



    }



    private void setList() {


        //Toast.makeText(getContext(), "" + Tem.size(), Toast.LENGTH_SHORT).show();
        diseaseArrayList = new ArrayList<>();
        ArrayList Temporary = new ArrayList();

        for (int i = 0; i < Tem.size(); i++) {

            if(!Temporary.contains(Tem.get(i))){
                int counts = Collections.frequency(Tem, Tem.get(i));
                // Toast.makeText(getContext(), "" + Tem.get(i) + "\n" + counts, Toast.LENGTH_SHORT).show();
                Temporary.add(Tem.get(i));
                diseaseArrayList.add(new Disease(counts, Tem.get(i)));
            }

        }


        //Sorting Arraylist in Decending
        Collections.sort(diseaseArrayList, Disease.Countno);

        DiseaseListAdapter arrayAdapter = new DiseaseListAdapter(Objects.requireNonNull(getContext()), R.layout.disease_list_adapter_layout, diseaseArrayList);
        curr_disease.setAdapter(arrayAdapter);




        curr_disease.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Disease temp = new Disease();
                    temp= (Disease) curr_disease.getItemAtPosition(position);
                    String nam=temp.getDiseae();
                   // Toast.makeText(getContext(), ""+nam, Toast.LENGTH_SHORT).show();

                    Intent intent  = new Intent(getContext(), Precautions.class);
                    intent.putExtra("Disease_Name",nam);
                    startActivity(intent);




            }
        });


       
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapAPI=googleMap;

        LatLng current = new LatLng(curLatitude, curLongitude );
        CameraPosition curr=CameraPosition.builder().target(current).zoom(14).build();
        mapAPI.moveCamera(CameraUpdateFactory.newCameraPosition(curr));
        Circle circle=mapAPI.addCircle(new CircleOptions()
                            .center(current)
                            .radius(100)
                            .strokeColor(Color.WHITE)
                            .fillColor(Color.LTGRAY)
        );
        addHeatMap();


       /* MarkerOptions markerOptions=new MarkerOptions().position(current).title("Your Location");
        mapAPI.addMarker(markerOptions);*/

    }



    private void addHeatMap() {

        list = new ArrayList<LatLng>();
        if(option_disease.contentEquals("all")){
            db.collection("Report")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    GeoPoint temp=null;
                                    temp= (GeoPoint) document.get("location");
                                    list.add(new LatLng(temp.getLatitude(),temp.getLongitude()));
                                }
                            } else {

                            }
                            if(list.size()!=0){
                                //heat Map
                                Toast.makeText(getContext(), ""+list.size(), Toast.LENGTH_SHORT).show();
                                if(mOverlay!=null){
                                    mOverlay.remove();
                                }
                                mProvider = (TileProvider) new HeatmapTileProvider.Builder()
                                        .data(list)
                                        .build();
                                // Add a tile overlay to the map, using the heat map tile provider.
                                mOverlay = mapAPI.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
                                mOverlay.clearTileCache();
                            }
                        }
                    });

        }else{

            db.collection("Report")
                    .whereEqualTo("Disease",option_disease)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    GeoPoint temp=null;
                                    temp= (GeoPoint) document.get("location");
                                    list.add(new LatLng(temp.getLatitude(),temp.getLongitude()));
                                }
                            } else {

                            }

                            if(list.size()!=0){
                                //heat Map
                                Toast.makeText(getContext(), ""+list.size(), Toast.LENGTH_SHORT).show();
                                mOverlay.remove();

                                mProvider = (TileProvider) new HeatmapTileProvider.Builder()
                                        .data(list)
                                        .build();
                                // Add a tile overlay to the map, using the heat map tile provider.
                                mOverlay = mapAPI.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
                                mOverlay.clearTileCache();
                            }


                        }
                    });

        }





    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        ///////////////////////////////////////////////////////////////////////////////////////////
        LocationManager locationManager = (LocationManager) Objects.requireNonNull(getActivity()).getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        Location location = Objects.requireNonNull(locationManager).getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        onLocationChanged(Objects.requireNonNull(location));

        //////////////////////////////////////////////////////////////////////////////////////////////


    }


    @Override
    public void onLocationChanged(Location location) {
        curLongitude = location.getLongitude();
        curLatitude = location.getLatitude();


    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }



}