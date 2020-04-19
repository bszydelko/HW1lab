package com.bs.hw1lab;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.bs.hw1lab.contacts.ContactListContent;

public class CallDialog extends DialogFragment {

    private static final String TAG = "CallDialog: ";
    private OnCallDialogInteractionListener mListener;

    public CallDialog() {
        // Required empty public constructor
    }
    static com.bs.hw1lab.CallDialog newInstance(){
        return new com.bs.hw1lab.CallDialog();
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String contactName = ContactListContent.ITEMS.get(MainActivity.getCurrentItemPosition()).name;
        StringBuilder message = new StringBuilder();

        message.append(getString(R.string.call_question)).append(" ").append(contactName).append("?");


        builder.setMessage(message);
        builder.setPositiveButton(getString(R.string.call_confirm), new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onCallDialogPositiveClick(com.bs.hw1lab.CallDialog.this);
            }
        });
        builder.setNegativeButton(getString(R.string.call_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onCallDialogNegativeClick(com.bs.hw1lab.CallDialog.this);
            }
        });
        return builder.create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCallDialogInteractionListener) {
            mListener = (OnCallDialogInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnCallDialogInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnCallDialogInteractionListener {
        void onCallDialogPositiveClick(DialogFragment dialog);
        void onCallDialogNegativeClick(DialogFragment dialog);
    }
}
