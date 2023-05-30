package com.example.privatesharingapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;
import java.util.Calendar;

public class NewOrder1Fragment extends Fragment {
    private EditText pickup, dropOff, receiver;
    private Button nextBtn;
    private DatePickerDialog datePickerDialog, datePickerDialog2;
    public NewOrder1Fragment() {
        // Required empty public constructor
    }

    public static NewOrder1Fragment newInstance() {
        NewOrder1Fragment fragment = new NewOrder1Fragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_order1, container, false);
        // Initialization
        Initialization(view);
        // Date Picker
        pickup.setShowSoftInputOnFocus(false);
        pickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Calender class's instance and get current date, month and year from calendar
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // Date picker dialog
                datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                pickup.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        dropOff.setShowSoftInputOnFocus(false);
        dropOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Calender class's instance and get current date, month and year from calendar
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // Date picker dialog
                datePickerDialog2 = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                dropOff.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog2.show();
            }
        });
        // Next button
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Makes sure EditText is not empty
                if (!pickup.getText().toString().isEmpty() ||
                        !dropOff.getText().toString().isEmpty() ||
                        !receiver.getText().toString().isEmpty()
                )
                {
                    // SharedPreferences
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
                    SharedPreferences.Editor myEditor = sharedPreferences.edit();
                    myEditor.putString("receiver", receiver.getText().toString());
                    myEditor.putString("pickup", pickup.getText().toString());
                    myEditor.putString("dropOff", dropOff.getText().toString());
                    myEditor.apply();

                    Fragment fragment = NewOrder2Fragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.hide(getActivity().getSupportFragmentManager().findFragmentByTag("order_fragment"));
                    transaction.add(R.id.frame_container2, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                else Toast.makeText(getContext(), "Please enter all fields!", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
    public void Initialization(View view) {
        // findViewById
        pickup = view.findViewById(R.id.editTextDate);
        dropOff = view.findViewById(R.id.editTextTime);
        nextBtn = view.findViewById(R.id.button4);
        receiver = view.findViewById(R.id.editTextText4);
    }
}