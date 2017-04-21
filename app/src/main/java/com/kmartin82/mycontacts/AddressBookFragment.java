package com.kmartin82.mycontacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AddressBookFragment extends Fragment {

    private RecyclerView mAddressBookRecyclerView;
    private ContactAdapter mContactAdapter;
    private boolean mShowFavoritesOnly = false;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_addressbook, menu);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create_contact:
                Contact contact = new Contact();
                AddressBook.get(getContext()).add(contact);
                Intent intent = ContactPagerActivity.newIntent(getActivity(), contact.getID());
                startActivity(intent);
                return true;
            case R.id.menu_item_toggle_favorites:
                mShowFavoritesOnly = !mShowFavoritesOnly;
                if (mShowFavoritesOnly) {
                    item.setTitle(R.string.show_all);
                    mContactAdapter.mContacts =
                            AddressBook.get(getContext()).GetFavoriteContacts();
                } else {
                    item.setTitle(R.string.show_favorites);
                    mContactAdapter.mContacts =
                            AddressBook.get(getContext()).getContacts();
                }
                mContactAdapter.notifyDataSetChanged();
                return true;
            case R.id.menu_item_settings:
                Intent settingIntent = new Intent(getContext(), SettingsActivity.class);
                startActivity(settingIntent);
                default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address_book, container,
                false);

        mAddressBookRecyclerView =
                    (RecyclerView) view.findViewById(R.id.address_book_recycler_view);
        mAddressBookRecyclerView.setLayoutManager(
                    new LinearLayoutManager(getActivity()));
        new SyncData(getContext()).execute();
        UpdateUi();

            return view;
        }

    @Override
    public void onResume() {
        super.onResume();
        UpdateUi();
    }

    private void UpdateUi(){
        AddressBook addressBook = AddressBook.get(getContext());
        List<Contact> contacts = addressBook.getContacts();
        if (mContactAdapter == null){
            mContactAdapter = new ContactAdapter(contacts);
            mAddressBookRecyclerView.setAdapter(mContactAdapter);
        }
        else {
            mContactAdapter.setContacts(contacts);
            mContactAdapter.notifyDataSetChanged();
        }
        mContactAdapter = new ContactAdapter(contacts);
        mAddressBookRecyclerView.setAdapter(mContactAdapter);

    }

    private class ContactHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mContactNameTextView;
        private Contact mContact;

        public ContactHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            mContactNameTextView = (TextView) itemView;
        }
        public void bindContact(Contact contact){
            mContact = contact;
            mContactNameTextView.setText(mContact.getmName());
        }

        @Override
        public void onClick(View v) {
            Intent intent = ContactPagerActivity.newIntent(getActivity(), mContact.getID());
            startActivity(intent);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        new SyncData(getContext()).execute();
    }

    private class ContactAdapter extends RecyclerView.Adapter<ContactHolder>{
        private List<Contact> mContacts;

        public ContactAdapter(List<Contact> contacts){
            mContacts = contacts;

        }
        public void setContacts(List<Contact> contacts) {
            mContacts = contacts;
        }
        @Override
        public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ContactHolder(view);
        }

        @Override
        public void onBindViewHolder(ContactHolder holder, int position) {
            Contact contact = mContacts.get(position);
            holder.bindContact(contact);
        }



        @Override
        public int getItemCount() {
            return mContacts.size();
        }
    }
}
