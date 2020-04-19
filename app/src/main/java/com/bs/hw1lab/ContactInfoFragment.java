package com.bs.hw1lab;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bs.hw1lab.contacts.ContactListContent;

import static androidx.constraintlayout.widget.Constraints.TAG;
/**
 * A simple {@link Fragment} subclass.
 */
public class ContactInfoFragment extends Fragment {

    public ContactInfoFragment() {
        // Required empty public constructor
    }

    public void displayContact(ContactListContent.Contact contact) {
        FragmentActivity activity = getActivity();

        TextView contactInfoNameSurname = activity.findViewById(R.id.contactInfoNameSurname);
        TextView contactInfoBirthday = activity.findViewById(R.id.contactInfoBirthday);
        TextView contactInfoPhoneNumber = activity.findViewById(R.id.contactInfoPhoneNumber);
        ImageView contactInfoImage = activity.findViewById(R.id.contactInfoImage);

        int imageResource = getResources().getIdentifier("drawable/"+contact.picPath,null, activity.getPackageName());

        contactInfoNameSurname.setText(String.format("%s %s", contact.name, contact.surname));
        contactInfoBirthday.setText(String.format("Birthday: %s", contact.birthday));
        contactInfoPhoneNumber.setText(String.format("Phone number: %s", contact.phoneNumber));
        contactInfoImage.setImageResource(imageResource);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG,"onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        Intent intent = getActivity().getIntent();

        if(intent != null) {
            ContactListContent.Contact receivedContact = intent.getParcelableExtra(MainActivity.contactExtra);
            if(receivedContact != null) {
                displayContact(receivedContact);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_info, container, false);
    }
}
