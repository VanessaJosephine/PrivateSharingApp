package com.example.privatesharingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class OrderDetailFragment extends Fragment {

    TextView name, sender, receiver, pickup, dropOff, weight, width, height, length;
    Button estimateBtn;
    private static final String NAME = "name";
    private static final String SENDER = "sender";
    private static final String RECEIVER = "receiver";
    private static final String PICKUP = "pickup";
    private static final String DROPOFF = "dropOff";
    private static final String WEIGHT = "weight";
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String LENGTH = "length";
    private String Name, Sender, Receiver, Pickup, DropOff, Weight, Width, Height, Length;
    public OrderDetailFragment() {
        // Required empty public constructor
    }

    public static OrderDetailFragment newInstance(String name, String sender, String receiver, String pickup, String dropOff, String weight, String width, String height, String length) {
        OrderDetailFragment fragment = new OrderDetailFragment();
        Bundle args = new Bundle();
        args.putString(NAME, name);
        args.putString(SENDER, sender);
        args.putString(RECEIVER, receiver);
        args.putString(PICKUP, pickup);
        args.putString(DROPOFF, dropOff);
        args.putString(WEIGHT, weight);
        args.putString(HEIGHT, height);
        args.putString(WIDTH, width);
        args.putString(LENGTH, length);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Name = getArguments().getString(NAME);
            Sender = getArguments().getString(SENDER);
            Receiver = getArguments().getString(RECEIVER);
            Pickup = getArguments().getString(PICKUP);
            DropOff = getArguments().getString(DROPOFF);
            Weight = getArguments().getString(WEIGHT);
            Width = getArguments().getString(WIDTH);
            Height = getArguments().getString(HEIGHT);
            Length = getArguments().getString(LENGTH);
        } else Toast.makeText(getContext(), "Empty", Toast.LENGTH_LONG).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_detail, container, false);
        // FindViewById
        name = view.findViewById(R.id.textViewDetailName);
        sender = view.findViewById(R.id.textViewDetailSender);
        receiver = view.findViewById(R.id.textViewDetailReceiver);
        pickup = view.findViewById(R.id.textViewDetailPickup);
        dropOff = view.findViewById(R.id.textViewDetailLocation);
        weight = view.findViewById(R.id.textViewDetailWeight);
        height = view.findViewById(R.id.textViewDetailHeight);
        length = view.findViewById(R.id.textViewDetailLength);
        width = view.findViewById(R.id.textViewDetailWidth);
        EditText location1 = view.findViewById(R.id.editTextText5);
        EditText location2 = view.findViewById(R.id.editTextText6);

        estimateBtn = view.findViewById(R.id.callBtn);
        estimateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
                SharedPreferences.Editor myEditor = sharedPreferences.edit();
                myEditor.putString("location1", location1.getText().toString());
                myEditor.putString("location2", location2.getText().toString());
                myEditor.apply();
                Intent intent = new Intent(getContext(), MapActivity.class);
                startActivity(intent);
            }
        });

        // Set Values
        name.setText(Name);
        sender.setText(sender.getText() + Sender);
        receiver.setText(receiver.getText() + Receiver);
        pickup.setText(pickup.getText() + Pickup);
        dropOff.setText(dropOff.getText() + DropOff);
        weight.setText(weight.getText() + Weight);
        width.setText(width.getText() + Width);
        height.setText(height.getText() + Height);
        length.setText(length.getText() + Length);

        return view;
    }
}