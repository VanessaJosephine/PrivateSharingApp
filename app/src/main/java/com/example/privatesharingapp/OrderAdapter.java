package com.example.privatesharingapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Telephony;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.privatesharingapp.sqlitehelper.Truck;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder>{

    Context context;
    ArrayList<Truck> trucks;
    ItemClickListener clickListener;

    public OrderAdapter(Context context, ArrayList<Truck> trucks, ItemClickListener clickListener) {
        this.context = context;
        this.trucks = trucks;
        this.clickListener = clickListener;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Making the layout appear
        View view = LayoutInflater.from(context).inflate(R.layout.order_layout, parent, false);
        // Calling the constructor
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Truck truck = trucks.get(position);
        // Set values
        holder.name.setText(truck.getName());
        holder.receiver.setText("Receiver: " + truck.getReceiver());
        holder.sender.setText("Sender: " + truck.getSender());
        // OnClickListener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(truck);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trucks.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, sender, receiver;
        ImageView sendBtn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewOrderName);
            sender = itemView.findViewById(R.id.textViewOrderSender);
            receiver = itemView.findViewById(R.id.textViewOrderReceiver);
            // ImageView
            sendBtn = itemView.findViewById(R.id.imageViewSend);
            sendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) //At least KitKat
                    {
                        String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(view.getContext()); //Need to change the build to API 19

                        Intent sendIntent = new Intent(Intent.ACTION_SEND);
                        sendIntent.setType("text/plain");
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "Test");

                        if (defaultSmsPackageName != null) //Can be null in case that there is no default, then the user would be able to choose any app that support this intent.
                        {
                            sendIntent.setPackage(defaultSmsPackageName);
                        }
                        view.getContext().startActivity(sendIntent);

                    }
                    else //For early versions, do what worked for you before.
                    {
                        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                        sendIntent.setData(Uri.parse("sms:"));
                        sendIntent.putExtra("sms_body", "Test");
                        view.getContext().startActivity(sendIntent);
                    }
                }
            });
        }
    }
    public interface ItemClickListener {
        public void onItemClick(Truck truck);
    }
}
