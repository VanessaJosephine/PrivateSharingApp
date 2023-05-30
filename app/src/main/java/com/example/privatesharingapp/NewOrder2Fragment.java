package com.example.privatesharingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.privatesharingapp.sqlitehelper.DatabaseHelper;
import com.example.privatesharingapp.sqlitehelper.Truck;
public class NewOrder2Fragment extends Fragment {
    RadioButton radioButton, radioButton2;
    RadioGroup radioGroup, radioGroup2;
    NumberPicker weight, height, width, length;
    String goodType, finalWeight, finalLength, finalWidth, finalHeight, vehicleType;
    Button confirm;
    DatabaseHelper databaseHelper;
    public NewOrder2Fragment() {
        // Required empty public constructor
    }
    public static NewOrder2Fragment newInstance() {
        NewOrder2Fragment fragment = new NewOrder2Fragment();

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
        View view = inflater.inflate(R.layout.fragment_new_order2, container, false);
        // Initialization
        Initialization(view);
        // DatabaseHelper
        databaseHelper = new DatabaseHelper(getContext());
        // SharedPreferences
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);

        // Radio Group
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioButton = view.findViewById(i);
                Toast.makeText(getContext(), radioButton.getText().toString() + " selected", Toast.LENGTH_LONG).show();
                goodType = radioButton.getText().toString();
            }
        });
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioButton2 = view.findViewById(i);
                Toast.makeText(getContext(), radioButton2.getText().toString() + " selected", Toast.LENGTH_LONG).show();
                vehicleType = radioButton2.getText().toString();
            }
        });
        // Picker
        weight.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                finalWeight = Integer.toString(weight.getValue());
            }
        });
        height.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                finalHeight = Integer.toString(height.getValue());
            }
        });
        width.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                finalWidth = Integer.toString(width.getValue());
            }
        });
        length.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                finalLength = Integer.toString(length.getValue());
            }
        });
        // Confirm button
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Makes sure EditText is not empty
                if (goodType != null || vehicleType != null || finalWeight != null || finalHeight != null || finalWidth != null || finalLength != null)
                {
                    long result = databaseHelper.insertTruck(new Truck(
                            "Order " + sharedPreferences.getString("username", ""),
                            goodType,
                            finalWeight,
                            finalHeight,
                            finalWidth,
                            finalLength,
                            sharedPreferences.getString("username", ""),
                            sharedPreferences.getString("receiver", ""),
                            sharedPreferences.getString("pickup", ""),
                            sharedPreferences.getString("dropOff", "")
                    ));
                    if(result > 0)
                    {
                        Toast.makeText(getContext(), "Order added successfully!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getContext(), HomeActivity.class);
                        startActivity(intent);
                    }
                    else Toast.makeText(getContext(), "Unsuccessful!", Toast.LENGTH_LONG).show();
                }
                else Toast.makeText(getContext(), "Please enter all fields!", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
    public void SetPicker() {
        // Number Picker
        weight.setMinValue(1);
        weight.setMaxValue(50);
        length.setMinValue(1);
        length.setMaxValue(10);
        width.setMinValue(1);
        width.setMaxValue(10);
        height.setMinValue(1);
        height.setMaxValue(10);
    }
    public void Initialization(View view) {
        // findViewById

        // NumberPicker
        weight = view.findViewById(R.id.weight_picker);
        height = view.findViewById(R.id.height_picker);
        width = view.findViewById(R.id.width_picker);
        length = view.findViewById(R.id.length_picker);

        // Button
        confirm = view.findViewById(R.id.button5);

        // RadioGroup
        radioGroup = view.findViewById(R.id.radioGroup1);
        radioGroup2 = view.findViewById(R.id.radioGroup2);

        SetPicker();
    }
}