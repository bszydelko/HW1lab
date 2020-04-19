package com.bs.hw1lab;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bs.hw1lab.contacts.ContactListContent;

import org.apache.http.params.CoreConnectionPNames;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.FormatFlagsConversionMismatchException;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewContactFragment extends Fragment {

    private static final String TAG = "NewContactFragment";
    private final String FILL_ALL = "0";
    private final String BAD_PHONE = "1";
    private final String BAD_BIRTHDAY = "2";

    public NewContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView");
        return inflater.inflate(R.layout.fragment_new_contact, container, false);


    }
    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {

        Log.d(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        Intent intent = getActivity().getIntent();
        final FragmentActivity activity = getActivity();
        final int status;


        final View view = getView();
        final Button addContactButton = view.findViewById(R.id.addContactButton);

        addContactButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    addContact();
                } catch (IOException e) {

                    if(e.getMessage().equals(FILL_ALL)){
                        Toast.makeText(view.getContext(), "Provide all data!", Toast.LENGTH_SHORT).show();

                    } else if(e.getMessage().equals(BAD_BIRTHDAY)){
                        Toast.makeText(view.getContext(), "Invalid birthdate!", Toast.LENGTH_SHORT).show();

                    } else if (e.getMessage().equals(BAD_PHONE)){
                        Toast.makeText(view.getContext(), "Invalid phone number!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        activity.setResult(Activity.RESULT_OK);
                        activity.finish();
                    }
                }
            }
        });
    }

    public void addContact() throws IOException {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Log.d(TAG, "addContact");
        View view = getView();


        FragmentActivity activity = getActivity();

        EditText contactNameEditText = activity.findViewById(R.id.contactName);
        EditText contactSurnameEditText = activity.findViewById(R.id.contactSurname);
        EditText contactBirthdayEditText = activity.findViewById(R.id.contactBirthday);
        EditText contactPhoneNumberEditText = activity.findViewById(R.id.contactPhoneNumber);


        String contactName = contactNameEditText.getText().toString();
        String contactSurname = contactSurnameEditText.getText().toString();
        String contactBirthday = contactBirthdayEditText.getText().toString();
        String contactPhoneNumber = contactPhoneNumberEditText.getText().toString();


        if (contactName.isEmpty() || contactSurname.isEmpty() || contactBirthday.isEmpty() || contactPhoneNumber.isEmpty()) {
            throw new IOException(FILL_ALL);
        }

        try {
            Date birthday = formatter.parse(contactBirthday);

        } catch (ParseException e) {
            throw new IOException(BAD_BIRTHDAY);
        }

        if(contactPhoneNumber.length() != 9 || !contactPhoneNumber.matches("\\d+")){
            throw  new IOException(BAD_PHONE);
        }


        //random avatar
        Random rnd = new Random();
        int id = rnd.nextInt(15) + 1;
        String selectedImage = "avatar_" + id;

        ContactListContent.addItem(new ContactListContent.Contact("Contact." + ContactListContent.ITEMS.size() + 1,
                contactName,
                contactSurname,
                contactBirthday, contactPhoneNumber, selectedImage));


        contactNameEditText.setText("");
        contactSurnameEditText.setText("");
        contactBirthdayEditText.setText("");
        contactPhoneNumberEditText.setText("");


        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        throw new IOException("all ok");
    }
}
