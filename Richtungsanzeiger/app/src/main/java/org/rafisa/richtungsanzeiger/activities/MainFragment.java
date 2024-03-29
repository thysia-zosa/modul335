package org.rafisa.richtungsanzeiger.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.rafisa.richtungsanzeiger.R;
import org.rafisa.richtungsanzeiger.databinding.FragmentFirstBinding;
import org.rafisa.richtungsanzeiger.models.Direction;
import org.rafisa.richtungsanzeiger.models.Location;
import org.rafisa.richtungsanzeiger.widgets.LocationView;
import org.rafisa.richtungsanzeiger.widgets.LocationViewListener;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    private static final int REQUEST_LOCATION = 1;
    private FragmentFirstBinding binding;
    ArrayList<Location> locationList;
    private RecyclerView locRecyclerView;
    private LocationView locationView;
    private FloatingActionButton addLocation;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getLocationList();
        if (getArguments() != null) {
            String json = getArguments().getString("location");
            int position = getArguments().getInt("position");
            Gson gson = new Gson();
            Location savedLocation = gson.fromJson(json, new TypeToken<Location>() {}.getType());
            if (position == -1) {
                locationList.add(savedLocation);
            } else {
                locationList.set(position, savedLocation);
            }
            saveLocationList();
        }

        LocationViewListener locationViewListener = new LocationViewListener() {
            @Override
            public void showLocation(View view, int position) {
                Location actualLocation = tryGetLocation();
                Location targetLocation = locationList.get(position);
                Direction direction = new Direction(actualLocation, targetLocation);
                int directionValue = direction.getDirectionAzimuth();
                Bundle bundle = new Bundle();
                bundle.putInt("directionValue", directionValue);
                NavHostFragment.findNavController(MainFragment.this)
                        .navigate(R.id.showDirectionAction, bundle);
            }

            @Override
            public void editLocation(View view, int position) {
                Location location = locationList.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("location", location.toJson());
                bundle.putInt("position", position);
                NavHostFragment.findNavController(MainFragment.this)
                        .navigate(R.id.action_FirstFragment_to_ThirdFragment, bundle);
            }
        };

        locationView = new LocationView(locationList, locationViewListener);
        locRecyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
        locRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        locRecyclerView.setAdapter(locationView);

        addLocation = (FloatingActionButton) getView().findViewById(R.id.fab);
        addLocation.setOnClickListener(v -> {
            NavHostFragment.findNavController(MainFragment.this).navigate(R.id.action_FirstFragment_to_ThirdFragment);
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        getLocationList();
    }

    /**
     * für das Folgende: Danke an https://www.tutorialspoint.com/how-to-get-current-location-latitude-and-longitude-in-android
     *
     * @return
     */
    private Location tryGetLocation() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getGpsEnabled();
            return new Location("Hier", 8.5210211, 47.3598043);
        } else {
            return getLocation(locationManager);
        }
    }

    private Location getLocation(LocationManager manager) {
        if (ActivityCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            return new Location("Hier", 8.5210211, 47.3598043);
        } else {
            android.location.Location locationGps = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGps != null) {
                double latitude = locationGps.getLatitude();
                double longitude = locationGps.getLongitude();
                return new Location("Hier", longitude, latitude);
            } else {
                return new Location("Hier", 8.5210211, 47.3598043);
            }
        }

    }

    private void getGpsEnabled() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("GPS ermöglichen?")
                .setCancelable(false)
                .setPositiveButton("Ja", (dialog, which)
                        -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                .setNegativeButton("Nein", (dialog, which) -> dialog.cancel());
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getLocationList() {
        SharedPreferences sharedPrefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        boolean init = sharedPrefs.getBoolean("initialisiert", false);
        if (!init) {
            initSharedPrefs();
        } //else {
        String json = sharedPrefs.getString("locationList", "");
        Gson gson = new Gson();
        locationList = gson.fromJson(json, new TypeToken<ArrayList<Location>>() {}.getType());
//        }
    }

    private void initSharedPrefs() {
        SharedPreferences sharedPrefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean("initialisiert", true);
        locationList = new ArrayList<>();
        locationList.add(new Location("Matterhorn", 7.6584519, 45.9765738));
        locationList.add(new Location("Bundeshaus", 7.4442559, 46.9465609));

        String json = new Gson().toJson(locationList);

        editor.putString("locationList", json);
        editor.apply();
    }

    private void saveLocationList() {
        SharedPreferences sharedPrefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        String json = new Gson().toJson(locationList);

        editor.putString("locationList", json);
        editor.putInt("locationListSize", locationList.size());
        editor.apply();

    }

}