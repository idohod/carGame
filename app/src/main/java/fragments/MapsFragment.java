package fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carsgame.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    GoogleMap myGoogleMap;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        initMap();

        return view;
    }

    public void goToLocation(double latitude, double longitude) {
        MarkerOptions markerOptions = new MarkerOptions();
        LatLng latLng = new LatLng(latitude,longitude);

        markerOptions.title(latitude+":"+longitude);
        markerOptions.position(latLng);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,15);
        myGoogleMap.clear();
        myGoogleMap.animateCamera(cameraUpdate);
        myGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        myGoogleMap.addMarker(markerOptions);

    }

    private void initMap() {
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        if (supportMapFragment!=null)
             supportMapFragment.getMapAsync(this);
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myGoogleMap =googleMap;
        myGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(latLng.latitude+":"+latLng.latitude);
                myGoogleMap.clear();
                myGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                myGoogleMap.addMarker(markerOptions);

            }
        });
    }
}