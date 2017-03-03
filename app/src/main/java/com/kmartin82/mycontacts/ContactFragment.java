package com.kmartin82.mycontacts;

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

import java.util.UUID;


public class ContactFragment extends Fragment {
    private static final String ARG_CONT_ID = "contact ID";
    private Contact mContact;
    private EditText mNameField;
    private EditText mEmailField;
    private CheckBox mFavorite;

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
        mNameField.setText(mContact.getMname());
        mNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mContact.setMname(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEmailField = (EditText)v.findViewById(R.id.contact_email);
        mEmailField.setText(mContact.getMemail());
        mEmailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // No new behavior
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                mContact.setMemail(s.toString());
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
        return v;
    }
}

