package com.example.privatesharingapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.privatesharingapp.sqlitehelper.DatabaseHelper;
import com.example.privatesharingapp.sqlitehelper.Truck;

import java.util.ArrayList;

public class MyOrderActivity extends AppCompatActivity implements OrderAdapter.ItemClickListener{

    DatabaseHelper databaseHelper;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    OrderAdapter orderAdapter;
    ImageView imageView;
    ArrayList<Truck> truckArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        // Get Trucks
        databaseHelper = new DatabaseHelper(this);
        // SharedPreferences
        SharedPreferences sharedPreferences = this.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        truckArrayList = databaseHelper.getMyTrucks(sharedPreferences.getString("username", ""));

        // RecyclerView
        recyclerView = findViewById(R.id.recyclerViewMyTrucks);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        orderAdapter = new OrderAdapter(this, truckArrayList, this);
        recyclerView.setAdapter(orderAdapter);
        recyclerView.setLayoutManager(layoutManager);

        imageView = findViewById(R.id.imageViewMyMenu);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuDialog(view);
            }
        });
    }
    @Override
    public void onItemClick(Truck truck) {
        Fragment fragment = OrderDetailFragment.newInstance(truck.getName(), truck.getSender(), truck.getReceiver(),
                truck.getPickup(), truck.getDropOff(), truck.getWeight(),
                truck.getWidth(), truck.getHeight(), truck.getLength());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(getSupportFragmentManager().findFragmentByTag("home_fragment"));
        transaction.add(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void menuDialog(View view) {
        // Option to display dialog
        String[] options = { "Home", "My Account", "My Orders" };
        // Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) // Handle clicks
                {
                    // Takes user to home (current page)
                    Intent intent = new Intent(view.getContext(), HomeActivity.class);
                    startActivity(intent);
                }
                else if (i == 1)
                {
                    // Takes user to My Account page
                }
                else if (i == 2)
                {
                    // Takes user to My Orders page (current page)
                }
            }
        });
        // Create / show dialog
        builder.create().show();
    }
}