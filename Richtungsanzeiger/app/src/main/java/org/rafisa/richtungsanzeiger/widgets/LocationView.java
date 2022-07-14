package org.rafisa.richtungsanzeiger.widgets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rafisa.richtungsanzeiger.R;
import org.rafisa.richtungsanzeiger.models.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Mit herzlichem Dank an https://stackoverflow.com/questions/28107692/how-can-i-make-my-android-swipeablecardviews-more-like-the-ios-7-mail-app-swipe
 */
public class LocationView extends RecyclerView.Adapter<LocationView.ViewHolder> {

    private List<Location> locations;
    private LocationViewListener locationViewListener;

    public LocationView(ArrayList<Location> locations,LocationViewListener locationViewListener) {
        this.locations = locations;
        this.locationViewListener = locationViewListener;
    }
    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull LocationView.ViewHolder holder, int position) {
        holder.locationName.setText(locations.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return locations == null ? 0 : locations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageButton showLocationButton;
        private TextView locationName;
        private ImageButton editLocationButton;

        public ViewHolder( View itemView) {
            super(itemView);
            this.showLocationButton = (ImageButton) itemView.findViewById(R.id.showDirectionButton);
            this.locationName = (TextView) itemView.findViewById(R.id.locationName);
            this.editLocationButton = (ImageButton) itemView.findViewById(R.id.editLocationButton);

            showLocationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    locationViewListener.showLocation(v, getAdapterPosition());
                }
            });

            editLocationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    locationViewListener.editLocation(v, getAdapterPosition());
                }
            });
        }
    }
}
