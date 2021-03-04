package org.rafisa.richtungsanzeiger.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import org.rafisa.richtungsanzeiger.R;
import org.rafisa.richtungsanzeiger.databinding.FragmentFirstBinding;
import org.rafisa.richtungsanzeiger.models.Direction;
import org.rafisa.richtungsanzeiger.models.Location;
import org.rafisa.richtungsanzeiger.widgets.LocationView;
import org.rafisa.richtungsanzeiger.widgets.LocationViewListener;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    private FragmentFirstBinding binding;
    ArrayList<Location> locationList;
    private RecyclerView locRecyclerView;
    private LocationView locationView;

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

        locationList = new ArrayList<>();
        locationList.add(new Location("Matterhorn", 7.6584519, 45.9765738));
        locationList.add(new Location ("Bundeshaus", 7.4442559, 46.9465609));

        LocationViewListener locationViewListener = new LocationViewListener() {
            @Override
            public void showLocation(View view, int position) {
                Location actualLocation = new Location("Hier", 8.5210211, 47.3598043);
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

            }
        };

        locationView = new LocationView(locationList, locationViewListener);
        locRecyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
        locRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        locRecyclerView.setAdapter(locationView);

//        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//            }
//        });
//        binding.fab.setOnClickListener(view1 -> Snackbar.make(view1, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show());

//        binding.showDirectionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Location actualLocation = new Location("Hier", 8.5210211, 47.3598043);
//                Location targetLocation = new Location("Matterhorn", 7.6584519, 45.9765738);
//                Direction direction = new Direction(actualLocation, targetLocation);
//                int directionValue = direction.getDirectionAzimuth();
//                Bundle bundle = new Bundle();
//                bundle.putInt("directionValue", directionValue);
//                NavHostFragment.findNavController(MainFragment.this)
//                        .navigate(R.id.showDirectionAction, bundle);
//            }
//        });
//        binding.editLocationButton.setOnClickListener(v -> NavHostFragment.findNavController(MainFragment.this)
//                .navigate(R.id.action_FirstFragment_to_ThirdFragment));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}