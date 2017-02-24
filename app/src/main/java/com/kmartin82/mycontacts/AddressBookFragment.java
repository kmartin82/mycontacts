package com.kmartin82.mycontacts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AddressBookFragment extends Fragment {

    private RecyclerView mAddressBookRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_address_book, container,
                    false);

            mAddressBookRecyclerView =
                    (RecyclerView) view.findViewById(R.id.address_book_recycler_view);
            mAddressBookRecyclerView.setLayoutManager(
                    new LinearLayoutManager(getActivity()));

            return view;
        }
}
