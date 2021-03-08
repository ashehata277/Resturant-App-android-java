package com.example.recently;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MenuFragment extends Fragment {
    private ListView menu;
    private ArrayList<MenuItem> source =  new ArrayList<>();
    private DatabaseReference reference;
    private Adapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_menu, container, false);
        menu  = root.findViewById(R.id.meunview);
        ImageView Chicken = (ImageView) root.findViewById(R.id.chicken);
        ImageView Meats = (ImageView) root.findViewById(R.id.meats);
        Chicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Category("Chicken");
            }
        });
        Meats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Category("Meat");
            }
        });
        reference= FirebaseDatabase.getInstance().getReference().child("Menu").child("Chicken");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childDataSnapshot : snapshot.getChildren()) {
                    String name = (String) childDataSnapshot.child("name").getValue();
                    String des = (String) childDataSnapshot.child("Description").getValue();
                    Long price = (Long) childDataSnapshot.child("price").getValue();
                    source.add(new MenuItem(name,des,price));
                }
                adapter = new Adapter(source);
                menu.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return root;
    }
    public void Category(String name)
    {
        source.clear();
        reference= FirebaseDatabase.getInstance().getReference().child("Menu").child(name);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childDataSnapshot : snapshot.getChildren()) {
                    String name = (String) childDataSnapshot.child("name").getValue();
                    String des = (String) childDataSnapshot.child("Description").getValue();
                    Long price = (Long) childDataSnapshot.child("price").getValue();
                    source.add(new MenuItem(name,des,price));
                }
                adapter = new Adapter(source);
                menu.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public class Adapter extends BaseAdapter
    {
        ArrayList<MenuItem> content =  new ArrayList<>();

        public Adapter(ArrayList<MenuItem> content) {
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
            View view= lay.inflate(R.layout.menitem,parent,false);
            TextView name = view.findViewById(R.id.name);
            TextView description = view.findViewById(R.id.des);
            TextView price = view.findViewById(R.id.price);
            ImageView add = view.findViewById(R.id.addtocard);
            name.setText(content.get(position).getName());
            description.setText(content.get(position).getDescription());
            price.setText(content.get(position).getPrice()+"L.E");
            add.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Card.addtoCard(new CardItem(content.get(position).getName(),content.get(position).getDescription(),content.get(position).getPrice(), (long) 1,"None",content.get(position).getPrice()));
                    Toast.makeText(getActivity(),"Added to Cart",Toast.LENGTH_LONG).show();
                }
            });
            return view;
        }
    }
}