package org.rafisa.richtungsanzeiger.activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.rafisa.richtungsanzeiger.R;
import org.rafisa.richtungsanzeiger.databinding.FragmentSecondBinding;
import org.rafisa.richtungsanzeiger.databinding.FragmentThirdBinding;
import org.rafisa.richtungsanzeiger.models.Location;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditLocationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditLocationFragment extends Fragment {

    private Location location;
    private FragmentThirdBinding binding;

    public EditLocationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThirdFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditLocationFragment newInstance(String param1, String param2) {
        EditLocationFragment fragment = new EditLocationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentThirdBinding.inflate(inflater, container, false);
        if (getArguments() != null) {
            String json = getArguments().getString("location");
            Gson gson = new Gson();
            location = gson.fromJson(json, new TypeToken<Location>() {
            }.getType());
            binding.editLocationName.setText(location.getName());
            binding.latitudeText.setText(String.valueOf(location.getLatitude()));
            binding.longitudeText.setText(String.valueOf(location.getLongitude()));
        }


        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}