package com.bs.hw1lab;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class NewContactActivity extends AppCompatActivity {

    private static final String TAG = "NewContactActivity: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_new_contact);

    }
}
