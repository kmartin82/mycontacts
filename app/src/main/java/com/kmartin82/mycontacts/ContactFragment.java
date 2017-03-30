package com.kmartin82.mycontacts;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static android.R.id.list;


public class ContactFragment extends Fragment {
    private static final String ARG_CONT_ID = "contact ID";
    private Contact mContact;
    private EditText mNameField;
    private EditText mEmailField;
    private CheckBox mFavorite;
    private EditText mAddessField;
    private MapView mMapView;

    public static  ContactFragment newInstance(UUID contactID){
        ContactFragment contactFragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CONT_ID, contactID);
        contactFragment.setArguments(args);
        return contactFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID contactID = (UUID) getArguments().getSerializable(ARG_CONT_ID);
        mContact = AddressBook.get().getContact(contactID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contact, container, false);

        mNameField = (EditText)v.findViewById(R.id.contact_name);
        mNameField.setText(mContact.getmName());
        mNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mContact.setmName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEmailField = (EditText)v.findViewById(R.id.contact_email);
        mEmailField.setText(mContact.getmEmail());
        mEmailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // No new behavior
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                mContact.setmEmail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No new behavior
            }
        });

        mFavorite = (CheckBox)v.findViewById(R.id.contact_favorite);
        mFavorite.setChecked(mContact.isFavorite());
        mFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mContact.setFavorite(isChecked);
            }
        });
        mAddessField = (EditText)v.findViewById(R.id.Contact_adress);
        mAddessField.setText(mContact.getAddress());
        mAddessField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mContact.setAddress(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mAddessField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    UpdateMap();
                }
            }
        });


        mMapView = (MapView) v.findViewById(R.id.contact_map);
        mMapView.onCreate(savedInstanceState);
        UpdateMap();
        return v;
    }
    private void UpdateMap(){

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.clear();
                Geocoder geo = new Geocoder(getContext());
                try {
                    List<Address> addresses = geo.getFromLocationName(mAddessField.getText().toString(), 1);
                    if(addresses.size() > 0){
                        LatLng latLng = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                        MarkerOptions marker = new MarkerOptions().position(latLng);
                        googleMap.addMarker(marker);
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    }

                }
                catch (IOException e){

                }
            }

        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }
}

