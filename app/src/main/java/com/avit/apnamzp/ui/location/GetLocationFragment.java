package com.avit.apnamzp.ui.location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avit.apnamzp.MainActivity;
import com.avit.apnamzp.R;
import com.avit.apnamzp.databinding.FragmentGetLocationBinding;
import com.avit.apnamzp.localdb.User;
import com.avit.apnamzp.utils.DisplayMessage;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class GetLocationFragment extends Fragment implements OnMapReadyCallback{

    private FragmentGetLocationBinding binding;
    private static int AUTOCOMPLETE_REQUEST_CODE = 1;
    private String TAG = "GetLocationFragment";
    private Geocoder geocoder;
    private GoogleMap mMap;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    private Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentGetLocationBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        bundle = getArguments();

        // Map Initilization
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapview);

        if(supportMapFragment == null){
            Log.i(TAG, "onCreateView: " + "NULL");
        }else {
            supportMapFragment.getMapAsync(this);
        }

        // Geo Coder Initialization
        geocoder = new Geocoder(getContext());

        // Places SDK Initiliazation
        Places.initialize(getContext(), getResources().getString(R.string.map_key));
        PlacesClient placesClient = Places.createClient(getContext());

        // Back Button
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(root).popBackStack();
            }
        });

        // Change Location
        binding.change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Place.Field> fields = Arrays.asList(Place.Field.LAT_LNG,Place.Field.ADDRESS);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                        .build(getContext());
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
            }
        });

        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Place.Field> fields = Arrays.asList(Place.Field.LAT_LNG,Place.Field.ADDRESS);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                        .build(getContext());
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
            }
        });

        // Save Address Button
        binding.saveAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String houseNo = binding.houseNo.getText().toString();
                String landMark = binding.landMark.getText().toString();

                if(houseNo.length() == 0) {
                    DisplayMessage.warningMessage(getContext(),"Please Enter Your House No/Building Name",Toasty.LENGTH_SHORT);
                    return;
                }

                if(bundle != null && bundle.containsKey("second_address")){
                    User.setSecondaryHomeDetails(getContext(),houseNo);
                    User.setSecondaryLandMark(getContext(),landMark);
                }
                else {
                    User.setHomeDetails(getContext(),houseNo);
                    User.setLandMark(getContext(),landMark);
                }

                DisplayMessage.successMessage(getContext(),"Address Saved Successfully",Toasty.LENGTH_SHORT);
                Navigation.findNavController(root).popBackStack();
            }
        });

        return root;
    }

    private Address getStreetAddressFromLatlan(LatLng latLng) throws IOException {
        List<Address> addresses =  geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
        return addresses.get(0);
    }

    private void updateTheLocation(String address){

        if(binding.loading.getVisibility() == View.VISIBLE){
            return;
        }

        binding.findLocation.setVisibility(View.GONE);
        binding.rawAddress.setVisibility(View.VISIBLE);
        binding.rawAddress.setText(address);
    }

    public void changeTheLocationOfMarker(LatLng latLng,String address){

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,16);
        mMap.animateCamera(cameraUpdate);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);

                if(bundle != null && bundle.containsKey("second_address")){
                    // Save The Location
                    User.setSecondaryGoogleMapStreetAddress(getContext(),place.getAddress());
                    User.setSecondaryLatLang(getContext(),place.getLatLng());
                }
                else {
                    // Save The Location
                    User.setGoogleMapStreetAddress(getContext(),place.getAddress());
                    User.setLatLang(getContext(),place.getLatLng());
                }


                updateTheLocation(place.getAddress());
                changeTheLocationOfMarker(place.getLatLng(),place.getAddress());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // The user canceled the operation.
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        binding.rippleBack.startRippleAnimation();

        getTheLocationPermission();

        // Get My Location
        binding.getMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTheLocationPermission();
            }
        });

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                try {
                    LatLng latlng = mMap.getCameraPosition().target;

                    binding.findLocation.setVisibility(View.VISIBLE);
                    binding.rawAddress.setVisibility(View.GONE);

                    Address address = getStreetAddressFromLatlan(latlng);

                    if(bundle != null && bundle.containsKey("second_address")){
                        // Save The Location
                        User.setSecondaryGoogleMapStreetAddress(getContext(),address.getAddressLine(0));
                        User.setSecondaryLatLang(getContext(),latlng);
                    }
                    else {
                        // Save The Location
                        User.setGoogleMapStreetAddress(getContext(),address.getAddressLine(0));
                        User.setLatLang(getContext(),latlng);
                    }


                    updateTheLocation(address.getAddressLine(0));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        LatLng mirzapur = User.getLatLng(getContext());

        if(mirzapur == null){
            mirzapur =  new LatLng(25.1337,82.5644);
        }

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(mirzapur,15);
        mMap.animateCamera(cameraUpdate);

    }

    @SuppressLint("MissingPermission")
    private void getAndSetFusedLocation(){

        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(100);
        locationRequest.setFastestInterval(50);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        fusedLocationProviderClient.requestLocationUpdates(locationRequest,new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if(locationResult == null){
                    return;
                }

                Location location =  locationResult.getLocations().get(0);

                for(Location location1 : locationResult.getLocations()){
                    
                    if(location1.getAccuracy() < 200){
                        location = location1;
                        fusedLocationProviderClient.removeLocationUpdates(this);
                        binding.loading.setVisibility(View.GONE);
                    }

                }

                // SAVE THE Location
                changeTheLocationOfMarker(new LatLng(location.getLatitude(),location.getLongitude()),"");

            }
        }, Looper.getMainLooper());

    }

    private void getTheLocationPermission(){
        Dexter.withContext(getContext())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        displayLocationSettingsRequest(getContext());
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        DisplayMessage.errorMessage(getContext(),"Permission Denied",Toasty.LENGTH_SHORT);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    private void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        getAndSetFusedLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                            getAndSetFusedLocation();
                        } catch (IntentSender.SendIntentException e) {
                            Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }

}