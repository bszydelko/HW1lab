package com.bs.hw1lab;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bs.hw1lab.contacts.ContactListContent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity
        implements
        ContactFragment.OnListFragmentInteractionListener,
        CallDialog.OnCallDialogInteractionListener,
        DeleteDialog.OnDeleteDialogInteractionListener{

    public static final String contactExtra = "contactExtra";
    private static final String TAG = "MainActivity" ;
    private static int currentItemPosition = -1;

    public static int getCurrentItemPosition(){
        return currentItemPosition;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton newContactButton = findViewById(R.id.newContactButton);
        newContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewContactActivity();
            }
        });
    }
    private void startNewContactActivity() {
        Intent intent = new Intent(this, NewContactActivity.class);
        startActivityForResult(intent,RESULT_OK);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            ContactFragment contactFragment = ((ContactFragment) getSupportFragmentManager().findFragmentById(R.id.contactFragment));
            contactFragment.notifyDataChange();
        }
    }

    private void startContactInfoActivity(ContactListContent.Contact contact, int position) {
        Log.d(TAG, "start contact info");

        Intent intent = new Intent(this, ContactInfoActivity.class);
        intent.putExtra(contactExtra, contact);
        startActivity(intent);
    }

    private void displayContactInFragment(ContactListContent.Contact contact){
        ContactInfoFragment contactInfoFragment = ((ContactInfoFragment) getSupportFragmentManager().findFragmentById(R.id.displayFragment));
        if(contactInfoFragment != null) {
            contactInfoFragment.displayContact(contact);
        }
    }

    private void showCallDialog() {
        CallDialog.newInstance().show(getSupportFragmentManager(),getString(R.string.call_dialog_tag));
    }
    private void showDeleteDialog() {
        DeleteDialog.newInstance().show(getSupportFragmentManager(), getString(R.string.delete_dialog_tag));
    }

    @Override
    public void onListFragmentClickInteraction(ContactListContent.Contact contact, int position) {
        Log.d(TAG, "list fragment click");

            currentItemPosition = position;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            displayContactInFragment(contact);
        } else {
            startContactInfoActivity(contact, position);
        }

    }

    @Override
    public void onListFragmentLongClickInteraction(int position) {

        currentItemPosition = position;
        showCallDialog();
    }

    @Override
    public void onListFragmentClickDeleteButton(int position) {

        currentItemPosition = position;
        showDeleteDialog();
    }

    @Override
    public void onCallDialogPositiveClick(DialogFragment dialog) {

    }

    @Override
    public void onCallDialogNegativeClick(DialogFragment dialog) {

    }

    @Override
    public void onDeleteDialogPositiveClick(DialogFragment dialog) {
        if(currentItemPosition != -1 && currentItemPosition < ContactListContent.ITEMS.size()) {
            ContactListContent.removeItem(currentItemPosition);

        }
        ContactFragment contactFragment = ((ContactFragment)getSupportFragmentManager().findFragmentById(R.id.contactFragment));
        contactFragment.notifyDataChange();

    }

    @Override
    public void onDeleteDialogNegativeClick(DialogFragment dialog) {
        View v = findViewById(R.id.addContactButton);
        if(v != null) {
            Snackbar.make(v,getString(R.string.delete_cancel_msg), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.retry_msg), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showDeleteDialog();
                        }
                    });
        }

    }



}
