package org.rafisa.test3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.MapTileProviderArray;
import org.osmdroid.tileprovider.MapTileProviderBase;
import org.osmdroid.tileprovider.modules.IArchiveFile;
import org.osmdroid.tileprovider.modules.MBTilesFileArchive;
import org.osmdroid.tileprovider.modules.MapTileFileArchiveProvider;
import org.osmdroid.tileprovider.modules.MapTileModuleProviderBase;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.tileprovider.util.SimpleRegisterReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.TilesOverlay;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationListener, MenuItem.OnMenuItemClickListener {

    MapView map;
    LocationManager locationManager;
    IMapController controller;
    Location here;
    boolean isCentered = false;
    MyItemizedOverlay overlay_Standort;
    MyOwnItemizedOverlay overlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPQUESTOSM);

        map.setMultiTouchControls(true);
        map.setBuiltInZoomControls(true);
        map.setMinZoomLevel(3);

        controller = map.getController();
        controller.setZoom(18);

        XYTileSource treasureMapTileSource = new XYTileSource("mbtiles", ResourceProxy.string.offline_mode, 1, 20, 256, ".png", "http://example.org/");
        File file = new File(Environment.getExternalStorageDirectory(), "hsr.mbtiles");

        MapTileModuleProviderBase treasureMapModuleProvider = new MapTileFileArchiveProvider(new SimpleRegisterReceiver(this), treasureMapTileSource, new IArchiveFile[]{
                MBTilesFileArchive.getDatabaseFileArchive(file)});

        MapTileProviderBase treasureMapProvider = new MapTileProviderArray(treasureMapTileSource, null, new MapTileModuleProviderBase[]{treasureMapModuleProvider});

        TilesOverlay treasureMapTilesOverlay = new TilesOverlay(treasureMapProvider, getBaseContext());
        treasureMapTilesOverlay.setLoadingBackgroundColor(Color.TRANSPARENT);

        map.getOverlays().add(treasureMapTilesOverlay);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        here = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    public void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, this);
        if(here != null){
            controller.setCenter(locationToGeoPoint(here));
        }
        ResourceProxy proxy = new DefaultResourceProxyImpl(getApplicationContext());

        Drawable marker_Standort = getResources().getDrawable(R.drawable.location_marker);
        marker_Standort = markerSetPreferences(marker_Standort);

        Drawable marker = getResources().getDrawable(R.drawable.red_location);
        marker = markerSetPreferences(marker);

        //Standort
        overlay_Standort = new MyItemizedOverlay(marker_Standort, proxy);
        map.getOverlays().add(overlay_Standort);

        //Markers
        ArrayList<OverlayItem> overlayItemArray = new ArrayList<OverlayItem>();
        overlay = new MyOwnItemizedOverlay(this, overlayItemArray,marker,proxy);
        map.getOverlays().add(overlay);

        overlay_Standort.addItem(locationToGeoPoint(here), "", "");
    }

    private Drawable markerSetPreferences(Drawable m) {
        int markerWidth = m.getIntrinsicWidth();
        int markerHeight = m.getIntrinsicHeight();
        m.setBounds(0, markerHeight, markerWidth, 0);
        return m;
    }

    public void onPause(){
        super.onPause();
        locationManager.removeUpdates(this);
    }

    private GeoPoint locationToGeoPoint(Location l) {
        int latitude = (int) ( l.getLatitude() * 1E6);
        int longitude = (int) (l.getLongitude() * 1E6) ;
        GeoPoint g = new GeoPoint(latitude,longitude);
        return g;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuItem setMarker = menu.add("Set Marker");
        MenuItem menuItem = menu.add("log");
        setMarker.setOnMenuItemClickListener(this);
        menuItem.setOnMenuItemClickListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.map) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {
        here = location;
        if(!isCentered){
            controller.setCenter(locationToGeoPoint(here));
            isCentered = true;
            overlay_Standort.changePosition(locationToGeoPoint(here));
        }else{
            overlay_Standort.changePosition(locationToGeoPoint(here));
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if("log".equals(item.getTitle())){
            Intent intent = new Intent("ch.appquest.intent.LOG");
            String cords = "[";
            char az ='"';
            List<OverlayItem> items = overlay.getItems();
            for(OverlayItem i : items){
                GeoPoint g = i.getPoint();
                cords = cords + "{" + az + "lat" + az + ": " + g.getLatitudeE6() + ", " +az + "lon" + az + ": " + g.getLongitudeE6() + "}," ;
            }
            cords = cords.substring(0, cords.length()-1) + "]";
            intent.putExtra("ch.appquest.taskname", "Schatzkarte");
            intent.putExtra("ch.appquest.logmessage", cords);
            startActivity(intent);

        }else if("Set Marker".equals(item.getTitle()) && here != null){
            overlay.addItem(locationToGeoPoint(here), "marker", "marker");
            map.invalidate();
        }
        return false;
    }
}