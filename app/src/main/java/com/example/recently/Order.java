package com.example.recently;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recently.databinding.ActivityOrderBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Order extends AppCompatActivity {
    private ActivityOrderBinding root;
    private ArrayList<CardItem> order= new ArrayList<>();
    private Adapter adapter;
    private double Totalall=0.00000;
    private DatabaseReference reference ;
    private String OrderCode;
    private AlertDialog.Builder builder;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private SimpleDateFormat format;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root=ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(root.getRoot());
        RegisterOrderReceiver();
        format  = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault());
        builder=new AlertDialog.Builder(this);
        preferences =  getSharedPreferences("Orderinfo", Context.MODE_PRIVATE);
        editor= preferences.edit();
        order=Card.CopyTheCart();
        adapter = new Adapter(order);
        root.Dynamic.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        reference = FirebaseDatabase.getInstance().getReference().child("Orders");
        try{
            for (int i = 0;i<order.size();i++)
            {
                Totalall+=order.get(i).getTotal();
            }
            root.textView8.setText(Totalall+"L.E");
        }catch (Exception exp)
        {
            exp.printStackTrace();
        }
        root.textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(order.size()>0)
                {
                    makeOrder();
                }
                else
                {
                    builder.setMessage("you don't have any order.")
                            .setTitle("Warning")
                            .setIcon(R.drawable.ic_baseline_warning_24);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Order.this,MainAppActivity.class));
                            finish();
                        }
                    }).show();
                }

            }
        });
        root.textView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Order.this,MainAppActivity.class));
                finish();
            }
        });
    }
    public void RegisterOrderReceiver()
    {
        RecieverBroadCast reciver = new RecieverBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.OrderReciever");
        registerReceiver(reciver,filter);
    }
    public void makeOrder()
    {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String FinalDate  = format.format(Calendar.getInstance().getTimeInMillis());
                OrderCode=reference.push().getKey();
                Map<String,Object> info  =  new HashMap<String, Object>();
                info.put("phone",MainActivity.fragmentphone);
                info.put("Accepted","Waiting for Response");
                info.put("Total Price",Totalall);
                info.put("OrderDate",FinalDate);
                info.put("Location",Location.getLocation());
                /*------this part not important and won't effect in the performance---------*/
                editor.putString("OrderCode",OrderCode);
                editor.putString("Accepted","false");
                editor.putString("Total price",String.valueOf(Totalall));
                editor.apply();
                /*--------------------------End of not important part------------------------*/
                reference=reference.child(OrderCode);
                reference.updateChildren(info, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        //Toast.makeText(Order.this,"Your order is sent",Toast.LENGTH_SHORT).show();

                        //we need to optimize this part by Reorder the code. first, send the info of the order
                        //then, wait till this information completely uploaded.
                        //finally, send the order embedded on the information.
                        //we can make that by get the reset of this function here.
                    }
                });
                DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference().child("Orders").child(OrderCode).child("The order");
                reference=reference.child("The order");
                Map<String,Object> coreOrder  =  new HashMap<String, Object>();
                for(int i=0;i<order.size();i++)
                {
                    reference=reference.child(String.valueOf(i+1));
                    coreOrder.put("name",order.get(i).getName());
                    coreOrder.put("number",order.get(i).getNumber());
                    coreOrder.put("note",order.get(i).getNotes());
                    reference.updateChildren(coreOrder, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                        }
                    });
                    reference=reference2;
                }
                Toast.makeText(Order.this,"Your order is sent",Toast.LENGTH_SHORT).show();
                Card.OrderSent();
                order.clear();
                root.textView6.setEnabled(false);
                Intent intent = new Intent("android.intent.action.OrderReciever");
                intent.putExtra("OrderCode",OrderCode);
                sendBroadcast(intent);
                builder.setMessage("Please Wait this may take Several minutes")
                        .setTitle("Wait untill Order accepted")
                        .setIcon(R.drawable.ic_baseline_cloud_done_24);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Order.this,MainAppActivity.class));
                        finish();
                    }
                }).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public class Adapter extends BaseAdapter
    {
        private ArrayList<CardItem> content;
        public Adapter(ArrayList<CardItem> content) {
            this.content = content;
        }

        @Override
        public int getCount() {
            return content.size();
        }

        @Override
        public Object getItem(int position) {
            return content.get(position).getName();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater lay = getLayoutInflater();
            View view  = lay.inflate(R.layout.orderitem,parent,false);
            TextView name = view.findViewById(R.id.orderitemname);
            TextView num = view.findViewById(R.id.orderitemnumber);
            TextView note  = view.findViewById(R.id.orderitemnote);
            TextView total = view.findViewById(R.id.orderitemtotal);
            name.setText(content.get(position).getName());
            num.setText(content.get(position).getNumber().toString());
            note.setText(content.get(position).getNotes());
            total.setText(content.get(position).getTotal().toString());
            return view;
        }
    }
}