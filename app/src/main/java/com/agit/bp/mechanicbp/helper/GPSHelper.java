package helper;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

/**
 * Created by Mushlihun on 18/08/2016.
 */
public class GPSHelper implements LocationListener {

    private boolean isGPSEnabled;

    // Flag for network status
    private boolean isNetworkEnabled = false;

    // Flag for GPS status
    private boolean canGetLocation = false;

    private LocationManager locationManager;
    private Location location;

    private final Context context;
    private double latitude; // Latitude
    private double longitude; // Longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    public GPSHelper(Context context) {
        this.context = context;
        locationManager = (LocationManager) this.context.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(isGPSEnabled==false){
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }
        Log.d("GPS STATUS", String.valueOf(isGPSEnabled));
        location = getLastKnownLocation();
        if(location==null){
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
            }
        }
    }

    private Location getLastKnownLocation() {
        locationManager = (LocationManager) this.context.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            locationManager.requestLocationUpdates(provider, 0, 0, this);
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }

        return bestLocation;
    }

    public void stopGPS() {
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
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

    public Location getLocation() {
        return location;
    }

    public boolean getIsGPSEnabled() {
        return isGPSEnabled;
    }
}
