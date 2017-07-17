package com.agit.bp.mechanicbp.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by knax on 6/15/16.
 */
public class GoogleLocationService {
    private Context context;

    public GoogleLocationService(Context context) {
        this.context = context;
    }

    public Observable<Location> getLocation() {
        final ProgressDialog progressDialog = ProgressDialog.show(context, "Getting Location", "Getting Current Location");
        return Observable.create(new Observable.OnSubscribe<Location>() {

            private GoogleApiClient googleApiClient;

            class LocationListenerService implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

                private Subscriber<? super Location> observer;

                public LocationListenerService(Subscriber<? super Location> observer) {
                    this.observer = observer;
                }
                @Override
                public void onConnected(Bundle bundle) {
                    try {
                        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

                        if (mLastLocation != null) {
                            observer.onNext(mLastLocation);
                            progressDialog.cancel();
                            googleApiClient.disconnect();
                            observer.onCompleted();
                        } else {
                            LocationRequest locationRequest = new LocationRequest();

                            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                            locationRequest.setInterval(1000);
                            locationRequest.setFastestInterval(1000);

                            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
                        }
                    } catch (SecurityException e) {

                        e.printStackTrace();
                    }
                }

                @Override
                public void onConnectionSuspended(int i) {

                }

                @Override
                public void onConnectionFailed(ConnectionResult connectionResult) {
                }

                @Override
                public void onLocationChanged(Location location) {
                    try {
                        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);

                        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

                        if (mLastLocation != null) {
                            observer.onNext(mLastLocation);
                            progressDialog.cancel();
                            googleApiClient.disconnect();
                            observer.onCompleted();
                        }
                    } catch (SecurityException e) {
                        Log.d("GoogleLocationService", "error" + String.valueOf(e));
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void call(Subscriber<? super Location> observer) {
                try {
                    LocationListenerService locationListenerService = new LocationListenerService(observer);

                    googleApiClient = new GoogleApiClient.Builder(context)
                            .addConnectionCallbacks(locationListenerService)
                            .addOnConnectionFailedListener(locationListenerService)
                            .addApi(LocationServices.API)
                            .build();

                    googleApiClient.connect();
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        });
    }
}
