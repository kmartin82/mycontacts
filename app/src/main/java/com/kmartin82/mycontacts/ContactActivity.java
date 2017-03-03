package com.kmartin82.mycontacts;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import java.util.UUID;

public class ContactActivity extends SingleFragmentActivity {
    private static final String EXTRA_CONTACT_ID = "com.kevinmartin.mycontacts.contact_ID";

    public static Intent newIntent(Context context, UUID contactID){
        Intent intent = new Intent(context, ContactActivity.class);
        intent.putExtra(EXTRA_CONTACT_ID, contactID);
        return intent;
    }
    @Override
    protected Fragment createFragment() {
        UUID contactID = (UUID) getIntent().getSerializableExtra(EXTRA_CONTACT_ID);
        return ContactFragment.newInstance(contactID);
    }

}
