package com.example.recently;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Card extends AppCompatActivity {
    private ListView card;
    private static  ArrayList<CardItem> source = new ArrayList<CardItem>();
    private TextView clearCart;
    private TextView SendOrder;
    private Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        card = findViewById(R.id.cardList);
        SendOrder = (TextView)findViewById(R.id.send);
        adapter = new Adapter(source);
        card.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        clearCart = (TextView) findViewById(R.id.clear);
        clearCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                source.clear();
                adapter.notifyDataSetChanged();
            }
        });
        SendOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Card.this,Location.class));
                finish();
            }
        });
    }
    public static ArrayList<CardItem> CopyTheCart()
    {
        return source;
    }
    public static void addtoCard(CardItem newItem)
    {
        source.add(newItem);
    }
    public static void OrderSent()
    {
        source.clear();
    }
    public class Adapter extends BaseAdapter
    {
        private ArrayList <CardItem> content;
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
            View view  = lay.inflate(R.layout.carditem,parent,false);
            TextView name = view.findViewById(R.id.name);
            TextView description = view.findViewById(R.id.des);
            TextView price = view.findViewById(R.id.totalprice);
            TextView oneprice = view.findViewById(R.id.piceprice);
            EditText note = view.findViewById(R.id.Notes);
            EditText num = view.findViewById(R.id.number);
            ImageView delete= (ImageView)view.findViewById(R.id.DeleteImage);
            name.setText(content.get(position).getName());
            description.setText(content.get(position).getDescription());
            oneprice.setText(content.get(position).getPrice()+"L.E");
            /*initiate the number with one number */
            num.setText("1");
            Editable number =num.getText();
            Long one = content.get(position).getPrice();
            Long total =  Long.parseLong(String.valueOf(number))*one;
            price.setText("Total Price is "+total +"L.E");
            /*to handle changes from user on every element*/
           num.addTextChangedListener(new TextWatcher() {
               @Override
               public void beforeTextChanged(CharSequence s, int start, int count, int after) {
               }
               @Override
               public void onTextChanged(CharSequence s, int start, int before, int count) {
               }
               @Override
               public void afterTextChanged(Editable s) {
                   try{
                       /*when the user change number display the calculations and store changes in source */
                       Long one = content.get(position).getPrice();
                       Long total =  Long.parseLong(String.valueOf(num.getText()))*one;
                       price.setText("Total"+total +"L.E");
                       source.get(position).setNumber(Long.parseLong(String.valueOf(num.getText())));
                       source.get(position).setTotal(total);
                   }
                   catch (Exception exp)
                   {
                       exp.printStackTrace();
                   }
               }
           });
           /*handle change any notes from user*/
           note.addTextChangedListener(new TextWatcher() {
               @Override
               public void beforeTextChanged(CharSequence s, int start, int count, int after) {
               }
               @Override
               public void onTextChanged(CharSequence s, int start, int before, int count) {
               }
               @Override
               public void afterTextChanged(Editable s) {
                   source.get(position).setNotes(String.valueOf(s));
               }
           });
           delete.setOnClickListener(new View.OnClickListener()
           {
               @Override
               public void onClick(View v) {
                   source.remove(position);
                   adapter.notifyDataSetChanged();
                   Toast.makeText(Card.this,"The Item is Deleted",Toast.LENGTH_SHORT).show();

               }
           });
            return view;
        }
    }
}