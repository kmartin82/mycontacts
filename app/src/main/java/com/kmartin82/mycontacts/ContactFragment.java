package com.kmartin82.mycontacts;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


public class ContactFragment extends Fragment {
    private static final String ARG_CONT_ID = "contact ID";
    private static final int REQUEST_IMAGE_CAPTURED = 1;
    private Contact mContact;
    private EditText mNameField;
    private EditText mEmailField;
    private CheckBox mFavorite;
    private EditText mAddessField;
    private MapView mMapView;
    private ImageView mImageView;



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
        mContact = AddressBook.get(getContext()).getContact(contactID);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_contact, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_send_email:
                if (mContact.getmEmail() == null){
                    return true;
                }
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                String[] addresses = {mContact.getmEmail()};
                intent.putExtra(Intent.EXTRA_EMAIL, addresses);
                ComponentName emailApp = intent.resolveActivity(getContext().getPackageManager());
                ComponentName unsupportedAction = ComponentName.unflattenFromString("com.android.fallback/.Fallback");
                if (emailApp != null && !emailApp.equals(unsupportedAction)){
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getContext(), R.string.email_app_error, Toast.LENGTH_SHORT).show();
                }

            default:
                return super.onOptionsItemSelected(item);
        }



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

        mImageView = (ImageView) v.findViewById(R.id.contact_image);
        if (mContact.getmImage() != null){
            mImageView.setImageBitmap(mContact.getmImage());
        }
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURED);
                }
            }
        });


        mMapView = (MapView) v.findViewById(R.id.contact_map);
        mMapView.onCreate(savedInstanceState);
        UpdateMap();
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURED && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mContact.setmImage(imageBitmap);
            mImageView.setImageBitmap(imageBitmap);
        }
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
        AddressBook.get(getContext()).updateContact(mContact);
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

